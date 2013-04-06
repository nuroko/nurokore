package nuroko.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;
import nuroko.core.IModule;
import nuroko.core.ITrainable;
import nuroko.core.Util;

public class NeuralNet extends ALayerStack implements ITrainable {
	
	private final int layerCount;
	private final AWeightLayer[] layers;
	private final Vector[] data;
	private final Vector[] grad;
	private final AVector parameters;
	private final AVector gradient;
	private final Op[] layerOps;
	
	public NeuralNet(AWeightLayer... layers) {
		this(layers, Op.LOGISTIC);
	}
	
	public NeuralNet(AWeightLayer[] layers, Op outputOp) {
		this(layers, Op.TANH, Op.LOGISTIC);
	}
	
	public NeuralNet(AWeightLayer[] layers, Op hiddenOp, Op outputOp) {
		layerCount=layers.length;
		this.layers=layers.clone();
		
		layerOps=new Op[layerCount];
		for (int i=0; i<layerCount-1; i++) {
			layerOps[i]=hiddenOp;
		}
		layerOps[layerCount-1]=outputOp;
		
		data=new Vector[layerCount+1];
		grad=new Vector[layerCount+1];
		data[0]=Vector.createLength(layers[0].getInputLength());
		grad[0]=Vector.createLength(layers[0].getInputLength());
		for (int i=0; i<layerCount; i++) {
			data[i+1]=Vector.createLength(layers[i].getOutputLength());
			grad[i+1]=Vector.createLength(layers[i].getOutputLength());
		}
		
		AVector params=layers[0].getParameters();
		for (int i=1; i<layerCount; i++) {
			params=params.join(layers[i].getParameters());
		}
		parameters=params;
		
		AVector g=layers[0].getGradient();
		for (int i=1; i<layerCount; i++) {
			g=g.join(layers[i].getGradient());
		}
		gradient=g;
	}
	
	@Override 
	public List<IModule> getComponents() {
		ArrayList<IModule> al=new ArrayList<IModule>();
		for (IModule m: layers) {
			al.add(m);
		}
		return al;
	}
	
	public NeuralNet getInverse() {
		AWeightLayer[] newLayers=new AWeightLayer[layerCount];
		for (int i=0; i<layerCount; i++) {
			newLayers[i]=this.layers[layerCount-1-i].getInverse();
		}
		return new NeuralNet(newLayers);
	}
	
	// (re) initialises the network with random weights
	public void initRandom() {
		for (AWeightLayer wl: layers) {
			wl.initRandom();
		}
	}

	@Override
	public void train(AVector input, AVector target) {
		assert(getOutputLength()==target.length());
		think(input,null);
		grad[layerCount].set(target);
		grad[layerCount].sub(data[layerCount]);
		backpropGradient(1.0,true);
	}
	
	@Override
	public void trainGradient(AVector input,
			AVector outputGradient, AVector inputGradient, double factor,boolean skipTopDerivative) {
		assert(getInputLength()==input.length());
		think(input,null);
		grad[layerCount].set(outputGradient);
		backpropGradient(factor,skipTopDerivative);
		if (inputGradient!=null) {
			inputGradient.add(grad[0]);
		}
	}
	
	@Override
	public void trainGradient(AVector gradient, double factor) {
		grad[layerCount].set(gradient);
		backpropGradient(factor,false);
	}

	private void backpropGradient(double factor,boolean skipTopDerivative) {
		for (int i=layerCount-1; i>=0; i--) {
			// clear the input gradient
			if (i>0) grad[i].fill(0.0);
			
			Op op=(skipTopDerivative&&(i==layerCount-1))?Op.LINEAR:getLayerOp(i);
			Util.scaleByDerivative(op,data[i+1],grad[i+1]);
				
			// backprop on current layer
			layers[i].trainGradient(data[i], grad[i+1], grad[i],factor);
		}
	}
	
	public Op getLayerOp(int i) {
		return layerOps[i];
	}

	@Override
	public void thinkInternal(AVector output) {
		for (int i=0; i<layerCount; i++) {
			layers[i].think(data[i], data[i+1]);
			getLayerOp(i).applyTo(data[i+1].getArray());
		}
		if (output!=null) output.set(data[layerCount]);
	}
	
	@Override
	public AWeightLayer getLayer(int i) {
		return layers[i];
	}

	@Override
	public int getInputLength() {
		return data[0].length();
	}

	@Override
	public int getOutputLength() {
		return data[layerCount].length();
	}

	@Override
	public int getParameterLength() {
		return parameters.length();
	}

	@Override
	public AVector getParameters() {
		return parameters;
	}
	
	@Override
	public AVector getGradient() {
		return gradient;
	}

	public List<AWeightLayer> getLayers() {
		return Arrays.asList(layers);
	}
	
	public AVector getInputSignal() {
		return grad[0];
	}

	public AVector getOutputSignal() {
		return grad[layerCount];
	}

	public AVector getOutput() {
		return data[layerCount];
	}
	
	public AVector getInput() {
		return data[0];
	}
	
	@Override
	public AVector getData(int i) {
		return data[i];
	}
	
	public NeuralNet clone() {
		AWeightLayer[] newlayers=new AWeightLayer[layerCount];
		for (int i=0; i<layerCount; i++) {
			newlayers[i]=layers[i].clone();
		}
		NeuralNet ns=new NeuralNet(newlayers);
		return ns;
	}

	@Override
	public int getLayerCount() {
		return layerCount;
	}

	@Override
	public AVector getInputGradient() {
		return grad[0];
	}


}

package nuroko.module.layers;

import java.util.Collections;
import java.util.List;

import nuroko.core.IModule;
import nuroko.module.AWeightLayer;

import mikera.indexz.Index;
import mikera.indexz.Indexz;
import mikera.matrixx.AMatrix;
import mikera.matrixx.impl.VectorMatrixMN;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;

/**
 * Fully connected weight layer
 * 
 * @author Mike
 */
public final class FullWeightLayer extends AWeightLayer {
	private final Vector bias;
	private final Vector biasGradient;
	private final Vector[] weights;
	private final Vector[] weightGradients;
	private final AVector parameters;
	private final AVector gradient;
	
	public FullWeightLayer(int inputLength, int outputLength) {
		super(inputLength,outputLength);
		
		bias=Vector.createLength(outputLength);
		weights=new Vector[outputLength];
		
		AVector params=bias;
		for (int j=0; j<outputLength; j++) {
			Vector wts=Vector.createLength(inputLength);
			weights[j]=wts;			
			params=params.join(wts);
		}
		parameters=params;
		
		biasGradient=Vector.createLength(outputLength);
		weightGradients=new Vector[outputLength];
		
		AVector g=biasGradient;
		for (int j=0; j<outputLength; j++) {
			Vector grd=Vector.createLength(inputLength);
			weightGradients[j]=grd;
			g=g.join(grd);
		}
		gradient=g;
		assert(gradient.length()==parameters.length());
	}
	
	
	@Override
	public AVector getParameters() {
		return parameters;
	}
	
	@Override 
	public List<IModule> getComponents() {
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public int getParameterLength() {
		return parameters.length();
	}

	@Override
	public void think(AVector input, AVector output) {
		assert(inputLength==input.length());
		assert(outputLength==output.length());
		for (int j=0; j<outputLength; j++) {
			double val=bias.get(j);
			val+=weights[j].dotProduct(input);
			output.set(j,val);
		}
	}

	@Override
	public AVector getGradient() {
		return gradient;
	}

	@Override
	public void trainGradient(AVector input,
			AVector outputGradient, AVector inputGradient, double factor) {
		biasGradient.addMultiple(outputGradient,factor);
		for (int j=0; j<outputLength; j++) {
			double grad=outputGradient.get(j);
			weightGradients[j].addMultiple(input, grad*factor);
			inputGradient.addMultiple(weights[j],grad);
		}
	}
	
	@Override 
	public FullWeightLayer clone() {
		FullWeightLayer wl=new FullWeightLayer(getInputLength(), getOutputLength());
		wl.getParameters().set(this.getParameters());
		return wl;
	}

	@Override
	public int getLinkCount(int outputIndex) {
		return inputLength;
	}

	@Override
	public double getLinkWeight(int outputIndex, int number) {
		return weights[outputIndex].array[number];
	}

	@Override
	public int getLinkSource(int outputIndex, int number) {
		return number;
	}
	
	@Override
	public void initRandom() {
		Vectorz.fillGaussian(bias, 0.0, BIAS_INITIAL_SCALE* INITIAL_WEIGHT_SCALE);
		for (Vector v:weights) {
			Vectorz.fillGaussian(v, 0.0, INITIAL_WEIGHT_SCALE/(Math.sqrt(v.length())));
		}
	}
	
	@Override public FullWeightLayer getInverse() {
		FullWeightLayer wl=getInverseStructure();
		for (int j=0; j<outputLength; j++) {
			AVector owts=getSourceWeights(j);
			for (int i=0; i<inputLength; i++) {
				AVector wts=wl.getSourceWeights(i);
				wts.set(j,owts.get(i));
			}
		}
		return wl;
	}
	
	public FullWeightLayer getInverseStructure() {
		FullWeightLayer wl=new FullWeightLayer(getOutputLength(),getInputLength());
		return wl;
	}

	@Override
	public Index getSourceIndex(int outputIndex) {
		return Indexz.createSequence(inputLength);
	}

	@Override
	public AVector getSourceWeights(int outputIndex) {
		return weights[outputIndex];
	}


	@Override
	public AMatrix asMatrix() {
		return VectorMatrixMN.wrap(weights);
	}
}

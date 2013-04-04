package nuroko.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nuroko.core.ITrainable;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

public class NetworkStack extends CompoundLayerStack<ALayerStack> implements ITrainable {
	
	private final Vector[] data;
	private final Vector[] grad;

	public NetworkStack(ALayerStack... components) {
		this(Arrays.asList(components));
	}
	
	public NetworkStack(List<ALayerStack> components) {
		super(components);
		data=new Vector[componentCount+1];
		grad=new Vector[componentCount+1];
		
		int il=getComponent(0).getInputLength();
		data[0]=Vector.createLength(il);
		grad[0]=Vector.createLength(il);
		for (int i=0; i<componentCount; i++) {
			data[i+1]=Vector.createLength(getComponent(i).getOutputLength());
			grad[i+1]=Vector.createLength(getComponent(i).getOutputLength());
		}
	}

	@Override
	public int getInputLength() {
		return data[0].length();
	}

	@Override
	public int getOutputLength() {
		return data[componentCount].length();
	}

	@Override
	public void think(AVector input, AVector output) {
		data[0].set(input);
		for (int i=0; i<componentCount; i++) {
			getComponent(i).think(data[i],data[i+1]);
		}		
		if (output!=null) {
			output.set(data[componentCount]);
		}
	}

	@Override
	public int getLayerCount() {
		int result=0;
		for (ALayerStack c:components) {
			result+=c.getLayerCount();
		}
		return result;
	}

	@Override
	public AWeightLayer getLayer(int i) {
		for (ALayerStack c:components) {
			int lc=c.getLayerCount();
			if (i<lc) return c.getLayer(i);
			i-=lc;
		}
		throw new IndexOutOfBoundsException("Invalid index:" + i);
	}

	@Override
	public AVector getData(int i) {
		for (ALayerStack c:components) {
			int lc=c.getLayerCount();
			if (i<lc) return c.getData(i);
			i-=lc;
		}
		throw new IndexOutOfBoundsException("Invalid index:" + i);
	}

	@Override
	public NetworkStack clone() {
		ArrayList<ALayerStack> al=new ArrayList<ALayerStack>();
		for (ALayerStack c:components) {
			al.add(c.clone());
		}
		return new NetworkStack(al);
	}

	@Override
	public void train(AVector input, AVector target) {
		assert(getOutputLength()==target.length());
		think(input,null);
		grad[componentCount].set(target);
		grad[componentCount].sub(data[componentCount]);
		trainGradient(input,grad[componentCount],null,1.0,true);
	}
	
	@Override
	public void trainGradient(AVector input,
			AVector outputGradient, AVector inputGradient, double factor,boolean skipTopDerivative) {
		assert(getInputLength()==input.length());
		grad[componentCount].set(outputGradient);
		for (int i=componentCount-1; i>=0; i--) {
			grad[i].fill(0.0);
			getComponent(i).trainGradient(data[i], grad[i+1], grad[i], factor,skipTopDerivative&&(i==(componentCount-1)));
		}
		if (inputGradient!=null) {
			inputGradient.add(grad[0]);
		}
	}

	@Override
	public AVector getInputGradient() {
		return grad[0];
	}
}

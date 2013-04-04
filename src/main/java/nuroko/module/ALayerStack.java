package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;

/**
 * Abstract base class representing a stack of WeightLayer components
 * 
 * @author Mike
 */
public abstract class ALayerStack extends AComponent {
	
	public abstract int getLayerCount();
	
	public abstract AWeightLayer getLayer(int i);
	
	public abstract AVector getData(int i);
	
	public AVector getOutput() {
		return getData(getLayerCount());
	}
	
	public AVector getInput() {
		return getData(0);
	}
	
	@Override 
	public void setInput(AVector input) {
		getInput().set(input);
	}
	
	public List<AWeightLayer> getLayers() {
		int lc=getLayerCount();
		ArrayList<AWeightLayer> al=new ArrayList<AWeightLayer>();
		for (int i=0; i<lc; i++) {
			al.add(getLayer(i));
		}
		return al;
	}

	
	public abstract ALayerStack clone();
	
	@Override
	public int getParameterLength() {
		return getParameters().length();
	}
	
	public abstract void train(AVector input, AVector target);

	/**
	 * Trains with a direct gradient. Assumes think has been called immediately prior to set any intermediate values.
	 */
	public abstract void trainGradient(AVector input, AVector outputGradient,
			AVector inputGradient, double factor, boolean skipTopDerivative);
	
}

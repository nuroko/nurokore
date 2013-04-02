package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.*;

public abstract class AThinkStack implements  IThinker, IParameterised {
	
	public abstract int getLayerCount();
	
	public abstract AWeightLayer getLayer(int i);
	
	public abstract AVector getData(int i);
	
	public AVector getOutput() {
		return getData(getLayerCount());
	}
	
	public AVector getInput() {
		return getData(0);
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

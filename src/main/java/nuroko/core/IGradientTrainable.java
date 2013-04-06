package nuroko.core;

import mikera.vectorz.AVector;

public interface IGradientTrainable  {

	/**
	 * Trains with a output gradient, incrementing inputGradient and 
	 * accumulated gradient for parameters.
	 * 
	 * @param gradient
	 * @param factor TODO
	 */
	public void trainGradient(AVector gradient, double factor);
}

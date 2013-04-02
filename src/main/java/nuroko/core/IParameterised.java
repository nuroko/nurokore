package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface representing a parameterised model. The model is assumed to contain two vectors:
 * - A parameter vector
 * - An accumulated gradient vector
 * 
 * Both vectors are mutable. They can be cleared, randomized etc. as desired. 
 * 
 * Altering the parameter vectors is *not thread safe*. It will not throw an exception, 
 * however results may be unpredictable if the model is being concurrently used by another thread.
 * 
 * @author Mike
 *
 */
public interface IParameterised extends IModule {
	/**
	 * Returns the length of the parameter vector for this model
	 * @return
	 */
	public int getParameterLength();
	
	/**
	 * Return an AVector referring to the parameters in the model. Expected to be mutable.
	 * 
	 * @return
	 */
	public AVector getParameters();

	/**
	 * Return an AVector referencing the accumulated gradient in this model
	 * @return
	 */
	public AVector getGradient();
	
	@Override
	public IParameterised clone();
}

package nuroko.core;

import nuroko.module.loss.LossFunction;
import mikera.vectorz.AVector;

/**
 * Represents a thinker that can be trained in a supervised fashion using a target output
 * 
 * @author Mike
 *
 */
public interface ITrainable extends IThinker, IParameterised {

	/**
	 * Trains towards a target value. Performs thinking as required.
	 * 
	 * Updates parameter gradient
	 * Overwrites outputGradient and inputGradient in components
	 */
	public void train(AVector input, AVector target);

	/**
	 * Trains towards a target value using a specified loss function.
	 * 
	 * Updates parameter gradient
	 * Overwrites outputGradient and inputGradient in components
	 */
	public void train(AVector input, AVector target, LossFunction loss, double factor);

	public void thinkInternalTraining();

	@Override
	public ITrainable clone();
}

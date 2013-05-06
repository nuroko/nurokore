package nuroko.module.loss;

import nuroko.core.IComponent;
import mikera.vectorz.AVector;

public abstract class LossFunction {
	/**
	 * Calculates a loss function for the given output and target
	 * 
	 * Overwrites the gradientOut vector with the error derivative.
	 * 
	 * @param output
	 * @param target
	 * @param gradientOut output paremeter (to be overwritten)
	 */
	public abstract void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut);

	/**
	 * Calculates an error derivative, storing it in the output gradient of the given component
	 */
	public void calculateErrorDerivative(AVector output, AVector target, IComponent comp) {
		calculateErrorDerivative(output,target,comp.getOutputGradient());
	}
}

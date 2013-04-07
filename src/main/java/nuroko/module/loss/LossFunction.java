package nuroko.module.loss;

import nuroko.core.IComponent;
import mikera.vectorz.AVector;

public abstract class LossFunction {
	public abstract void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut);

	public void calculateErrorDerivative(AVector output, AVector target,
			IComponent comp) {
		calculateErrorDerivative(output,target,comp.getOutputGradient());
	}
}

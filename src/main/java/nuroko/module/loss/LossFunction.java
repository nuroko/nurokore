package nuroko.module.loss;

import mikera.vectorz.AVector;

public abstract class LossFunction {
	public abstract void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut);
}

package nuroko.module.loss;

import mikera.vectorz.AVector;

public class SquaredErrorLoss extends LossFunction {
	public SquaredErrorLoss INSTANCE=new SquaredErrorLoss();
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target,
			AVector gradientOut) {
		gradientOut.set(target);
		gradientOut.sub(output);
		gradientOut.scale(2.0);
	}

}

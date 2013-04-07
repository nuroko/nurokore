package nuroko.module.loss;

import mikera.vectorz.AVector;

public class AbsoluteErrorLoss extends LossFunction {
	public static AbsoluteErrorLoss INSTANCE=new AbsoluteErrorLoss();
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target,
			AVector gradientOut) {
		gradientOut.set(target);
		gradientOut.sub(output);
		gradientOut.signum();
	}

}

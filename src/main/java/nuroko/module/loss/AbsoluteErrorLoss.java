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
	
	@Override
	public double calculateError(AVector output, AVector target) {
		int len=output.length();
		double ce=0;
		for (int i=0; i<len; i++) {
			double p=target.get(i);
			double q=output.get(i);
			ce+=Math.abs(p-q);
		}
		return ce;
	}

}

package nuroko.module.loss;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class SquaredErrorLoss extends LossFunction {
	public static SquaredErrorLoss INSTANCE=new SquaredErrorLoss();
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target,
			AVector gradientOut) {
		gradientOut.set(target);
		gradientOut.sub(output);
		gradientOut.scale(2.0);
	}

	@Override
	public double calculateError(AVector output, AVector target) {
		return Vectorz.averageSquaredDifference(output, target);
	}

}

package nuroko.module.loss;

import mikera.vectorz.AVector;

public class CrossEntropyLoss extends LossFunction {
	public static CrossEntropyLoss INSTANCE=new CrossEntropyLoss();
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut) {
		int n=target.length();
		for (int i=0; i<n; i++) {
			double y=output.get(i);
			double t=target.get(i);
			double k=y*(1.0-y);
			if (k!=0.0) {
				gradientOut.set(i,(t-y)/k);
			} else {
				gradientOut.set(i,0);
			}
		}
	}
}

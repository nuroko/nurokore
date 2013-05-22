package nuroko.module.loss;

import nuroko.core.NurokoException;
import mikera.vectorz.AVector;

public class CrossEntropyLoss extends LossFunction {
	public static CrossEntropyLoss INSTANCE=new CrossEntropyLoss();
	
	public static final double BOUND=0.000000000001;
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut) {
		if (output.length()!=target.length()) throw new NurokoException("Target / output size mismtach");
		int n=target.length();
		for (int i=0; i<n; i++) {
			double y=output.get(i);
			double t=target.get(i);
			double k=Math.max(BOUND, y*(1.0-y));
			if (k!=0.0) {
				gradientOut.set(i,(t-y)/k);
			} else {
				gradientOut.set(i,0);
			}
		}
	}
}

package nuroko.module.loss;

import nuroko.core.NurokoException;
import mikera.vectorz.AVector;

public class CrossEntropyLoss extends LossFunction {
	public static CrossEntropyLoss INSTANCE=new CrossEntropyLoss();
	
	public static final double BOUND=0.000000000001;
	
	@Override
	public void calculateErrorDerivative(AVector output, AVector target, AVector gradientOut) {
		int olen=output.length();
		int tlen=target.length();
		if (olen!=tlen) throw new NurokoException("Target / output size mismtach: " +tlen +" vs. "+olen);
		for (int i=0; i<tlen; i++) {
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

	@Override
	public double calculateError(AVector output, AVector target) {
		int len=output.length();
		double ce=0;
		for (int i=0; i<len; i++) {
			double p=target.get(i);
			double q=output.get(i);
			ce-=Math.log(p*q+(1-p)*(1-q));
		}
		return ce;
	}
}

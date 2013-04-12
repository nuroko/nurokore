package nuroko.module.ops;

import mikera.vectorz.Op;

public class ScaledLogistic extends Op {

	public static final ScaledLogistic INSTANCE=new ScaledLogistic();
	
	static double scaledLogisticFunction(double a) {
		double ea=Math.exp(-4.0*a);
		double df=(1/(1.0f+ea));
		if (Double.isNaN(df)) return (a>0)?1:0;
		return df;
	}
	
	private static double inverseLogistic (double a) {
		if (a>=1) return 200;
		if (a<=0) return -200;
		double ea=a/(1.0-a);
		return 0.25*Math.log(ea);
	}
	
	@Override
	public double apply(double x) {
		return scaledLogisticFunction(x);
	}
	
	@Override
	public double applyInverse(double y) {
		return inverseLogistic(y);
	}
	
	@Override
	public void applyTo(double[] data, int start,int length) {
		for (int i=0; i<length; i++) {
			data[i+start]=scaledLogisticFunction(data[i+start]);
		}	
	}
	
	@Override
	public boolean hasDerivative() {
		return true;
	}
	
	@Override
	public double derivativeForOutput(double y) {
		return 4*y*(1-y);
	}
	
	@Override
	public double derivative(double x) {
		double y=scaledLogisticFunction(x);
		return 4*y*(1-y);
	}

	@Override
	public double minValue() {
		return 0.0;
	}

	@Override
	public double maxValue() {
		return 1.0;
	}
	
	@Override
	public double averageValue() {
		return 0.5;
	}

}

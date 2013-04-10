package nuroko.module;

import mikera.util.Rand;

public class Dropout extends AOperationComponent {
	private double dropoutRate=0.5;
	private final boolean[] dropped;

	public Dropout(int length) {
		super(length);
		dropped=new boolean[length];
	}

	public Dropout(int length, double dropoutRate) {
		this(length);
		this.dropoutRate=dropoutRate;
	}

	@Override
	public void thinkInternal() {
		output.set(input);
	}
	
	@Override
	public void thinkInternalTraining() {
		output.set(input);
		if (dropoutRate>0.0) {
			double scaleFactor=1.0/dropoutRate;
			double[] dt=output.getArray();
			for (int i=0; i<dt.length; i++) {
				boolean drop=Rand.chance(dropoutRate);
				if (Rand.chance(dropoutRate)) {
					dt[i]=0.0;
				} else {
					dt[i]*=scaleFactor;
				}
				dropped[i]=drop;
			}
		}
	}
	
	@Override
	public void trainGradientInternal(double factor) {
		inputGradient.set(outputGradient);
		double[] ig=inputGradient.getArray();
		for (int i=0; i<length; i++) {
			if (dropped[i]) ig[i]=0.0;
		}
	}

	@Override
	public Dropout clone() {
		return new Dropout(getInputLength(),dropoutRate);
	}

}

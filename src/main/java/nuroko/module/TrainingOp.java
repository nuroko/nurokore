package nuroko.module;

import mikera.vectorz.Op;

public class TrainingOp extends AOperationComponent {
	private final Op op;
	
	public TrainingOp(int length, Op  op) {
		super(length);
		this.op = op;
	}

	@Override
	public void thinkInternal() {
		// identity function while thinking normally
		output.set(input);
	}
	
	@Override
	public void thinkInternalTraining() {
		// apply op only when training
		output.set(input);
		op.applyTo(output);
	}

	@Override
	public void trainGradientInternal(double factor) {
		double[] ov=output.getArray();
		double[] og=outputGradient.getArray();
		double[] ig=outputGradient.getArray();
		for (int i=0; i<length; i++) {
			ig[i]=op.derivativeForOutput(ov[i])*og[i];
		}
	}
	
	@Override
	public boolean hasDifferentTrainingThinking() {
		return true;
	}

	@Override
	public TrainingOp clone() {
		return new TrainingOp(length,op);
	}
	
	@Override public String toString() {
		return length+":"+length+" "+op.toString();
	}

}

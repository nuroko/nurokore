package nuroko.module;

import nuroko.core.Components;
import nuroko.module.loss.LossFunction;
import mikera.vectorz.Op;

public class ThinkingOp extends AOperationComponent {
	private final Op op;
	
	public ThinkingOp(int length, Op  op) {
		super(length);
		this.op = op;
	}

	@Override
	public void thinkInternal() {
		// apply op only when thinking
		output.set(input);
		op.applyTo(output);
	}
	
	@Override
	public void thinkInternalTraining() {
		output.set(input);
	}

	@Override
	public void trainGradientInternal(double factor) {
		inputGradient.set(outputGradient);
	}
	
	@Override
	public LossFunction getDefaultLossFunction() {
		return Components.defaultLossFunction(op);
	}
	
	@Override
	public boolean hasDifferentTrainingThinking() {
		return true;
	}

	@Override
	public ThinkingOp clone() {
		return new ThinkingOp(length,op);
	}
	
	@Override public String toString() {
		return length+":"+length+" "+op.toString();
	}

}

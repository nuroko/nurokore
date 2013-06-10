package nuroko.module;

import nuroko.core.IComponent;

public class Identity extends AOperationComponent {

	public Identity(int length) {
		super(length);
	}

	@Override
	public void thinkInternal() {
		getOutput().set(getInput());
	}

	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}

	@Override
	public AOperationComponent clone() {
		return this;
	}
	
	@Override
	public IComponent getDownStack() {
		return this;
	}
	
	@Override
	public boolean isSynthesiser() {
		return false;
	}

	@Override
	public void trainGradientInternal(double factor) {
		getInputGradient().set(getOutputGradient());
	}
}

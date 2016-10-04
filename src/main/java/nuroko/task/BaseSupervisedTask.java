package nuroko.task;

import mikera.vectorz.AVector;

public abstract class BaseSupervisedTask extends AbstractTask {
	protected final int inputLength;
	protected final int outputLength;
	
	public BaseSupervisedTask(int inputLength, int outputLength) {
		this.inputLength=inputLength;
		this.outputLength=outputLength;
	}
	
	
	@Override
	public long getExampleCount() {
		return Long.MAX_VALUE;
	}

	
	@Override
	public int getInputLength() {
		return inputLength;
	}

	@Override
	public int getOutputLength() {
		return outputLength;
	}

	@Override
	public abstract void getInput(AVector inputOut);
	
	@Override
	public abstract void getTarget(AVector input, AVector targetOut);
	
}

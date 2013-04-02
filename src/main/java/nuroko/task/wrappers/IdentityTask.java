package nuroko.task.wrappers;

import mikera.vectorz.AVector;
import nuroko.core.ITask;
import nuroko.task.BaseSupervisedTask;

public class IdentityTask extends BaseSupervisedTask {
	protected final ITask task;
	
	public IdentityTask(ITask task) {
		super(task.getInputLength(),task.getInputLength());
		this.task=task;
	}

	@Override
	public void reset() {
		task.reset();
	}
	
	@Override
	public void getInput(AVector inputOut) {
		task.getInput(inputOut);
	}

	@Override
	public void getTarget(AVector input, AVector targetOut) {
		targetOut.set(input);
	}

}

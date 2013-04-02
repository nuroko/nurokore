package nuroko.task;

import nuroko.core.ITask;
import nuroko.core.NurokoException;
import mikera.vectorz.AVector;

public abstract class AbstractTask implements ITask {
	public AbstractTask() {
		// nothing needed
	}

	@Override
	public void reset() {
		// Do nothing by default
		
	}
	

	@Override
	public abstract void getInput(AVector inputOut);

	@Override
	public void getTarget(AVector input, AVector targetOut) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getEvaluation(AVector input, AVector output) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isComplete() {
		return true;
	}
	
	public AbstractTask clone() {
		try {
			return (AbstractTask) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new NurokoException(e);
		}
	}

}

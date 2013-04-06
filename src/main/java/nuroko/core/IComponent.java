package nuroko.core;

import nuroko.core.IInputState;

public interface IComponent extends ITrainable, IGradientTrainable, IInputState, IOutputState {

	public IComponent clone();

	public IInputState getInputState();
	
	public void thinkInternal();
	
	public void thinkInternalTraining();

	public void trainGradientInternal(double factor);

	public boolean isStochastic();
}

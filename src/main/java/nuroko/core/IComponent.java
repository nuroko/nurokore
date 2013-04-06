package nuroko.core;

import nuroko.core.IInputState;
import mikera.vectorz.AVector;

public interface IComponent extends IThinker, IParameterised, IGradientTrainable, IInputState, IOutputState {

	public IComponent clone();

	public IInputState getInputState();
	
	public void thinkInternal();
}

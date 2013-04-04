package nuroko.core;

import nuroko.core.IInputState;
import mikera.vectorz.AVector;

public interface IComponent extends IInputState, IThinker, IParameterised {

	public IComponent clone();
	
	public void thinkInternal(AVector output);

	public IInputState getInputState();
}

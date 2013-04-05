package nuroko.core;

import nuroko.core.IInputState;
import mikera.vectorz.AVector;

public interface IComponent extends IThinker, IParameterised, IGradientTrainable {

	public IComponent clone();
	
	public void thinkInternal(AVector output);

	public IInputState getInputState();
}

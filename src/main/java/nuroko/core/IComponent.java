package nuroko.core;

import mikera.vectorz.AVector;

public interface IComponent extends IInputState, IThinker, IParameterised {

	public IComponent clone();

	public void setInput(AVector input);
	
	public void think(AVector output);
}

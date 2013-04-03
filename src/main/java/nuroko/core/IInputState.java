package nuroko.core;

import mikera.vectorz.AVector;

public interface IInputState  {

	public AVector getInput();
	
	public AVector getInputGradient();
}

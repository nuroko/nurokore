package nuroko.core;

import mikera.vectorz.AVector;

public interface IInputState extends IInput {

	public AVector getInput();
	
	public AVector getInputGradient();
}

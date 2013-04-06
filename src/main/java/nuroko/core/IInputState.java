package nuroko.core;

import mikera.vectorz.AVector;

public interface IInputState extends IInput {

	public void setInput(AVector inputValues);
	
	public AVector getInput();
	
	public AVector getInputGradient();
}

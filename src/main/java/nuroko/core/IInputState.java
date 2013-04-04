package nuroko.core;

import mikera.vectorz.AVector;

public interface IInputState extends IInput {

	public void setInput(AVector input);
	
	public AVector getInput();
	
	public AVector getInputGradient();
}

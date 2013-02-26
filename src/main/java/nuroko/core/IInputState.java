package nuroko.core;

import mikera.vectorz.AVector;

public interface IInputState extends IState {

	public AVector getInput();
	
	public AVector getInputGradient();
}

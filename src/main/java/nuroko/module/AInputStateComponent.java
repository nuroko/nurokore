package nuroko.module;

import nuroko.core.IInputState;
import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

/**
 * Component with input state
 * 
 * @author Mike
 *
 */
public abstract class AInputStateComponent extends AComponent {

	private final AVector input;
	private final AVector inputGradient;
	
	public AInputStateComponent (int inputLength) {
		input=Vectorz.newVector(inputLength);
		inputGradient=Vectorz.newVector(inputLength);
	}
	
	@Override 
	public IInputState getInputState() {
		return this;
	}
	
	@Override 
	public AVector getInput() {
		return input;
	}
	
	@Override
	public void setInput(AVector input) {
		if (this.input==input) return;
		assert(input.length()==this.input.length());
		this.input.set(input);
	}
	
	@Override 
	public AVector getInputGradient() {
		return inputGradient;
	}
}

package nuroko.module;

import nuroko.core.IInputState;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

/**
 * Component with input state
 * 
 * @author Mike
 *
 */
abstract class AStateComponent extends AComponent {

	private final Vector input;
	private final Vector inputGradient;
	private final Vector output;
	private final Vector outputGradient;
	
	public AStateComponent (int inputLength, int outputLength) {
		input=Vector.createLength(inputLength);
		inputGradient=Vector.createLength(inputLength);
		output=Vector.createLength(outputLength);
		outputGradient=Vector.createLength(outputLength);
	}
	
	@Override 
	public IInputState getInputState() {
		return this;
	}
	
	@Override 
	public Vector getInput() {
		return input;
	}
	
	@Override
	public Vector getOutput() {
		return output;
	}

	@Override
	public Vector getOutputGradient() {
		return outputGradient;
	}
	
	@Override
	public void setInput(AVector input) {
		if (this.input==input) return;
		assert(input.length()==this.input.length());
		this.input.set(input);
	}
	
	@Override 
	public Vector getInputGradient() {
		return inputGradient;
	}
}

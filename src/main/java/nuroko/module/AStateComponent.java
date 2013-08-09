package nuroko.module;

import java.util.Collections;
import java.util.List;

import nuroko.core.IComponent;
import nuroko.core.IInputState;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

/**
 * A basic component with input and output state.
 * 
 * Designed to be extended to implement new components
 * 
 * @author Mike
 *
 */
abstract class AStateComponent extends AComponent {

	protected final Vector input;
	protected final Vector inputGradient;
	protected final Vector output;
	protected final Vector outputGradient;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IComponent> getComponents() {
		return Collections.EMPTY_LIST;
	}
}

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
abstract class AStateComponent extends AComponent {

	private final AVector input;
	private final AVector inputGradient;
	private final AVector output;
	private final AVector outputGradient;
	
	public AStateComponent (int inputLength, int outputLength) {
		input=Vectorz.newVector(inputLength);
		inputGradient=Vectorz.newVector(inputLength);
		output=Vectorz.newVector(outputLength);
		outputGradient=Vectorz.newVector(outputLength);
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
	public AVector getOutput() {
		return output;
	}

	@Override
	public AVector getOutputGradient() {
		return outputGradient;
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

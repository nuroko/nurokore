package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;

public abstract class AComponent implements IComponent {

	@Override
	public void think(AVector input, AVector output) {
		setInput(input);
		thinkInternal();
		if (output!=null) {
			output.set(getOutput());
		}
	}
	
	
	@Override 
	public void setInput(AVector inputValues) {
		getInput().set(inputValues);
	}
	
	@Override
	public void setOutput(AVector outputValues) {
		getOutput().set(outputValues);
	}
	
	@Override
	public int getParameterLength() {
		return getParameters().length();
	}
	
	public IComponent getComponent(int i) {
		return (IComponent) getComponents().get(i);
	}
	
	@Override 
	public IInputState getInputState() {
		return this;
	}
	
	@Override
	public int getInputLength() {
		return getInput().length();
	}
	
	@Override
	public int getOutputLength() {
		return getOutput().length();
	}
	
	public abstract AComponent clone();
}

package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;

public abstract class AComponent implements IComponent {

	@Override
	public void think(AVector input, AVector output) {
		setInput(input);
		thinkInternal(output);
	}
	
	
	@Override 
	public void setInput(AVector input) {
		getInput().set(input);
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
	
	public abstract AComponent clone();
}

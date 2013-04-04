package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;

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
	
	@Override
	public int getInputLength() {
		return getInput().length();
	}
	
	public abstract AComponent clone();
}

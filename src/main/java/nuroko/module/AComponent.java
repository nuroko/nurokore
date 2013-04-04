package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;

public abstract class AComponent implements IComponent {


	@Override
	public void think(AVector input, AVector output) {
		setInput(input);
		think(output);
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

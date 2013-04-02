package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IParameterised;
import nuroko.core.IThinker;

public abstract class ALayer implements IThinker, IParameterised {

	@Override
	public abstract AVector getParameters();
	
	@Override
	public abstract int getParameterLength();
	
	@Override
	public abstract void think(AVector input, AVector output);

	public abstract ALayer clone();
}

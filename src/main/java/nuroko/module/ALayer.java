package nuroko.module;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.IParameterised;
import nuroko.core.IThinker;

/**
 * Layers represent layers of computation from input to output nodes
 * 
 * @author Mike
 *
 */
public abstract class ALayer implements IThinker, IParameterised {

	@Override
	public abstract AVector getParameters();
	
	@Override
	public abstract int getParameterLength();
	
	@Override
	public abstract void think(AVector input, AVector output);
	
	@Override
	public AVector think(AVector input) {
		Vector output=Vector.createLength(getOutputLength());
		think(input,output);
		return output;
	}

	public abstract ALayer clone();
}

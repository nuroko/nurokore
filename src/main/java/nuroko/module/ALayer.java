package nuroko.module;

import mikera.vectorz.AVector;
import nuroko.core.IParameterised;
import nuroko.core.IThinker;

/**
 * Layers represent layers of computation from input to output nodes
 * 
 * Layers have no internal state, but do maintain parameters and gradient
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

	public abstract ALayer clone();
}

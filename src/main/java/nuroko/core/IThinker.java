package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface for standard "Thinker" operations
 * 
 * Conceptually, thinkers are modules that map from an input to an output. They are equivalent to
 * mathematical functions in this sense, although might not be "pure" - their behaviour may vary
 * according to some internal state.
 * 
 * @author Mike
 */
public interface IThinker extends IModule {
	public void think(AVector input, AVector output);
	
	public int getInputLength();
	
	public int getOutputLength();
	
	@Override
	public IThinker clone();
}

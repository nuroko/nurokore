package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface for a synthesizer, i.e. a thinker that can regenerate its input from its output
 * 
 * @author Mike
 *
 */
public interface ISynthesiser extends IThinker, IParameterised {

	public void generate(AVector input, AVector output);
	
	public void trainSynth(AVector input);
	
	@Override
	public ISynthesiser clone();
}

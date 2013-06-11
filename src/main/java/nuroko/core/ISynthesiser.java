package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface for a synthesizer, i.e. a thinker that can regenerate its input from its output
 * 
 * @author Mike
 *
 */
public interface ISynthesiser {

	public AVector generate(AVector output);
	
	public void generate(AVector input, AVector output);
	
	public void trainSynth(AVector input);
	
	public void trainSynth(AVector input, double factor);

	public IComponent getUpStack();
	
	public IComponent getDownStack();
	
	public ISynthesiser clone();
}

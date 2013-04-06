package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Represents a thinker that can be trained in a supervised fashion using a target output
 * 
 * @author Mike
 *
 */
public interface ITrainable extends IThinker, IParameterised {
	public void train(AVector input, AVector target);
	
	public void thinkInternalTraining();

	@Override
	public ITrainable clone();
}

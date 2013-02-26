package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface representing a module which contains internal state.
 * 
 * @author Mike
 *
 */
public interface IState extends IModule {
	public int getStateLength();
	
	public AVector getState();
	
	public AVector getStateGradient();
	
}

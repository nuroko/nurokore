package nuroko.core;

import mikera.vectorz.AVector;

public interface IState extends IModule {
	public int getStateLength();
	
	public AVector getState();
	
	public AVector getStateGradient();
	
}

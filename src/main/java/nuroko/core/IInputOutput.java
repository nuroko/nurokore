package nuroko.core;

/**
 * Interface for a module that has fixed-size inut and output
 * 
 * @author Mike
 */
public interface IInputOutput extends IModule {
	public int getInputLength();
	
	public int getOutputLength();
}

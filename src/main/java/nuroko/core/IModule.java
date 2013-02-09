package nuroko.core;

/**
 * Interface for general purpose learning modules.
 * 
 * @author Mike
 */
public interface IModule extends Cloneable {
	
	/**
	 * Creates a clone of a module, including a deep copy of any mutable state.
	 * 
	 * @return
	 */
	public IModule clone();
}

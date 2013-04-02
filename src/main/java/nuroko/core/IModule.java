package nuroko.core;

import java.util.List;

/**
 * Interface for general purpose learning modules.
 * 
 * @author Mike
 */
public interface IModule extends Cloneable {
	
	/**
	 * Returns a list of sub-components of this module
	 * @return
	 */
	public List<? extends IModule> getComponents();
	
	/**
	 * Creates a clone of a module, including a deep copy of any mutable state.
	 * 
	 * @return
	 */
	public IModule clone();
}

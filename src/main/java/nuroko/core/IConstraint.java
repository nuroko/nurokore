package nuroko.core;

/**
 * Interface for component
 * 
 * Applied by calling the applyConstraints() method in IComponent;
 * @author Mike
 *
 */
public interface IConstraint {

	public void applyTo(IComponent c);
}

package nuroko.module;

/**
 * Base class for components that perform a single operation on each element
 * from input to output
 * @author Mike
 *
 */
public abstract class AOperationComponent extends AStateComponent {
	protected final int length;
	
	public AOperationComponent(int length) {
		super(length, length);
		this.length=length;
	}

}

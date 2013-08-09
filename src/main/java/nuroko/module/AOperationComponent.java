package nuroko.module;

import java.util.Collections;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.impl.Vector0;
import nuroko.core.IComponent;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<IComponent> getComponents() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public int getInputLength() {
		return length;
	}

	@Override
	public int getOutputLength() {
		return length;
	}

	@Override
	public AVector getGradient() {
		return Vector0.INSTANCE;
	}
	
	@Override
	public AVector getParameters() {
		return Vector0.INSTANCE;
	}
	

	@Override
	public abstract AOperationComponent clone();
}

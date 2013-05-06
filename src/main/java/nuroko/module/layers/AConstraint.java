package nuroko.module.layers;

import nuroko.core.IComponent;
import nuroko.core.IConstraint;

public abstract class AConstraint implements IConstraint {

	@Override
	public abstract void applyTo(IComponent c);
}

package nuroko.core;

import java.util.List;

import nuroko.module.Connect;

public class Components {

	public IComponent connect(List<? extends IComponent> components) {
		return new Connect(components);
	}
}

package nuroko.core;

import java.util.List;

import nuroko.module.Connect;

public final class Components {

	public static IComponent connect(List<? extends IComponent> components) {
		return new Connect(components);
	}
}

package nuroko.core;

import java.util.Arrays;
import java.util.List;

import nuroko.module.Connect;

public final class Components {

	public static Connect connect(List<? extends IComponent> components) {
		return new Connect(components);
	}
	
	public static Connect connect(IComponent... components) {
		return new Connect(Arrays.asList(components));
	}
}

package nuroko.module.layers;

import nuroko.core.IConstraint;

public class Constraints {
	public static IConstraint weightLength(double maxWeightLength) {
		return new WeightLengthConstraint(maxWeightLength);
	}
}


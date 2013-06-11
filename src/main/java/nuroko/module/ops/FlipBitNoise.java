package nuroko.module.ops;

import mikera.util.Rand;
import mikera.vectorz.Op;

public class FlipBitNoise extends Op {
	private final double chance;

	public FlipBitNoise(double chance) {
		this.chance=chance;
	}
	
	@Override
	public boolean isStochastic() {
		return true;
	}
	
	@Override
	public double apply(double x) {
		return Rand.chance(chance)?(1-x):x;
	}

	@Override
	public double averageValue() {
		return 0.5;
	}

}

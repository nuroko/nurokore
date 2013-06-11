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
	
	@Override public double derivative(double x) {
		return (1.0-chance);
	}
	
	@Override public double derivativeForOutput(double y) {
		return (1.0-chance);
	}
	
	@Override
	public double minValue() {
		return 0.0;
	}

	@Override
	public double maxValue() {
		return 1.0;
	}
	
	@Override
	public boolean hasDerivative() {
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

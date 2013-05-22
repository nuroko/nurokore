package nuroko.module;

public class GradientAmplifier extends AOperationComponent {
	private final double factor;
	
	public GradientAmplifier(int length, double factor) {
		super(length);
		this.factor=factor;
	}

	@Override
	public void thinkInternal() {
		output.set(input);
	}

	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}

	@Override
	public AOperationComponent clone() {
		return new GradientAmplifier(length,factor);
	}

	@Override
	public void trainGradientInternal(double factor) {
		inputGradient.set(outputGradient);
		inputGradient.scale(factor);
	}

}

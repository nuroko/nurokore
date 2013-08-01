package nuroko.module;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

public class Bias extends AStateComponent {
	private final Vector bias;
	private final Vector biasGradient;

	public Bias(int length) {
		super(length, length);
		bias=Vector.createLength(length);
		biasGradient=Vector.createLength(length);		
	}

	@Override
	public void thinkInternal() {
		output.set(input);
		output.add(bias);
	}

	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}

	@Override
	public AVector getParameters() {
		return bias;
	}


	@Override
	public AVector getGradient() {
		return biasGradient;
	}


	@Override
	public void trainGradientInternal(double factor) {
		// TODO Auto-generated method stub
		bias.addMultiple(this.outputGradient, factor*this.getLearnFactor());
		inputGradient.set(outputGradient);
	}
	
	@Override
	public Bias clone() {
		Bias b=new Bias(this.getInputLength());
		b.bias.set(this.bias);
		return b;
	}

}

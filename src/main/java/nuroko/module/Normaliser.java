package nuroko.module;

import java.util.List;

import nuroko.core.IComponent;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.impl.Vector0;

public class Normaliser extends AStateComponent {
	private final Vector mean;
	private final Vector stdev;
	
	private Normaliser(int length) {
		super(length, length);
		mean=Vector.createLength(length);
		stdev=Vector.createLength(length);
		stdev.fill(1.0);
	}
	
	public static Normaliser create(AVector mean, AVector stdev) {
		Normaliser n=new Normaliser(mean.length());
		n.mean.set(mean);
		n.stdev.set(stdev);
		return n;
	}
	
	public static Normaliser create(int length, double mean, double stdev) {
		Normaliser n=new Normaliser(length);
		n.mean.fill(mean);
		n.stdev.fill(stdev);
		return n;
	}

	@Override
	public void thinkInternal() {
		output.set(input);
		output.sub(mean);
		output.divide(stdev);
	}

	@Override
	public List<IComponent> getComponents() {
		return null;
	}

	@Override
	public AVector getParameters() {
		return Vector0.INSTANCE;
	}

	@Override
	public AVector getGradient() {
		return Vector0.INSTANCE;
	}

	@Override
	public void trainGradientInternal(double factor) {
		double[] og=outputGradient.data;
		double[] ig=inputGradient.data;
		double[] sd=stdev.data;
		for (int i=0; i<og.length; i++) {
			if (sd[i]==0) {
				ig[i]=og[i];
			} else {
				ig[i]=og[i]/sd[i];
			}
		}
	}

	@Override
	public Normaliser clone() {
		Normaliser n=new Normaliser(getInputLength());
		n.mean.set(mean);
		n.stdev.set(stdev);
		return n;
	}

	@Override
	public void generate(AVector input, AVector output) {
		this.output.set(output);
		this.input.set(output);
		this.input.multiply(stdev);
		this.input.add(mean);
		input.set(this.input);
	}
	
	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}
}

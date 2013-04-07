package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.impl.Vector0;
import nuroko.core.IComponent;

/**
 * Class representing a side-by-side joining of networks
 * @author Mike
 *
 */
public class Join extends ACompoundComponent {
	private final AVector input;
	private final AVector inputGradient;
	private final AVector output;
	private final AVector outputGradient;
	
	protected final int inputLength;
	protected final int outputLength;
	
	public Join(List<? extends IComponent> comps) {
		super(comps);
		int il=0;
		int ol=0;
		AVector input=Vector0.INSTANCE, 
				inputGradient=Vector0.INSTANCE, 
				output=Vector0.INSTANCE,
				outputGradient=Vector0.INSTANCE;
		for (int i=0; i<componentCount; i++) {
			IComponent comp=getComponent(i);
			il+=comp.getInputLength();
			ol+=comp.getOutputLength();
			input=input.join(comp.getInput());
			inputGradient=inputGradient.join(comp.getInputGradient());
			output=output.join(comp.getOutput());
			outputGradient=outputGradient.join(comp.getOutputGradient());
		}
		inputLength=il;
		outputLength=ol;
		this.input=input;
		this.output=output;
		this.inputGradient=inputGradient;
		this.outputGradient=outputGradient;
	}

	@Override
	public void thinkInternal() {
		for (IComponent c:components) {
			c.thinkInternal();
		}
	}

	@Override
	public void trainGradient(AVector gradient, double factor) {
		outputGradient.set(gradient);
		trainGradientInternal(factor);
	}
	
	@Override
	public void trainGradientInternal(double factor) {
		for (IComponent c:components) {
			c.trainGradientInternal(factor);
		}
	}

	@Override
	public AVector getInput() {
		return input;
	}

	@Override
	public AVector getInputGradient() {
		return inputGradient;
	}

	@Override
	public AVector getOutput() {
		return output;
	}

	@Override
	public AVector getOutputGradient() {
		return outputGradient;
	}

	@Override
	public Join clone() {
		ArrayList<IComponent> al=new ArrayList<IComponent>();
		for (IComponent c: components) {
			al.add(c.clone());
		}
		return new Join(al);
	}
}

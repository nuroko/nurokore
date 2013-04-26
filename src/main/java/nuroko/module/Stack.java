package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;
import nuroko.module.loss.LossFunction;

/**
 * A stack of connected components, with the output of each feeding into the 
 * input of the next
 * 
 * @author Mike
 *
 */
public class Stack extends ACompoundComponent {

	public Stack(List<? extends IComponent> comps) {
		super(comps);
	}
	
	public IComponent topComponent() {
		return components.get(componentCount-1);
	}
	
	@Override
	public LossFunction getDefaultLossFunction() {
		return topComponent().getDefaultLossFunction();
	}


	@Override
	public AVector getInput() {
		return components.get(0).getInput();
	}

	@Override
	public AVector getInputGradient() {
		return components.get(0).getInputGradient();
	}

	@Override
	public int getInputLength() {
		return components.get(0).getInputLength();
	}


	@Override
	public void thinkInternal() {
		for (int i=0; i<componentCount; i++) {
			getComponent(i).thinkInternal();
			if (i<(componentCount-1)) {
				getComponent(i+1).setInput(getComponent(i).getOutput());
			}
		}
	}
	
	@Override
	public void thinkInternalTraining() {
		for (int i=0; i<componentCount; i++) {
			IComponent ci=getComponent(i);
			ci.thinkInternal();
			if (i<(componentCount-1)) {
				getComponent(i+1).setInput(ci.getOutput());
			}
			if (ci.hasDifferentTrainingThinking()) {
				ci.thinkInternalTraining();
			}
		}
	}

	@Override
	public Stack clone() {
		ArrayList<IComponent> al=new ArrayList<IComponent>();
		for (IComponent c: components) {
			al.add(c.clone());
		}
		return new Stack(al);
	}

	@Override
	public void setInput(AVector input) {
		components.get(0).setInput(input);
	}

	@Override
	public IInputState getInputState() {
		return components.get(0).getInputState();
	}
	
	public void trainGradientInternal(double factor) {
		int n=this.componentCount;
		topComponent().trainGradientInternal(factor);
		for (int i=n-2; i>=0; i--) {
			AVector gradient=getComponent(i+1).getInputGradient();
			IComponent comp=getComponent(i);
			comp.getOutputGradient().set(gradient);
			comp.trainGradientInternal(factor);
		}
	}

	@Override
	public AVector getOutput() {
		return getComponent(componentCount-1).getOutput();
	}

	@Override
	public AVector getOutputGradient() {
		return getComponent(componentCount-1).getOutputGradient();
	}
}

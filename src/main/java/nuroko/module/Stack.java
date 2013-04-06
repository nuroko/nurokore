package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;

public class Stack extends ACompoundComponent {

	public Stack(List<? extends IComponent> comps) {
		super(comps);
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

	@Override
	public void trainGradient(AVector gradient, double factor) {
		getComponent(componentCount-1).getOutputGradient().set(gradient);
		trainGradientInternal(factor);
	}
	
	public void trainGradientInternal(double factor) {
		int n=this.componentCount;
		getComponent(n-1).trainGradientInternal(factor);
		for (int i=n-2; i>=0; i--) {
			AVector gradient=getComponent(i+1).getInputGradient();
			IComponent comp=getComponent(i);
			comp.getOutputGradient().set(gradient);
			comp.trainGradientInternal(factor);
			gradient=comp.getInputGradient();
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

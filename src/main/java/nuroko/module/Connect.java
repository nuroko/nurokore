package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;

public class Connect extends ACompoundComponent {

	public Connect(List<? extends IComponent> comps) {
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
	public Connect clone() {
		ArrayList<IComponent> al=new ArrayList<IComponent>();
		for (IComponent c: components) {
			al.add(c.clone());
		}
		return new Connect(al);
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
		int n=this.componentCount;
		for (int i=n-1; i>=0; i--) {
			IComponent comp=getComponent(i);
			comp.trainGradient(gradient, 1.0);
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

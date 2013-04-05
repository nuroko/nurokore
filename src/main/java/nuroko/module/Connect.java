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
	public void thinkInternal(AVector output) {
		for (int i=0; i<(componentCount-1); i++) {
			components.get(i).thinkInternal(components.get(i+1).getInput());
		}
		components.get(componentCount-1).thinkInternal(output);
	}
	
	@Override
	public void think(AVector input, AVector output) {
		components.get(0).setInput(input);
		thinkInternal(output);
	}

	@Override
	public int getOutputLength() {
		return components.get(componentCount-1).getOutputLength();
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
}

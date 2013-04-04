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
	public void think(AVector output) {
		for (int i=0; i<(componentCount-1); i++) {
			components.get(i).think(components.get(i+1).getInput());
		}
		components.get(componentCount-1).think(output);
	}
	
	@Override
	public void think(AVector input, AVector output) {
		components.get(0).setInput(input);
		think(output);
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


}

package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;

public abstract class ACompoundComponent extends AComponent {
	protected final ArrayList<IComponent> components;
	protected final AVector gradient;
	protected final AVector parameters;
	protected final int componentCount;
	
	public ACompoundComponent(List<? extends IComponent> comps) {
		this.componentCount=comps.size();
		this.components=new ArrayList<IComponent>(comps);
		
		AVector g=comps.get(0).getGradient();
		AVector p=comps.get(0).getParameters();
		for (int i=1; i<componentCount; i++) {
			g=g.join(comps.get(i).getGradient());
			p=p.join(comps.get(i).getParameters());
		}
		gradient=g;
		parameters=p;
	}
	
	@Override
	public final AVector getGradient() {return gradient;}
	
	@Override
	public final AVector getParameters() {return parameters;}
	
	@Override
	public int getParameterLength() {
		return parameters.length();
	}
	
	public IComponent getComponent(int i) {
		return components.get(i);
	}
	
	public List<IComponent> getComponents() {
		return components;
	}
	
	@Override
	public boolean hasDifferentTrainingThinking() {
		for (IComponent c:getComponents()) {
			if (c.hasDifferentTrainingThinking()) return true;
		}
		return false;
	}
}

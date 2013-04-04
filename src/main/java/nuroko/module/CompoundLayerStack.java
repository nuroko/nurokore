package nuroko.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nuroko.core.IParameterised;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public abstract class CompoundLayerStack<T extends IParameterised> extends ALayerStack {

	protected final ArrayList<T> components=new ArrayList<T>();
	protected final int componentCount;
	private final AVector parameters;
	private final AVector gradient;
	
	public List<T> getComponents() {
		return components;
	}
	
	@SuppressWarnings("unchecked")
	public CompoundLayerStack(Collection<T> comps) {
		Object tmp=comps;
		this.components.addAll((Collection<T>) tmp);
		componentCount=this.components.size();
		
		AVector params=Vectorz.newVector(0);
		for (T comp:components) {
			params=params.join(comp.getParameters());
		}
		parameters=params;
		
		AVector grad=Vectorz.newVector(0);
		for (T comp:components) {
			grad=grad.join(comp.getGradient());
		}
		gradient=grad;
	}
	
	public T getComponent(int i) {
		return components.get(i);
	}

	@Override
	public abstract void think(AVector input, AVector output);

	@Override
	public AVector getParameters() {
		return parameters;
	}

	@Override
	public AVector getGradient() {
		return gradient;
	}

	@Override
	public abstract int getLayerCount();

	@Override
	public abstract AWeightLayer getLayer(int i);

	@Override
	public abstract AVector getData(int i);

	@Override
	public abstract CompoundLayerStack<T> clone();
	
}

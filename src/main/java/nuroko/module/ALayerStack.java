package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;

/**
 * Abstract base class representing a stack of AWeightLayer components
 * 
 * @author Mike
 */
public abstract class ALayerStack extends AComponent {
	
	public abstract int getLayerCount();
	
	public abstract AWeightLayer getLayer(int i);
	
	public abstract AVector getData(int i);
	
	@Override
	public AVector getOutput() {
		return getData(getLayerCount());
	}
	
	@Override
	public AVector getInput() {
		return getData(0);
	}
	
	public List<AWeightLayer> getLayers() {
		int lc=getLayerCount();
		ArrayList<AWeightLayer> al=new ArrayList<AWeightLayer>();
		for (int i=0; i<lc; i++) {
			al.add(getLayer(i));
		}
		return al;
	}
	
	public ALayerStack subStack(int start, int length) {
		List<AWeightLayer> layers=getLayers().subList(start, start+length);
		return CompoundLayerStack.create(layers);
	}

	@Override
	public abstract ALayerStack clone();
}

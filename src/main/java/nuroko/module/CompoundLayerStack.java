package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;

public class CompoundLayerStack extends ALayerStack {
	private final int layerCount;
	private final int split;
	
	private final ALayerStack bottom;
	private final ALayerStack top;
	
	
	public CompoundLayerStack(ALayerStack a, ALayerStack b) {
		bottom=a;
		top=b;
		split=a.getLayerCount();
		layerCount=split+b.getLayerCount();
	}
	

	public static CompoundLayerStack stack(ALayerStack a, ALayerStack b) {
		return new CompoundLayerStack(a,b);
	}

	@Override
	public void thinkInternal() {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}

	@Override
	public void trainGradientInternal(double factor) {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}

	@Override
	public List<IComponent> getComponents() {
		List<IComponent> al=new ArrayList<IComponent>();
		al.add(bottom);
		al.add(top);
		return al;
	}

	@Override
	public AVector getParameters() {
		return bottom.getParameters().join(top.getParameters());
	}

	@Override
	public AVector getGradient() {
		return bottom.getGradient().join(top.getGradient());
	}

	@Override
	public void trainGradient(AVector gradient, double factor) {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}

	@Override
	public AVector getInputGradient() {
		return bottom.getInputGradient();
	}

	@Override
	public AVector getOutputGradient() {
		return top.getOutputGradient();
	}

	@Override
	public int getLayerCount() {
		return layerCount;
	}

	@Override
	public AWeightLayer getLayer(int i) {
		if (i<split) {
			return bottom.getLayer(i);
		} else {
			return top.getLayer(i-split);
		}
	}

	@Override
	public AVector getData(int i) {
		if (i<split) {
			return bottom.getData(i);
		} else {
			return top.getData(i-split);
		}	
	}

	@Override
	public ALayerStack clone() {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}

	@Override
	public void train(AVector input, AVector target) {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}

	@Override
	public void trainGradient(AVector input, AVector outputGradient,
			AVector inputGradient, double factor, boolean skipTopDerivative) {
		throw new UnsupportedOperationException("CompundLayerStack is view-only");
	}


}

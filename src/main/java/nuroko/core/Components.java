package nuroko.core;

import java.util.Arrays;
import java.util.List;

import mikera.vectorz.Op;
import nuroko.module.ALayerStack;
import nuroko.module.AWeightLayer;
import nuroko.module.Join;
import nuroko.module.Operator;
import nuroko.module.Stack;
import nuroko.module.NeuralNet;
import nuroko.module.layers.FullWeightLayer;
import nuroko.module.layers.SparseWeightLayer;

public final class Components {

	private static final int DEFAULT_SPARSE_LINKS = 50;

	public static Stack stack(List<? extends IComponent> components) {
		return new Stack(components);
	}
	
	public static Stack stack(IComponent... components) {
		return new Stack(Arrays.asList(components));
	}
	
	public static Join join(List<? extends IComponent> components) {
		return new Join(components);
	}
	
	public static Join join(IComponent... components) {
		return new Join(Arrays.asList(components));
	}
	
	public static Operator operator(Op op, int length) {
		return new Operator(op, length);
	}

	public static NeuralNet neuralLayer(int inputLength, int outputLength, Op op) {
		return neuralLayer(inputLength,outputLength,op,false);
	}
	
	public static AWeightLayer weightLayer(int inputLength, int outputLength, int maxLinks) {
		if (maxLinks>=inputLength) {
			return new FullWeightLayer(inputLength,outputLength);
		} else {
			return new SparseWeightLayer(inputLength,outputLength,maxLinks);
		}
	}
	
	public static NeuralNet neuralLayer(int inputLength, int outputLength, Op op, boolean fullyConnected) {
		AWeightLayer wl;
		if (fullyConnected) {
			wl=new FullWeightLayer(inputLength,outputLength);
		} else {
			wl=weightLayer(inputLength,outputLength,DEFAULT_SPARSE_LINKS);
		}
		return new NeuralNet(new AWeightLayer[] {wl},op);
	}
	
	public static ALayerStack asLayerStack(IComponent comp) {
		if (comp instanceof ALayerStack) {
			return (ALayerStack)comp;
		}
		List<IComponent> comps=(List<IComponent>) comp.getComponents();
		
		throw new IllegalArgumentException("Can't convert to ALayerStack: " +comp);
	}
}

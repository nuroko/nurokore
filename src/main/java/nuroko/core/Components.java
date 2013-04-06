package nuroko.core;

import java.util.Arrays;
import java.util.List;

import mikera.vectorz.Op;
import nuroko.module.AWeightLayer;
import nuroko.module.Connect;
import nuroko.module.NeuralNet;
import nuroko.module.layers.FullWeightLayer;
import nuroko.module.layers.SparseWeightLayer;

public final class Components {

	private static final int DEFAULT_SPARSE_LINKS = 50;

	public static Connect connect(List<? extends IComponent> components) {
		return new Connect(components);
	}
	
	public static Connect connect(IComponent... components) {
		return new Connect(Arrays.asList(components));
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
}

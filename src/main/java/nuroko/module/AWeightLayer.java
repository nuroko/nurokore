package nuroko.module;

import java.util.Collections;
import java.util.List;

import nuroko.core.IModule;
import mikera.indexz.Index;
import mikera.matrixx.AMatrix;
import mikera.vectorz.AVector;

public abstract class AWeightLayer extends ALayer {
	protected static final double INITIAL_WEIGHT_SCALE = 0.5;
	protected static final double BIAS_INITIAL_SCALE = 0.01;

	protected final int inputLength;
	protected final int outputLength;

	public AWeightLayer(int inputLength, int outputLength) {
		this.inputLength=inputLength;
		this.outputLength=outputLength;
	}
	
	@Override 
	public List<IModule> getComponents() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public int getInputLength() {
		return inputLength;
	}

	@Override
	public int getOutputLength() {
		return outputLength;
	}
	
	public abstract AWeightLayer getInverse();
	
	public abstract int getLinkCount(int outputIndex);
	
	public abstract double getLinkWeight(int outputIndex, int number);

	public abstract int getLinkSource(int outputIndex, int number);
	
	public abstract Index getSourceIndex(int outputIndex);

	public abstract AVector getSourceWeights(int outputIndex);
	
	@Override
	public abstract AWeightLayer clone();

	public abstract void trainGradient(AVector input, 
			AVector outputGradient, AVector inputGradient, double factor);

	public abstract void initRandom();

	public abstract AMatrix asMatrix();

}

package nuroko.module;

import java.util.Collections;
import java.util.List;

import nuroko.core.IModule;
import mikera.indexz.Index;
import mikera.matrixx.AMatrix;
import mikera.vectorz.AVector;

/**
 * Abstract base class representing a weighted linear transformation.
 * @author Mike
 *
 */
public abstract class AWeightLayer extends AStateComponent {
	protected static final double INITIAL_WEIGHT_SCALE = 1.0;
	protected static final double BIAS_INITIAL_SCALE = 0.3;

	protected final int inputLength;
	protected final int outputLength;
	
	public AWeightLayer(int inputLength, int outputLength) {
		super(inputLength,outputLength);
		this.inputLength=inputLength;
		this.outputLength=outputLength;
	}
	
	@Override 
	public List<IModule> getModules() {
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

	public void trainGradient(AVector input, AVector outputGradient, AVector inputGradient, double factor) {
		getOutputGradient().set(outputGradient);
		getInput().set(input);
		trainGradientInternal(factor);
		inputGradient.set(getInputGradient());
	}

	public abstract void initRandom();

	public abstract AMatrix asMatrix();

}

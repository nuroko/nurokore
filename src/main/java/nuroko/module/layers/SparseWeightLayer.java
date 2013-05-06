package nuroko.module.layers;

import java.util.ArrayList;
import nuroko.module.AWeightLayer;

import mikera.indexz.Index;
import mikera.indexz.Indexz;
import mikera.matrixx.AMatrix;
import mikera.matrixx.Matrixx;
import mikera.vectorz.AVector;
import mikera.vectorz.GrowableVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;

/**
 * Fully connected weight layer
 * 
 * @author Mike
 */
public final class SparseWeightLayer extends AWeightLayer {
	private static final double MAX_WEIGHT_VECTOR_LENGTH = 4.0;

	private final Vector bias;
	private final Vector biasGradient;
	private final Index[] indexes;
	private final Vector[] weights;
	private final Vector[] weightGradients;
	private AVector parameters;
	private AVector gradient;
	
	public SparseWeightLayer(int inputLength, int outputLength, int maxLinks) {
		super(inputLength,outputLength);
		
		bias=Vector.createLength(outputLength);
		weights=new Vector[outputLength];
		indexes=new Index[outputLength];

		int links=Math.min(maxLinks, inputLength);
		
		AVector params=bias;
		for (int j=0; j<outputLength; j++) {
			Vector wts=Vector.createLength(links);
			Index inds=Indexz.createRandomChoice(links, inputLength);
			weights[j]=wts;			
			indexes[j]=inds;			
			params=params.join(wts);
		}
		parameters=params;
		
		biasGradient=Vector.createLength(outputLength);
		weightGradients=new Vector[outputLength];
		
		AVector g=biasGradient;
		for (int j=0; j<outputLength; j++) {
			Vector grd=Vector.createLength(weights[j].length());
			weightGradients[j]=grd;
			g=g.join(grd);
		}
		gradient=g;
		assert(gradient.length()==parameters.length());
	}
	
	public SparseWeightLayer(SparseWeightLayer wl) {
		super(wl.inputLength,wl.outputLength);
		bias=wl.bias.clone();
		biasGradient=wl.biasGradient.clone();
		
		weights=new Vector[outputLength];
		indexes=new Index[outputLength];
		
		AVector params=bias;
		for (int j=0; j<outputLength; j++) {
			Vector wts=wl.weights[j].clone();
			Index inds=wl.indexes[j].clone();
			weights[j]=wts;			
			indexes[j]=inds;			
			params=params.join(wts);
		}
		parameters=params;
		
		weightGradients=new Vector[biasGradient.length()];
		
		AVector g=biasGradient;
		for (int j=0; j<outputLength; j++) {
			Vector grd=wl.weightGradients[j].clone();
			weightGradients[j]=grd;
			g=g.join(grd);
		}
		gradient=g;
		assert(gradient.length()==parameters.length());

	}
	
	@Override
	public AMatrix asMatrix() {
		return Matrixx.createSparse(getInputLength(),indexes,weights);
	}

	@Override
	public AVector getParameters() {
		return parameters;
	}
	
	
	@Override
	public int getParameterLength() {
		return parameters.length();
	}

	@Override
	public void think(AVector input, AVector output) {
		assert(inputLength==input.length());
		assert(outputLength==output.length());
		setInput(input);
		thinkInternal();
		output.set(getOutput());
	}
	
	@Override
	public void thinkInternal() {
		for (int i=0; i<outputLength; i++) {
			double val=bias.get(i);
			Vector wts=weights[i];
			Index inds=indexes[i];
			val+=input.dotProduct(wts, inds);
			output.set(i,val);
		}
	}


	@Override
	public AVector getGradient() {
		return gradient;
	}

	@Override
	public void trainGradientInternal(double factor) {
		factor*=this.getLearnFactor();
		inputGradient.fill(0.0);
		biasGradient.addMultiple(outputGradient,factor);
		for (int j=0; j<outputLength; j++) {
			double grad=outputGradient.get(j);
			weightGradients[j].addMultiple(indexes[j], input, grad*factor);
			inputGradient.addMultiple(weights[j], indexes[j],grad);
		}
	}
	
	@Override 
	public SparseWeightLayer clone() {
		SparseWeightLayer wl=new SparseWeightLayer(this);
		wl.getParameters().set(this.getParameters());
		return wl;
	}

	@Override
	public int getLinkCount(int outputIndex) {
		return weights[outputIndex].length();
	}

	@Override
	public double getLinkWeight(int outputIndex, int number) {
		return weights[outputIndex].data[number];
	}

	@Override
	public int getLinkSource(int outputIndex, int number) {
		return indexes[outputIndex].data[number];
	}
	
	@Override
	public void applyConstraintsInternal() {
		for (Vector v: weights) {
			double len=v.magnitude();
			if (len>MAX_WEIGHT_VECTOR_LENGTH) {
				v.multiply(MAX_WEIGHT_VECTOR_LENGTH/len);
			}
		}
	}

	@Override
	public void initRandom() {
		Vectorz.fillGaussian(bias, 0.0, BIAS_INITIAL_SCALE* INITIAL_WEIGHT_SCALE);
		for (Vector v:weights) {
			Vectorz.fillGaussian(v, 0.0, INITIAL_WEIGHT_SCALE/(Math.sqrt(v.length())));
		}
	}

	@Override
	public Index getSourceIndex(int outputIndex) {
		return indexes[outputIndex];
	}

	@Override
	public AVector getSourceWeights(int outputIndex) {
		return weights[outputIndex];
	}

	@Override
	public SparseWeightLayer getInverse() {
		int inps=getInputLength();
		int outps=getOutputLength();
		SparseWeightLayer wl=new SparseWeightLayer(getOutputLength(),getInputLength(),0);
		
		GrowableVector[] weightVectors=new GrowableVector[inps];
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] indexVectors = new ArrayList[inps];
		for (int i=0; i<inps; i++) {
			weightVectors[i]=new GrowableVector();
			indexVectors[i]=new ArrayList<Integer>();
		}
		
		for (int j=0; j<outps; j++) {
			AVector owts=getSourceWeights(j);
			Index oixs=getSourceIndex(j);
			assert(owts.length()==oixs.length());
			for (int i=0; i<owts.length(); i++) {
				int si=oixs.get(i);
				indexVectors[si].add(j);
				weightVectors[si].append(owts.get(i));	
			}			
		}	
		
		for (int i=0; i<inps; i++) {
			wl.weights[i]=new Vector(weightVectors[i]);
			wl.indexes[i]=Indexz.create(indexVectors[i]);
			wl.weightGradients[i]=Vector.createLength(wl.weights[i].length());
		}
		wl.rebuildVectors();
		return wl;
	}

	private void rebuildVectors() {
		AVector params=bias;
		AVector grads=biasGradient;
		for (int i=0; i<outputLength; i++) {
			params=params.join(weights[i]);
			grads=grads.join(weightGradients[i]);
		}
		parameters=params;
		gradient=grads;
	}

	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}
}

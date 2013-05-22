package nuroko.module;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.impl.Vector0;

public class Sparsifier extends AStateComponent {
	private static final double MEAN_RATE=0.003;
	
	private final double weight;
	private final double targetMean;
	private final Vector mean;
	private double meanLearnRate=MEAN_RATE;
	//private final Vector temp;
	
	public Sparsifier (int length, double targetMean, double weight) {
		super(length,length);
		this.targetMean=targetMean;
		mean=Vector.createLength(length);
		mean.fill(targetMean);
		this.weight=weight;
		//temp=Vector.createLength(length);
	}
	
	public Sparsifier (int length, double targetMean, double weight, double meanLearnRate) {
		this(length,targetMean,weight);
		this.meanLearnRate=meanLearnRate;
	}

	@Override
	public void thinkInternal() {
		output.set(input);
	}

	@Override
	public boolean hasDifferentTrainingThinking() {
		return false;
	}

	@Override
	public AVector getParameters() {
		return Vector0.INSTANCE;
	}

	@Override
	public AVector getGradient() {
		return Vector0.INSTANCE;
	}

	@Override
	public void trainGradientInternal(double factor) {
		mean.multiply(1-MEAN_RATE);
		mean.addMultiple(input, meanLearnRate);
		
		int n=getInputLength();
		for (int i=0; i<n; i++) {
			double mi=mean.get(i);
			inputGradient.set(i,this.weight * ( (targetMean/mi) - ((1-targetMean)/(1-mi)) ));
		}
			
		inputGradient.add(outputGradient);
	}
	
	@Override
	public Sparsifier clone() {
		Sparsifier s=new Sparsifier(getInputLength(),targetMean,weight);
		s.mean.set(this.mean);
		s.meanLearnRate=this.meanLearnRate;
		return s;
	}
}

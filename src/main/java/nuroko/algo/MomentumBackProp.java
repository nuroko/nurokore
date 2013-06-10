package nuroko.algo;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.IComponent;
import nuroko.module.loss.LossFunction;

public class MomentumBackProp {
	private Vector lastUpdate;
	private double momentum;

	public MomentumBackProp(IComponent nn, double momentum) {
		this(nn.getParameterLength(), momentum);
	}
	
	public MomentumBackProp(int paramLength, double momentum) {
		lastUpdate = Vector.createLength(paramLength);
		this.momentum=momentum;
	}
	
	public void train(IComponent comp, AVector input, AVector target,double learnRate) {
		train(comp,input,target,learnRate,comp.getDefaultLossFunction());
	}
	
	public void train(IComponent comp, AVector input, AVector target,double learnRate, LossFunction loss) {
		comp.getGradient().fill(0.0);
		comp.train(input,target,loss,1.0);
		lastUpdate.multiply(1.0-momentum);
		lastUpdate.addMultiple(comp.getGradient(),learnRate);
		comp.getParameters().add(lastUpdate);
	}

	public void trainSynth(IComponent comp,AVector input, double learnRate) {
		comp.getGradient().fill(0.0);
		comp.trainSynth(input);
		lastUpdate.multiply(1.0-momentum);
		lastUpdate.addMultiple(comp.getGradient(),learnRate);
		comp.getParameters().add(lastUpdate);		
	}
}

package nuroko.algo;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.module.loss.LossFunction;
import nuroko.module.loss.SquaredErrorLoss;

public class SimpleBackProp {
	public static void train(IComponent comp, AVector input, AVector target,double learnRate) {
		train(comp,input,target,learnRate,SquaredErrorLoss.INSTANCE);
	}
	
	public static void train(IComponent comp, AVector input, AVector target,double learnRate, LossFunction loss) {
		comp.getGradient().fill(0.0);
		comp.train(input,target,loss,1.0);
		comp.getParameters().addMultiple(comp.getGradient(), learnRate);
	}
}

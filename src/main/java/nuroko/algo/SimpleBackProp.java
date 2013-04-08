package nuroko.algo;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;

public class SimpleBackProp {
	public static void train(IComponent comp, AVector input, AVector target,double learnRate) {
		comp.train(input,target);
		comp.getParameters().addMultiple(comp.getGradient(), learnRate);
		comp.getGradient().fill(0.0);
	}
}

package nuroko.demo.training;

import mikera.vectorz.AVector;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import nuroko.algo.MomentumBackProp;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.task.samples.XORTask;

public class XORTrainingDemo {
	
	
	public static void main(String[] args) {
		int ARGS=4;
		int HIDDEN=8;

		XORTask xt=new XORTask(ARGS);
		
		IComponent l1=Components.neuralLayer(ARGS, HIDDEN, Ops.TANH);
		IComponent l2=Components.neuralLayer(HIDDEN, xt.getOutputLength(), Ops.LOGISTIC);
		IComponent nn=Components.stack(l1, l2);
		nn.initRandom();
		
		AVector input=Vector.createLength(xt.getInputLength());
		AVector target=Vector.createLength(xt.getOutputLength());
		
		MomentumBackProp m=new MomentumBackProp(nn,0.9);
		
		int SKIP=1000;
		for (int i=0; i<100000; i++) {
			xt.getInput(input);
			xt.getTarget(input, target);
			
			m.train(nn, input, target, 0.01);
			
			if ((i%SKIP)==0) {
				double error=nn.getDefaultLossFunction().calculateError(nn.getOutput(), target);
				System.out.println(i+ " : "+error + "   Output = "+nn.getOutput().toString());
			}
		}
		
	}
}

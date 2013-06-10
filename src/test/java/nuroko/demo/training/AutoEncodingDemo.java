package nuroko.demo.training;

import mikera.vectorz.AVector;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import nuroko.algo.MomentumBackProp;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.task.samples.XORTask;

public class AutoEncodingDemo {
	
	
	public static void main(String[] args) {
		int ARGS=4;
		int HIDDEN=8;
		
		IComponent l1=Components.neuralLayer(ARGS, HIDDEN, Ops.TANH);
		IComponent l2=Components.neuralLayer(HIDDEN, ARGS, Ops.LOGISTIC);
		IComponent nn=Components.stack(l1, l2);
		nn.initRandom();
		
		AVector input=Vector.createLength(ARGS);
		AVector target=Vector.createLength(ARGS);
		
		MomentumBackProp m=new MomentumBackProp(nn,0.9);
		
		int SKIP=1000;
		for (int i=0; i<100000; i++) {
			Vectorz.fillBinaryRandom(input);
			target.set(input);
			
			m.train(nn, input, target, 0.01);
			
			if ((i%SKIP)==0) {
				double error=nn.getDefaultLossFunction().calculateError(nn.getOutput(), target);
				System.out.println(i+ " : "+error + "Output = "+nn.getOutput().toString());
			}
		}
		
	}
}

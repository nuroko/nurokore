package nuroko.module;

import static org.junit.Assert.*;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.ops.Linear;
import nuroko.algo.SimpleBackProp;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.module.loss.*;

import org.junit.Test;

public class TestTraining {
	
	@Test public void testSimpleBackProp() {
		IComponent nn=Components.neuralLayer(3, 3, Ops.LOGISTIC);
		//System.out.println(nn.getParameters());
		
		AVector input =Vector.of(1,0,-1);
		AVector target =Vector.of(1,0,0.5);
		
		LossFunction loss= CrossEntropyLoss.INSTANCE;
		
		AVector o1=nn.apply(input);
		SimpleBackProp.train(nn, input, target, 0.1, loss);
		SimpleBackProp.train(nn, input, target, 0.1, loss);
		AVector o2=nn.apply(input);
		
		assertTrue(o1.distance(target)>o2.distance(target));
		
		for (int i=0; i<1000; i++) {
			o1=o2;
			SimpleBackProp.train(nn, input, target, 0.1,loss);
			o2=nn.apply(input);	
			assertTrue(o1.distance(target)>o2.distance(target));
		}
		
		//System.out.println(o2);
		//System.out.println(nn.getParameters());
		assertTrue(o2.epsilonEquals(target,0.1));
		
		// zero learn factor should stop any gradient effect
		nn.getGradient().fill(0.0);
		nn.setLearnFactor(0.0);
		nn.train(input, Vector.of(1,1,1));
		assertTrue(nn.getGradient().isZeroVector());
	}

	@Test public void testTrainLoss() {	
		int LEN=3;
		Op op1=Ops.LINEAR;
		Op op2=Linear.create(2.0, 1.0);
		
		Stack c=Components.stack(new IComponent[] {
			new Operator(op1,LEN),
			new Operator(op2,LEN),});
		assertTrue(c.getGradient().isZeroVector());
		
		AVector input=Vector.of(0,1,2);
		AVector output=Vectorz.newVector(LEN);
		
		c.think(input,output);
		assertEquals(Vector.of(1,3,5),output);
		
		c.train(input, Vector.of(2,5,8));
		assertEquals(Vector.of(2,4,6),c.getOutputGradient()); // 2*(t-y)	
		assertEquals(Vector.of(4,8,12),c.getInputGradient());
		
		// learn factor shouldn't affect gradient propagation
		c.setLearnFactor(c.getLearnFactor()*10);
		c.train(input, Vector.of(2,5,8));
		assertEquals(Vector.of(2,4,6),c.getOutputGradient()); // 2*(t-y)	
		assertEquals(Vector.of(4,8,12),c.getInputGradient());
		
	

	}

}

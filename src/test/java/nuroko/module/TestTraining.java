package nuroko.module;

import static org.junit.Assert.*;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.ops.LinearOp;
import nuroko.core.Components;
import nuroko.core.IComponent;

import org.junit.Test;

public class TestTraining {

	@Test public void testTrainLoss() {	
		int LEN=3;
		Op op1=Ops.LINEAR;
		Op op2=LinearOp.create(2.0, 1.0);
		
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
	}

}

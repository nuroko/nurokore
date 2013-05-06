package nuroko.module;

import static org.junit.Assert.*;

import org.junit.Test;

import nuroko.module.loss.CrossEntropyLoss;
import nuroko.module.loss.LossFunction;
import mikera.vectorz.Vector;

public class TestLossFunctions {
	@Test 
	public void testCrossEntropy() {
		Vector o=Vector.of(0,0,0.5,0.5,1,1);
		Vector t=Vector.of(1,0,1,0,1,0);
		
		Vector g=Vector.createLength(o.length());
		g.fill(Double.NaN);
		
		LossFunction ce=CrossEntropyLoss.INSTANCE;
		
		ce.calculateErrorDerivative(o, t, g);
		double CB=1.0/CrossEntropyLoss.BOUND;
		
		assertEquals(Vector.of(CB,0,4*0.5,4*-0.5,0,-CB),g);
	}
}

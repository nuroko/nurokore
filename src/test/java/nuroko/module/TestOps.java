package nuroko.module;

import static org.junit.Assert.assertEquals;
import mikera.vectorz.Op;
import nuroko.module.ops.ScaledLogistic;

import org.junit.Test;

public class TestOps {
	private void testDerivativesAt(Op op, double... xs) {
		for (double x:xs) {
			testDerivativeAt(op,x);
		}
	}
	
	private void testDerivativeAt(Op op, double x) {
		double dx=op.derivative(x);
		double epsilon=0.001;
		double edx=(op.apply(x+epsilon)-op.apply(x-epsilon))/(2*epsilon);
		assertEquals(1.0,(dx==0)?(edx+1.0):(edx/dx),0.01);
	}
	
	private void testInversesAt(Op op, double... xs) {
		for (double x:xs) {
			testInverseAt(op,x);
		}
	}
	
	private void testInverseAt(Op op, double x) {
		double y=op.apply(x);
		double rx=op.applyInverse(y);
		
		assertEquals(y,op.apply(rx),0.001);
	}
	
	@Test public void testVAlues() {
		assertEquals(1.0,ScaledLogistic.INSTANCE.apply(1000),0.0001);
		assertEquals(0.5,ScaledLogistic.INSTANCE.apply(0),0.0001);
		assertEquals(0.0,ScaledLogistic.INSTANCE.apply(-1000),0.0001);
	}
	
	@Test public void testOpDerivatives() {
		testDerivativesAt(ScaledLogistic.INSTANCE,-1000, -100,-10, -1, 0, 1, 10, 100, 1000);
		
		testInversesAt(ScaledLogistic.INSTANCE,-100,-10,-1,0,1,10,100);
	}
}

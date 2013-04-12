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
	
	@Test public void testOpDerivatives() {
		testDerivativesAt(ScaledLogistic.INSTANCE,-1000, -100,-10, -1, 0, 1, 10, 100, 1000);
	}
}

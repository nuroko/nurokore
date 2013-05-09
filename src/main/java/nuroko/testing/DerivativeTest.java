package nuroko.testing;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import nuroko.module.AComponent;
import nuroko.module.loss.SquaredErrorLoss;
import static org.junit.Assert.*;

public class DerivativeTest {
	private static final double EPS=0.000001;
	
	public static void testDerivative(AComponent c) {
		c=c.clone();
		c.getGradient().fill(0.0);
		
		AVector t=Vector.createLength(c.getOutputLength());
		Vectorz.fillGaussian(t);
		
		AVector x=Vector.createLength(c.getInputLength());
		Vectorz.fillGaussian(x);
		
		c.train(x,t,SquaredErrorLoss.INSTANCE,1.0);
		
		AVector g=c.getGradient().clone();
		AVector p=c.getParameters().clone();
		AVector o=c.getOutput().clone();
		double L=-o.distanceSquared(t);
		
		int n=c.getParameterLength();
		for (int i=0; i< n ; i++) {
			c.getParameters().set(p);
			c.getParameters().addAt(i, EPS);
			
			AVector y=c.think(x);
			double L2=-y.distanceSquared(t);
			
			double expected=((L2-L)/EPS);
			double calculated=g.get(i);
			
			double d= expected/calculated;
			boolean ok= (d>0.9)&&(d<1.1);
			assertTrue("Gradient at position "+i+" expected="+expected+" calculated="+calculated,ok);
		}
	}
}

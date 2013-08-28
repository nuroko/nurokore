package nuroko.module;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

public class TestSparsifier {

	@Test 
	public void testSparse() {
		Vector v=Vector.of(0,1,2);
		
		Sparsifier s=new Sparsifier(3,0.5,0.1);
		
		s.train(v, v);
		
		assertEquals(v,s.getOutput());
		assertTrue(s.getOutputGradient().isZero());
		
		AVector ig=s.getInputGradient();
		assertTrue(ig.get(0)>0);
		assertTrue(ig.get(1)<0);
	}
}

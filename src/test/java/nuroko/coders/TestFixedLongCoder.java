package nuroko.coders;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;

public class TestFixedLongCoder {
	
	private static final IntegerCoder cc=new IntegerCoder(4);
	
	@Test 
	public void testLength() {
		assertEquals(4,cc.codeLength());
	}
	
	@Test 
	public void testRoundTrip() {
		for (long i=0; i<16; i++) {
			AVector v=cc.encode(i);
			assertEquals(i,(long)cc.decode(v));
		}
	}
}

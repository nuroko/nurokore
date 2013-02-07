package nuroko.coders;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class TestCharCoder {
	
	private static final CharCoder cc=new CharCoder();
	
	@Test 
	public void testLength() {
		assertEquals(8,cc.codeLength());

	}
	
	@Test 
	public void testRoundTrip() {
		AVector v=Vectorz.newVector(8);
		
		for (int i=0; i<256; i++) {
			char c=(char)i;
			cc.encode(c, v, 0);
			
			char rc=cc.decode(v,0);
			assertEquals(c,rc);
		}
	}
	
}

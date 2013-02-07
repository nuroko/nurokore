package nuroko.coders;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class TestChoiceCoder {
	
	private static final CharCoder cc=new CharCoder();
	private static final MaybeCoder<Character> mc=new MaybeCoder<Character>(cc);
	
	@Test 
	public void testLength() {
		assertEquals(1+cc.codeLength(),mc.codeLength());
	}
	
	@Test 
	public void testRoundTrip() {
		AVector v=Vectorz.newVector(mc.codeLength());
		
		for (int i=0; i<256; i++) {
			char c=(char)i;
			mc.encode(c, v, 0);
			
			Character rl=mc.decode(v,0);
			assertEquals(c,(char)rl);
		}
	}
	
	@Test 
	public void testNull() {
		AVector v=Vectorz.newVector(mc.codeLength());
		mc.encode(null, v);
		assertNull(mc.decode(v));
	}
}

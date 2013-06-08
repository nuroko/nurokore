package nuroko.coders;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDoubleCoder {
	
	private static final DoubleCoder dc=new DoubleCoder(10,2);
	
	@Test 
	public void testLength() {
		assertEquals(1,dc.codeLength());

	}
	
	@Test public void testEncode() {
		assertEquals(1.0,dc.encode(12.0).get(0),0.0001);
		assertEquals(-2.0,dc.encode(6.0).get(0),0.0001);
	}

	@Test public void testRoundTrip() {
		assertEquals(12.0,dc.decode(dc.encode(12.0)),0.0001);
		assertEquals(6.0,dc.decode(dc.encode(6.0)),0.0001);
	}

	
}

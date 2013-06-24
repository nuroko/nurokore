package nuroko.coders;

import static org.junit.Assert.assertEquals;
import mikera.vectorz.AVector;
import nuroko.core.IFunction;
import nuroko.core.NurokoException;

import org.junit.Test;

public class TestFixedStringCoder {
	
	private static final FixedStringCoder sc=new FixedStringCoder(4);
	
	@Test 
	public void testLength() {
		assertEquals(4*new CharCoder().codeLength(),sc.codeLength());
	}
	
	
	@Test 
	public void testEncodeDecodeFunctions() {
		IFunction<String, AVector> ef=new EncodeFunction<String>(sc);
		IFunction<AVector, String> df=new DecodeFunction<String>(sc);
		assertEquals("foo",df.apply(ef.apply("foo")));
	}
	
	@Test 
	public void testRoundTrip() {
		doTest("");
		doTest("foo");
		doTest("foob");
	}

	
	@Test (expected=NurokoException.class)
	public void testTooBig() {
		doTest("foobar");
	}

	private void doTest(String string) {
		AVector v=sc.encode(string);
		String s=sc.decode(v);
		assertEquals(string,s);
	}
	
}

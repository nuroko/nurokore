package nuroko.coders;

import static org.junit.Assert.*;

import java.util.Collection;

import nuroko.core.NurokoException;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class TestMaybeCoder {
	
	private static final ChoiceCoder<Integer> cc=new ChoiceCoder<Integer>(10,20,30,40);
	
	@Test 
	public void testLength() {
		assertEquals(4,cc.codeLength());
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	public void testRoundTrip() {
		AVector v=Vectorz.newVector(cc.codeLength());
		
		Collection<Integer> coll=(Collection<Integer>) cc.getChoiceList();
		
		for (Integer i : coll) {
			cc.encode(i, v);
			
			Integer result=cc.decode(v);
			assertEquals(i,result);
		}
	}
	
	@Test (expected=NurokoException.class)
	public void testNull() {
		cc.encode(null);
	}
	
	@Test (expected=NurokoException.class)
	public void testNotInList() {
		cc.encode(50);
	}
}

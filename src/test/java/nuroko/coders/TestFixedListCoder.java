package nuroko.coders;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class TestFixedListCoder {
	
	private static final CharCoder cc=new CharCoder();
	private static final ListCoder<Character> lc=new ListCoder<Character>(4,cc);
	
	@Test 
	public void testLength() {
		assertEquals(4*8,lc.codeLength());

	}
	
	@Test 
	public void testRoundTrip() {
		AVector v=Vectorz.newVector(lc.codeLength());
		
		ArrayList<Character> al=new ArrayList<Character>();
		for (int i=0; i<256; i++) {
			char c=(char)i;
			al.clear();
			al.add(c);
			al.add(c);
			al.add(c);
			al.add(c);
			lc.encode(al, v, 0);
			
			List<Character> rl=lc.decode(v,0);
			assertEquals(4,rl.size());
			assertEquals(c,(char)rl.get(0));
			assertEquals(c,(char)rl.get(3));
		}
	}
}

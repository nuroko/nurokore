package nuroko.module;

import static org.junit.Assert.*;
import mikera.vectorz.Vector;
import nuroko.core.Components;

import org.junit.Test;

public class TestStack {

	@Test public void testStackIdentity() {
		Vector v=Vector.of(1,2,3);
		Stack s=Components.stack(Components.identity(3),Components.identity(3));
		
		assertEquals(v,s.think(v));
		assertEquals(v,Components.identity(3).generate(v));
		assertEquals(v,s.generate(v));
	}
}

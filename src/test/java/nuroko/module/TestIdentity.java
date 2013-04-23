package nuroko.module;

import static org.junit.Assert.*;

import nuroko.testing.GenericModuleTests;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;

public class TestIdentity {
	@Test public void testIdentity() {
		Identity id=new Identity(3);
		
		AVector r=id.think(Vector.of(1,2,3));
		
		assertEquals(Vector.of(1,2,3),r);
		
		GenericModuleTests.test(id);
		
	}
}

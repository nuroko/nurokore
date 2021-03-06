package nuroko.module;

import static org.junit.Assert.assertEquals;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import nuroko.module.layers.SparseWeightLayer;
import nuroko.testing.GenericModuleTests;

import org.junit.Test;

public class TestSparseWeightLayer {
	@Test
	public void testFullWeightLayer() {
		SparseWeightLayer wl=new SparseWeightLayer(2,2,2);
		
		AVector params=wl.getParameters();
		int pl=params.length();
		assertEquals(6,pl);
		
		AVector input=Vectorz.createUniformRandomVector(2);
		AVector output=Vectorz.createUniformRandomVector(2);
		
		// output should be zero
		wl.think(input, output);
		assertEquals(Vector.of(0,0),output);
		
		// identity weights
		params.set(2,1);
		params.set(5,1);
		wl.think(input, output);
		assertEquals(input,output);
		
		// constant weights
		params.fill(0);
		params.set(0,0.2);
		params.set(1,0.3);
		wl.think(input, output);
		assertEquals(Vector.of(0.2,0.3),output);
		
		// all weights
		input.fill(10);
		params.fill(1);
		wl.think(input, output);
		assertEquals(Vector.of(21,21),output);
	}
	
	@Test public void testInverse() {
		SparseWeightLayer wl=new SparseWeightLayer(2,2,20);
		AVector params=wl.getParameters();
		int pl=params.length();
		assertEquals(6,pl);
		
		Vectorz.fillGaussian(params);
		
		SparseWeightLayer inv=wl.getInverse();
		AVector iparams=inv.getParameters();
		assertEquals(6,iparams.length());
		
		// diagonal maps should be equal
		assertEquals(params.get(2),iparams.get(2),0.0);
		assertEquals(params.get(5),iparams.get(5),0.0);
		
		// cross maps should be swapped
		assertEquals(params.get(3),iparams.get(4),0.0);
		assertEquals(params.get(4),iparams.get(3),0.0);
		
		

	}
	
	@Test
	public void testGeneric() {
		SparseWeightLayer wl=new SparseWeightLayer(2,2,2);
		GenericModuleTests.test(wl);
	}
}

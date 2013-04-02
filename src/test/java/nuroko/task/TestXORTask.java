package nuroko.task;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import nuroko.task.samples.XORTask;

public class TestXORTask {

	@Test 
	public void testXOR() {
		XORTask t=new XORTask(3);
		
		AVector output=Vectorz.newVector(t.getOutputLength());
		t.getTarget(Vector.of(1,0,0), output);
		assertEquals(0.0,output.get(0),0.0);
		assertEquals(1.0,output.get(1),0.0);
		
		t=new XORTask(9);
		t.getTarget(Vector.of(0,0,0,1,1,0,1,1,0), output);
		assertEquals(1.0,output.get(0),0.0);
		assertEquals(0.0,output.get(1),0.0);
	}
}

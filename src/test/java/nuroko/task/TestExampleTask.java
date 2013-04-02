package nuroko.task;

import static org.junit.Assert.*;

import java.util.ArrayList;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;

import org.junit.Test;

public class TestExampleTask {
	@Test
	public void testSingleExample() {
		
		ArrayList<AVector> inputs=new ArrayList<AVector>();
		inputs.add(Vector.of(0,1,0));
		ArrayList<AVector> outputs=new ArrayList<AVector>();
		outputs.add(Vector.of(1,1));
		
		ExampleTask t=new ExampleTask(inputs,outputs);
		assertEquals(3,t.getInputLength());
		assertEquals(2,t.getOutputLength());
		
		AVector v=Vectorz.newVector(t.getInputLength());
		t.getInput(v);
		assertEquals(Vector.of(0,1,0),v);
		
		AVector w=Vectorz.newVector(t.getOutputLength());
		t.getTarget(v,w);
		assertEquals(Vector.of(1,1),w);

	}

}

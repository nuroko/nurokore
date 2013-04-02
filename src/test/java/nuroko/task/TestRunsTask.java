package nuroko.task;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import nuroko.task.samples.RunsTask;

public class TestRunsTask {

	@Test 
	public void testXOR() {
		RunsTask t=new RunsTask(40);
		
		AVector input=Vectorz.newVector(t.getInputLength());
		AVector output=Vectorz.newVector(t.getOutputLength());
		
		t.getInput(input);
		t.getTarget(input, output);
		assertTrue(input.epsilonEquals(output));
	}
}

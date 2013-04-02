package nuroko.task.samples;

import mikera.util.Rand;
import mikera.vectorz.AVector;
import nuroko.task.BaseSupervisedTask;

public class RunsTask extends BaseSupervisedTask {

	public RunsTask (int inputs) {
		super(inputs,inputs);
	}

	@Override
	public void getInput(AVector inputOut) {
		assert(inputOut.length()==inputLength);
		double v=Rand.binary(0.5);
		for (int i=0; i<inputLength; i++) {
			if (Rand.chance(0.2)) {
				v=1.0-v;
			}
			inputOut.set(i,v);
		}
	}

	@Override
	public void getTarget(AVector input, AVector targetOut) {
		assert(targetOut.length()==outputLength);
		assert(input.length()==inputLength);
		targetOut.set(input);
	}
}

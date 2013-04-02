package nuroko.task.samples;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import nuroko.task.BaseSupervisedTask;

public class XORTask extends BaseSupervisedTask {

	public XORTask (int inputs) {
		super(inputs,2);
	}

	@Override
	public void getInput(AVector inputOut) {
		assert(inputOut.length()==inputLength);
		Vectorz.fillBinaryRandom(inputOut);
	}

	@Override
	public void getTarget(AVector input, AVector targetOut) {
		assert(targetOut.length()==outputLength);
		assert(input.length()==inputLength);
		boolean odd=false;
		for (int i=0; i<inputLength; i++) {
			if (input.get(i)>0.5) odd=!odd;
		}
		targetOut.set(0,odd?0:1);
		targetOut.set(1,odd?1:0);
	}
}

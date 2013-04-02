package nuroko.task;

import java.util.List;

import mikera.util.Rand;
import mikera.vectorz.AVector;

public class ExampleTask extends BaseSupervisedTask {
	private final List<AVector> inputs;
	private final List<AVector> outputs;
	
	private int current=0;
	
	public ExampleTask(List<AVector> inputs, List<AVector> outputs) {
		super(inputs.get(0).length(),outputs.get(0).length());
		
		this.inputs=inputs;
		this.outputs=outputs;
	}
	
	@Override
	public void getInput(AVector inputOut) {
		int n=inputs.size();
		assert(n==outputs.size());
		
		current=Rand.r(n);
		
		inputOut.set(inputs.get(current));
	}
	
	public void getInput(AVector inputOut, int i) {
		current=i;
		
		inputOut.set(inputs.get(current));
	}

	@Override
	public void getTarget(AVector input, AVector targetOut) {
		targetOut.set(outputs.get(current));
	}

}

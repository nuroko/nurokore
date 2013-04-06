package nuroko.core;

import mikera.vectorz.AVector;

public interface IOutputState extends IOutput {

	public void setOutput(AVector outputValues);
	
	public AVector getOutput();
	
	public AVector getOutputGradient();
}

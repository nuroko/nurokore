package nuroko.core;

import mikera.vectorz.AVector;

public interface IOutputState extends IOutput {

	public void setOutput(AVector input);
	
	public AVector getOutput();
	
	public AVector getOutputGradient();
}

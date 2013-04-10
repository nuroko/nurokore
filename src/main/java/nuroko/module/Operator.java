package nuroko.module;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;

public class Operator extends AOperationComponent {
	private final Op op;

	public Operator(Op op, int length) {
		super(length);
		this.op=op;
	}

	@Override
	public void thinkInternal() {
		AVector output=getOutput();
		output.set(getInput());
		op.applyTo(output);
	}
	
	@Override
	public void trainGradientInternal(double factor) {
		Vector ig=getInputGradient();
		ig.fill(0.0);
		Vector output=getOutput();		
		Vector gradient=getOutputGradient();
		int len=ig.length();
		for (int i=0; i<len; i++) {
			double y=output.get(i);
			ig.addAt(i, gradient.get(i)*op.derivativeForOutput(y));
		}	
	}




	@Override
	public Operator clone() {
		return new Operator(op,length);
	}

	@Override 
	public boolean isStochastic() {
		return op.isStochastic();
	}



}

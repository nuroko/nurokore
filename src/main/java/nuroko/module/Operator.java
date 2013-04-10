package nuroko.module;

import java.util.Collections;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;
import mikera.vectorz.impl.Vector0;
import nuroko.core.IComponent;

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
	public List<IComponent> getComponents() {
		return Collections.EMPTY_LIST;
	}


	@Override
	public int getOutputLength() {
		return length;
	}

	@Override
	public AVector getGradient() {
		return Vector0.INSTANCE;
	}
	
	@Override
	public AVector getParameters() {
		return Vector0.INSTANCE;
	}

	@Override
	public Operator clone() {
		return new Operator(op,length);
	}

	@Override
	public int getInputLength() {
		return length;
	}

	@Override 
	public boolean isStochastic() {
		return op.isStochastic();
	}



}

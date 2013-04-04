package nuroko.module;

import java.util.Collections;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.impl.Vector0;
import nuroko.core.IModule;

public class OpComponent extends AInputStateComponent {
	private final int length;
	private final Op op;

	public OpComponent(int inputLength, Op op) {
		super(inputLength);
		this.length=inputLength;
		this.op=op;
	}

	@Override
	public void thinkInternal(AVector output) {
		output.set(getInput());
		op.applyTo(output);
	}

	@Override
	public List<? extends IModule> getComponents() {
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
	public OpComponent clone() {
		return new OpComponent(length,op);
	}

	@Override
	public int getInputLength() {
		return length;
	}
}

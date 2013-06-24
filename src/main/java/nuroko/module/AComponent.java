package nuroko.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.IComponent;
import nuroko.core.IConstraint;
import nuroko.core.IInputState;
import nuroko.core.IModule;
import nuroko.module.loss.LossFunction;
import nuroko.module.loss.SquaredErrorLoss;

public abstract class AComponent implements IComponent , Iterable<IComponent> {
	
	// learn rate multiplier for entire component
	private double learnFactor=1.0;
	
	private ArrayList<IConstraint> constraints=new ArrayList<IConstraint>();

	public IComponent topComponent() {
		return this;
	}
	
	@Override
	public void think(AVector input, AVector output) {
		setInput(input);
		thinkInternal();
		if (output!=null) {
			output.set(getOutput());
		}
	}
	
	@Override
	public void generate(AVector input, AVector output) {
		getDownStack().think(output,input);
	}
	
	@Override
	public IComponent getUpStack() {
		return this;
	}
	
	@Override
	public IComponent getDownStack() {
		throw new UnsupportedOperationException("Synthesising not supported by: "+this.getClass());
	}
	

	
	@Override
	public void thinkInternalTraining() {
		thinkInternal();
	}
	
	@Override
	public AVector apply(AVector input) {
		Vector output=Vector.createLength(getOutputLength());
		think(input,output);
		return output;
	}
	
	public void setConstraint(IConstraint con) {
		constraints.clear();
		constraints.add(con);
	}
	
	public ArrayList<IConstraint> getConstraints() {
		return constraints;
	}
	
	public final AVector generate(AVector output) {
		Vector input=Vector.createLength(getInputLength());
		generate(input,output);
		return input;
	}
	
	@Override
	public LossFunction getDefaultLossFunction() {
		return SquaredErrorLoss.INSTANCE;
	}

	public final double getLearnFactor() {
		return learnFactor;
	}
	
	public final void setLearnFactor(double value) {
		learnFactor=value;
	}

	public final void train(AVector input, AVector target) {
		train(input,target,getDefaultLossFunction(),1.0);
	}
	
	public final void trainSynth(AVector input) {
		trainSynth(input,1.0);	
	}
	
	public void trainSynth(AVector input, double factor) {
		setInput(input);
		thinkInternalTraining();
		this.getOutputGradient().fill(0.0);
		trainGradientInternal(factor);	
	}
	
	public final void train(AVector input, AVector target, LossFunction loss, double factor) {
		setInput(input);
		thinkInternalTraining();
		loss.calculateErrorDerivative(getOutput(), target, this);
		trainGradientInternal(factor);
	}
	
	/**
	 * Abstract method for training gradients. 
	 * 
	 * Assumes use of the current activation states for all inputs/outputs.
	 * 
	 * Should overwrite inputGradient, and adjust gradient
	 * for all parameters. Should apply own internal learn rate factor
	 */
	public abstract void trainGradientInternal(double factor);
	
	@Override
	public void initRandom() {
		for (IComponent c: getComponents()) {
			c.initRandom();
		}
	}
	
	@Override 
	public void setInput(AVector inputValues) {
		getInput().set(inputValues);
	}
	
	@Override
	public void setOutput(AVector outputValues) {
		getOutput().set(outputValues);
	}
	
	@Override
	public int getParameterLength() {
		return getParameters().length();
	}
	
	public IComponent getComponent(int i) {
		return getComponents().get(i);
	}
	
	public List<IModule> getModules() {
		ArrayList<IModule> al=new ArrayList<IModule>();
		al.addAll(getComponents());
		return al;
	}
	
	@Override 
	public IInputState getInputState() {
		return this;
	}
	
	@Override
	public int getInputLength() {
		return getInput().length();
	}
	
	@Override
	public int getOutputLength() {
		return getOutput().length();
	}
	
	@Override 
	public boolean isStochastic() {
		return false;
	}
	
	@Override
	public boolean isSynthesiser() {
		return false;
	}

	
	protected void applyConstraintsInternal() {
		for (IConstraint c: constraints) {
			c.applyTo(this);
		}
	}
	
	public final void applyConstraints() {
		applyConstraintsInternal();
		for (IComponent c: getComponents()) {
			c.applyConstraints();
		}
	}
	
	public AComponent clone() {
		AComponent c=null;
		try {
			c=(AComponent) super.clone();
			c.constraints=(ArrayList<IConstraint>) this.constraints.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return c;
	}
	

	@Override
	public Iterator<IComponent> iterator() {
		return getComponents().iterator();
	}
}

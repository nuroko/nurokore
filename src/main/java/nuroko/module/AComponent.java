package nuroko.module;

import java.util.ArrayList;
import java.util.List;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.IComponent;
import nuroko.core.IInputState;
import nuroko.core.IModule;
import nuroko.module.loss.LossFunction;
import nuroko.module.loss.SquaredErrorLoss;

public abstract class AComponent implements IComponent {

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
	
	public void thinkInternalTraining() {
		thinkInternal();
	}
	
	@Override
	public AVector think(AVector input) {
		Vector output=Vector.createLength(getOutputLength());
		think(input,output);
		return output;
	}
	
	public void train(AVector input, AVector target) {
		train(input,target,SquaredErrorLoss.INSTANCE,1.0);
	}
	
	public void train(AVector input, AVector target, LossFunction loss, double factor) {
		setInput(input);
		thinkInternalTraining();
		loss.calculateErrorDerivative(getOutput(), target, this);
		trainGradientInternal(factor);
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
	
	public abstract AComponent clone();
}

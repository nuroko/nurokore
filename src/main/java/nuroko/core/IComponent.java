package nuroko.core;

import java.util.List;

import nuroko.core.IInputState;
import nuroko.module.loss.LossFunction;

public interface IComponent extends ITrainable, IInputState, IOutputState {

	public IComponent clone();

	public IInputState getInputState();
	
	/**
	 * Thinks within the scope of the component. Updates component's output.
	 */
	public void thinkInternal();
	
	/**
	 * Thinks within the scope of the component. Update's component's output.
	 * May be different from regular thinking.
	 */
	public void thinkInternalTraining();
	
	/**
	 * Returns the default loss function that should be used
	 * @return
	 */
	public LossFunction getDefaultLossFunction();

	public void trainGradientInternal(double factor);
	
	public double getLearnFactor();

	public boolean isStochastic();
	
	public void applyConstraints();
	
	public List<IComponent> getComponents();

	/**
	 * Randomly initialises a component's parameters
	 */
	public void initRandom();

}

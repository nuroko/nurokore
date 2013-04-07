package nuroko.core;

import java.util.List;

import nuroko.core.IInputState;

public interface IComponent extends ITrainable, IGradientTrainable, IInputState, IOutputState {

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

	public void trainGradientInternal(double factor);

	public boolean isStochastic();
	
	public List<IComponent> getComponents();

}

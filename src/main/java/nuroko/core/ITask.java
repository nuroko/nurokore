package nuroko.core;

import mikera.vectorz.AVector;

public interface ITask extends Cloneable {
	/**
	 * Returns the input size of the task
	 * @return
	 */
	public int getInputLength();
	
	/**
	 * Returns the output size of the task
	 * @return
	 */
	public int getOutputLength();
	
	/**
	 * Resets the task to a random initial state
	 */
	public void reset();
	
	/**
	 * Gets the input vector for the task, advancing the task state to the next stage
	 * @param input
	 */
	public void getInput(AVector inputOut);
	
	/**
	 * Gets the target vector for the task
	 * @param output
	 */
	public void getTarget(AVector input, AVector targetOut);
	
	/**
	 * Returns the evaluation of the given output vector
	 * @param output
	 * @return
	 */
	public double getEvaluation(AVector input, AVector output);
	
	/**
	 * Returns true if the task execution has finished
	 * @return
	 */
	public boolean isComplete();
	
	public ITask clone();
}

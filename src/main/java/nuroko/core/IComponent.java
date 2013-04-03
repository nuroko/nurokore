package nuroko.core;

public interface IComponent extends IInputState, IThinker, IParameterised {

	public IComponent clone();
}

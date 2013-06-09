package nuroko.function;

import nuroko.core.IFunction;

public abstract class AbstractFunction<A,B> implements IFunction<A,B> {

	@Override
	public abstract B think(A input);

}

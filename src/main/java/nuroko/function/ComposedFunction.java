package nuroko.function;

import nuroko.core.IFunction;

public class ComposedFunction <A,B> extends AFunction <A,B> {

	public final IFunction<A,Object> first;
	public final IFunction<Object,B> second;
	
	@SuppressWarnings("unchecked")
	public ComposedFunction(IFunction<A,?> a, IFunction<?,B> b) {
		first=(IFunction<A, Object>) a;
		second=(IFunction<Object, B>) b;
		
	}
	
	@Override
	public B apply(A input) {
		return second.apply(first.apply(input));
	}

}

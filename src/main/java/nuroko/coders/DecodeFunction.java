package nuroko.coders;

import mikera.vectorz.AVector;
import nuroko.core.ICoder;
import nuroko.core.IFunction;

public class DecodeFunction<T> implements IFunction <AVector,T>{

	private final ICoder<T> coder;
	
	public DecodeFunction(ICoder<T> c) {
		coder=c;
	}
	
	@Override
	public T think(AVector input) {
		return coder.decode(input, 0);
	}

}

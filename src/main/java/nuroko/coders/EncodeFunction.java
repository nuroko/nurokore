package nuroko.coders;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.ICoder;
import nuroko.core.IFunction;

public class EncodeFunction<T> implements IFunction <T,AVector>{

	private final ICoder<T> coder;
	
	public EncodeFunction(ICoder<T> c) {
		coder=c;
	}
	
	@Override
	public AVector think(T input) {
		Vector v=Vector.createLength(coder.codeLength());
		coder.encode(input, v, 0) ;
		return v;
	}
}

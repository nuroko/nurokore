package nuroko.coders;

import mikera.vectorz.AVector;
import nuroko.core.ICoder;
import nuroko.core.IDecoder;
import nuroko.core.IFunction;

public class DecodeFunction<T> implements IFunction <AVector,T>, IDecoder<T> {

	private final ICoder<T> coder;
	
	public DecodeFunction(ICoder<T> c) {
		coder=c;
	}
	
	@Override
	public T think(AVector input) {
		return coder.decode(input, 0);
	}

	@Override
	public T decode(AVector v, int offset) {
		return coder.decode(v, offset);
	}

	@Override
	public int codeLength() {
		// TODO Auto-generated method stub
		return 0;
	}

}

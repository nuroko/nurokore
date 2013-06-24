package nuroko.coders;

import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import nuroko.core.ICoder;
import nuroko.core.IEncoder;
import nuroko.core.IFunction;

public class EncodeFunction<T> implements IFunction <T,AVector>, IEncoder<T> {

	private final ICoder<T> coder;
	
	public EncodeFunction(ICoder<T> c) {
		coder=c;
	}
	
	@Override
	public AVector apply(T input) {
		Vector v=Vector.createLength(coder.codeLength());
		coder.encode(input, v, 0) ;
		return v;
	}

	@Override
	public void encode(T object, AVector dest, int offset) {
		coder.encode(object, dest, offset);
	}

	@Override
	public int codeLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AVector createOutputVector() {
		// TODO Auto-generated method stub
		return null;
	}
}

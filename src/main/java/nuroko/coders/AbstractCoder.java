package nuroko.coders;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import nuroko.core.ICoder;

/**
 * Abstract base class for coders
 *
 * @param <T>
 */
public abstract class AbstractCoder<T> implements ICoder<T> {

	@Override
	public abstract T decode(AVector v, int offset);
	
	@Override
	public abstract void encode(T object, AVector dest, int offset) ;
	
	@Override
	public abstract int codeLength();
	
	public AVector encode(T object) {
		AVector v=Vectorz.newVector(codeLength());
		encode(object,v,0);
		return v;
	}
	
	public void encode(T object, AVector dest) {
		encode(object,dest,0);
	}
	
	public T decode(AVector v) {
		return decode(v,0);
	}
	
	public AVector createOutputVector() {
		return Vectorz.newVector(codeLength());
	}

}

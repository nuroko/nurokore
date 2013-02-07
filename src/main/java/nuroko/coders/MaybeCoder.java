package nuroko.coders;

import mikera.vectorz.AVector;
import nuroko.core.ICoder;

public class MaybeCoder<T> extends AbstractCoder<T> {
	final ICoder<T> coder;
	
	public MaybeCoder(ICoder<T> coder) {
		this.coder=coder;
	}
	
	@Override 
	public int codeLength() {
		return coder.codeLength()+1;
	}

	@Override
	public T decode(AVector v, int offset) {
		double nv=v.get(offset);
		if (nv>=0.5) {
			return coder.decode(v, offset+1);
		}
		return null;
	}

	@Override
	public void encode(T object, AVector dest, int offset) {
		if (object==null) {
			dest.fillRange(offset,codeLength(),0.0);
		} else {
			dest.set(offset,1.0);
			coder.encode(object,dest,offset+1);
		}
	}
}

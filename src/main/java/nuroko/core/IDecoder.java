package nuroko.core;

import mikera.vectorz.AVector;

public interface IDecoder<T> {
	/**
	 * Decodes an object from the given vector
	 * 
	 * @param v
	 * @return
	 */
	public T decode(AVector v, int offset);
	
	public int codeLength();
}

package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface for a decoder that converts a vector of double values into an
 * object of the appropriate type T.
 * 
 * Should be stateless and/or immutable
 * 
 * @author Mike
 *
 * @param <T>
 */
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

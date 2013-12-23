package nuroko.core;

import mikera.vectorz.AVector;

/**
 * Interface for an encoder that converts an object of type T
 * into a vector of double values
 * 
 * Instances should be stateless and/or immutable
 * 
 * @author Mike
 *
 * @param <T>
 */
public interface IEncoder<T> {
	/**
	 * Encodes an object into the given destination vector
	 * @param object The object to encode
	 * @param dest The destination vector
	 */
	public void encode(T object, AVector dest, int offset);
	
	/**
	 * Returns the length of output vector produced by this encoder
	 * @return
	 */
	public int codeLength();
	
	/**
	 * Create a new vector with a length necessary to store the output of this encoder
	 * @return
	 */
	public AVector createOutputVector();
}

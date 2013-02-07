package nuroko.core;

import mikera.vectorz.AVector;

public interface IEncoder<T> {
	/**
	 * Encodes an object into the given destination vector
	 * @param object
	 * @param dest
	 */
	public void encode(T object, AVector dest, int offset);
	
	/**
	 * Returns the length of output vector produced by this encoder
	 * @return
	 */
	public int codeLength();
	
	public AVector createOutputVector();
}

package nuroko.core;

/**
 * Interface for an object that handles both encoding and decoding of values
 * 
 * Coders must be stateless and/or immutable, as it is expected that they we be re-used
 * by concurrent code.
 * 
 * @author Mike
 *
 * @param <T>
 */
public interface ICoder<T> extends IDecoder<T>, IEncoder<T> {
	// no extra methods
}

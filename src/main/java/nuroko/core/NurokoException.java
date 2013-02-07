package nuroko.core;

@SuppressWarnings("serial")
public class NurokoException extends RuntimeException {

	public NurokoException(String string) {
		super(string);
	}

	public NurokoException(Throwable e) {
		super(e);
	}
}

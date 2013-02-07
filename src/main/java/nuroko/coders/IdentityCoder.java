package nuroko.coders;

import mikera.vectorz.AVector;

public final class IdentityCoder extends AbstractCoder<AVector> {

	protected final int size;

	public IdentityCoder(int size) {
		this.size=size;
	}
	
	@Override
	public AVector decode(AVector v, int offset) {
		assert(v.length()==size);
		return v.clone();
	}

	@Override
	public void encode(AVector object, AVector dest, int offset) {
		assert(object.length()==size);
		object.copyTo(dest,offset);
	}

	@Override
	public int codeLength() {
		return size;
	}

}

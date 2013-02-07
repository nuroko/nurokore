package nuroko.coders;

import mikera.vectorz.AVector;

public class FixedLongCoder extends AbstractCoder<Long> {
	private final int bits;
	
	public FixedLongCoder(int bits) {
		this.bits=bits;
	}
	
	@Override
	public Long decode(AVector v, int offset) {
		long value=0;
		for (int i=0; i<bits; i++) {
			value<<=1;
			if (v.get(i)>=0.5) value|=1L;
		}
		return value;
	}

	@Override
	public void encode(Long value, AVector dest, int offset) {
		long mask=(1<<bits)-1;
		long v=value & mask;
		long hibit=(1<<(bits-1));
		
		for (int i=0; i<bits; i++) {
			dest.set(i, (v&hibit)==0?0.0:1.0);
			v<<=1;
		}
	}

	@Override
	public int codeLength() {
		return bits;
	}
}

package nuroko.coders;

import mikera.vectorz.AVector;

public class DoubleCoder extends AbstractCoder<Number> {

	private final double mean;
	private final double sd;

	public DoubleCoder(double mean, double sd) {
		this.mean=mean;
		this.sd=sd;
	}
	
	@Override
	public Number decode(AVector v, int offset) {
		return mean+(sd*v.get(offset));
	}

	@Override
	public void encode(Number c, AVector dest, int offset) {
		encode(c.doubleValue(),dest,offset);	
	}

	@Override
	public int codeLength() {
		return 1;
	}
}

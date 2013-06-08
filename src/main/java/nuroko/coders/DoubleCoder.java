package nuroko.coders;

import nuroko.core.NurokoException;
import mikera.vectorz.AVector;

public class DoubleCoder extends AbstractCoder<Double> {

	private final double mean;
	private final double sd;

	public DoubleCoder(double mean, double sd) {
		this.mean=mean;
		this.sd=sd;
		if (sd==0.0) throw new NurokoException("Can't create DoubleCoder with zero sd");
	}
	
	@Override
	public Double decode(AVector v, int offset) {
		return mean+(sd*v.get(offset));
	}

	@Override
	public void encode(Double c, AVector dest, int offset) {
		dest.set(offset,(c-mean)/sd);	
	}

	@Override
	public int codeLength() {
		return 1;
	}
}

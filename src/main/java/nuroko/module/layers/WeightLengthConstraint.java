package nuroko.module.layers;

import mikera.vectorz.AVector;
import nuroko.core.IComponent;
import nuroko.core.NurokoException;
import nuroko.module.AWeightLayer;

public class WeightLengthConstraint extends AConstraint {

	private double maxLength;

	public WeightLengthConstraint(double maxLength) {
		this.maxLength=maxLength;
	}

	@Override
	public void applyTo(IComponent c) {
		if (!(c instanceof AWeightLayer)) throw new NurokoException("Invalid component!");
		
		AWeightLayer wl=(AWeightLayer)c;
		
		int ol=wl.getOutputLength();
		for (int i=0; i<ol; i++) {
			AVector v=wl.getSourceWeights(i);
			double len=v.magnitude();
			if (len>maxLength) {
				v.multiply(maxLength/len);
			}
		}
	}
}

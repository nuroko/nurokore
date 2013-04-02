package nuroko.core;

import mikera.util.Rand;
import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;

public class Util {

	public static void addNoise(AVector v, double noiseProportion, double noiseMean) {
		int n=v.length();
		for (int j=0; j<n; j++) {
			if (Rand.chance(noiseProportion)) {
				v.set(j,Rand.chance(noiseMean)?1.0:0.0);
			}
		}
	}
	
	public static void addNoise(AVector v, double minNoiseProportion, double maxNoiseProportion, double noiseMean) {
		int n=v.length();
		double noiseProportion=minNoiseProportion+(Rand.nextDouble()*(maxNoiseProportion-minNoiseProportion));
		
		for (int j=0; j<n; j++) {
			if (Rand.chance(noiseProportion)) {
				v.set(j,Rand.chance(noiseMean)?1.0:0.0);
			}
		}
	}

	/**
	 * Scales a gradient vector by the derivatives of the specified op outputs
	 * @param op
	 * @param outputs
	 * @param gradToScale
	 */
	public static void scaleByDerivative(Op op, Vector outputs, Vector gradToScale) {
		int len=gradToScale.length();
		assert(len==outputs.length());
		double[] data=gradToScale.array;
		for (int i=0; i<len; i++) {
			data[i]*=op.derivativeForOutput(outputs.get(i));
		}
	}

	public static void randomizeBinary(double[] array) {
		int len=array.length;
		for (int i=0; i<len; i++) {
			array[i]=(Rand.nextDouble()<array[i])?1.0:0.0;
		}
	}
}

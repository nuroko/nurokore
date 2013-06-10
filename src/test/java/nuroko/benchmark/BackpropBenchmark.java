package nuroko.benchmark;

import nuroko.algo.MomentumBackProp;
import nuroko.algo.SimpleBackProp;
import nuroko.core.Components;
import nuroko.module.AComponent;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

import mikera.vectorz.AVector;
import mikera.vectorz.Ops;
import mikera.vectorz.Vectorz;

/**
 * Caliper based benchmarks
 * 
 * @author Mike
 */

public class BackpropBenchmark extends SimpleBenchmark {
	private static final int VECTOR_SIZE=200;
	
	AVector input=Vectorz.createUniformRandomVector(VECTOR_SIZE);
	AVector output=Vectorz.createUniformRandomVector(VECTOR_SIZE);
	private static final AComponent nn1=Components.neuralLayer(VECTOR_SIZE, VECTOR_SIZE, Ops.LOGISTIC, true);
	private static final AComponent nn2=Components.neuralLayer(VECTOR_SIZE, VECTOR_SIZE, Ops.LOGISTIC, false);
	private static final AComponent nn3=Components.stack(nn1,nn2);
	
	
	public void timeFullyConnectedBackprop(int runs) {
		for (int i=0; i<runs; i++) {
			SimpleBackProp.train(nn1, input, output, 0.001);
		}
	}
	
	public void timeSparseConnectedBackprop(int runs) {
		for (int i=0; i<runs; i++) {
			SimpleBackProp.train(nn2, input, output, 0.001);
		}
	}
	
	public void timeStackedBackprop(int runs) {
		for (int i=0; i<runs; i++) {
			SimpleBackProp.train(nn3, input, output, 0.001);
		}
	}
	
	public void timeMomentumBackprop(int runs) {
		MomentumBackProp m=new MomentumBackProp(nn3,0.9);
		for (int i=0; i<runs; i++) {
			m.train(nn3, input, output, 0.001);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BackpropBenchmark().run();
	}

	private void run() {
		Runner runner=new Runner();
		runner.run(new String[] {this.getClass().getCanonicalName()});
	}

}
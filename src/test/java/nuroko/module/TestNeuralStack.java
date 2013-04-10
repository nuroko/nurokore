package nuroko.module;

import static org.junit.Assert.*;
import mikera.vectorz.AVector;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.Op;
import nuroko.core.Components;
import nuroko.module.layers.FullWeightLayer;
import nuroko.module.loss.CrossEntropyLoss;
import nuroko.module.loss.SquaredErrorLoss;

import org.junit.Test;

public class TestNeuralStack {
	
	@Test 
	public void genericTests() {
		AWeightLayer wl=new FullWeightLayer(2,2);
		NeuralNet ns=new NeuralNet(wl);

		GenericModuleTests.test(wl);
		GenericModuleTests.test(ns);
		
		Vectorz.fillGaussian(ns.getParameters());

		GenericModuleTests.test(wl);
		GenericModuleTests.test(ns);
	}
	
	@Test 
	public void equivalenceTests() {
		AWeightLayer wl1=new FullWeightLayer(2,2);
		Vectorz.fillGaussian(wl1.getParameters());
		
		AWeightLayer wl2=new FullWeightLayer(2,2);
		Vectorz.fillGaussian(wl1.getParameters());
		
		NeuralNet ns=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.LOGISTIC,Ops.LOGISTIC);
		
		Stack ss=Components.stack(new NeuralNet(wl1.clone(),Ops.LOGISTIC),new NeuralNet(wl2.clone(),Ops.LOGISTIC));
		
		AVector input=Vectorz.createUniformRandomVector(2);
		assertEquals(ns.think(input),ss.think(input));
	}
	
	@Test 
	public void testMininal() {
		Op op=Ops.LOGISTIC;
		
		AWeightLayer wl=new FullWeightLayer(2,2);
		NeuralNet ns=new NeuralNet(wl);
		
		assertEquals(CrossEntropyLoss.INSTANCE, ns.getDefaultLossFunction());
		
		assertEquals(6,wl.getParameterLength());
		assertEquals(6,ns.getParameterLength());
		assertEquals(6,ns.getGradient().length());
		
		assertEquals(1,ns.getLayerCount());
		assertEquals(wl,ns.getLayer(0));
		
		AVector input=Vectorz.newVector(2);
		AVector output=Vectorz.newVector(2);

		// should think to 0.5 for default logistic output
		Vectorz.fillGaussian(input);
		Vectorz.fillGaussian(output);
		ns.think(input, output);
		assertEquals(Vector.of(0.5,0.5),output);

		// setup a simple situation
		AVector p=ns.getParameters();
		p.set(1,0.5);
		p.set(2,1.0);
		ns.think(input, output);
		assertEquals(op.apply(input.get(0)),output.get(0),0.00001); // identity
		assertEquals(op.apply(0.5),output.get(1),0.00001); // constant bias 
		
		// calculate a gradient
		AVector target=Vectorz.newVector(2);
		target.fill(1.0);

		assertTrue(ns.getOutputGradient().isZeroVector());
		
		// do training
		double v=input.get(0);
		ns.train(input, target,SquaredErrorLoss.INSTANCE,1.0);
		
		// output gradient signal (squared error loss)
		AVector og=ns.getOutputGradient();
		assertEquals(2,og.length());
		assertEquals(2*(1-op.apply(0.5)),og.get(1),0.00001);
		assertEquals(2*(1-op.apply(v)),og.get(0),0.00001);
		
		// check gradient values
		AVector g=ns.getGradient();
		assertEquals(2*(1-op.apply(0.5))*op.derivative(0.5),g.get(1),0.000001);
		assertEquals(2*(1-op.apply(v))*op.derivative(v),g.get(0),0.000001);
		assertEquals(2*(1-op.apply(v))*v*op.derivative(v),g.get(2),0.000001);
		
		// input gradient signal
		AVector ig=ns.getInputGradient();
		assertEquals(input.length(),ig.length());
		assertEquals(2*(1-op.apply(v))*op.derivative(v),ig.get(0),0.000001);
		assertEquals(0.0,ig.get(1),0.000001);
	}
}

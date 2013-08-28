package nuroko.module;

import static org.junit.Assert.*;
import mikera.vectorz.AVector;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.Op;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.module.layers.FullWeightLayer;
import nuroko.module.layers.SparseWeightLayer;
import nuroko.module.loss.CrossEntropyLoss;
import nuroko.module.loss.SquaredErrorLoss;
import nuroko.testing.DerivativeTest;
import nuroko.testing.GenericModuleTests;

import org.junit.Test;

public class TestNeuralStack {
	
	@Test 
	public void genericTests() {
		AWeightLayer wl=new FullWeightLayer(2,2);
		NeuralNet ns=new NeuralNet(wl);

		GenericModuleTests.test(wl);
		GenericModuleTests.test(ns);
		
		Vectorz.fillGaussian(ns.getParameters());
		
		DerivativeTest.testDerivative(ns);

		GenericModuleTests.test(wl);
		GenericModuleTests.test(ns);
	}
	
	public void isEquivalentParameters(IComponent a, IComponent b) {
		assertEquals(a.getParameters(),b.getParameters());
	}
	
	public void isEquivalentThinker(IComponent a, IComponent b) {
		AVector input=Vectorz.createUniformRandomVector(a.getInputLength());
		assertEquals( a.think(input),b.think(input));
	}
	
	public void isEquivalentTraining(IComponent a, IComponent b) {
		AVector input=Vectorz.createUniformRandomVector(a.getInputLength());
		AVector target=Vectorz.createUniformRandomVector(a.getOutputLength());
		
		a.train(input,target);
		b.train(input,target);
		
		assertEquals(a.getInputGradient(),b.getInputGradient());
		assertEquals(a.getOutputGradient(),b.getOutputGradient());
		assertEquals(a.getGradient(),b.getGradient());
		
		a.train(input,target);
		b.train(input,target);

		assertEquals(a.getInputGradient(),b.getInputGradient());
		assertEquals(a.getOutputGradient(),b.getOutputGradient());
		assertEquals(a.getGradient(),b.getGradient());
	}
	
	public void testEquivalence(IComponent a, IComponent b) {
		
		isEquivalentParameters(a,b);
		isEquivalentThinker(a,b);
		isEquivalentTraining(a,b);
		
	}
	
	@Test 
	public void equivalenceTests() {
		AWeightLayer wl1=new FullWeightLayer(2,2);
		Vectorz.fillGaussian(wl1.getParameters());
		
		AWeightLayer wl2=new FullWeightLayer(2,2);
		Vectorz.fillGaussian(wl2.getParameters());
		
		NeuralNet ns=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.LOGISTIC,Ops.LOGISTIC);	
		Stack ss=Components.stack(new NeuralNet(wl1.clone(),Ops.LOGISTIC),new NeuralNet(wl2.clone(),Ops.LOGISTIC));
		testEquivalence(ns,ss);
		
		NeuralNet ns2=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.SOFTPLUS,Ops.TANH);	
		Stack ss2=Components.stack(new NeuralNet(wl1.clone(),Ops.SOFTPLUS),new Identity(wl1.getOutputLength()), new NeuralNet(wl2.clone(),Ops.TANH));
		testEquivalence(ns2,ss2);	
	}
	
	@Test 
	public void equivalenceTestsWithBias() {
		AWeightLayer wl1=new FullWeightLayer(2,2);
		Vectorz.fillGaussian(wl1.getParameters());
		
		Bias b=new Bias(2);
		b.getParameters().set(wl1.getBias());
		AWeightLayer wl2=wl1.clone();
		wl2.getBias().fill(0.0);
		AComponent c1=Components.stack(wl2,b);
		
		isEquivalentThinker(wl1,c1);
	}
	
	@Test 
	public void equivalenceTestsSparse() {
		AWeightLayer wl1=new SparseWeightLayer(5,5,3);
		Vectorz.fillGaussian(wl1.getParameters());
		
		AWeightLayer wl2=new SparseWeightLayer(5,5,3);
		Vectorz.fillGaussian(wl2.getParameters());
		
		NeuralNet ns=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.SCALED_LOGISTIC,Ops.SCALED_LOGISTIC);	
		Stack ss=Components.stack(new NeuralNet(wl1.clone(),Ops.SCALED_LOGISTIC),new NeuralNet(wl2.clone(),Ops.SCALED_LOGISTIC));
		testEquivalence(ns,ss);

		DerivativeTest.testDerivative(ns);

		NeuralNet ns2=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.SOFTPLUS,Ops.TANH);	
		Stack ss2=Components.stack(new NeuralNet(wl1.clone(),Ops.SOFTPLUS),new NeuralNet(wl2.clone(),Ops.TANH));
		testEquivalence(ns2,ss2);	
		
		DerivativeTest.testDerivative(ns2);
	}
	
	@Test 
	public void equivalenceTestsOps() {
		AWeightLayer wl1=new SparseWeightLayer(5,5,3);
		Vectorz.fillGaussian(wl1.getParameters());
		
		AWeightLayer wl2=new SparseWeightLayer(5,5,3);
		Vectorz.fillGaussian(wl2.getParameters());
		
		NeuralNet ns=new NeuralNet(new AWeightLayer[] {wl1.clone(),wl2.clone()},Ops.SOFTPLUS,Ops.SCALED_LOGISTIC);	
		Stack ss=Components.stack(
				wl1.clone(),
				Components.operator(Ops.SOFTPLUS, wl1.getOutputLength()),
				wl2.clone(),
				Components.operator(Ops.SCALED_LOGISTIC, wl2.getOutputLength()));
		testEquivalence(ns,ss);
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
		p.set(5,0.5); // second bias
		p.set(0,1.0); // first weight
		ns.think(input, output);
		assertEquals(op.apply(input.get(0)),output.get(0),0.00001); // identity
		assertEquals(op.apply(0.5),output.get(1),0.00001); // constant bias 
		
		// calculate a gradient
		AVector target=Vectorz.newVector(2);
		target.fill(1.0);

		assertTrue(ns.getOutputGradient().isZero());
		
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
		assertEquals(2*(1-op.apply(0.5))*op.derivative(0.5),g.get(5),0.000001);
		assertEquals(2*(1-op.apply(v))*op.derivative(v),g.get(4),0.000001);
		assertEquals(2*(1-op.apply(v))*v*op.derivative(v),g.get(0),0.000001);
		
		// input gradient signal
		AVector ig=ns.getInputGradient();
		assertEquals(input.length(),ig.length());
		assertEquals(2*(1-op.apply(v))*op.derivative(v),ig.get(0),0.000001);
		assertEquals(0.0,ig.get(1),0.000001);
	}
}

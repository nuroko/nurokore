package nuroko.module;

import static org.junit.Assert.*;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Ops;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.ops.Linear;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.module.loss.CrossEntropyLoss;
import nuroko.testing.GenericModuleTests;

import org.junit.Test;

public class TestComponents {
	
	@Test public void testTrainingOp () {
		AVector v=Vector.of(1,2,3);
		int LEN=v.length();
		IComponent op1=new TrainingOp(LEN,Ops.NEGATE);

		AVector r=op1.think(v);
		assertEquals(v,r);
		
		op1.thinkInternalTraining();
		assertEquals(Vector.of(-1,-2,-3),op1.getOutput());
	}

	@Test public void testStack() {	
		int LEN=3;
		Op op1=Ops.LINEAR;
		Op op2=Linear.create(2.0, 1.0);
		
		Stack c=Components.stack(new IComponent[] {
			new Operator(op1,LEN),
			new Operator(op2,LEN),
			new Operator(op2,LEN)});
		
		AVector input=Vector.of(0,1,2);
		AVector output=Vectorz.newVector(LEN);
		
		c.think(input,output);
		assertEquals(3,output.get(0),0.0001);
		assertEquals(7,output.get(1),0.0001);
		assertEquals(11,output.get(2),0.0001);
		
		assertEquals(input,c.getInput());
		
		c.getOutputGradient().set(Vector.of(10,20,30));
		c.trainGradientInternal(10.0);
		assertEquals(Vector.of(40,80,120),c.getInputGradient());
		
		c.getOutputGradient().set(Vector.of(1,2,3));
		c.trainGradientInternal(100000.0);
		assertEquals(Vector.of(4,8,12),c.getInputGradient());

		GenericModuleTests.test(c);
	}
	
	@Test public void testJoin() {
		Operator op1=Components.operator(Ops.LINEAR, 2);
		Operator op2=Components.operator(Ops.LOGISTIC, 2);
		NeuralNet nn1=Components.neuralLayer(2, 1, Ops.SOFTPLUS);
		
		
		IComponent j=Components.join(new IComponent[] {op1,op2,nn1});
		assertEquals(5,j.getOutputLength());
		assertEquals(6,j.getInputLength());
		
		AVector input=Vector.of(0,1,0,1,0,1);
		AVector output=j.think(input);
		assertEquals(Vector.of(0,1),output.subVector(0, 2));
		assertEquals(Vector.of(0.5),output.subVector(2,1));
		
		GenericModuleTests.test(j);

	}
	
	@Test public void testNeuralNet() {
		NeuralNet nn=Components.neuralLayer(3, 3, Ops.LOGISTIC);
		
		assertEquals(12,nn.getParameterLength());
		assertTrue(nn.getParameters().isZeroVector());
		Components.stack(nn).initRandom();
		assertFalse(nn.getParameters().isZeroVector());
		
		Vectorz.fillGaussian(nn.getParameters());
		GenericModuleTests.test(nn);
		
		AVector grad=nn.getGradient();
		AVector ig=nn.getInputGradient();
		assertTrue(grad.isZeroVector());
		assertTrue(ig.isZeroVector());
		nn.train(Vectorz.createUniformRandomVector(3), 
				Vector.of(0,1,2),CrossEntropyLoss.INSTANCE,1.0);
		assertTrue(!grad.isZeroVector());
		assertTrue(!ig.isZeroVector());
		assertTrue(grad.get(9)<0); // should be first bias element => 0
		assertTrue(grad.get(10)>0); // should be second bias element => 1
		assertTrue(grad.get(11)>1); // should be last bias element => 2, and (2-y)/y*(1-y) > 1
		assertTrue(grad.get(8)>0); // should be positive, since output grad positive and input positive
		// System.out.println(grad);
	}
	
	@Test public void testCompoundLayerStack() {
		NeuralNet nn1=Components.neuralLayer(3, 3, Ops.LOGISTIC);
		NeuralNet nn2=Components.neuralLayer(3, 3, Ops.LOGISTIC);
		IComponent c=Components.stack(nn1,nn2);
		ALayerStack st=Components.asLayerStack(c);
		assertEquals(3,st.getData(2).length());
		assertEquals(2,st.getLayerCount());
		assertTrue(st.getData(0)==nn1.getInput());
		assertTrue(st.getData(2)==nn2.getOutput());
	}
	
	@Test public void testOffset() {
		IComponent c=Components.offset(3, -1);
		assertEquals(Vector.of(0,1,2),c.think(Vector.of(1,2,3)));
		GenericModuleTests.test(c);
		
		Vectorz.fillRandom(c.getOutputGradient());
		assertNotEquals(c.getInputGradient(),c.getOutputGradient());
		c.trainGradientInternal(0.0);
		assertEquals(c.getInputGradient(),c.getOutputGradient());
	}
	
	@Test public void testBias() {
		IComponent c=Components.bias(5);
		GenericModuleTests.test(c);
	}
}

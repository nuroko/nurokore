package nuroko.module;

import static org.junit.Assert.*;

import java.util.Arrays;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.ops.LinearOp;
import nuroko.core.Components;
import nuroko.core.IComponent;

import org.junit.Test;

public class TestComponents {

	@Test public void testOpConnect() {	
		int LEN=3;
		Op op1=Op.LINEAR;
		Op op2=LinearOp.create(2.0, 1.0);
		
		Stack c=Components.connect(new IComponent[] {
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
		
		GenericModuleTests.test(c);
	}
	
	@Test public void testJoin() {
		Operator op1=Components.operator(Op.LINEAR, 2);
		Operator op2=Components.operator(Op.LOGISTIC, 2);
		NeuralNet nn1=Components.neuralLayer(2, 1, Op.SOFTPLUS);
		
		
		IComponent j=Components.join(new IComponent[] {op1,op2,nn1});
		assertEquals(5,j.getOutputLength());
		assertEquals(6,j.getInputLength());
		
		AVector input=Vector.of(0,1,0,1,0,1);
		AVector output=j.think(input);

		
		GenericModuleTests.test(j);

	}
}

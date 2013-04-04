package nuroko.module;

import static org.junit.Assert.*;

import java.util.Arrays;

import mikera.vectorz.AVector;
import mikera.vectorz.Op;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.ops.LinearOp;
import nuroko.core.IComponent;

import org.junit.Test;

public class TestComponents {

	@Test public void testOpConnect() {	
		int LEN=3;
		Op op1=Op.LINEAR;
		Op op2=LinearOp.create(2.0, 1.0);
		
		Connect c=new Connect(Arrays.asList(new IComponent[] {
			new OpComponent(LEN,op1),
			new OpComponent(LEN,op2),
			new OpComponent(LEN,op2)}));
		
		AVector input=Vector.of(0,1,2);
		AVector output=Vectorz.newVector(LEN);
		
		c.think(input,output);
		assertEquals(3,output.get(0),0.0001);
		assertEquals(7,output.get(1),0.0001);
		assertEquals(11,output.get(2),0.0001);
		
		assertEquals(input,c.getInput());
		
		GenericModuleTests.test(c);
	}
}

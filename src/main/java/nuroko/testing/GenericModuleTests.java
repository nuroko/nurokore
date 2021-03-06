package nuroko.testing;

import java.util.List;

import mikera.util.Tools;
import mikera.vectorz.AVector;
import mikera.vectorz.Vector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.impl.Vector0;
import nuroko.core.Components;
import nuroko.core.IComponent;
import nuroko.core.IInputState;
import nuroko.core.IModule;
import nuroko.core.IParameterised;
import nuroko.core.ISynthesiser;
import nuroko.core.IThinker;
import static org.junit.Assert.*;

public class GenericModuleTests {
	
	private static void testDecomposeParams(IComponent c) {
		c=c.clone();
		
		List<IComponent> cs=c.getComponents();
		int ccount=cs.size();
		
		if (ccount==0) return;
		
		AVector cp=Vector0.INSTANCE;
		AVector cg=Vector0.INSTANCE;
		for (int i=0; i<ccount; i++) {
			IComponent ch=cs.get(i);
			cp=cp.join(ch.getParameters());
			cg=cg.join(ch.getGradient());
		}
		
		Vectorz.fillGaussian(cp);
		Vectorz.fillGaussian(cg);	
		
		assertEquals(c.getParameterLength(),cp.length());
		assertEquals(c.getParameterLength(),cg.length());
		assertEquals(cp,c.getParameters());
		assertEquals(cg,c.getGradient());
	}


	private static void testFill(IParameterised p) {
		// test that parameters and gradient can both be filled
		p=p.clone();
		
		AVector param=p.getParameters();
		AVector grad=p.getGradient();
		
		param.fill(1);
		grad.fill(1);
		assertTrue(param.epsilonEquals(grad));
		
		// extra logic should pick up any duplicated / repeated element references
		for (int i=0; i<param.length(); i++) {
			assertEquals(1.0, param.get(i),0.0);
			param.set(i,2.0);
		}
		grad.add(grad);
		assertTrue(param.epsilonEquals(grad));	
	}
	
	private static void testCloneNotLinked(IParameterised p) {
		// Test that a clone does not share any elements of the parameter or gradient vectors
		if(p.getParameterLength()==0) return;
		
		p=p.clone();
		
		AVector param=p.getParameters();
		AVector grad=p.getGradient();
		param.fill(1.0);
		grad.fill(1.0);	
		
		IParameterised p2=p.clone();
		p2.getParameters().fill(2);
		p2.getGradient().fill(2);
		
		assertEquals(1.0,Vectorz.maxValue(param),0.0);
		assertEquals(1.0,Vectorz.maxValue(grad),0.0);
	}
	
	private static void testCloneCopyParameters(IParameterised p) {
		if (p instanceof IThinker) {
			// Test that a clone does not share any elements of the parameter or gardient vectors
			p=p.clone();
			
			AVector param=p.getParameters();
			Vectorz.fillGaussian(param);
			IParameterised p2=p.clone();
			p2.getParameters().fill(2);
			p2.getParameters().set(param);
			
			AVector input=Vectorz.newVector(((IThinker)p).getInputLength());
			AVector output=Vectorz.newVector(((IThinker)p).getOutputLength());
			AVector output2=Vectorz.newVector(((IThinker)p).getOutputLength());
			
			Vectorz.fillRandom(input);
			((IThinker)p).think(input, output);
			((IThinker)p2).think(input, output2);
			assertTrue(output.epsilonEquals(output2));
		}
	}
	
	private static void testParameterVectors(IParameterised p) {
		int pl=p.getParameterLength();
		AVector parameters=p.getParameters();
		AVector gradient=p.getGradient();
		assertEquals(pl,parameters.length());
		assertEquals(pl,gradient.length());
		
		if (pl>0) {
			assertTrue(Tools.distinctObjects(p.getParameters(),p.getGradient()));
		}
	}
	
	
	private static void testParameterized(IParameterised p) {
		p=p.clone();
		testParameterVectors(p);
		testFill(p);
		testCloneNotLinked(p);
		testCloneCopyParameters(p);
	}
	
	private static void testOverwriteOutput(IThinker p) {
		// test that output vector is completely overwritten by any IThinker
		p=p.clone();
		AVector input=Vectorz.newVector(p.getInputLength());
		AVector output=Vectorz.newVector(p.getOutputLength());
		
		output.fill(Double.NaN);
		p.think(input, output);
		for (int i=0; i<output.length(); i++) {
			assertTrue(output.get(i)!=Double.NaN);
		}
	}
	
	private static void testOverwriteInputGradient(IComponent p) {
		// test that output vector is completely overwritten by any IThinker
		p=p.clone();
		AVector og=Vectorz.newVector(p.getOutputLength());
		Vectorz.fillGaussian(og);
		
		p.getInputGradient().fill(Double.NaN);
		p.getOutputGradient().set(og);
		p.trainGradientInternal(1.0);
		AVector ig=p.getInputGradient();
		for (int i=0; i<ig.length(); i++) {
			assertTrue(ig.get(i)!=Double.NaN);
		}
	}
	
	private static void testJoinedGradient(IComponent p) {
		if (p.isStochastic()) return;
		p=p.clone();
		int il=p.getInputLength();
		int pl=p.getParameterLength();
		
		AVector og=Vectorz.newVector(p.getOutputLength());
		Vectorz.fillGaussian(og);
		og=og.join(og);
		
		p=Components.join(p,p.clone());
		
		p.getInputGradient().fill(Double.NaN);
		p.getOutputGradient().set(og);
		p.trainGradientInternal(1.0);
		AVector ig=p.getInputGradient();
		for (int i=0; i<ig.length(); i++) {
			assertTrue(ig.get(i)!=Double.NaN);
		}
		
		for (int i=0; i<il; i++) {
			assertTrue(ig.get(i)==ig.get(i+il));
		}
		
		AVector params=p.getParameters();
		assertEquals(pl*2, params.length());
		params.addMultiple(p.getGradient(),0.0001);
		for (int i=0; i<pl; i++) {
			assertTrue(params.get(i)==params.get(i+pl));
		}
		
	}
	
	

	
	private static void testThinker(IThinker p) {
		p=p.clone();
		testOverwriteOutput(p);
	}
	

	private static void testInput(IInputState o) {
		AVector input=o.getInput();
		assertEquals(o.getInputLength(),input.length());
		assertEquals(input.length(),o.getInputGradient().length());
	}
	

	private static void testModule(IModule o) {
		for (IModule m:o.getModules()) {
			test(m);
		}	
	}
	
	private static void testGeneralThinking(IComponent p) {
		// test that output vector is completely overwritten by any IThinker
		p=p.clone();
		AVector input=Vectorz.createUniformRandomVector(p.getInputLength());
		AVector output=Vectorz.createUniformRandomVector(p.getOutputLength());
		
		p.think(input, output);
		for (int i=0; i<output.length(); i++) {
			assertTrue(output.get(i)!=Double.NaN);
		}
		 
		AVector res=p.think(input);
		if (!p.isStochastic()) {
			assertEquals(res,output);
		}
		
		assertEquals(output,p.getOutput());
	}
	
	private static void testStates(IComponent p) {
		assertTrue(Tools.distinctObjects(p.getInput(),p.getOutput(),p.getInputGradient(),p.getOutputGradient()));
	}
	
	private static void testParameterUpdates(IComponent p) {
		// testing that trainGradient overwrites input gradient
		p=p.clone();
		int ol=p.getOutputLength();
		int il=p.getInputLength();
		AVector grad=p.getGradient();
		grad.fill(0.0);
		
		AVector output=Vector.createLength(ol);
		AVector target=Vector.createLength(ol);
		AVector input=Vector.createLength(il);
		Vectorz.fillGaussian(target);
		Vectorz.fillGaussian(input);
		
		p.think(input,output);
		assertTrue(grad.isZeroVector());
		
		p.train(input,target);
		AVector tg=grad.clone();
		
		if (!p.isStochastic()&&(!(p instanceof ISynthesiser))) {
			p.train(input, output); // shouldn't accumulate any gradient
			assertEquals(tg,grad);
		}		
	}
	

	private static void testSubComponents(IComponent o) {
		for (IComponent m:o.getComponents()) {
			test(m);
		}	
	}
	
	private static void testComponent(IComponent o) {
		testGeneralThinking(o);
		testStates(o);
		testSubComponents(o);
		testDecomposeParams(o);
		testParameterUpdates(o);
		assertTrue(o.getInputState().getInput()==o.getInput());
	}
	
	public static void test(Object o) {
		if (o instanceof IParameterised) {
			testParameterized((IParameterised)o);
		}
		if (o instanceof IInputState) {
			testInput((IInputState)o);
		}
		if (o instanceof IThinker) {
			testThinker((IThinker)o);
		}
		
		if (o instanceof IModule) {
			testModule((IModule)o);
		}
		if (o instanceof IComponent) {
			IComponent c=((IComponent)o);
			testComponent(c);
			testOverwriteInputGradient(c);
			testJoinedGradient(c);
		}
	}


}

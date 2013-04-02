package nuroko.module;

import static org.junit.Assert.*;
import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;
import mikera.vectorz.Op;

import org.junit.Test;

public class TestNetworkStack {

	@Test 
	public void genericTests() {
		AWeightLayer wl1=new FullWeightLayer(2,2);
		wl1.initRandom();
		AWeightLayer wl2=new FullWeightLayer(2,2);
		wl2.initRandom();
		NeuralNet nn=new NeuralNet(wl1,wl2);
		NeuralNet nn2=nn.clone();

		NetworkStack ns=new NetworkStack(nn,nn2);
		
		GenericModuleTests.test(ns);
		
		Vectorz.fillGaussian(ns.getParameters());

		GenericModuleTests.test(ns);
	}
	
	@Test 
	public void equalToNNTest() {
		AWeightLayer wl1=new FullWeightLayer(2,2);
		wl1.initRandom();
		AWeightLayer wl2=new FullWeightLayer(2,2);
		wl2.initRandom();
		
		NeuralNet nn=new NeuralNet(new AWeightLayer[] {wl1,wl2},Op.LOGISTIC,Op.LOGISTIC);
		NetworkStack ns=new NetworkStack(new NeuralNet(wl1.clone()),new NeuralNet(wl2.clone()));
		
		assertTrue(nn.getParameterLength()==ns.getParameterLength());
		assertTrue(nn.getGradient().length()==ns.getGradient().length());
		assertEquals(nn.getParameters(),ns.getParameters());
		assertEquals(2,ns.getLayerCount());
		
		AVector input=Vectorz.createUniformRandomVector(2);
		AVector output=Vectorz.createUniformRandomVector(2);
		AVector output2=Vectorz.createUniformRandomVector(2);
		AVector target=Vectorz.createUniformRandomVector(2);
		
		nn.think(input, output);
		ns.think(input, output2);
		assertTrue(output.epsilonEquals(output2));
		
		assertTrue(nn.getGradient().epsilonEquals(ns.getGradient()));
		//System.out.println(nn.getGradient());
		//System.out.println(ns.getGradient());
		nn.train(input, target);
		//System.out.println(nn.getGradient());
		//System.out.println(ns.getGradient());
		ns.train(input, target);
		//System.out.println(nn.getGradient());
		//System.out.println(ns.getGradient());
		assertTrue(nn.getGradient().epsilonEquals(ns.getGradient()));

	}
	
}

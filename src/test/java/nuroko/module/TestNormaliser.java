package nuroko.module;

import static org.junit.Assert.*;

import org.junit.Test;

import mikera.vectorz.AVector;
import mikera.vectorz.Vectorz;

public class TestNormaliser {
	@Test public void testRegen() {
		Normaliser n=Normaliser.create(Vectorz.createUniformRandomVector(10), Vectorz.createUniformRandomVector(10));
		AVector input=Vectorz.createUniformRandomVector(10);
		AVector output=n.think(input);
		AVector regen=n.generate(output);
		
		assertTrue(regen.epsilonEquals(input));
	}
}

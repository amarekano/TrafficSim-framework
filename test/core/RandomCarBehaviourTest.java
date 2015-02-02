package core;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomCarBehaviourTest {

	@Test
	public void test_random_deceleration()
	{
		int initial_acc = 10;
		Car c = new Car(2,initial_acc,100);
		
		c.setDecelaration_probability(0.5);
		
		assertTrue(c.getAcceleration() <= initial_acc );
	}
}

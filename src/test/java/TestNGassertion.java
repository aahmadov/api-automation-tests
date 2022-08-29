import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.*;

public class TestNGassertion {
	
	@Test
	public void verifyBasicStuff() {

		assertTrue(3<5);
		System.out.println("condition is true" );
		System.out.println("condition is true and it passed" );
	}
	@Test
	public void verifyBasicStuff2() {

		assertFalse(3>5);
		System.out.println("condition is true" );

	}
}

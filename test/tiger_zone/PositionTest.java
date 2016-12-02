package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {
	private static Position cell;

	@BeforeClass
	public static void setup() {
		cell = new Position(1, 2);
	}
	
	@Test
	public void getXTest() {
		assertEquals(1, cell.getX());
	}

	@Test
	public void getYTest() {
		assertEquals(2, cell.getY());
	}
	
	@Test
	public void northTest() {
		assertEquals(new Position(2, 3), (new Position(2, 2)).north());
	}
	
	@Test
	public void eastTest() {
		assertEquals(new Position(3, 2), (new Position(2, 2)).east());
	}
	
	@Test
	public void southTest() {
		assertEquals(new Position(2, 1), (new Position(2, 2)).south());
	}
	
	@Test
	public void westTest() {
		assertEquals(new Position(1, 2), (new Position(2, 2)).west());
	}
	
	@Test
	public void equalsTest() {
		assertEquals(new Position(5, 3), new Position(5, 3));
		assertNotEquals(new Position(0, 0), new Position(0, 1));
	}
	
	@Test
	public void toStringTest() {
		assertEquals("(-1, 5)", (new Position(-1, 5)).toString());
	}
}

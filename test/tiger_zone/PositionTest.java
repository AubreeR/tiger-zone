package tiger_zone;

import static org.junit.Assert.assertEquals;

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
}

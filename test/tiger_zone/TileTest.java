package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TileTest {
	private static Tile tile;

	@BeforeClass
	public static void setup() {
		char[] sides = {'j', 'r', 'j', 'l', 'l', 'l', 'j', 'r', 'j', 'j', 'j', 'j'};
		tile = new Tile(sides, 'r', "./src/resources/tile20.png");
	}

	@Test
	public void getImagePath() {
		assertEquals("./src/resources/tile20.png", tile.getImagePath());
	}

	@Test
	public void rotationTest() {
		char[] originalSides = {'j', 'r', 'j', 'l', 'l', 'l', 'j', 'r', 'j', 'j', 'j', 'j'};
		char[] rotatedSides1 = {'j', 'j', 'j', 'j', 'r', 'j', 'l', 'l', 'l', 'j', 'r', 'j'};
		char[] rotatedSides2 = {'j', 'r', 'j', 'j', 'j', 'j', 'j', 'r', 'j', 'l', 'l', 'l'};
		char[] rotatedSides3 = {'l', 'l', 'l', 'j', 'r', 'j', 'j', 'j', 'j', 'j', 'r', 'j'};

		assertEquals(0, tile.getRotation());

		tile.rotate();
		assertEquals(90, tile.getRotation());
		assertArrayEquals(rotatedSides1, tile.getSides());

		tile.rotate();
		assertEquals(180, tile.getRotation());
		assertArrayEquals(rotatedSides2, tile.getSides());

		tile.rotate();
		assertEquals(270, tile.getRotation());
		assertArrayEquals(rotatedSides3, tile.getSides());

		tile.rotate();
		assertEquals(0, tile.getRotation());
		assertArrayEquals(originalSides, tile.getSides());

		tile.rotate();
		assertEquals(90, tile.getRotation());
		assertArrayEquals(rotatedSides1, tile.getSides());
	}
}

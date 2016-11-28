package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertArrayEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TileTest {
	private static Tile tile;
	private static char[] sides = {'t', 'l', 't', 'j'};

	@BeforeClass
	public static void setup() {
		tile = new Tile(sides, '-', "./src/resources/tile20.png");
	}

	@Test
	public void getImagePathTest() {
		assertEquals("./src/resources/tile20.png", tile.getImagePath());
	}

	@Test
	public void getCenterTest() {
		assertEquals('-', tile.getCenter());
	}

	@Test
	public void rotationTest() {
		char[] originalSides = {'t', 'l', 't', 'j'};
		char[] rotatedSides1 = {'l', 't', 'j', 't'};
		char[] rotatedSides2 = {'t', 'j', 't', 'l'};
		char[] rotatedSides3 = {'j', 't', 'l', 't'};

		assertEquals(0, tile.getRotation());

		tile.rotate();
		assertEquals(90, tile.getRotation());
		assertArrayEquals(rotatedSides1, tile.getSides());
		assertArrayEquals(originalSides, sides);

		tile.rotate();
		assertEquals(180, tile.getRotation());
		assertArrayEquals(rotatedSides2, tile.getSides());
		assertArrayEquals(originalSides, sides);

		tile.rotate();
		assertEquals(270, tile.getRotation());
		assertArrayEquals(rotatedSides3, tile.getSides());
		assertArrayEquals(originalSides, sides);

		tile.rotate();
		assertEquals(0, tile.getRotation());
		assertArrayEquals(originalSides, tile.getSides());
		assertArrayEquals(originalSides, sides);

		tile.rotate();
		assertEquals(90, tile.getRotation());
		assertArrayEquals(rotatedSides1, tile.getSides());
		assertArrayEquals(originalSides, sides);
	}
	
	@Test
	public void cloneTest() {
		Tile copy = tile.clone();
		assertNotSame(tile, copy);
		assertNotSame(tile.getSides(), copy.getSides());
		assertArrayEquals(tile.getSides(), copy.getSides());
		assertEquals(tile.getRotation(), copy.getRotation());
	}
}

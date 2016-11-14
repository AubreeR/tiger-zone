package tiger_zone;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class TileTest {
	private static Tile tile;

	@BeforeClass
	public static void setup() {
		char[] sides = {'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f'};
		tile = new Tile(sides, 'm', "./src/resources/tile2.png");
	}

	@Test
	public void getImagePath() {
		assertEquals("./src/resources/tile2.png", tile.getImagePath());
	}
}

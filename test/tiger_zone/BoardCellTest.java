package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.BeforeClass;
import org.junit.Test;

public class BoardCellTest {
	private static BoardCell cell;

	@BeforeClass
	public static void setup() {
		cell = new BoardCell(1, 2);
	}

	@Test
	public void getXTest() {
		assertEquals(1, cell.getXCoord());
	}

	@Test
	public void getYTest() {
		assertEquals(2, cell.getYCoord());
	}

	@Test
	public void setGetTileTest() {
		char[] sides = {'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f'};
		Tile t = new Tile(sides, 'm', "./src/resources/tile2.png");
		cell.setTile(t);
		assertSame(t, cell.getTile());
	}
}

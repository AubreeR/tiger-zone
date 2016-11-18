package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest {
	private static Stack<Tile> pile;
	private static Board board;

	@BeforeClass
	public static void setup() {
		pile = new Stack<Tile>();
		board = new Board(pile);
	}

	@Test
	public void addGetTileTest() {
		char[] sides = {'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f'};
		Tile t = new Tile(sides, 'm', "./src/resources/tile2.png");
		board.addTile(0, 0, t);
		assertSame(t, board.getTile(0, 0));
	}

	@Test
	public void getPileTest() {
		assertSame(pile, board.getPile());
	}

	@Test
	public void createDefaultStackTest() {
		assertEquals(27, Board.createDefaultStack().size());
	}
}

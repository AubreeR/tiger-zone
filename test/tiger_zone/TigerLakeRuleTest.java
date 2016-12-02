package tiger_zone;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.BeforeClass;
import org.junit.Test;

import tiger_zone.action.PassAction;

public class TigerLakeRuleTest {
	private static Game game;

	@BeforeClass
	public static void setup() {
		Stack<Tile> pile = Board.createDefaultStack();
		game = new Game(new Board(pile, Board.tileMap.get("tltj-"), new Position(0, 0), 0));
	}

	private final static boolean makeTile(final Position position, final String tile, int rotation, int zone) {
		Tile t = Board.tileMap.get(tile).clone();
		while (t.getRotation() != rotation) {
			t.rotate();
		}
		game.getBoard().addTile(position, t);
		if (zone != 0) {
			assertTrue(game.getBoard().validTigerPlacement(position, zone, false));
			return t.addTiger(zone, new Tiger(new Player(0, "")));
		}
		return true;
	}

	@Test
	public void test() {
		assertTrue(makeTile(new Position(0, -1), "tjjt-", 0, 0));
		assertTrue(makeTile(new Position(1, 0), "lljj-", 90, 1));
		assertTrue(makeTile(new Position(-1, 0), "ljlj-", 0, 0));
		assertTrue(makeTile(new Position(0, 1), "tllt-", 90, 2));
		assertTrue(makeTile(new Position(1, 1), "jllj-", 270, 0));
		assertTrue(makeTile(new Position(0, -2), "jljl-", 0, 4));
		assertTrue(makeTile(new Position(0, 2), "llll-", 0, 0));
		assertTrue(makeTile(new Position(2, 0), "ljtjd", 0, 2));
		assertTrue(makeTile(new Position(-1, -1), "tlltb", 180, 1));
		assertTrue(makeTile(new Position(-2, 0), "tjtt-", 0, 0));
		assertTrue(makeTile(new Position(-1, 1), "tlttp", 270, 0));
		assertTrue(makeTile(new Position(1, -2), "tlllc", 0, 0));
		assertTrue(makeTile(new Position(1, 2), "jltt-", 180, 0));
		assertTrue(makeTile(new Position(0, -3), "tjtj-", 90, 0));
		assertTrue(makeTile(new Position(2, 1), "lljj-", 270, 0));
		assertTrue(makeTile(new Position(0, 3), "jljl-", 90, 0));
		assertTrue(makeTile(new Position(2, -2), "lljj-", 90, 0));
		assertTrue(makeTile(new Position(3, 0), "ljjj-", 0, 0));

		// have CloseAiPlayer make a move, expect it to be (-1, -2)
		final Tile current = Board.tileMap.get("tlltb").clone();
		final Map<Position, List<Integer>> validTilePlacements = game.getBoard().getValidTilePlacements(current);
		assertFalse(validTilePlacements.isEmpty());

		// get closest placement to origin
		double minDistance = Double.MAX_VALUE;
		Position closest = null;
		final Position origin = new Position(0, 0);
		for (Position p : validTilePlacements.keySet()) {
			if (origin.distance(p) < minDistance) {
				minDistance = origin.distance(p);
				closest = p;
			}
		}

		final int rotation = validTilePlacements.get(closest).get(0);
		assertEquals(0, rotation);

		// Rotate tile until we get desired rotation
		while (current.getRotation() != rotation) {
			current.rotate();
		}

		assertEquals(new Position(-1, -2), closest);
		assertTrue(game.getBoard().addTile(closest, current));

		assertFalse(current.hasDen());
		assertTrue(current.hasAnimal());

		int i = -1;
		for (i = 1; i < 10; i++) {
			if (current.getZone(i) == 'l') {
				boolean isValid = game.getBoard().validTigerPlacement(closest, i, false);
				if (isValid) {
					current.addTiger(i, new Tiger(new Player(0, "")));
					break;
				}
			}
		}

		assertNotEquals(6, i);

		assertFalse(game.getBoard().validTigerPlacement(new Position(-1, -2), 6, false));
	}
}

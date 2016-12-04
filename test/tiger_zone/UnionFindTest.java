package tiger_zone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnionFindTest {

	@Test
	public void test() {
		Board.createDefaultStack();
		Tile tile = Board.tileMap.get("tlltb");
		while (tile.getRotation() != 270) {
			tile.rotate();
		}
		UnionFind union = new UnionFind(tile);
		assertEquals(1, union.getLowestZone(1));
		assertEquals(2, union.getLowestZone(2));
		assertEquals(3, union.getLowestZone(3));
		assertEquals(4, union.getLowestZone(4));
		assertEquals(2, union.getLowestZone(5));
		assertEquals(2, union.getLowestZone(6));
		assertEquals(4, union.getLowestZone(7));
		assertEquals(4, union.getLowestZone(8));
		assertEquals(1, union.getLowestZone(9));
	}
}
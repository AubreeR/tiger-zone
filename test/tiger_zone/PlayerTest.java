package tiger_zone;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {
	private static Player player;

	@BeforeClass
	public static void test() {
		player = new Player(0);
	}

	@Test
	public void getIndexTest() {
		assertEquals(0, player.getIndex());
	}

	@Test
	public void pointsTest() {
		assertEquals(0, player.getPoints());
		player.setPoints(1);
		assertEquals(1, player.getPoints());
	}
}

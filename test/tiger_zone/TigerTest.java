package tiger_zone;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class TigerTest {

	@Test
	public void getOwnerTest() {
		Player player = new Player(0, null);
		Tiger tiger = new Tiger(player);
		assertSame(player, tiger.getOwner());
	}
}

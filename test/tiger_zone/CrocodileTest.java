package tiger_zone;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class CrocodileTest {

	@Test
	public void getOwnerTest() {
		Player player = new Player(0);
		Crocodile crocodile = new Crocodile(player);
		assertSame(player, crocodile.getOwner());
	}
}

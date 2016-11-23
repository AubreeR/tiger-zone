package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameTest {

	@Test
	public void getBoardTest() {
		Game game = new Game();
		assertTrue(game.getBoard() instanceof Board);
	}

	@Test
	public void getPlayersTest() {
		Game game = new Game();
		List<Player> players = new ArrayList<Player>();
		game.setPlayers(players);
		assertEquals(0, game.getPlayers().size());
	}

	@Test
	public void setPlayersTest() {
		Game game = new Game();
		List<Player> players = new ArrayList<Player>(2);
		players.add(new Player(0));
		players.add(new Player(1));
		game.setPlayers(players);
		assertEquals(2, game.getPlayers().size());
		assertEquals(0, game.getPlayers().get(0).getIndex());
		assertEquals(1, game.getPlayers().get(1).getIndex());
	}
}

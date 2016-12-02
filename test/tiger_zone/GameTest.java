package tiger_zone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.PoorAiPlayer;

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
		players.add(new Player(0,"0"));
		players.add(new Player(1,"1"));
		game.setPlayers(players);
		assertEquals(2, game.getPlayers().size());
		assertEquals(0, game.getPlayers().get(0).getIndex());
		assertEquals(1, game.getPlayers().get(1).getIndex());
	}

	@Test
	public void getSetAiPlayersTest() {
		Game game = new Game();
		List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);
		aiPlayers.add(new PoorAiPlayer(game));
		aiPlayers.add(new PoorAiPlayer(game));
		game.setAiPlayers(aiPlayers);
		assertNotSame(aiPlayers, game.getAiPlayers());
		assertEquals(2, game.getAiPlayers().size());
		assertSame(aiPlayers.get(0), game.getAiPlayers().get(0));
		assertSame(aiPlayers.get(1), game.getAiPlayers().get(1));
	}
	
	@Test
	public void getCurrentPlayerTest() {
		Game game = new Game();
		List<Player> players = new ArrayList<Player>(2);
		players.add(new Player(0, "0"));
		players.add(new Player(1, "1"));
		game.setPlayers(players);
		assertSame(game.getPlayers().get(0), game.getCurrentPlayer());
		game.nextPlayer();
		assertSame(game.getPlayers().get(1), game.getCurrentPlayer());
	}
}

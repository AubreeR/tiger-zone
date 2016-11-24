package tiger_zone;

import java.util.List;
import java.util.Stack;

/**
 * The <code>Game</code> class unites the board and the players, and handles the current turn/phase of the game.
 */
public class Game {
	private final Board board;
	private List<Player> players;

	/**
	 * Creates a new instance of <code>Game</code> with the default tile stack being the pile.
	 */
	public Game() {
		board = new Board(Board.createDefaultStack());
	}

	/**
	 * Creates a new instance of <code>Game</code> with the specified pile.
	 *
	 * @param pile Stack of tiles.
	 */
	public Game(Stack<Tile> pile) {
		board = new Board(pile);
	}

	/**
	 * Returns this game's board.
	 *
	 * @return board
	 */
	public final Board getBoard() {
		return this.board;
	}

	/**
	 * Returns this game's players.
	 *
	 * @return players
	 */
	public final List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Sets the players of this game
	 *
	 * @param players List of players.
	 */
	public final void setPlayers(List<Player> players) {
		this.players = players;
	}
}

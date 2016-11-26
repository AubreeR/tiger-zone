package tiger_zone;

import java.util.List;
import java.util.Stack;

import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.PoorAiPlayer;

/**
 * The <code>Game</code> class unites the board and the players, and handles the current turn/phase of the game.
 */
public class Game {
	private final Board board;
	private List<Player> players;
	private List<AiPlayer> aiPlayers;
	private Tile currentTile;
	private Player currentPlayer;
	private boolean isOver = false;
	private String gid; 

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
	 * Get last tile to be taken from the pile.
	 *
	 * @return current tile
	 */
	public final Tile getCurrentTile() {
		return this.currentTile;
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
	 * Sets the players of this game.
	 *
	 * @param players List of players.
	 */
	public final void setPlayers(List<Player> players) {
		this.players = players;

		// TODO: probably set currentPlayer elsewhere
		if (this.players.size() > 0) {
			this.currentPlayer = this.players.get(0);
		}
	}

	/**
	 * Sets the AI players of this game.
	 *
	 * @param aiPlayers List of AI players.
	 */
	public final void setAiPlayers(List<AiPlayer> aiPlayers) {
		this.aiPlayers = aiPlayers;
	}

	/**
	 * Set current player to the next player.
	 */
	private final void nextPlayer() {
		if (this.currentPlayer.getIndex() == 0) {
			this.currentPlayer = this.players.get(1);
		}
		else {
			this.currentPlayer = this.players.get(0);
		}
	}

	public final void conductTurn() {
		// TODO: consider if this check is best done elsewhere
		if (this.board.getPile().size() == 0) {
			this.isOver = true;
		}

		if (this.isOver) {
			return;
		}

		AiPlayer currentAiPlayer = this.aiPlayers.get(this.currentPlayer.getIndex());
		this.currentTile = this.board.getPile().pop();

		// It is our AiPlayer's turn. Make our move, send it to the server, and then conduct next turn.
		if (currentAiPlayer instanceof PoorAiPlayer) {
			currentAiPlayer.makeMove();
			this.nextPlayer();
			this.conductTurn();
		}

		// Otherwise, it is the opponent's AI turn. We can't force them to play but rather we can only wait for them to
		// make a move on their own time.
	}
}

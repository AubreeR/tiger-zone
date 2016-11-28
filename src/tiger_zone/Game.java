package tiger_zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.CloseAiPlayer;
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
	private int turnNumber = 0;

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
	 * Returns this game's AI players.
	 *
	 * @return ai players
	 */
	public final List<AiPlayer> getAiPlayers() {
		return this.aiPlayers;
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
		this.aiPlayers = new ArrayList<>(aiPlayers);
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

		this.turnNumber++;

		AiPlayer currentAiPlayer = this.aiPlayers.get(this.currentPlayer.getIndex());
		this.currentTile = this.board.getPile().pop();
		
		Player me = new Player(1, "s1");

		// It is our AiPlayer's turn. Make our move, send it to the server, and then conduct next turn.
		if (currentAiPlayer instanceof PoorAiPlayer || currentAiPlayer instanceof CloseAiPlayer) {
			final long start = System.currentTimeMillis();
			currentAiPlayer.makeMove();
			
			int score = me.updateScore(this.board, this.board.getLatest()); //new "player" to check scores after a move
			System.out.printf("(#%d) %s placed %s at position %s", this.turnNumber, this.currentPlayer,
					this.currentTile, this.board.getLatest());
			if (this.currentTile.hasTiger()) {
				System.out.printf(" and tiger at position %d", this.currentTile.getTigerPosition());
			}
			System.out.printf(" in %d ms", System.currentTimeMillis() - start);
			System.out.printf("\nScore: " + score + "\n");

			this.nextPlayer();
			this.conductTurn();
		}
	}
}

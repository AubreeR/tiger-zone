package tiger_zone;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tiger_zone.action.Action;
import tiger_zone.action.PassAction;
import tiger_zone.action.TigerPlacementAction;
import tiger_zone.action.TilePlacementAction;
import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.NetworkAiPlayer;

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
	private int turnNumber = 1;

	/**
	 * Creates a new instance of <code>Game</code> with the default tile stack being the pile.
	 */
	public Game() {
		this.board = new Board(Board.createDefaultStack());
	}

	/**
	 * Creates a new instance of <code>Game</code> with the specified pile.
	 *
	 * @param pile Stack of tiles.
	 */
	public Game(Stack<Tile> pile) {
		this.board = new Board(pile);
	}
	
	/**
	 * Creates a new instance of <code>Game</code> with the specified board.
	 * 
	 * @param board
	 */
	public Game(final Board board) {
		this.board = board;
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
	 * Returns true if game is over. Otherwise returns false.
	 * 
	 * @return is game over?
	 */
	public final boolean isOver() {
		return this.isOver;
	}
	
	/**
	 * Sets the players of this game.
	 *
	 * @param players List of players.
	 */
	public final void setPlayers(List<Player> players) {
		this.players = players;

		
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
	public final void nextPlayer() {
		if (this.currentPlayer.getIndex() == 0) {
			this.currentPlayer = this.players.get(1);
		}
		else {
			this.currentPlayer = this.players.get(0);
		}
		this.turnNumber++;
	}

	public final Action conductTurn() {
		// TODO: consider if this check is best done elsewhere
		if (this.board.getPile().size() == 0) {
			this.isOver = true;
		}

		if (this.isOver) {
			return null;
		}

		AiPlayer currentAiPlayer = this.aiPlayers.get(this.currentPlayer.getIndex());
		this.currentTile = this.board.getPile().pop();

		// It is our AiPlayer's turn. Make our move, send it to the server, and then conduct next turn.
		if (!(currentAiPlayer instanceof NetworkAiPlayer)) {
			final long start = System.currentTimeMillis();
			Action action = currentAiPlayer.makeMove();

			System.out.printf("(#%d) %s", this.turnNumber, this.currentPlayer);
			if (action instanceof TilePlacementAction || action instanceof TigerPlacementAction) {
				System.out.printf(" placed %s at position %s (%d)", ((TilePlacementAction) action).getTile(),
						((TilePlacementAction) action).getPosition(),
						((TilePlacementAction) action).getTile().getRotation());
			}
			if (action instanceof TigerPlacementAction) {
				System.out.printf(" and tiger at zone %d", ((TigerPlacementAction) action).getZone());
			}
			if (action instanceof PassAction) {
				System.out.printf(" passed their turn");
			}
			System.out.printf(" in %d ms\n", System.currentTimeMillis() - start);

			this.nextPlayer();
			return action;
		}
		
		// Otherwise, the current turn is the NetworkAiPlayer's. We cannot ask for a move here.
		return null;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
}

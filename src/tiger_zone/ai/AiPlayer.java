package tiger_zone.ai;

import tiger_zone.Game;

/**
 * Abstract class for AI players to extend.
 */
public abstract class AiPlayer {
	protected final Game game;
	
	/**
	 * Creates a new instance of <code>AiPlayer</code>.
	 *
	 * @param game The game instance.
	 */
	public AiPlayer(Game game) {
		this.game = game;
	}

	/**
	 * Have this AI player make a move on the board.
	 */
	public void makeMove() {
		// TODO: figure out if an implementation should be here?
	}
}

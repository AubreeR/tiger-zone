package tiger_zone.ai;

import tiger_zone.Game;
import tiger_zone.Player;
import tiger_zone.action.Action;

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
	 * Get the Player instance that corresponds to this AiPlayer.
	 * 
	 * @return player
	 */
	protected final Player getPlayer() {
		int i = 0;
		for (AiPlayer player : this.game.getAiPlayers()) {
			if (player.equals(this)) {
				return this.game.getPlayers().get(i);
			}
			i++;
		}
		return null;
	}
	
	/**
	 * Have this AI player make a move on the board.
	 */
	public Action makeMove() {
		// TODO: figure out if an implementation should be here?
		return null;
	}
}

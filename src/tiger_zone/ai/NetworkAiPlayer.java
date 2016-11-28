package tiger_zone.ai;

import tiger_zone.Game;

/**
 * Represents an AiPlayer that is not intended to be operated by us. Exists for Game to distinguish network players from
 * ourself.
 */
public class NetworkAiPlayer extends AiPlayer {

	/**
	 * Creates a new instance of <code>NetworkAiPlayer</code>.
	 * 
	 * @param game Instance of game.
	 */
	public NetworkAiPlayer(Game game) {
		super(game);
	}
}
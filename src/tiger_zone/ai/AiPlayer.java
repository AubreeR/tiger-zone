package tiger_zone.ai;

import tiger_zone.Game;

/**
 * Abstract class for AI players to extend.
 */
public abstract class AiPlayer {
	protected final Game game;
	protected final String pid;
	
	/**
	 * Creates a new instance of <code>AiPlayer</code>.
	 * 
	 * @param game The game instance.
	 */
	public AiPlayer(Game game, String pid) {
		
		this.game = game;
		this.pid = pid;
	}
	
	/**
	 * Have this AI player make a move on the board.
	 */
	public void makeMove() {
		// TODO: figure out if an implementation should be here?
	}
	
	public String getPid(){
		return pid; 
	}
}

package tiger_zone.ai;

import java.util.Stack;

import tiger_zone.Game;
import tiger_zone.Tiger;

/**
 * Abstract class for AI players to extend.
 */
public abstract class AiPlayer {
	protected final Game game;
	protected final String pid;
	private final Stack<Tiger> tigers = new Stack<Tiger>();
	/**
	 * Creates a new instance of <code>AiPlayer</code>.
	 * 
	 * @param game The game instance.
	 */
	public AiPlayer(Game game, String pid) {
		
		this.game = game;
		this.pid = pid;
		
		Tiger t = new Tiger();
		for(int i = 0; i < 7; i++){
		tigers.push(t); 
		}
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
	
	public Stack<Tiger> getTigers(){
		return tigers; 
	}
}

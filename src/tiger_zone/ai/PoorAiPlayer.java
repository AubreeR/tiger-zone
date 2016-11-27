package tiger_zone.ai;

import tiger_zone.Game;
import tiger_zone.PossibleMovesRule;
import tiger_zone.TigerTrailRule;

import tiger_zone.Tile;
import tiger_zone.UnionFind;
import tiger_zone.Player;

/**
 * A pretty bad AI player which simply makes the first possible move found. This exists simply to demonstrate how an AI
 * player should function.
 */
public class PoorAiPlayer extends AiPlayer {
	private int move_num = 1;
	private String pid;
	private String currentPlayer;

	/**
	 * Create a new instance of <code>PoorAiPlayer</code>.
	 *
	 * @param game The game instance.
	 */
	public PoorAiPlayer(Game game, String pid){
		super(game, pid);
	}

	/**
	 * Have this AI player place a tile on the board.
	 */
	public final void makeMove() {
		currentPlayer = getPid();

		long millis = System.currentTimeMillis();
		Tile current = this.game.getCurrentTile();
		PossibleMovesRule pmr = new PossibleMovesRule(this.game.getBoard(), 0, 0, current, false);

		// no possible move; simply pass our turn (not what's actually supposed to happen)
		if (!pmr.evaluate()) {
			return;
		}

		// x = move[0]
		// y = move[1]
		// rotation = move[2]
		int[] move = pmr.getClosestToOriginEstimateMove();
		while (current.getRotation() != move[2]) {
			current.rotate();
		}

		game.getBoard().addTile(move[0], move[1], current);
		int i = -1;
		if (current.hasDen()) {
		
			if(current.addTiger(5, currentPlayer)){
				i = 5;

			}
		}
		else if(current.hasAnimal() ){
		
			for( i = 1; i < 10; i++){
				
				if(current.getZone(i) == 'l'){
					boolean check = this.game.getBoard().validTigerPlacement(move[0],  move[1], current,  i, false);
					if(check && current.addTiger(i, currentPlayer)){
						break;
					}
				}
			}
			i = (i == 10) ? -1:i;

		}
		
		Player me = new Player(1,"s1");
		int score = me.updateScore(move[0], move[1], this.game.getBoard());
		System.err.println("Score: " + score);
		
		millis = System.currentTimeMillis() - millis;
		if( i != -1)
		System.err.println(currentPlayer + "'s Move: " + move_num++ + " \tCoor: (" + move[0] +"," + move[1] +") \ttile: "
				+ current.getSide(0)+current.getSide(1)+current.getSide(2)+current.getSide(3)
				+ "\tTiger Locations: "  + ((i != -1)? i:"N/A")
				+ "\tElapsed Time: " + millis);
		else
			System.out.println(currentPlayer + "'s Move: " + move_num++ + " \tCoor: (" + move[0] +"," + move[1] +") \ttile: "
					+ current.getSide(0)+current.getSide(1)+current.getSide(2)+current.getSide(3)
					+ "\tTiger Locations: "  + ((i != -1)? i:"N/A")
					+ "\tElapsed Time: " + millis);


	}
}

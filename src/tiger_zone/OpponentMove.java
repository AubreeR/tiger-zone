package tiger_zone;

import java.util.List;
import java.util.Map;

import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.CloseAiPlayer;
import tiger_zone.ai.PoorAiPlayer;

public class OpponentMove {
	private final Board board;
	
	public OpponentMove(){
		board = new Board(Board.createDefaultStack());
	}
	
	
	public final void makeMove(Tile current, int x, int y, int rotation) {

		Position pos = new Position(x,y);
		board.addTile(pos, current);
		
		while(current.getRotation() != rotation){
			current.rotate();
		}
		
	}
}



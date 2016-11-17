package tiger_zone;

/**
 * The <code>Player</code> class represents one of the two players in a game of Tiger Zone, and is used to keep track of
 * points and available tigers.
 */
public class Player {
	
	static class Scoring {
		private int player1Score;
		private int player2Score;
		private Board boardState;
		private int lastTileX;
		private int lastTileY;
		
		public int getplayer1Score() {
			return player1Score;
		}
		
		public int getplayer2Score() {
			return player2Score;
		}
		
		public int[] updateScore(int lastTileX, int lastTileY) {
			int[] updatedScores = new int[2]; //used to return the final values
			int[] foundScores = new int[2]; //used to find new scores calculated and then added to updatedScores
			updatedScores[0] = getplayer1Score(); //set up scores already calculated
			updatedScores[1] = getplayer2Score();
			
			Tile lastTile = boardState.getTile(lastTileX, lastTileY); //get the last updated tile
			char[] lastTileSides = lastTile.getSides(); //get sides of last updated tile
			
			//check if last tile placed finishes a/some den(s)
			//ISSUES: not checking if lastTileX-1 < 0 or lastTileX+1 > MaxBoardX
			//it is possible it make this more efficient such as, only checking sides with jungles for completed dens instead of all sides
			//
			if(lastTileSides[0] == 'j' || lastTileSides[2] == 'j' || lastTileSides[3] == 'j' || lastTileSides[5] == 'j' || lastTileSides[6] == 'j' || lastTileSides[8] == 'j' || lastTileSides[9] == 'j' || lastTileSides[11] == 'j')
			{
				updatedScores = checkForDen(lastTileX+1, lastTileY, updatedScores);
				updatedScores = checkForDen(lastTileX, lastTileY+1, updatedScores);
				updatedScores = checkForDen(lastTileX-1, lastTileY, updatedScores);
				updatedScores = checkForDen(lastTileX+1, lastTileY-1, updatedScores);
				updatedScores = checkForDen(lastTileX+1, lastTileY+1, updatedScores);
				updatedScores = checkForDen(lastTileX+1, lastTileY-1, updatedScores);
				updatedScores = checkForDen(lastTileX-1, lastTileY+1, updatedScores);
				updatedScores = checkForDen(lastTileX-1, lastTileY-1, updatedScores);
			}
			
			return updatedScores;
		}
		
		public int[] checkForDen(int x, int y, int[] scores) {
			int[] foundScores = new int[2];
			foundScores[0] = 0;
			foundScores[0] = 0;
			Tile checkTile = boardState.getTile(x, y);
			if(checkTile.getCenter() == 'd'){ //check if tile has a den
				if((boardState.getTile(x+1,y) != null) //check if den is complete
						&& (boardState.getTile(x-1,y) != null) 
						&& (boardState.getTile(x,y+1) != null) 
						&& (boardState.getTile(x,y-1) != null)
						&& (boardState.getTile(x+1,y+1) != null)
						&& (boardState.getTile(x+1,y-1) != null)
						&& (boardState.getTile(x-1,y+1) != null)
						&& (boardState.getTile(x-1,y-1) != null)){
					//assign score values based on which player's tiger is there if any 
					//NOT IMPLEMENTED BECAUSE OF UNKNOWN HOW TIGERS ARE IMPLEMENTED
				}
				scores[0] += foundScores[0];
				scores[1] += foundScores[1];
			}
			return scores;
		}
		
		
	}
}

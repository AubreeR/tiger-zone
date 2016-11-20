package tiger_zone;

import java.util.Stack;
import java.util.Queue;

/**
 * The <code>Player</code> class represents one of the two players in a game of Tiger Zone, and is used to keep track of
 * points and available tigers.
 */
public class Player {
	
	public Scoring scores;
	public Player()
	{
		scores = new Scoring();
	}
	public static class Scoring 
	{
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
		
		public Scoring(Board boardState, int lastTileX, int lastTileY)
		{
			this.player1Score = 0;
			this.player2Score = 0;
			this.boardState = boardState;
			this.lastTileX = lastTileX;
			this.lastTileY = lastTileY;
		}
		public Scoring()
		{
			this.player1Score = 0;
			this.player2Score = 0;
		}
		
		public int scoreLake(Board boardState, int cartX, int cartY)
		{
			this.boardState = boardState;
			int lakeScore = 0;
			boolean[][] testedTiles = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
			lakeScore = recursiveLake(testedTiles, cartX, cartY);
			return (lakeScore > 0 ? lakeScore : -1);
		}
		
		public int recursiveLake(boolean[][] testedTiles, int cartX, int cartY)
		{
			//mark adjacent tiles with lakes
			//check if 
			
			if(testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)])
			{return 0;}
			if(boardState.getTile(cartX, cartY) == null)
			{return 0;}
			int sum = 1;
			int visitCount = 0;
			for(int i = 0; i < 4; i++)
			{
				if( boardState.getTile(cartX, cartY).getSide(i) == 'l')
				{
					switch(i)
					{
					case 0:	testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
							sum += recursiveLake(testedTiles, cartX,cartY+1);
							visitCount++;
						break;
					case 1: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
							sum += recursiveLake(testedTiles, cartX+1,cartY);
							visitCount++;
						break;
					case 2: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
							sum += recursiveLake(testedTiles, cartX,cartY-1);
							visitCount++;
						break;
					case 3: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
							sum += recursiveLake(testedTiles, cartX-1,cartY);
							visitCount++;
						break;
					default:
						break;
						
					}
					
				}
			}
			if(visitCount == 0)//
			{
				return -200000;
			}
			//visited this tile
			
			return sum;
		}
		
	}
}

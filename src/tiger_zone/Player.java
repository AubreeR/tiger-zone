package tiger_zone;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Player</code> class represents one of the two players in a game of Tiger Zone, and is used to keep track of
 * points and available tigers/crocodiles.
 */
public class Player {
	private int points;
	private final List<Tiger> tigers = new ArrayList<Tiger>(7);
	private final List<Crocodile> crocodiles = new ArrayList<Crocodile>(2);
	private final int index;

	/**
	 * Creates a new instance of <code>Player</code> with the specified index.
	 *
	 * @param index The player's index.
	 */
	public Player(int index) {
		this.index = index;
	}

	/**
	 * Returns the number of points this player has.
	 *
	 * @return points
	 */
	public final int getPoints() {
		return this.points;
	}

	/**
	 * Returns the index of this player.
	 *
	 * @return index
	 */
	public final int getIndex() {
		return this.index;
	}

	/**
	 * Sets the number of points this player has.
	 *
	 * @param points The number of points to set.
	 */
	public final void setPoints(final int points) {
		this.points = points;
	}
	
	public final List<Tiger> getTigers(){
		return this.tigers;
	}

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
			updatedScores[0] = getplayer1Score(); //get player scores
			updatedScores[1] = getplayer2Score();

			Tile lastTile = boardState.getTile(lastTileX, lastTileY); //get the last updated tile
			char[] lastTileSides = lastTile.getSides(); //get sides of last updated tile

			//check if last tile placed finishes a/some den(s)
			//ISSUES: not checking if lastTileX-1 < 0 or lastTileX+1 > MaxBoardX
			//it is possible it make this more efficient such as, only checking sides with jungles for completed dens instead of all sides
			//
			if(lastTileSides[0] == 'j' || lastTileSides[1] == 'j' || lastTileSides[2] == 'j' || lastTileSides[3] == 'j')
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

			//check if any lakes are completed
			updatedScores = checkForLake(lastTileX, lastTileY, updatedScores);

			//check if road complete, one issue where intersection loops scored twice
			
			updatedScores = scoreRoad(boardState, lastTileX, lastTileY);
			
		

			return updatedScores;
		}

		public int[] checkForDen(int x, int y, int[] scores) {
			int[] foundScores = new int[2];
			foundScores[0] = 0;
			foundScores[0] = 0;
			if(boardState.getTile(x, y) != null) {
				Tile checkTile = boardState.getTile(x, y);
				if(checkTile.getCenter() == 'x'){ //check if tile has a den
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
			}
			else {
				scores[0] = 0;
				scores[1] = 0;
			}
			return scores;
		}


		/* TONS OF SPAGHETTI...... here's what I attempted
		 * first if: push the cords and the tile (may only need to push cords) of the lastplaced tile into a stack
		 * while stack is not empty: pop top, check every side if it has a lake. if a side has a lake, check if there is another tile next to it.
		 * if there is a tile thats not flagged then check if that tile is a special case
		 * if special case: then flag the special cases cordinates and add one to total
		 * if normal tile: put that tile in the stack, add one to total
		 */
		public int[] checkForLake(int x, int y, int[] scores) {
			Boolean nullFound = false;
			int totalScore = 0;
			int[] XY = new int[2]; //used for the cordinate stack
			int[] tempXY = new int[2];
			XY[0] = x;
			XY[1] = y;
			Stack<int[]> XYStack = new Stack<int[]>(); //XY and tile stacks will be the same XY is the cords for the tile
			Stack<Tile> tileStack = new Stack<Tile>();
			Boolean[][] flagGrid = new Boolean[152][152];
			int p1Tigers = 0;
			int p2Tigers = 0;
			Tile temp;
			char tempcenter;
			char[] tempsides;


			Tile checkTile = boardState.getTile(x, y);
			char center = checkTile.getCenter();
			char[] sides = checkTile.getSides();

			//ideas: check all sides of current Tile -> if any sides has lake, check those sides ->
			//if tile null, means lake not done end immediately, otherwise add to queue if not flagged, and flag on board
			//if nothing to add and no nulls, take from queue and repeat
			//how to implement flags: create "bool[][] flagGrid = new bool[152][152];" flag that way, takes time to make but quick to check
			//how to score: every pop yields +1 to totalScore
			//tigers still need to be implemented
			//special case tiles, change a value in the center for where we connected to it ('T','B','L','R') <- need caps
			//if special case tile is the placed tile: need to check both sides for completedness


			if(sides[1] == 'l' || sides[4] == 'l' || sides[7] == 'l' || sides[10] == 'l'){ //setting up stack: if the first tile has a lake side, add it to stack
				tileStack.push(boardState.getTile(x, y)); //set up tile stack
				XYStack.push(XY); //set up XY cordinate stack
				flagGrid[x][y] = true; //flag first tile on flaggrid
				totalScore += 1; //add 1 to total score

			}

			while(tileStack.peek() != null) {
				XY = XYStack.pop();
				checkTile = tileStack.pop();
				center = checkTile.getCenter();
				sides = checkTile.getSides();

				if(sides[1] == 'l') //check top
				{
					if(boardState.getTile(XY[0], XY[1]-1) == null) { //check if top has a tile
						nullFound = true;
						break;
					}
					else if (flagGrid[XY[0]][XY[1]-1] == true) {}
					else {
						temp = boardState.getTile(XY[0], XY[1]-1);
						tempcenter = temp.getCenter();
						tempsides = temp.getSides();
						if((tempcenter != 'l' && tempsides[1] == 'l' && tempsides[7] == 'l') //test for special case tiles ex. Tile N
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[1] == 'l' && tempsides[4] == 'l')
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[7] == 'l')
								|| (tempcenter != 'l' && tempsides[7] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[10] == 'l' && tempsides[1] == 'l')){
							flagGrid[XY[0]][XY[1]-1] = true; //if true, flag it then add to score, dont push it to stack
							totalScore += 1;
						}
						else {
							tempXY = XY;
							tempXY[1] = XY[1] - 1;
							tileStack.push(temp); //set up tile stack
							XYStack.push(tempXY); //set up XY cordinate stack
							flagGrid[XY[0]][XY[1]-1] = true; //flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				if(sides[4] == 'l') //check right
				{
					if(boardState.getTile(XY[0]+1, XY[1]) == null) { //check if right has a tile
						nullFound = true;
						break;
					}
					else if (flagGrid[XY[0]+1][XY[1]] == true) {}
					else {
						temp = boardState.getTile(XY[0]+1, XY[1]);
						tempcenter = temp.getCenter();
						tempsides = temp.getSides();
						if((tempcenter != 'l' && tempsides[1] == 'l' && tempsides[7] == 'l') //test for special case tiles ex. Tile N
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[1] == 'l' && tempsides[4] == 'l')
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[7] == 'l')
								|| (tempcenter != 'l' && tempsides[7] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[10] == 'l' && tempsides[1] == 'l')){
							flagGrid[XY[0]+1][XY[1]] = true; //if true, flag it then add to score, dont push it to stack
							totalScore += 1;
						}
						else {
							tempXY = XY;
							tempXY[0] = XY[0] + 1;
							tileStack.push(temp); //set up tile stack
							XYStack.push(tempXY); //set up XY cordinate stack
							flagGrid[XY[0]+1][XY[1]] = true; //flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				if(sides[7] == 'l') //check bottom
				{
					if(boardState.getTile(XY[0], XY[1]+1) == null) { //check if bottom has a tile
						nullFound = true;
						break;
					}
					else if (flagGrid[XY[0]][XY[1]+1] == true) {}
					else {
						temp = boardState.getTile(XY[0], XY[1]+1);
						tempcenter = temp.getCenter();
						tempsides = temp.getSides();
						if((tempcenter != 'l' && tempsides[1] == 'l' && tempsides[7] == 'l') //test for special case tiles ex. Tile N
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[1] == 'l' && tempsides[4] == 'l')
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[7] == 'l')
								|| (tempcenter != 'l' && tempsides[7] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[10] == 'l' && tempsides[1] == 'l')){
							flagGrid[XY[0]][XY[1]+1] = true; //if true, flag it then add to score, dont push it to stack
							totalScore += 1;
						}
						else {
							tempXY = XY;
							tempXY[1] = XY[1] + 1;
							tileStack.push(temp); //set up tile stack
							XYStack.push(tempXY); //set up XY cordinate stack
							flagGrid[XY[0]][XY[1]+1] = true; //flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				if(sides[10] == 'l') //check left
				{
					if(boardState.getTile(XY[0]-1, XY[1]) == null) { //check if left has a tile
						nullFound = true;
						break;
					}
					else if (flagGrid[XY[0]-1][XY[1]] == true) {}
					else {
						temp = boardState.getTile(XY[0]-1, XY[1]);
						tempcenter = temp.getCenter();
						tempsides = temp.getSides();
						if((tempcenter != 'l' && tempsides[1] == 'l' && tempsides[7] == 'l') //test for special case tiles ex. Tile N
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[1] == 'l' && tempsides[4] == 'l')
								|| (tempcenter != 'l' && tempsides[4] == 'l' && tempsides[7] == 'l')
								|| (tempcenter != 'l' && tempsides[7] == 'l' && tempsides[10] == 'l')
								|| (tempcenter != 'l' && tempsides[10] == 'l' && tempsides[1] == 'l')){
							flagGrid[XY[0]-1][XY[1]] = true; //if true, flag it then add to score, dont push it to stack
							totalScore += 1;
						}
						else {
							tempXY = XY;
							tempXY[0] = XY[0] - 1;
							tileStack.push(temp); //set up tile stack
							XYStack.push(tempXY); //set up XY cordinate stack
							flagGrid[XY[0]-1][XY[1]] = true; //flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}

			}


			return scores;
		}
		
		
		public int[] scoreRoad(Board boardState, int cartX, int cartY){
			this.boardState = boardState;
			boolean[][] testedTiles = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
			int[] scores = new int[2];
			int p1Tigers = 0;
			int p2Tigers = 0;
			int croc = 0;
			int unique = 0;
			int tileX = cartX;
			int tileY = cartY;
			int roadcount = 0;
			int scoreCount = 0;
			Boolean Error = false;
			int side = -1;
			
			Tile checkTile = boardState.getTile(tileX, tileY);
			char[] tileSides = checkTile.getSides();
			testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] = true;
			scoreCount++;
			
			
			//check if tile has any roads
			for(int i = 0; i < 4; i++){
				if(tileSides[i] == 't'){
					roadcount++;
				}
			}
			if(roadcount == 0){
				return scores;
			}
			
			//loop through all sides
			for(int k = 0; k < 4; k++){
				if(tileSides[k] == 't'){
					if(k == 0){
						side = 2;
						tileY--;
					}
					else if(k == 1){
						side = 3;
						tileX++;
					}
					else if(k == 2){
						side = 0;
						tileY++;
					}
					else if(k == 3){
						side = 1;
						tileX--;
					}
				}
				
				while(true){
					if(boardState.getTile(tileX, tileY) == null) { //if tile is there
						Error = true;
						break;
					}
					//check if tile has been flagged, if it has, a loop has been made/finished
					if(testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] == true){
						break;
					}
					scoreCount++;
					checkTile = boardState.getTile(tileX, tileY);
					tileSides = checkTile.getSides();
					
					for(int i = 0; i < 4; i++){ //find how many sides have a road
						if(tileSides[i] == 't') {
							roadcount++;
						}
					}
					
					if(roadcount == 0){
						System.err.println("Road Scoring Error: Tile has no roads");
						Error = true;
						break;
					}
					else if (roadcount == 1 || roadcount == 3 || roadcount == 4){
						//came to end of road, score up points
						break;
					}
					
					//road has two sides, so need to find the other side to continue path
					testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] = true;
					for(int i = 0; i < 4; i++){ //find the other side with road
						if(tileSides[i] == 't' && side != i) {
							if(i == 0){
								tileY--;
								side = 2;
							}
							else if(i == 1){
								tileX++;
								side = 3;
							}
							else if(i == 2){
								tileY++;
								side = 0;
							}
							else if(i == 3){
								tileX--;
								side = 1;
							}
						}
					}	
				}
			}
			
			//score up points
			if(Error == true){
				scores[0] = 0;
				scores[1] = 0;
			}
			return scores;
		}
		
		
		
		/*public int scoreLake(Board boardState, int cartX, int cartY)
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
		*/


	}
}
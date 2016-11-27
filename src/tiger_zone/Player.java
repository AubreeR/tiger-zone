package tiger_zone;

import java.util.Stack;
import java.util.List;

/**
 * The <code>Player</code> class represents one of the two players in a game of Tiger Zone, and is used to keep track of
 * points and available tigers/crocodiles.
 */
public class Player {
	private int points;
	private final Stack<Tiger> tigers = new Stack<Tiger>();
	private final Stack<Crocodile> crocodiles = new Stack<Crocodile>();
	private final int index;
	private final String pid;

	/**
	 * Creates a new instance of <code>Player</code> with the specified index.
	 *
	 * @param index The player's index.
	 */
	public Player(int index, String pid) {
		this.index = index;
		this.pid = pid;

		for (int i = 0; i < 7; i++){
			tigers.push(new Tiger(this));
		}
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
	 * Returns the index of this player.
	 *
	 * @return index
	 */
	public final String getPid() {
		return this.pid;
	}

	/**
	 * Sets the number of points this player has.
	 *
	 * @param points The number of points to set.
	 */
	public final void setPoints(final int points) {
		this.points = points;
	}

	public final Stack<Tiger> getTigers() {
		return this.tigers;
	}

	@Override
	public final String toString() {
		return "Player " + this.index;
	}

		public int updateScore(int lastTileX, int lastTileY, Board boardState) {
			int updatedScores = getPoints(); //get player score

			Tile lastTile = boardState.getTile(lastTileX, lastTileY); //get the last updated tile
			char[] lastTileSides = lastTile.getSides(); //get sides of last updated tile

			//check if last tile placed finishes a/some den(s)
			//it is possible it make this more efficient such as, only checking sides with jungles for completed dens instead of all sides
			//
			if(lastTileSides[0] == 'j' || lastTileSides[1] == 'j' || lastTileSides[2] == 'j' || lastTileSides[3] == 'j')
			{
				updatedScores = checkForDen(lastTileX, lastTileY, updatedScores, boardState); //check placed tile
				updatedScores = checkForDen(lastTileX+1, lastTileY, updatedScores, boardState); //check all tiles around the placed tile for dens and if they are complete
				updatedScores = checkForDen(lastTileX, lastTileY+1, updatedScores, boardState);
				updatedScores = checkForDen(lastTileX-1, lastTileY, updatedScores, boardState);
				updatedScores = checkForDen(lastTileX+1, lastTileY-1, updatedScores, boardState);
				updatedScores = checkForDen(lastTileX+1, lastTileY+1, updatedScores, boardState);
				updatedScores = checkForDen(lastTileX+1, lastTileY-1, updatedScores,boardState);
				updatedScores = checkForDen(lastTileX-1, lastTileY+1, updatedScores, boardState);
				updatedScores = checkForDen(lastTileX-1, lastTileY-1, updatedScores, boardState);
			}

			//check if any lakes are completed
			//updatedScores = checkForLake(lastTileX, lastTileY, updatedScores, boardState);

			//check if road complete
			updatedScores += scoreRoad(boardState, lastTileX, lastTileY);

			return updatedScores;
		}

		public int checkForDen(int x, int y, int scores, Board boardState) {
			int pointsEarned = 0;
			if(boardState.getTile(x, y) != null) {
				Tile checkTile = boardState.getTile(x, y);
				if(checkTile.getCenter() == 'x' && checkTile.getTigerPosition() == 5){ //check if tile has a den and has a Tiger on the den
					if((boardState.getTile(x+1,y) != null) //check if den is complete
							&& (boardState.getTile(x-1,y) != null)
							&& (boardState.getTile(x,y+1) != null)
							&& (boardState.getTile(x,y-1) != null)
							&& (boardState.getTile(x+1,y+1) != null)
							&& (boardState.getTile(x+1,y-1) != null)
							&& (boardState.getTile(x-1,y+1) != null)
							&& (boardState.getTile(x-1,y-1) != null)){
						//assign score values based on which player's tiger is there
						Player tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)) {
							pointsEarned = 9;
						}
					}
				}
			}
			else {
				pointsEarned = 0;
			}
			scores = scores+pointsEarned;
			return scores;
		}


		/* TONS OF SPAGHETTI...... here's what I attempted
		 * first if: push the cords and the tile (may only need to push cords) of the lastplaced tile into a stack
		 * while stack is not empty: pop top, check every side if it has a lake. if a side has a lake, check if there is another tile next to it.
		 * if there is a tile thats not flagged then check if that tile is a special case
		 * if special case: then flag the special cases cordinates and add one to total
		 * if normal tile: put that tile in the stack, add one to total
		 * checking special lake tiles for tigers will just check if either lake has a tiger and then adds it to either player tiger count
		 */
		public int checkForLake(int x, int y, int scores, Board boardState) {
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
			int croc = 0;
			int deer = 0;
			int boar = 0;
			int buffalo = 0;
			Tile temp;
			char tempcenter;
			char[] tempsides;
			String originalSides;
			String tigerOwner;
			int[] tigerCheck;


			Tile checkTile = boardState.getTile(x, y);
			char center = checkTile.getCenter();
			char[] sides = checkTile.getSides();

			//ideas: check all sides of current Tile -> if any sides has lake, check those sides ->
			//if tile null, means lake not done end immediately, otherwise add to queue if not flagged, and flag on board
			//if nothing to add and no nulls, take from queue and repeat
			//if special case tile is the placed tile: need to check both sides for completedness


			if(sides[0] == 'l' || sides[1] == 'l' || sides[2] == 'l' || sides[3] == 'l'){ //setting up stack: if the first tile has a lake side, add it to stack
				tileStack.push(boardState.getTile(x, y)); //set up tile stack
				XYStack.push(XY); //set up XY cordinate stack
				flagGrid[x][y] = true; //flag first tile on flaggrid
				totalScore += 1; //add 1 to total score

				while(tileStack.peek() != null) {
					XY = XYStack.pop();
					checkTile = tileStack.pop();
					center = checkTile.getCenter();
					sides = checkTile.getSides();

					if(center == 'p'){ //count up the animals
						boar++;
					}
					else if(center == 'd'){
						deer++;
					}
					else if(center == 'b'){
						buffalo++;
					}
					if(center == 'c'){
						croc++;
					}

					//tiger check here
					tigerCheck = checkLake4Tigers(checkTile);
					p1Tigers += tigerCheck[0];
					p2Tigers += tigerCheck[1];

					if(sides[0] == 'l') //check top
					{
						if(boardState.getTile(XY[0], XY[1]+1) == null) { //check if top has a tile
							nullFound = true;
							break;
						}
						else if (flagGrid[XY[0]][XY[1]+1] == true) {}
						else {
							temp = boardState.getTile(XY[0], XY[1]+1);
							tempcenter = temp.getCenter();
							tempsides = temp.getSides();
							originalSides = temp.getOriginalSides();
							if(originalSides == "ljlj" || originalSides == "jllj"){ //special case tiles
								flagGrid[XY[0]][XY[1]+1] = true; //if true, flag it then add to score, dont push it to stack
								totalScore += 1;
								// add tiger check for special case tiles
								tigerCheck = checkLake4Tigers(checkTile);
								p1Tigers += tigerCheck[0];
								p2Tigers += tigerCheck[1];
							}
							else {
								tempXY = XY;
								tempXY[1] = XY[1] + 1;
								tileStack.push(temp); //push to tile stack
								XYStack.push(tempXY); //push to XY cordinate stack
								flagGrid[XY[0]][XY[1]-1] = true; //flag first tile on flaggrid
								totalScore += 1; //add 1 to total score
							}
						}
					}
					if(sides[1] == 'l') //check right
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
							originalSides = temp.getOriginalSides();
							if(originalSides == "ljlj" || originalSides == "jllj"){
								flagGrid[XY[0]][XY[1]-1] = true; //if true, flag it then add to score, dont push it to stack
								totalScore += 1;
								//tiger scoring
								tigerCheck = checkLake4Tigers(checkTile);
								p1Tigers += tigerCheck[0];
								p2Tigers += tigerCheck[1];
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
					if(sides[2] == 'l') //check bottom
					{
						if(boardState.getTile(XY[0], XY[1]-1) == null) { //check if bottom has a tile
							nullFound = true;
							break;
						}
						else if (flagGrid[XY[0]][XY[1]-1] == true) {}
						else {
							temp = boardState.getTile(XY[0], XY[1]-1);
							tempcenter = temp.getCenter();
							tempsides = temp.getSides();
							originalSides = temp.getOriginalSides();
							if(originalSides == "ljlj" || originalSides == "jllj"){
								flagGrid[XY[0]][XY[1]-1] = true; //if true, flag it then add to score, dont push it to stack
								totalScore += 1;
								tigerCheck = checkLake4Tigers(checkTile);
								p1Tigers += tigerCheck[0];
								p2Tigers += tigerCheck[1];
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
					if(sides[3] == 'l') //check left
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
							originalSides = temp.getOriginalSides();
							if(originalSides == "ljlj" || originalSides == "jllj"){
								flagGrid[XY[0]-1][XY[1]] = true; //if true, flag it then add to score, dont push it to stack
								totalScore += 1;
								tigerCheck = checkLake4Tigers(checkTile);
								p1Tigers += tigerCheck[0];
								p2Tigers += tigerCheck[1];
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
			}
			if(nullFound == true){
				return 0;
			}
			else{
				int uniques = 0;
				if(boar > 0){
					uniques++;
				}
				if(buffalo > 0){
					uniques++;
				}
				if(deer > 0){
					uniques++;
				}
				if(croc > 0){
					if(uniques > 0){
						uniques--;
					}
				}
				return ((2*totalScore)*(1+uniques));
			}
		}


		public int scoreRoad(Board boardState, int cartX, int cartY){
			boolean[][] testedTiles = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
			int score = 0;
			int totalScoreSide[] = {0,0,0,0};
			int p1Tigers = 0;
			int p2Tigers = 0;
			int croc = 0;
			int deer = 0;
			int boar = 0;
			int buffalo = 0;
			int tileX = cartX;
			int tileY = cartY;
			int roadcount = 0;
			int scoreCount = 0;
			Boolean Error = false;
			Boolean Error2 = true;
			int side = -1;
			Player tigerOwner;

			Tile checkTile = boardState.getTile(tileX, tileY);
			char[] tileSides = checkTile.getSides();
			testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] = true;

			//check if tile has any roads
			for(int i = 0; i < 4; i++){
				if(tileSides[i] == 't'){
					roadcount++;
				}
			}
			if(roadcount == 0){
				return score;
			}

			//loop through all sides
			for(int k = 0; k < 4; k++){
				p1Tigers = 0;
				p2Tigers = 0;
				croc = 0;
				deer = 0;
				boar = 0;
				buffalo = 0;
				tileX = cartX;
				tileY = cartY;
				roadcount = 0;
				scoreCount = 0;
				if(tileSides[k] == 't'){
					if(k == 0){//go up
						side = 2;
						tileY++;
						//tiger count
						if(checkTile.getTigerPosition() == 2 && roadcount != 2){
							tigerOwner = checkTile.getTigerOwner();
							if (tigerOwner.equals(this)) {
								p1Tigers++;
							}
							else {
								p2Tigers++;
							}
						}
					}
					else if(k == 1){ //go right
						side = 3;
						tileX++;
						//tiger count
						if(checkTile.getTigerPosition() == 6 && roadcount != 2){
							tigerOwner = checkTile.getTigerOwner();
							if (tigerOwner.equals(this)) {
								p1Tigers++;
							}
							else
							{
								p2Tigers++;
							}
						}
					}
					else if(k == 2){ //go down
						side = 0;
						tileY--;
						//tiger count
						if(checkTile.getTigerPosition() == 8 && roadcount != 2){
							tigerOwner = checkTile.getTigerOwner();
							if (tigerOwner.equals(this)) {
								p1Tigers++;
							}
							else
							{
								p2Tigers++;
							}
						}
					}
					else if(k == 3){ //go left
						side = 1;
						tileX--;
						//tiger count
						if(checkTile.getTigerPosition() == 4 && roadcount != 2){
							tigerOwner = checkTile.getTigerOwner();
							if (tigerOwner.equals(this)) {
								p1Tigers++;
							}
							else
							{
								p2Tigers++;
							}
						}
					}
				}

				if(roadcount == 2){ //before looping, check for tigers on a road with two sides
					if(checkTile.getTigerPosition() == 2 && tileSides[0] == 't'){ //starting at the top, going clockwise, check for tiger on a road
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 6 && tileSides[1] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 8 && tileSides[2] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 4 && tileSides[3] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
				}

				while(true){
					Error2 = true;
					if(boardState.getTile(tileX, tileY) == null) { //if tile is there
						Error = true;
						break;
					}
					//check if tile has been flagged, if it has, a loop has been made/finished
					if(testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] == true){
						break;
					}

					checkTile = boardState.getTile(tileX, tileY);
					tileSides = checkTile.getSides();
					scoreCount++;

					if(checkTile.getCenter() == 'p'){ //count up the animals
						boar++;
					}
					else if(checkTile.getCenter() == 'd'){
						deer++;
					}
					else if(checkTile.getCenter() == 'b'){
						buffalo++;
					}

					if(checkTile.getCenter() == 'c'){
						croc++;
					}

					roadcount = 0;
					for(int i = 0; i < 4; i++){ //find how many sides have a road
						if(tileSides[i] == 't') {
							roadcount++;
						}
					}

					if(roadcount == 0){
						System.err.println("Road Scoring Error1: Tile has no roads");
						Error = true;
						break;
					}
					else if (roadcount == 1 || roadcount == 3 || roadcount == 4){//came to end of road, score up points
						if(checkTile.getTigerPosition() == 2 && side == 0){ // check which side we came from and if there is a tiger there
							tigerOwner = checkTile.getTigerOwner();
							if(tigerOwner.equals(this)){p1Tigers++;}
							else{p2Tigers++;}
						}
						else if(checkTile.getTigerPosition() == 6 && side == 1){
							tigerOwner = checkTile.getTigerOwner();
							if(tigerOwner.equals(this)){p1Tigers++;}
							else{p2Tigers++;}
						}
						else if(checkTile.getTigerPosition() == 8 && side == 2){
							tigerOwner = checkTile.getTigerOwner();
							if(tigerOwner.equals(this)){p1Tigers++;}
							else{p2Tigers++;}
						}
						else if(checkTile.getTigerPosition() == 4 && side == 3){
							tigerOwner = checkTile.getTigerOwner();
							if(tigerOwner.equals(this)){p1Tigers++;}
							else{p2Tigers++;}
						}
						break;
					}

					//road has two sides, so need to find the other side to continue path
					testedTiles[boardState.getBoardPosX(tileX)][boardState.getBoardPosY(tileY)] = true;
					for(int i = 0; i < 4; i++){ //find the other side with road
						if(tileSides[i] == 't' && side != i) {
							Error2 = false;
							if(i == 0){
								tileY--;
								side = 2; //set side for the next tile, telling the next tile that, that is the side we came from
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

					//check road with two trail sides for a tiger
					if(checkTile.getTigerPosition() == 2 && tileSides[0] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 6 && tileSides[1] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 8 && tileSides[2] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 4 && tileSides[3] == 't'){
						tigerOwner = checkTile.getTigerOwner();
						if(tigerOwner.equals(this)){p1Tigers++;}
						else{p2Tigers++;}
					}

					//error check
					if(Error2 == true){
						System.err.println("Road Scoring Error2: couldn't find other side of road with 2 sides.");
						Error = true;
						break;
					}
				}
				//score up and add to scores
				scoreCount = scoreCount + buffalo + deer + boar - croc;
				//decide who gets the points
				if(p2Tigers > p1Tigers){
					totalScoreSide[k] = 0;
				}
				else if(p1Tigers > 0 && p1Tigers >= p2Tigers){
					totalScoreSide[k] = scoreCount;
				}
			}

			if(Error == true){
				score = 0;
			}
			else{
				score = totalScoreSide[0] + totalScoreSide[1] + totalScoreSide[2] + totalScoreSide[3];
			}
			return score;
		}

		public int[] checkLake4Tigers(Tile tile){
			int[] playersTigerCount = {0,0};
			char[] sides = tile.getSides();
			int tigerPos = tile.getTigerPosition();
			if(tile.hasTiger() == true){
				if((tigerPos == 1 && sides[0] == 'l')
					|| (tigerPos == 2 && sides[0] == 'l')
					|| (tigerPos == 3 && sides[0] == 'l')
					|| (tigerPos == 4 && sides[3] == 'l')
					|| (tigerPos == 6 && sides[1] == 'l')
					|| (tigerPos == 8 && sides[2] == 'l'))
				{
					if(tile.getTigerOwner().equals(this)){
						playersTigerCount[0]++;
					}
					else{
						playersTigerCount[1]++;
					}
				}
			}
			return playersTigerCount;
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

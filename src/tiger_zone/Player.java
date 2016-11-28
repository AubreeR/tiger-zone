package tiger_zone;

import java.util.Stack;
import java.util.HashSet;
import java.util.Set;

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

	public int updateScore(Board boardState, Position position) {
		
		int[] lakeScore; 
		Set<Position> testedTiles = new HashSet<Position>();
		int updatedScores = getPoints(); //get player score
		int lastTileX = position.getX();
		int lastTileY = position.getY();

		//check if last tile placed finishes a/some den(s)
		//it is possible it make this more efficient such as, only checking sides with jungles for completed dens instead of all sides
		//
		updatedScores = checkForDen(lastTileX, lastTileY, updatedScores, boardState); //check placed tile
		updatedScores = checkForDen(lastTileX+1, lastTileY, updatedScores, boardState); //check all tiles around the placed tile for dens and if they are complete
		updatedScores = checkForDen(lastTileX-1, lastTileY, updatedScores, boardState);
		updatedScores = checkForDen(lastTileX, lastTileY+1, updatedScores, boardState);
		updatedScores = checkForDen(lastTileX, lastTileY-1, updatedScores, boardState);
		updatedScores = checkForDen(lastTileX+1, lastTileY+1, updatedScores, boardState);
		updatedScores = checkForDen(lastTileX+1, lastTileY-1, updatedScores,boardState);
		updatedScores = checkForDen(lastTileX-1, lastTileY+1, updatedScores, boardState);
		updatedScores = checkForDen(lastTileX-1, lastTileY-1, updatedScores, boardState);

		//check if any lakes are completed
		//updatedScores += checkForLake(lastTileX, lastTileY, updatedScores, boardState);
		//trash{totalScore, p1Tiger, p2Tiger, deer, boar, buffalo, croc, the null check}
		lakeScore = recursiveLake(testedTiles, position, boardState, -1);
		//System.err.println("lakeScore = " + lakeScore[0] + " " + lakeScore[1] + " " + lakeScore[2] + " " + lakeScore[3] + " " + lakeScore[4] + " " + lakeScore[5] + " " + lakeScore[6] + " " + lakeScore[7] + "Tile:" + lastTileX + " " + lastTileY);
		if(lakeScore[7] == 0){
			int uniques = 0;
			if(lakeScore[3] > 0){
				uniques++;
			}
			if(lakeScore[4] > 0){
				uniques++;
			}
			if(lakeScore[5] > 0){
				uniques++;
			}
			if(lakeScore[6] > 0){
				if(uniques > 0){
					uniques--;
				}
			}
			//System.err.println("tiles: " + totalScore + "uniques: " + uniques );
			if(lakeScore[1] >= lakeScore[2] && lakeScore[1] != 0){
				//System.err.println("tiles: " + lakeScore[0] + " uniques: " + uniques + " Score: " + (((2*lakeScore[0])*(1+uniques)) + updatedScores + " Tile: " + lastTileX + " " + lastTileY));
				updatedScores = ((2*lakeScore[0])*(1+uniques)) + updatedScores;
			}
			if(lakeScore[2] >= lakeScore[1] && lakeScore[2] != 0){
				//System.err.println("tiles: " + lakeScore[0] + " uniques: " + uniques + " Score: " + ((((2*lakeScore[0])*(1+uniques))*-1) + updatedScores + " Tile: " + lastTileX + " " + lastTileY));
				updatedScores = (((2*lakeScore[0])*(1+uniques))*-1) + updatedScores;
			}
			else{
				updatedScores += 0;
			}
		}
		else
		{
			updatedScores += 0;
		}
		

		//check if road complete
		//updatedScores += scoreRoad(boardState, lastTileX, lastTileY);
		
		return updatedScores;
	}

	public int checkForDen(int x, int y, int scores, Board boardState) {
		String tigerOwner;
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
					tigerOwner = checkTile.getTigerOwner().pid;
					//System.err.println("Den Completed at: " + x + " " + y + " " + tigerOwner + " " + checkTile.getTigerOwner().pid);
					if(tigerOwner.equals(getPid())){
						pointsEarned = 9;
					}
					else{pointsEarned = -9;} //+100 for other player to differentiate
				}
				
			}
		}
		else {
			pointsEarned = 0;
		}
		scores = scores+pointsEarned;
		return scores;
	}
	
	
	
	
	
	
	public int scoreRoad(Board boardState, int cartX, int cartY){
		Set<int[]> testedTiles = new HashSet<int[]>();
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
		int[] tileXY = new int[2];
		tileXY[0] = cartX;
		tileXY[1] = cartY;
		int roadcount = 0;
		int scoreCount = 0;
		Boolean Error = false;
		Boolean Error2 = true;
		int side = -1;
		String tigerOwner;
		
		Tile checkTile = boardState.getTile(tileX, tileY);
		char[] tileSides = checkTile.getSides();
		testedTiles.add(tileXY);		
		
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
			Error = false;
			Error2 = true;
			p1Tigers = 0;
			p2Tigers = 0;
			croc = 0;
			deer = 0;
			boar = 0;
			buffalo = 0;
			tileX = cartX;
			tileY = cartY;
			tileXY[0] = tileX;
			tileXY[1] = tileY;
			roadcount = 0;
			scoreCount = 0;
			checkTile = boardState.getTile(tileX, tileY);
			tileSides = checkTile.getSides();
			side = -1;
			int loopkillercount = 0;
			if(tileSides[k] == 't'){
				if(k == 0){//go up
					side = 2; 
					tileY++;
					//tiger count
					if(checkTile.getTigerPosition() == 2 && roadcount != 2){
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){
							p1Tigers++;
						}
						else{
							p2Tigers++;
						}
					}
				}
				else if(k == 1){ //go right
					side = 3;
					tileX++;
					//tiger count
					if(checkTile.getTigerPosition() == 6 && roadcount != 2){
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){
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
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){
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
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){
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
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 6 && tileSides[1] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 8 && tileSides[2] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 4 && tileSides[3] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
			}
			
			while(true){
				loopkillercount++;
				if(loopkillercount == 30){
					Error = true;
					break;
				}
				tileXY[0] = tileX;
				tileXY[1] = tileY;
				//System.err.println("X: " + tileX + " Y: " + tileY);
				Error2 = true;
				if(boardState.getTile(tileX, tileY) == null) { //if tile is there
					Error = true;
					break;
				}
				//check if tile has been flagged, if it has, a loop has been made/finished
				if(!testedTiles.contains(tileXY)){
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
					System.err.println("Road Scoring Error1: Tile has no roads, Side: " + side + " X: " + tileX + " Y: " + tileY + "CARTS:" + cartX + " " + cartY + " K:" + k);
					Error = true;
					break;
				}
				else if (roadcount == 1 || roadcount == 3 || roadcount == 4){//came to end of road, score up points
					if(checkTile.getTigerPosition() == 2 && side == 0){ // check which side we came from and if there is a tiger there
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 6 && side == 1){
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 8 && side == 2){
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){p1Tigers++;}
						else{p2Tigers++;}
					}
					else if(checkTile.getTigerPosition() == 4 && side == 3){
						tigerOwner = checkTile.getTigerOwner().pid;
						if(tigerOwner.equals(getPid())){p1Tigers++;}
						else{p2Tigers++;}
					}
					break;
				}
				
				//road has two sides, so need to find the other side to continue path
				Boolean nullTile = false;
				int sidetrailscount = 0;
				testedTiles.add(tileXY);
				for(int i = 0; i < 4; i++){ //find the other side with road
					if(tileSides[i] == 't'){
						Error2 = false;
						if(i == 0){
							tileXY[0] = tileX;
							tileXY[1] = tileY+1;
							if(boardState.getTile(tileX, tileY+1) == null){
								nullTile = true;
								break;
							}
							else if(testedTiles.contains(tileXY)){
								sidetrailscount++;
							}
							else {
								tileY++;
								side = 2;
								break;
							}
						}
						if(i == 1){
							tileXY[0] = tileX+1;
							tileXY[1] = tileY;
							if(boardState.getTile(tileX+1, tileY) == null){
								nullTile = true;
								break;
							}
							else if(testedTiles.contains(tileXY)){
								sidetrailscount++;
							}
							else {
								tileX++;
								side = 3;
								break;
							}
							
						}
						if(i == 2){
							tileXY[0] = tileX;
							tileXY[1] = tileY-1;
							if(boardState.getTile(tileX, tileY-1) == null){
								nullTile = true;
								break;
							}
							else if(testedTiles.contains(tileXY)){
								sidetrailscount++;
							}
							else {
								tileY--;
								side = 0;
								break;
							}
							
						}
						if(i == 3){
							tileXY[0] = tileX-1;
							tileXY[1] = tileY;
							if(boardState.getTile(tileX-1, tileY) == null){
								nullTile = true;
								break;
							}
							else if(testedTiles.contains(tileXY)){
								sidetrailscount++;
							}
							else {
								tileX--;
								side = 1;
								break;
							}
							
						}
					}
					
					
				}
				if(nullTile == true){ //happens if one side of the two sided road has a null
					Error = true; 
					break;
				}
				if(sidetrailscount == 2){
					break;
				}
				
				//check road with two trail sides for a tiger
				if(checkTile.getTigerPosition() == 2 && tileSides[0] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 6 && tileSides[1] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 8 && tileSides[2] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				else if(checkTile.getTigerPosition() == 4 && tileSides[3] == 't'){
					tigerOwner = checkTile.getTigerOwner().pid;
					if(tigerOwner.equals(getPid())){p1Tigers++;}
					else{p2Tigers++;}
				}
				
				//error check
				if(Error2 == true){
					System.err.println("Road Scoring Error2: couldn't find other side of road with 2 sides.");
					Error = true;
					break;
				}
			}
			if(Error == true){totalScoreSide[k] = 0;}
			else{
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
		}
		
		
		score = totalScoreSide[0] + totalScoreSide[1] + totalScoreSide[2] + totalScoreSide[3];
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
				if(tile.getTigerOwner().equals(getPid())){
					playersTigerCount[0]++;
				}
				else{
					playersTigerCount[1]++;
				}
			}
		}
		return playersTigerCount;
	}
	
	
	public int[] recursiveLake(Set<Position> testedTiles, Position position, Board boardState, int side) { 
		//trash{totalScore, p1Tiger, p2Tiger, deer, boar, buffalo, croc}
		Boolean special = false;
		int[] trash = {0,0,0,0,0,0,0,0};
		int[] recieved;
		String owner;
		String original;
		if(boardState.getTile(position) == null) {
			trash[7] = -1;
			return trash;
		}
		
		//already visited this tile or no more tiles to check
		if(testedTiles.contains(position)) {
			return trash;
		}
		
		//mark adjacent tiles with lakes
		if(boardState.getTile(position).hasTiger()) {
			owner = boardState.getTile(position).getTigerOwner().getPid();
			int tigerPos = boardState.getTile(position).getTigerPosition();
			char[] sides = boardState.getTile(position).getSides();
			if((tigerPos == 1 && sides[0] == 'l')
					|| (tigerPos == 2 && sides[0] == 'l')
					|| (tigerPos == 3 && sides[0] == 'l')
					|| (tigerPos == 4 && sides[3] == 'l')
					|| (tigerPos == 6 && sides[1] == 'l')
					|| (tigerPos == 8 && sides[2] == 'l'))
				{
					if(owner.equals(getPid())){
						trash[1]++;
					}
					else{
						trash[2]++;
					}
				}
		}
		
		original = boardState.getTile(position).getOriginalSides();
		if(original.equals("jllj") || original.equals("ljlj")){
			if(side == -1){
				for (int i = 0; i < 4; i++) {
					if (boardState.getTile(position).getSide(i) == 'l') {
						if(!testedTiles.contains(position)) {
							testedTiles.add(position);
						}
						switch(i) {
							case 0:	
								recieved = recursiveLake(testedTiles, position.north(), boardState, 2);
								if(recieved[7] < 0 || ((recieved[1] == 0) && recieved[2] == 0)){
									for(int k = 0; i < 8; i++){
										recieved[k] = 0;
									}
								}
								
								trash[0] += recieved[0];
								trash[1] += recieved[1];
								trash[2] += recieved[2];
								trash[3] += recieved[3];
								trash[4] += recieved[4];
								trash[5] += recieved[5];
								trash[6] += recieved[6];
								trash[7] += recieved[7];
								
								break;
							case 1: 
								recieved = recursiveLake(testedTiles, position.east(), boardState, 3);
								if(recieved[7] < 0 || ((recieved[1] == 0) && recieved[2] == 0)){
									for(int k = 0; i < 8; i++){
										recieved[k] = 0;
									}
								}
								
								trash[0] += recieved[0];
								trash[1] += recieved[1];
								trash[2] += recieved[2];
								trash[3] += recieved[3];
								trash[4] += recieved[4];
								trash[5] += recieved[5];
								trash[6] += recieved[6];
								trash[7] += recieved[7];
								
								break;
							case 2: 
								recieved = recursiveLake(testedTiles, position.south(), boardState, 0);
								if(recieved[7] < 0 || ((recieved[1] == 0) && recieved[2] == 0)){
									for(int k = 0; i < 8; i++){
										recieved[k] = 0;
									}
								}
								
								trash[0] += recieved[0];
								trash[1] += recieved[1];
								trash[2] += recieved[2];
								trash[3] += recieved[3];
								trash[4] += recieved[4];
								trash[5] += recieved[5];
								trash[6] += recieved[6];
								trash[7] += recieved[7];
								
								break;
							case 3: 
								recieved = recursiveLake(testedTiles, position.west(), boardState, 1);
								if(recieved[7] < 0 || ((recieved[1] == 0) && recieved[2] == 0)){
									for(int k = 0; i < 8; i++){
										recieved[k] = 0;
									}
								}
								
								trash[0] += recieved[0];
								trash[1] += recieved[1];
								trash[2] += recieved[2];
								trash[3] += recieved[3];
								trash[4] += recieved[4];
								trash[5] += recieved[5];
								trash[6] += recieved[6];
								trash[7] += recieved[7];
								
								break;
							default:
								break;	
						}	
					}
				}
			}
			else if(side != -1){
				special = true;
			}
		}
		
		if(boardState.getTile(position).getCenter() == 'p'){ //count up the animals
			trash[4]++;
		}
		else if(boardState.getTile(position).getCenter() == 'd'){
			trash[3]++;
		}
		else if(boardState.getTile(position).getCenter() == 'b'){
			trash[5]++;
		}
		if(boardState.getTile(position).getCenter() == 'c'){
			trash[6]++;
		}
		
		trash[0]++;
		if(special == false){
			for (int i = 0; i < 4; i++) {
				if (boardState.getTile(position).getSide(i) == 'l') {
					if(!testedTiles.contains(position)) {
						testedTiles.add(position);
					}
					switch(i) {
						case 0:	
							recieved = recursiveLake(testedTiles, position.north(), boardState, 2);
							trash[0] += recieved[0];
							trash[1] += recieved[1];
							trash[2] += recieved[2];
							trash[3] += recieved[3];
							trash[4] += recieved[4];
							trash[5] += recieved[5];
							trash[6] += recieved[6];
							trash[7] += recieved[7];
							break;
						case 1: 
							recieved = recursiveLake(testedTiles, position.east(), boardState, 3);
							trash[0] += recieved[0];
							trash[1] += recieved[1];
							trash[2] += recieved[2];
							trash[3] += recieved[3];
							trash[4] += recieved[4];
							trash[5] += recieved[5];
							trash[6] += recieved[6];
							trash[7] += recieved[7];
							break;
						case 2: 
							recieved = recursiveLake(testedTiles, position.south(), boardState, 0);
							trash[0] += recieved[0];
							trash[1] += recieved[1];
							trash[2] += recieved[2];
							trash[3] += recieved[3];
							trash[4] += recieved[4];
							trash[5] += recieved[5];
							trash[6] += recieved[6];
							trash[7] += recieved[7];
							break;
						case 3: 
							recieved = recursiveLake(testedTiles, position.west(), boardState, 1);
							trash[0] += recieved[0];
							trash[1] += recieved[1];
							trash[2] += recieved[2];
							trash[3] += recieved[3];
							trash[4] += recieved[4];
							trash[5] += recieved[5];
							trash[6] += recieved[6];
							trash[7] += recieved[7];
							break;
						default:
							break;	
					}	
				}
			}
		}
		return trash;
	}



	//if special tile is placed and scored from, not scored correctly
		/*public int checkForLake(int x, int y, int scores, Board boardState) {
			Boolean nullFound = false;
			Boolean special = false;
			int totalScore = 0;
			int[] XY = new int[2]; //used for the cordinate stack
			int[] tempXY = new int[2];
			Stack<int[]> XYStack = new Stack<int[]>(); //XY and tile stacks will be the same XY is the cords for the tile
			Set<int[]> flagGrid = new HashSet<int[]>();
			int p1Tigers = 0;
			int p2Tigers = 0;
			int croc = 0;
			int deer = 0;
			int boar = 0;
			int buffalo = 0;
			Tile temp;
			char[] tempsides;
			String originalSides;
			int[] tigerCheck;
			int LOOPKILLER = 30;
			int LOOPKILLERCOUNT = 0;

			//check if recieved Tile is null
			if(boardState.getTile(x,y) == null){
				System.err.println("Score Lake Error 1");
				return 0;
			}
			
			//get first tiles info
			Tile checkTile = boardState.getTile(x, y);
			char center = checkTile.getCenter();
			char[] sides = checkTile.getSides();
			originalSides = checkTile.getOriginalSides();
			XY[0] = x;
			XY[1] = y;
			
			//if its special case, dont do anything
			if(originalSides.equals("jllj") || originalSides.equals("ljlj")){
				special = true;
			}
			
			//if it is not a special case, and has a lake side, add it to the stack
			if((sides[0] == 'l' || sides[1] == 'l' || sides[2] == 'l' || sides[3] == 'l') && special == false){//setting up stack: if the first tile has a lake side, add it to stack
				XYStack.push(XY); //set up XY cordinate stack
				System.err.println("pushed1 " + XY[0] + " " + XY[1]);
				flagGrid.add(XY);
				totalScore += 1; //add 1 to total score
			}
			
			

			while(!XYStack.isEmpty()) {
				//pop from stack and get tile info
				XY = XYStack.pop();
				System.err.println("pop " + XY[0] + " " + XY[1]);
				checkTile = boardState.getTile(XY[0], XY[1]);
				center = checkTile.getCenter();
				sides = checkTile.getSides();
				originalSides = checkTile.getOriginalSides();
				
				//error checker for infinite loop
				LOOPKILLERCOUNT++;
				if(LOOPKILLERCOUNT == LOOPKILLER){
					System.err.println("Error: lakecompletecheck LOOP KILLER ACTIVATED X,Y:" + XY[0] + " " + XY[1]);
					nullFound = true;
					break;
				}
				
				//check for animals on current tile
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
				
				//check for tiger on current tile
				tigerCheck = checkLake4Tigers(checkTile);
				p1Tigers += tigerCheck[0];
				p2Tigers += tigerCheck[1];

				//check all sides of current tile for another lake
				if(sides[0] == 'l') //check top
				{
					//tempXY is equal to current tile cordinates, plus one tile away in a direction
					tempXY[0] = XY[0];
					tempXY[1] = XY[1];
					tempXY[1] = tempXY[1] + 1;
					if(boardState.getTile(tempXY[0],tempXY[1]) == null) { //check if top has a tile
						System.err.println("done");
						nullFound = true;
						break;
					}
					else if (flagGrid.contains(tempXY)) {} //check is top has been flagged yet
					else {
						temp = boardState.getTile(tempXY[0],tempXY[1]);
						tempsides = temp.getSides();
						originalSides = temp.getOriginalSides();
						if(originalSides.equals("jllj") || originalSides.equals("ljlj")){ //if the next tile is a special case tile
							flagGrid.add(tempXY); //flag the tile but dont push it
							totalScore += 1; 
							tigerCheck = checkLake4Tigers(temp);
							p1Tigers += tigerCheck[0];
							p2Tigers += tigerCheck[1];
						}
						else 
						{
							XYStack.push(tempXY); //push to XY cordinate stack
							System.err.println("pushed " + tempXY[0] + " " + tempXY[1]);
							flagGrid.add(tempXY);//flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				
				if(sides[1] == 'l') //check right
				{
					tempXY[0] = XY[0];
					tempXY[1] = XY[1];
					tempXY[0] = tempXY[0] + 1;
					if(boardState.getTile(tempXY[0],tempXY[1]) == null) { //check if top has a tile
						System.err.println("done");
						nullFound = true;
						break;
					}
					else if (flagGrid.contains(tempXY)) {}
					else {
						temp = boardState.getTile(tempXY[0],tempXY[1]);
						tempsides = temp.getSides();
						originalSides = temp.getOriginalSides();
						if(originalSides.equals("jllj") || originalSides.equals("ljlj")){ //special case tiles
							flagGrid.add(tempXY);//if true, flag it then add to score, dont push it to stack
							totalScore += 1;
							// add tiger check for special case tiles
							tigerCheck = checkLake4Tigers(temp);
							p1Tigers += tigerCheck[0];
							p2Tigers += tigerCheck[1];
						}
						else
						{
							XYStack.push(tempXY); //push to XY cordinate stack
							System.err.println("pushed " + tempXY[0] + " " + tempXY[1]);
							flagGrid.add(tempXY);//flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				
				if(sides[2] == 'l') //check bottom
				{
					tempXY[0] = XY[0];
					tempXY[1] = XY[1];
					tempXY[1] = tempXY[1] - 1;
					if(boardState.getTile(tempXY[0],tempXY[1]) == null) { //check if top has a tile
						System.err.println("done");
						nullFound = true;
						break;
					}
					else if (flagGrid.contains(tempXY)) {}
					else {
						temp = boardState.getTile(tempXY[0],tempXY[1]);
						tempsides = temp.getSides();
						originalSides = temp.getOriginalSides();
						if(originalSides.equals("jllj") || originalSides.equals("ljlj")){ //special case tiles
							flagGrid.add(tempXY);//if true, flag it then add to score, dont push it to stack
							totalScore += 1;
							// add tiger check for special case tiles
							tigerCheck = checkLake4Tigers(temp);
							p1Tigers += tigerCheck[0];
							p2Tigers += tigerCheck[1];
						}
						else
						{
							XYStack.push(tempXY); //push to XY cordinate stack
							System.err.println("pushed " + tempXY[0] + " " + tempXY[1]);
							flagGrid.add(tempXY);//flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				
				if(sides[3] == 'l') //check left
				{
					tempXY[0] = XY[0];
					tempXY[1] = XY[1];
					tempXY[0] = tempXY[0] - 1;
					if(boardState.getTile(tempXY[0],tempXY[1]) == null) { //check if top has a tile
						System.err.println("done");
						nullFound = true;
						break;
					}
					else if (flagGrid.contains(tempXY)) {}
					else {
						temp = boardState.getTile(tempXY[0],tempXY[1]);
						tempsides = temp.getSides();
						originalSides = temp.getOriginalSides();
						if(originalSides.equals("jllj") || originalSides.equals("ljlj")){ //special case tiles
							flagGrid.add(tempXY);//if true, flag it then add to score, dont push it to stack
							totalScore += 1;
							// add tiger check for special case tiles
							tigerCheck = checkLake4Tigers(temp);
							p1Tigers += tigerCheck[0];
							p2Tigers += tigerCheck[1];
						}
						else
						{
							XYStack.push(tempXY); //push to XY cordinate stack
							System.err.println("pushed " + tempXY[0] + " " + tempXY[1]);
							flagGrid.add(tempXY);//flag first tile on flaggrid
							totalScore += 1; //add 1 to total score
						}
					}
				}
				
				//if current tile has no lakes, error occured
				if(sides[0] != 'l' && sides[1] != 'l' && sides[2] != 'l' && sides[3] != 'l'){
					System.err.println("Lake complete Error2: tile has no lakes... XY:" + XY[0] + " " + XY[1] + "cartXY: " + x + " " + y);
					nullFound = true;
					break;
				}
			}
			//return 0 because lake is not complete
			if(nullFound == true){
				return 0;
			}
			else{ //lake is completed and score
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
				//System.err.println("tiles: " + totalScore + "uniques: " + uniques );
				if(p1Tigers >= p2Tigers && p1Tigers != 0){
					System.err.println("tiles: " + totalScore + " uniques: " + uniques + " Score: " + (((2*totalScore)*(1+uniques)) + scores));
					return ((2*totalScore)*(1+uniques)) + scores;
				}
				else if(p2Tigers >= p1Tigers && p2Tigers != 0){
					System.err.println("tiles: " + totalScore + " uniques: " + uniques + " Score: " + ((((2*totalScore)*(1+uniques))+100) + scores));
					return (((2*totalScore)*(1+uniques))+100) + scores;
				}
				else{
					return 0;
				}
				
			}
		}*/


	


}

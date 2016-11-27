package tiger_zone;

import java.util.Stack;

/**
 * The <code>Board</code> class represents both the collection of placed tiles and the stack of tiles which have not yet
 * been placed.
 */
public class Board {
	private BoardCell[][] gameGrid = new BoardCell[152][152];
	private final Stack<Tile> pile;
	private final int origin; // center of the gameGrid cartesian view
	private final RuleEngine placementEngine = new RuleEngine();

	/**
	 * Creates an empty board with a particular stack of tiles.
	 * Assigns an origin point to map cartesian coordinates.
	 * @param pile Stack of unplaced tiles.
	 */
	public Board(BoardCell[][] brd, Stack<Tile> pile, int origin)
	{
		this.gameGrid= brd;
		this.pile=pile;
		this.origin=origin;
	}
	public Board(final Stack<Tile> pile) {
		this.pile = pile;
		this.origin = pile.size(); //calculates mid-point of initialized gameGrid.length
		for (int i = 0; i < this.gameGrid.length; ++i) {
			for (int j = 0; j <  this.gameGrid.length; ++j) {
				this.gameGrid[i][j] = new BoardCell(i, j);
			}
		}

		char[] Ssides = {'t','l','t','j'};
		char[] Stigers = {'j','t','j','=','=','l','=','=','='};
		char[] Scrocs = {'=','t','j','=','=','=','=','=','='};
		Tile init = new Tile( Ssides, '-', Stigers, Scrocs, "./src/resources/tile19.png");
		this.gameGrid[this.getBoardPosX(0)][this.getBoardPosY(0)].setTile(init);

	}

	/**
	 * Adds tile to this board at position (x, y).
	 *
	 * @param x The x coordinate of the destination translation to the cartesian system.
	 * @param y The y coordinate of the destination translation to the cartesian system.
	 * @param tile The instance of <code>Tile</code> to add
	 * @return if tile was successfully placed
	 */
	public final boolean addTile(int x, int y, final Tile tile) {
		if (this.validTilePlacement(x, y, tile, false)) {

			x += this.getOrigin();
   			y =  this.getOrigin() - y;


			this.gameGrid[x][y].setTile(tile);
			return true;
		}
		return false;
	}
	
	public BoardCell[][] getGameGrid()
	{
		return this.gameGrid;
	}

	/**
	 * Check if the tile can be placed at board position (x, y).
	 *
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @param tile The instance of <code>Tile</code> to add
	 * @return true, if the tile can be placed in the location, otherwise false
	 */
	public final boolean validTilePlacement(final int x, final int y, final Tile tile, boolean trace) {

		if (Math.abs(x) >= this.gameGrid.length|| Math.abs(y) >= this.gameGrid.length) {

			return false;
		}
		
		placementEngine.clearRules();

		// Check for adjacent tiles
		placementEngine.addRule(new AdjacencyRule(this, x, y,trace));
		placementEngine.addRule(new SideMatchRule(this, x, y, tile, trace));

		return placementEngine.evaluateRules();
	}
	
	/**
	 * Check if the tile can be placed at board position (x, y).
	 *
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @param tile The instance of <code>Tile</code> to add
	 * @return true, if the tile can be placed in the location, otherwise false
	 */
	public final boolean validTilePlacement(final int x, final int y, final Tile tile) {

		if (Math.abs(x) >= this.gameGrid.length|| Math.abs(y) >= this.gameGrid.length) {

			return false;
		}

		placementEngine.clearRules();

		// Check for adjacent tiles
		placementEngine.addRule(new AdjacencyRule(this, x, y,true));
		placementEngine.addRule(new SideMatchRule(this, x, y, tile, true));

		return placementEngine.evaluateRules();
	}

	/**
	 * Check if the tiger can be placed at board position (x, y).
	 *
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @param tile The instance of <code>Tile</code> to add
	 * @return true, if the tile can be placed in the location, otherwise false
	 */
	public final boolean validTigerPlacement(final int x, final int y, final Tile tile, final int zone, boolean trace)
	{
		if (Math.abs(x) >= this.gameGrid.length|| Math.abs(y) >= this.gameGrid.length || zone < 1 || zone >9) {

			return false;
		}
		placementEngine.clearRules();
		switch(tile.getZone(zone))
		{
		case 'j': return false;
		//case 't': placementEngine.addRule(new TigerTrailRule(this,x,y,tile, zone));
		case 'l': placementEngine.addRule(new TigerLakeRule(this,x,y,tile,zone, trace));
			break;
		case 'x': return true;
		}
		
		

		return placementEngine.evaluateRules();
	}
	/**
	 * Translates: Cartesian-> 2D Matrix position
	 * @param type int coordinate point
	 * @return value from cartesian points to matrix gameGrid[x][?] index
	 *
	 */
	 public int  getBoardPosX(int x) {
    		return x + this.getOrigin();
 	}

	/**
	 * Translates: Cartesian-> 2D Matrix position
	 * @param type int coordinate point
	 * @return value from cartesian points to matrix gameGrid[?][y] index
	 *
	 */
  	public int getBoardPosY(int y) {

    		return this.getOrigin() - y;
  	}


  	public int getBoardLength()
  	{
  		return this.gameGrid.length;
  	}



	/**
	 * Returns the tile located at position (x, y).
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the instance of <code>Tile</code> at position (x, y)
	 */
	public Tile getTile(final int x, final int y){

		return this.gameGrid[this.getBoardPosX(x)][this.getBoardPosY(y)].getTile();

	}
	
	public BoardCell getBoardCell(final int x, final int y)
	{
		return this.gameGrid[this.getBoardPosX(x)][this.getBoardPosY(y)];
	}

	/**
	 * Returns this board's stack of unplaced tiles.
	 *
	 * @return Stack of unplaced tiles.
	 */
	public Stack<Tile> getPile() {
		return this.pile;
	}

	public static void buildStack(Stack<Tile> pile, char[] edges, char center, char[] tigerSpots,
			char[] crocSpots, String file, int tileCount){
		for(int i = 0; i < tileCount; i++){
			pile.push(new Tile(edges, center, tigerSpots, crocSpots, file));
		}
	}

	/**
	 * Creates a "default stack", which contains one of each tile "in order" (e.g. tile A is at the bottom of the stack
	 * whilst tile X is at the top of the stack).
	 *
	 * @return Stack of tiles.
	 */
	public static Stack<Tile> createDefaultStack() {
		// j = jungle, l = lake, t = game-trail, x = den, b = buffalo, d = deer, p = boar, - = nothing specal
		//sides names:    N   E   S   W
		//sides values:   1   2   3   4
		char[] Asides = {'j','j','j','j'};
		char[] Bsides = {'j','j','j','j'};
		char[] Csides = {'j','j','t','j'};
		char[] Dsides = {'t','t','t','t'};
		char[] Esides = {'t','j','t','j'};
		char[] Fsides = {'t','j','j','t'};
		char[] Gsides = {'t','j','t','t'};
		char[] Hsides = {'l','l','l','l'};
		char[] Isides = {'j','l','l','l'};
		char[] Jsides = {'l','l','j','j'};
		char[] Ksides = {'j','l','j','l'};
		char[] Lsides = {'l','j','l','j'};
		char[] Msides = {'l','j','j','j'};
		char[] Nsides = {'j','l','l','j'};
		char[] Osides = {'t','l','j','t'};
		char[] Psides = {'t','l','j','t'};
		char[] Qsides = {'j','l','t','t'};
		char[] Rsides = {'j','l','t','t'};
		char[] Ssides = {'t','l','t','j'};
		char[] Tsides = {'t','l','t','j'};
		char[] Usides = {'t','l','l','l'};
		char[] Vsides = {'t','l','t','t'};
		char[] Wsides = {'t','l','t','t'};
		char[] Xsides = {'t','l','l','t'};
		char[] Ysides = {'t','l','l','t'};
		char[] Zsides = {'l','j','t','j'};
		char[] AAsides = {'l','j','t','j'};
		char[] ABsides = {'t','l','l','l'};

		char[] Atigers = {'j','=','=','=','=','=','=','=','='};
		char[] Btigers = {'j','=','=','=','x','=','=','=','='};
		char[] Ctigers = {'j','=','=','=','x','=','=','t','='};
		char[] Dtigers = {'j','t','j','t','=','t','j','t','j'};
		char[] Etigers = {'j','t','j','=','=','=','=','=','='};
		char[] Ftigers = {'j','t','j','=','=','=','=','=','='};
		char[] Gtigers = {'j','t','j','t','=','=','j','t','='};
		char[] Htigers = {'l','=','=','=','=','=','=','=','='};
		char[] Itigers = {'j','=','=','l','=','=','=','=','='};
		char[] Jtigers = {'j','l','=','=','=','=','=','=','='};
		char[] Ktigers = {'j','=','=','l','=','=','j','=','='};
		char[] Ltigers = {'j','l','=','=','=','=','=','l','='};
		char[] Mtigers = {'j','l','=','=','=','=','=','=','='};
		char[] Ntigers = {'j','=','=','=','=','l','=','l','='};
		char[] Otigers = {'j','t','j','t','=','l','=','=','='};
		char[] Ptigers = {'j','t','j','t','=','l','=','=','='};
		char[] Qtigers = {'j','=','=','t','=','l','j','=','='};
		char[] Rtigers = {'j','=','=','t','=','l','j','=','='};
		char[] Stigers = {'j','t','j','=','=','l','=','=','='};
		char[] Ttigers = {'j','t','j','=','=','l','=','=','='};
		char[] Utigers = {'j','t','j','l','=','=','=','=','='};
		char[] Vtigers = {'j','t','j','t','=','l','j','t','='};
		char[] Wtigers = {'j','t','j','t','=','l','j','t','='};
		char[] Xtigers = {'j','t','j','=','=','l','=','=','='};
		char[] Ytigers = {'j','t','j','=','=','l','=','=','='};
		char[] Ztigers = {'j','l','j','=','t','=','=','=','='};	//Check this one
		char[] AAtigers = {'j','l','j','=','t','=','=','=','='};//And this one
		char[] ABtigers = {'j','t','j','l','=','=','=','=','='};
		
		
		char[] Acrocs = {'=','=','=','=','=','=','=','=','='};
		char[] Bcrocs = {'=','=','=','=','=','=','=','=','='};
		char[] Ccrocs = {'=','=','=','=','=','=','=','t','='};
		char[] Dcrocs = {'=','t','=','=','=','=','=','=','='};
		char[] Ecrocs = {'=','t','=','=','=','=','=','=','='};
		char[] Fcrocs = {'=','t','=','=','=','=','=','=','='};
		char[] Gcrocs = {'=','t','=','=','=','=','=','=','='};
		char[] Hcrocs = {'=','=','=','=','=','=','=','=','='};
		char[] Icrocs = {'j','=','=','=','=','=','=','=','='};
		char[] Jcrocs = {'j','=','=','=','=','=','=','=','='};
		char[] Kcrocs = {'j','=','=','=','=','=','j','=','='};
		char[] Lcrocs = {'j','=','=','=','=','=','j','=','='};
		char[] Mcrocs = {'j','=','=','=','=','=','=','=','='};
		char[] Ncrocs = {'=','=','j','=','=','=','j','=','='};
		char[] Ocrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Pcrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Qcrocs = {'=','=','j','t','=','=','=','=','='};
		char[] Rcrocs = {'=','=','j','t','=','=','=','=','='};
		char[] Scrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Tcrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Ucrocs = {'j','t','j','=','=','=','=','=','='};
		char[] Vcrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Wcrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Xcrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Ycrocs = {'=','t','j','=','=','=','=','=','='};
		char[] Zcrocs = {'j','=','j','=','t','=','=','=','='};
		char[] AAcrocs = {'j','=','j','=','t','=','=','=','='};
		char[] ABcrocs = {'j','t','j','=','=','=','=','=','='};
		

		Stack<Tile> pile = new Stack<Tile>();
		// One Tile1
		buildStack(pile, Asides, '-', Atigers, Acrocs, "./src/resources/tile1.png", 1);
		buildStack(pile, Bsides, 'x', Btigers, Bcrocs, "./src/resources/tile2.png",4);
		buildStack(pile, Csides, 'x', Ctigers, Ccrocs, "./src/resources/tile3.png",2);
		buildStack(pile, Dsides, '-', Dtigers, Dcrocs, "./src/resources/tile4.png",1);
		buildStack(pile, Esides, '-', Etigers, Ecrocs, "./src/resources/tile5.png",8);
		buildStack(pile, Fsides, '-', Ftigers, Fcrocs, "./src/resources/tile6.png",9);	// Change 8 to 9
		buildStack(pile, Gsides, '-', Gtigers, Gcrocs, "./src/resources/tile7.png",4);
		buildStack(pile, Hsides, '-', Htigers, Hcrocs, "./src/resources/tile8.png",1);
		buildStack(pile, Isides, '-', Itigers, Icrocs, "./src/resources/tile9.png",4);
		buildStack(pile, Jsides, '-', Jtigers, Jcrocs, "./src/resources/tile10.png",5);
		buildStack(pile, Ksides, '-', Ktigers, Kcrocs, "./src/resources/tile11.png",3);
		buildStack(pile, Lsides, '-', Ltigers, Lcrocs, "./src/resources/tile12.png",3);
		buildStack(pile, Msides, '-', Mtigers, Mcrocs, "./src/resources/tile13.png",5);
		buildStack(pile, Nsides, '-', Ntigers, Ncrocs, "./src/resources/tile14.png",2);
		buildStack(pile, Osides, '-', Otigers, Ocrocs, "./src/resources/tile15.png",1);
		buildStack(pile, Psides, 'p', Ptigers, Pcrocs, "./src/resources/tile16.png",2);
		buildStack(pile, Qsides, '-', Qtigers, Qcrocs, "./src/resources/tile17.png",1);
		buildStack(pile, Rsides, 'b', Rtigers, Rcrocs, "./src/resources/tile18.png",2);
		buildStack(pile, Ssides, '-', Stigers, Scrocs, "./src/resources/tile19.png",2);
		buildStack(pile, Tsides, 'd', Ttigers, Tcrocs, "./src/resources/tile20.png",2);
		buildStack(pile, Usides, '-', Utigers, Ucrocs, "./src/resources/tile21.png",1);
		buildStack(pile, Vsides, '-', Vtigers, Vcrocs, "./src/resources/tile22.png",1);
		buildStack(pile, Wsides, 'p', Wtigers, Wcrocs, "./src/resources/tile23.png",2);
		buildStack(pile, Xsides, '-', Xtigers, Xcrocs, "./src/resources/tile24.png",3);
		buildStack(pile, Ysides, 'b', Ytigers, Ycrocs, "./src/resources/tile25.png",2);
		buildStack(pile, Zsides, '-', Ztigers, Zcrocs, "./src/resources/tile26.png",1);
		buildStack(pile, AAsides, 'd', AAtigers, AAcrocs, "./src/resources/tile27.png",2);
		buildStack(pile, ABsides, 'c', ABtigers, ABcrocs, "./src/resources/tile28.png",2);
		return pile;
	}

	public int getOrigin() {
		return origin;
	}
}

package tiger_zone;

import java.util.Stack;

/**
 * The <code>Board</code> class represents both the collection of placed tiles and the stack of tiles which have not yet
 * been placed.
 */
public class Board {
	private final BoardCell[][] gameGrid = new BoardCell[152][152];
	private Stack<Tile> pile;
	public int origin; // center of the gameGrid cartesian view
	private final RuleEngine placementEngine = new RuleEngine();

	/**
	 * Creates an empty board with a particular stack of tiles.
	 * Assigns an origin point to map cartesian coordinates.
	 * @param pile Stack of unplaced tiles.
	 */
	public Board(final Stack<Tile> pile) {
		this.pile = pile;
		this.origin = pile.size(); //calculates mid-point of initialized gameGrid.length
		for (int i = 0; i < this.gameGrid.length; ++i) {
			for (int j = 0; j <  this.gameGrid.length; ++j) {
				this.gameGrid[i][j] = new BoardCell(i, j);
			}
		}


		char[] sides = {'t','l','t','j'};
		Tile init = new Tile(sides, '-', "./src/resources/tile19.png");
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
		if (this.validPlacement(x, y, tile)) {
			  x += this.origin;
   			  y =  this.origin - y;
    			//System.out.println("x = " + x);
    			//System.out.println("y = " + y);


			this.gameGrid[x][y].setTile(tile);
			return true;
		}
		return false;
	}

	/**
	 * Check if the tile can be placed at board position (x, y).
	 *
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 * @param tile The instance of <code>Tile</code> to add
	 * @return true, if the tile can be placed in the location, otherwise false
	 */
	public final boolean validPlacement(final int x, final int y, final Tile tile) {

		if (Math.abs(x) >= this.gameGrid.length|| Math.abs(y) >= this.gameGrid.length) {

			return false;
		}

		placementEngine.clearRules();

		// Check for adjacent tiles

		placementEngine.addRule(new AdjacencyRule(this, x, y));
		placementEngine.addRule(new SideMatchRule(this, x, y, tile));


		return placementEngine.evaluateRules();
	}
	/**
	 * Translates: Cartesian-> 2D Matrix position
	 * @param type int coordinate point
	 * @return value from cartesian points to matrix gameGrid[x][?] index 
	 *
	 */
	 public int  getBoardPosX(int x) {
    		return x + this.origin;
 	}
	
	/**
	 * Translates: Cartesian-> 2D Matrix position
	 * @param type int coordinate point
	 * @return value from cartesian points to matrix gameGrid[?][y] index 
	 *
	 */
  	public int getBoardPosY(int y) {
		
    		return this.origin - y;
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

	/**
	 * Returns this board's stack of unplaced tiles.
	 *
	 * @return Stack of unplaced tiles.
	 */
	public Stack<Tile> getPile() {
		return this.pile;
	}
	
	public static void buildStack(Stack<Tile> pile, char[] edges, char center, String file, int tileCount){
		for(int i = 0; i < tileCount; i++){
			pile.push(new Tile(edges, center, file)); 
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
		char[] Ftigers = {'j','t','j','t','=','=','j','t','='};
		char[] Gtigers = {'l','=','=','=','=','=','=','=','='};
		char[] Htigers = {'j','=','=','l','=','=','=','=','='};
		//char[] ITigers =
		
		 

		Stack<Tile> pile = new Stack<Tile>();
		// One Tile1
		buildStack(pile, Asides, '-', "./src/resources/tile1.png", 1);
		buildStack(pile, Bsides, 'x', "./src/resources/tile2.png",4);
		buildStack(pile, Csides, 'x', "./src/resources/tile3.png",2);
		buildStack(pile, Dsides, '-', "./src/resources/tile4.png",1);
		buildStack(pile, Esides, '-', "./src/resources/tile5.png",8);
		buildStack(pile, Fsides, '-', "./src/resources/tile6.png",9);	// Change 8 to 9
		buildStack(pile, Gsides, '-', "./src/resources/tile7.png",4);
		buildStack(pile, Hsides, '-', "./src/resources/tile8.png",1);
		buildStack(pile, Isides, '-', "./src/resources/tile9.png",4);
		buildStack(pile, Jsides, '-', "./src/resources/tile10.png",5);
		buildStack(pile, Ksides, '-', "./src/resources/tile11.png",3);
		buildStack(pile, Lsides, '-', "./src/resources/tile12.png",3);
		buildStack(pile, Msides, '-', "./src/resources/tile13.png",5);
		buildStack(pile, Nsides, '-', "./src/resources/tile14.png",2);
		buildStack(pile, Osides, '-', "./src/resources/tile15.png",1);
		buildStack(pile, Psides, 'p', "./src/resources/tile16.png",2);
		buildStack(pile, Qsides, '-', "./src/resources/tile17.png",1);
		buildStack(pile, Rsides, 'b', "./src/resources/tile18.png",2);
		buildStack(pile, Ssides, '-', "./src/resources/tile19.png",2);
		buildStack(pile, Tsides, 'd', "./src/resources/tile20.png",2);
		buildStack(pile, Usides, '-', "./src/resources/tile21.png",1);
		buildStack(pile, Vsides, '-', "./src/resources/tile22.png",1);
		buildStack(pile, Wsides, 'p', "./src/resources/tile23.png",2);
		buildStack(pile, Xsides, '-', "./src/resources/tile24.png",3);
		buildStack(pile, Ysides, 'b', "./src/resources/tile25.png",2);
		buildStack(pile, Zsides, '-', "./src/resources/tile26.png",1);
		buildStack(pile, AAsides, 'd', "./src/resources/tile27.png",2);
		buildStack(pile, ABsides, 'c', "./src/resources/tile28.png",2);
		return pile;
	}
}

package tiger_zone;

import java.util.Stack;

/**
 * The <code>Board</code> class represents both the collection of placed tiles and the stack of tiles which have not yet
 * been placed.
 */
public class Board {
	private final BoardCell[][] gameGrid = new BoardCell[152][152];
	private Stack<Tile> pile;
	private int origin; // center of the gameGrid cartesian view
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

		char[] sides = {'T','L','T','J','-'};
		Tile init = new Tile(sides, 'r', "./src/resources/tile19.png");
		this.gameGrid[5][5].setTile(init);
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
		if (x < 0 || y < 0 || x > this.gameGrid.length || y > this.gameGrid.length) {
			return false;
		}

		placementEngine.clearRules();

		// Check for adjacent tiles
		placementEngine.addRule(new AdjacencyRule(this.gameGrid, x, y));
		placementEngine.addRule(new SideMatchRule(this.gameGrid, x, y, tile));

		return placementEngine.evaluateRules();
	}

	/**
	 * Returns the tile located at position (x, y).
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the instance of <code>Tile</code> at position (x, y)
	 */
	public Tile getTile(final int x, final int y){
		return this.gameGrid[x][y].getTile();
	}

	/**
	 * Returns this board's stack of unplaced tiles.
	 *
	 * @return Stack of unplaced tiles.
	 */
	public Stack<Tile> getPile() {
		return this.pile;
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

		Stack<Tile> pile = new Stack<Tile>();
		pile.push(new Tile(Asides, '-', "./src/resources/tile1.png"));
		pile.push(new Tile(Bsides, 'X', "./src/resources/tile2.png"));
		pile.push(new Tile(Csides, 'X', "./src/resources/tile3.png"));
		pile.push(new Tile(Dsides, '-', "./src/resources/tile4.png"));
		pile.push(new Tile(Esides, '-', "./src/resources/tile5.png"));
		pile.push(new Tile(Fsides, '-', "./src/resources/tile6.png"));
		pile.push(new Tile(Gsides, '-', "./src/resources/tile7.png"));
		pile.push(new Tile(Hsides, '-', "./src/resources/tile8.png"));
		pile.push(new Tile(Isides, '-', "./src/resources/tile9.png"));
		pile.push(new Tile(Jsides, '-', "./src/resources/tile10.png"));
		pile.push(new Tile(Ksides, '-', "./src/resources/tile11.png"));
		pile.push(new Tile(Lsides, '-', "./src/resources/tile12.png"));
		pile.push(new Tile(Msides, '-', "./src/resources/tile13.png"));
		pile.push(new Tile(Nsides, '-', "./src/resources/tile14.png"));
		pile.push(new Tile(Osides, '-', "./src/resources/tile15.png"));
		pile.push(new Tile(Psides, 'P', "./src/resources/tile16.png"));
		pile.push(new Tile(Qsides, '-', "./src/resources/tile17.png"));
		pile.push(new Tile(Rsides, 'B', "./src/resources/tile18.png"));
		pile.push(new Tile(Ssides, '-', "./src/resources/tile19.png"));
		pile.push(new Tile(Tsides, 'D', "./src/resources/tile20.png"));
		pile.push(new Tile(Usides, '-', "./src/resources/tile21.png"));
		pile.push(new Tile(Vsides, '-', "./src/resources/tile22.png"));
		pile.push(new Tile(Wsides, 'P', "./src/resources/tile23.png"));
		pile.push(new Tile(Xsides, '-', "./src/resources/tile24.png"));
		pile.push(new Tile(Ysides, 'B', "./src/resources/tile25.png"));
		pile.push(new Tile(Zsides, '-', "./src/resources/tile26.png"));
		pile.push(new Tile(AAsides, 'D', "./src/resources/tile27.png"));
		return pile;
	}
}

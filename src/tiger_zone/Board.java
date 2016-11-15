package tiger_zone;

import java.util.Stack;

/**
 * The <code>Board</code> class represents both the collection of placed tiles and the stack of tiles which have not yet
 * been placed.
 */
public class Board {
	private final BoardCell[][] gameGrid = new BoardCell[152][152];
	private Stack<Tile> pile;

	/**
	 * Creates an empty board with a particular stack of tiles.
	 *
	 * @param pile Stack of unplaced tiles.
	 */
	public Board(final Stack<Tile> pile) {
		this.pile = pile;

		for (int i = 0; i < this.gameGrid.length; ++i) {
			for (int j = 0; j <  this.gameGrid.length; ++j) {
				this.gameGrid[i][j] = new BoardCell(i, j);
			}
		}
	}

	/**
	 * Adds tile to this board at position (x, y).
	 *
	 * @param x The x coordinate of the destination
	 * @param y The y coordinate of the destination
	 * @param tile The instance of <code>Tile</code> to add
	 */
	public void addTile(final int x, final int y, final Tile tile) {
		gameGrid[x][y].setTile(tile);
	}

	/**
	 * Returns the tile located at position (x, y).
	 *
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the instance of <code>Tile</code> at position (x, y)
	 */
	public Tile getTile(final int x, final int y){
		return gameGrid[x][y].getTile();
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
		// f = farm, c = city,
		//sides names:    TL  TM  TR RT  RM  RB  BR  BM   BL LB  LM   LT
		//sides values:   1   2   3   4   5   6   7   8   9  10  11   12  
		char[] Asides = {'j','j','j','j','j','j','j','j','j','j','j','j'};
		char[] Bsides = {'j','j','j','j','j','j','j','j','j','j','j','j'};
		char[] Csides = {'j','j','j','j','j','j','j','r','j','j','j','j'};
		char[] Dsides = {'j','j','j','j','j','j','j','j','j','j','j','j'};
		char[] Esides = {'j','r','j','j','r','j','j','r','j','j','r','j'};
		char[] Fsides = {'j','r','j','j','j','j','j','j','j','j','r','j'};
		char[] Gsides = {'j','r','j','j','j','j','j','r','j','j','r','j'};
		char[] Hsides = {'l','l','l','l','l','l','l','l','l','l','l','l'};
		char[] Isides = {'j','j','j','l','l','l','l','l','l','l','l','l'};
		char[] Jsides = {'l','l','l','l','l','l','j','j','j','j','j','j'};
		char[] Ksides = {'j','j','j','l','l','l','j','j','j','l','l','l'};
		char[] Lsides = {'l','l','l','j','j','j','l','l','l','j','j','j'};
		char[] Msides = {'l','l','l','j','j','j','j','j','j','j','j','j'};
		char[] Nsides = {'j','j','j','l','l','l','l','l','l','j','j','j'};
		char[] Osides = {'j','r','j','l','l','l','j','j','j','j','r','j'};
		char[] Psides = {'j','r','j','l','l','l','j','j','j','j','r','j'};
		char[] Qsides = {'j','j','j','l','l','l','j','r','j','j','r','j'};
		char[] Rsides = {'j','j','j','l','l','l','j','r','j','j','r','j'};
		char[] Ssides = {'j','r','j','l','l','l','j','r','j','j','j','j'};
		char[] Tsides = {'j','r','j','l','l','l','j','r','j','j','j','j'};
		char[] Usides = {'j','r','j','l','l','l','l','l','l','l','l','l'};
		char[] Vsides = {'j','r','j','l','l','l','j','r','j','j','r','j'};
		char[] Wsides = {'j','r','j','l','l','l','j','r','j','j','r','j'};
		char[] Xsides = {'j','r','j','l','l','l','l','l','l','j','r','j'};
		char[] Ysides = {'j','r','j','l','l','l','l','l','l','j','r','j'};
		char[] Zsides = {'l','l','l','j','j','j','j','r','j','j','j','j'};
		char[] AAsides = {'l','l','l','j','j','j','j','r','j','j','j','j'};
		

		Stack<Tile> pile = new Stack<Tile>();
		pile.push(new Tile(Asides, 'm', "./src/resources/tile1.png"));
		pile.push(new Tile(Bsides, 'm', "./src/resources/tile2.png"));
		pile.push(new Tile(Csides, 'c', "./src/resources/tile3.png"));
		pile.push(new Tile(Dsides, 'r', "./src/resources/tile4.png"));
		pile.push(new Tile(Esides, 'f', "./src/resources/tile5.png"));
		pile.push(new Tile(Fsides, 'c', "./src/resources/tile6.png"));
		pile.push(new Tile(Gsides, 'c', "./src/resources/tile7.png"));
		pile.push(new Tile(Hsides, 'f', "./src/resources/tile8.png"));
		pile.push(new Tile(Isides, 'f', "./src/resources/tile9.png"));
		pile.push(new Tile(Jsides, 'r', "./src/resources/tile10.png"));
		pile.push(new Tile(Ksides, 'r', "./src/resources/tile11.png"));
		pile.push(new Tile(Lsides, 'r', "./src/resources/tile12.png"));
		pile.push(new Tile(Msides, 'f', "./src/resources/tile13.png"));
		pile.push(new Tile(Nsides, 'f', "./src/resources/tile14.png"));
		pile.push(new Tile(Osides, 'r', "./src/resources/tile15.png"));
		pile.push(new Tile(Psides, 'r', "./src/resources/tile16.png"));
		pile.push(new Tile(Qsides, 'c', "./src/resources/tile17.png"));
		pile.push(new Tile(Rsides, 'c', "./src/resources/tile18.png"));
		pile.push(new Tile(Ssides, 'c', "./src/resources/tile19.png"));
		pile.push(new Tile(Tsides, 'c', "./src/resources/tile20.png"));
		pile.push(new Tile(Usides, 'r', "./src/resources/tile21.png"));
		pile.push(new Tile(Vsides, 'r', "./src/resources/tile22.png"));
		pile.push(new Tile(Wsides, 'r', "./src/resources/tile23.png"));
		pile.push(new Tile(Xsides, 'r', "./src/resources/tile24.png"));
		return pile;
	}
}

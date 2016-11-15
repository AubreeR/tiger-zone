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
		//sides names:   TL  TM  TR   RT  RM  RB  BR  BM  BL LB  LM  LT
		//sides values:   1   2   3   4   5   6   7   8   9  10  11  12 
		char[] Asides = {'j','j','j','j','j','j','j','j','j','j','j','j'};
		char[] Bsides = {'j','j','j','j','j','j','j','j','j','j','j','j'};
		char[] Csides = {'c','c','c','c','c','c','c','c','c','c','c','c'};
		char[] Dsides = {'f','f','r','f','c','c','c','c','c','f','r','f'};
		char[] Esides = {'c','c','c','c','c','f','f','f','f','f','f','f'};
		char[] Fsides = {'c','f','f','f','c','c','c','c','c','f','f','f'};
		char[] Gsides = {'c','c','c','c','c','f','f','f','c','c','c','c'};
		char[] Hsides = {'c','f','f','f','c','c','c','c','c','f','f','f'};
		char[] Isides = {'f','f','f','f','c','c','c','c','c','c','c','c'};
		char[] Jsides = {'c','c','c','c','c','f','r','f','f','f','r','f'};
		char[] Ksides = {'f','f','r','f','c','c','c','c','c','f','f','f'};
		char[] Lsides = {'f','f','r','f','c','c','c','c','c','f','r','f'};
		char[] Msides = {'c','c','c','c','c','f','f','f','f','f','f','f'};
		char[] Nsides = {'c','c','c','c','c','f','f','f','f','f','f','f'};
		char[] Osides = {'c','c','c','c','c','f','r','f','f','f','r','f'};
		char[] Psides = {'c','c','c','c','c','f','r','f','f','f','r','f'};
		char[] Qsides = {'c','c','c','c','c','c','c','c','c','f','f','f'};
		char[] Rsides = {'c','c','c','c','c','c','c','c','c','f','f','f'};
		char[] Ssides = {'c','c','c','c','c','c','c','c','c','f','r','f'};
		char[] Tsides = {'c','c','c','c','c','c','c','c','c','f','r','f'};
		char[] Usides = {'f','f','r','f','f','f','f','f','f','f','r','f'};
		char[] Vsides = {'f','f','f','f','f','f','f','f','f','f','r','f'};
		char[] Wsides = {'f','f','f','f','f','f','r','f','f','f','r','f'};
		char[] Xsides = {'f','f','r','f','f','f','r','f','f','f','r','f'};
		char[] Ysides = {'f','f','r','f','f','f','r','f','f','f','r','f'};
		char[] Zsides = {'f','f','r','f','f','f','r','f','f','f','r','f'};
		char[] AAsides = {'f','f','r','f','f','f','r','f','f','f','r','f'};

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
		pile.push(new Tile(Ysides, 'r', "./src/resources/tile25.png"));
		pile.push(new Tile(Zsides, 'r', "./src/resources/tile26.png"));
		pile.push(new Tile(AAsides, 'r', "./src/resources/tile27.png"));
		return pile;
	}
}

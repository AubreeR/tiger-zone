package tiger_zone;

import java.util.Stack;

/**
 * The <code>Board</code> class represents both the collection of placed tiles and the stack of tiles which have not yet
 * been placed.
 */
public class Board {
	private Tile[][] tileArray = new Tile[152][152];
	private Stack<Tile> pile;
	
	/**
	 * Creates an empty board with a particular stack of tiles.
	 * 
	 * @param pile Stack of unplaced tiles.
	 */
	public Board(Stack<Tile> pile) {
		this.pile = pile;
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
		//sides names:   TLC  TL  TM  TR TRC  RT  RM  RB BRC BR  BM   BL BLC  LB  LM  LT
		//sides values:   1   2   3   4   5   6   7   8   9  10  11   12  13  14  15  16
		char[] Asides = {'f','f','f','f','f','f','f','f','f','f','r','f','f','f','f','f'};
		char[] Bsides = {'f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f'};
		char[] Csides = {'c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'};
		char[] Dsides = {'f','f','r','f','c','c','c','c','c','f','r','f','f','f','f','f'};
		char[] Esides = {'c','c','c','c','c','f','f','f','f','f','f','f','f','f','f','f'};
		char[] Fsides = {'c','f','f','f','c','c','c','c','c','f','f','f','c','c','c','c'};
		char[] Gsides = {'c','c','c','c','c','f','f','f','c','c','c','c','c','f','f','f'};
		char[] Hsides = {'c','f','f','f','c','c','c','c','c','f','f','f','c','c','c','c'};
		char[] Isides = {'f','f','f','f','c','c','c','c','c','c','c','c','c','f','f','f'};
		char[] Jsides = {'c','c','c','c','c','f','r','f','f','f','r','f','f','f','f','f'};
		char[] Ksides = {'f','f','r','f','c','c','c','c','c','f','f','f','f','f','r','f'};
		char[] Lsides = {'f','f','r','f','c','c','c','c','c','f','r','f','f','f','r','f'};
		char[] Msides = {'c','c','c','c','c','f','f','f','f','f','f','f','c','c','c','c'};
		char[] Nsides = {'c','c','c','c','c','f','f','f','f','f','f','f','c','c','c','c'};
		char[] Osides = {'c','c','c','c','c','f','r','f','f','f','r','f','c','c','c','c'};
		char[] Psides = {'c','c','c','c','c','f','r','f','f','f','r','f','c','c','c','c'};
		char[] Qsides = {'c','c','c','c','c','c','c','c','c','f','f','f','c','c','c','c'};
		char[] Rsides = {'c','c','c','c','c','c','c','c','c','f','f','f','c','c','c','c'};
		char[] Ssides = {'c','c','c','c','c','c','c','c','c','f','r','f','c','c','c','c'};
		char[] Tsides = {'c','c','c','c','c','c','c','c','c','f','r','f','c','c','c','c'};
		char[] Usides = {'f','f','r','f','f','f','f','f','f','f','r','f','f','f','f','f'};
		char[] Vsides = {'f','f','f','f','f','f','f','f','f','f','r','f','f','f','r','f'};
		char[] Wsides = {'f','f','f','f','f','f','r','f','f','f','r','f','f','f','r','f'};
		char[] Xsides = {'f','f','r','f','f','f','r','f','f','f','r','f','f','f','r','f'};

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
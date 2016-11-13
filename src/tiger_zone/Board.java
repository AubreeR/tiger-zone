package tiger_zone;

import java.util.Stack;

public class Board {
	private Tile[][] tileArray = new Tile[152][152];
	private Stack<Tile> pile;
	
	Board(Stack<Tile> pile) {
		this.pile = pile;
	}
	
	public static void initTiles() {
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

		Tile A = new Tile(Asides, 'm', "./src/resources/tile1.png");
		Tile B = new Tile(Bsides, 'm', "./src/resources/tile2.png");
		Tile C = new Tile(Csides, 'c', "./src/resources/tile3.png");
		Tile D = new Tile(Dsides, 'r', "./src/resources/tile4.png");
		Tile E = new Tile(Esides, 'f', "./src/resources/tile5.png");
		Tile F = new Tile(Fsides, 'c', "./src/resources/tile6.png");
		Tile G = new Tile(Gsides, 'c', "./src/resources/tile7.png");
		Tile H = new Tile(Hsides, 'f', "./src/resources/tile8.png");
		Tile I = new Tile(Isides, 'f', "./src/resources/tile9.png");
		Tile J = new Tile(Jsides, 'r', "./src/resources/tile10.png");
		Tile K = new Tile(Ksides, 'r', "./src/resources/tile11.png");
		Tile L = new Tile(Lsides, 'r', "./src/resources/tile12.png");
		Tile M = new Tile(Msides, 'f', "./src/resources/tile13.png");
		Tile N = new Tile(Nsides, 'f', "./src/resources/tile14.png");
		Tile O = new Tile(Osides, 'r', "./src/resources/tile15.png");
		Tile P = new Tile(Psides, 'r', "./src/resources/tile16.png");
		Tile Q = new Tile(Qsides, 'c', "./src/resources/tile17.png");
		Tile R = new Tile(Rsides, 'c', "./src/resources/tile18.png");
		Tile S = new Tile(Ssides, 'c', "./src/resources/tile19.png");
		Tile T = new Tile(Tsides, 'c', "./src/resources/tile20.png");
		Tile U = new Tile(Usides, 'r', "./src/resources/tile21.png");
		Tile V = new Tile(Vsides, 'r', "./src/resources/tile22.png");
		Tile W = new Tile(Wsides, 'r', "./src/resources/tile23.png");
		Tile X = new Tile(Xsides, 'r', "./src/resources/tile24.png");
	}	
}
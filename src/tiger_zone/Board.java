package tiger_zone;

import java.util.Stack;

public class Board {
	
	Tile[][] tileArray = new Tile[154][154];
	public static Stack gameTiles = new Stack();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		
		// tile name, sides  center row col meeple
		Tile A = new Tile(Asides,'r',-1,-1,-1);
		Tile B = new Tile(Bsides,'m',-1,-1,-1);
		Tile C = new Tile(Csides,'c',-1,-1,-1);
		Tile D = new Tile(Dsides,'r',-1,-1,-1);
		Tile E = new Tile(Esides,'f',-1,-1,-1);
		Tile F = new Tile(Fsides,'c',-1,-1,-1);
		Tile G = new Tile(Gsides,'c',-1,-1,-1);
		Tile H = new Tile(Hsides,'f',-1,-1,-1);
		Tile I = new Tile(Isides,'f',-1,-1,-1);
		Tile J = new Tile(Jsides,'r',-1,-1,-1);
		Tile K = new Tile(Ksides,'r',-1,-1,-1);
		Tile L = new Tile(Lsides,'r',-1,-1,-1);
		Tile M = new Tile(Msides,'f',-1,-1,-1);
		Tile N = new Tile(Nsides,'f',-1,-1,-1);
		Tile O = new Tile(Osides,'r',-1,-1,-1);
		Tile P = new Tile(Psides,'r',-1,-1,-1);
		Tile Q = new Tile(Qsides,'c',-1,-1,-1);
		Tile R = new Tile(Rsides,'c',-1,-1,-1);
		Tile S = new Tile(Ssides,'c',-1,-1,-1);
		Tile T = new Tile(Tsides,'c',-1,-1,-1);
		Tile U = new Tile(Usides,'r',-1,-1,-1);
		Tile V = new Tile(Vsides,'r',-1,-1,-1);
		Tile W = new Tile(Wsides,'r',-1,-1,-1);
		Tile X = new Tile(Xsides,'r',-1,-1,-1);
		
		
		
		return;
	}	

}

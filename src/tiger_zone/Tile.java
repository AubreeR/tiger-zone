package tiger_zone;

import java.io.File;

public class Tile {
	public Tile(char[] sides, char cent){
		private int row;
		private int col; 
		private boolean shield;
		File file; 
		private boolean meeple; 
		
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
		Tile A = new Tile(Asides,'r');
		Tile B = new Tile(Bsides,'m');
		Tile C = new Tile(Csides,'c');
		Tile D = new Tile(Dsides,'r');
		Tile E = new Tile(Esides,'f');
		Tile F = new Tile(Fsides,'c');
		Tile G = new Tile(Gsides,'c');
		Tile H = new Tile(Hsides,'f');
		Tile I = new Tile(Isides,'f');
		Tile J = new Tile(Jsides,'r');
		Tile K = new Tile(Ksides,'r');
		Tile L = new Tile(Lsides,'r');
		Tile M = new Tile(Msides,'f');
		Tile N = new Tile(Nsides,'f');
		Tile O = new Tile(Osides,'r');
		Tile P = new Tile(Psides,'r');
		Tile Q = new Tile(Qsides,'c');
		Tile R = new Tile(Rsides,'c');
		Tile S = new Tile(Ssides,'c');
		Tile T = new Tile(Tsides,'c');
		Tile U = new Tile(Usides,'r');
		Tile V = new Tile(Vsides,'r');
		Tile W = new Tile(Wsides,'r');
		Tile X = new Tile(Xsides,'r');
		
		
		
		return;
	}
}
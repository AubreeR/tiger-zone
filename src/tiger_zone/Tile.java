package tiger_zone;

import java.util.Collections;
import java.util.Stack;

public class Tile {
	// TLC = Top left corner, TL = Top left, BRC = Bottom right corner, etc
	char TLC, TL, TM, TR, TRC, RT, RM, RB, BRC, BR, BM, BL, BLC, LB, LM, LT, CENT;

	static Stack<Tile> stack = new Stack<>(); 
	
	public static void main(String[] args){
		Tile t1 = new Tile(); 
		Tile t2 = new Tile();
		Tile t3 = new Tile();
		Tile ts;
		
		// Monastery surrounded by farm and south road (tile 1)
		t1.TLC = 'f'; t1.TL = 'f'; t1.TM = 'f'; t1.TR =  'f'; t1.TRC = 'f';
		t1.RT = 'f'; t1.RM = 'f'; t1.RB = 'f'; t1.BRC =  'f'; t1.BR = 'f';
		t1.BM = 'r'; t1.BL = 'f'; t1.BLC = 'f'; t1.LB =  'f'; t1.LM = 'f'; t1.LT = 'f'; t1.CENT = 'm';
		
		// Monastery surrounded by farm (tile 2)
		t2.TLC = 'f'; t2.TL = 'f'; t2.TM = 'f'; t2.TR =  'f'; t2.TRC = 'f';
		t2.RT = 'f'; t2.RM = 'f'; t2.RB = 'f'; t2.BRC =  'f'; t2.BR = 'f';
		t2.BM = 'f'; t2.BL = 'f'; t2.BLC = 'f'; t2.LB =  'f'; t2.LM = 'f'; t2.LT = 'f'; t2.CENT = 'm';
		
		t3.TLC = 'c'; t3.TL = 'c'; t3.TM = 'c'; t3.TR =  'c'; t3.TRC = 'c';
		t3.RT = 'c'; t3.RM = 'c'; t3.RB = 'c'; t3.BRC =  'c'; t3.BR = 'c';
		t3.BM = 'c'; t3.BL = 'c'; t3.BLC = 'c'; t3.LB =  'c'; t3.LM = 'c'; t3.LT = 'c'; t3.CENT = 'c';
		
		
		stack.push(t1);
		stack.push(t2);
		stack.push(t3);
		Collections.shuffle(stack);
		ts = stack.pop(); 
		
		System.out.println(ts.BLC + "\n");
	}
}
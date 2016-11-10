import java.io.File;
import java.util.Collections;
import java.util.Stack;

public class Tile {
	// TLC = Top left corner, TL = Top left, BRC = Bottom right corner, etc
	char[] tileArr; 
	char cent;
	File file; 
	Boolean pennant; 
	int row, col;

	//static Stack<Tile> stack = new Stack<>(); 
	
	public static void main(String[] args){
		Tile t1 = new Tile(); 
		Tile t2 = new Tile();
		Tile t3 = new Tile();
		
		// Monastery surrounded by farm and south road (tile 1)
		char[] t1Arr = {'f','f','f','f','f','f','f','f','f','f','r','f','f','f','f','f'};
		t1.tileArr = t1Arr;
		t1.pennant = false;
		t1.cent = 'm';
		File f1 = new File("C:/Users/Kyle/Dropbox/Tiles/Tile1.png");
		t1.file = f1; 
		
		// Monastery surrounded by farm (tile 2)
		char[] t2Arr = {'f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f'};
		t2.tileArr = t2Arr;
		t2.pennant = false;
		t2.cent = 'm'; 
		File f2 = new File("C:/Users/Kyle/Dropbox/Tiles/Tile2.png");
		t2.file = f2;
		
		// Completely city tile
		char[] t3Arr = {'c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c','c'};
		t3.tileArr = t3Arr;
		t3.pennant = true;
		File f3 = new File("C:/Users/Kyle/Dropbox/Tiles/Tile3.png");
		t3.file = f3;
		
//		stack.push(t1);
//		stack.push(t2);
//		stack.push(t3);
//		Collections.shuffle(stack);
//		ts = stack.pop(); 
		
	}
}
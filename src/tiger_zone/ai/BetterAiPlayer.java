package tiger_zone.ai;

import tiger_zone.Game;
import tiger_zone.PossibleMovesRule;
import tiger_zone.Tile;
import tiger_zone.Board;
import tiger_zone.BoardCell;
import java.util.Stack;
import java.util.ArrayList;


public class BetterAiPlayer extends AiPlayer {
	private int move_num = 1;
	/**
	 * Create a new instance of <code>BetterAiPlayer</code>.
	 * 
	 * @param game The game instance.
	 */
	public BetterAiPlayer(Game game, String pid) {
		super(game, pid);
	}

	/**
	 * Have this AI player place a tile on the Game.
	 */
	public final void makeMove() {
		long millis = System.currentTimeMillis();
		//tempmove[] consists of coordinates, rotation, tiger placement, evaluation number(default set to 0)--0 won't work need other
		//move[] consists of coordinates: (x,y), rotation, tiger placement
		BoardCell[][] temp= Clone2D(game.getBoard().getGameGrid());
		Stack<Tile> temptile= new Stack<Tile>();
		temptile=(Stack<Tile>)game.getBoard().getPile().clone();
		int[] tempmove= MiniMax(temp, 0, true, temptile);
		int[] move= new int[tempmove.length-1];
		for(int i=0; i<move.length; i++)
		{
			move[i]=tempmove[i];
		}
		Tile current = this.game.getBoard().getPile().pop();
		int size=this.game.getBoard().getPile().size();
		while (current.getRotation() != move[2]) {
			current.rotate();
		}
		game.getBoard().addTile(move[0], move[1], current);
		millis = System.currentTimeMillis() - millis;
		System.out.println("Move: " + move_num++ + " \tCoor: (" + move[0] +"," + move[1] +") \ttile: " 
				+ current.getSide(0)+current.getSide(1)+current.getSide(2)+current.getSide(3) 
				+ "\tTiger Locations: "  + (-1)
				+ "\tElapsed Time: " + millis);
	}
	
	public int[] MiniMax(BoardCell[][] tempgm, int loop, boolean current, Stack<Tile> StackTile)
	{
		BoardCell[][] gm= Clone2D(tempgm);
		Stack<Tile> TileStack=new Stack<Tile>();
		TileStack= (Stack<Tile>)StackTile.clone();
		if(loop != 0) TileStack.pop();
		int[] move= new int[5];//single move
		ArrayList<int[]> possiblemoves = new ArrayList<int[]>();
		//array of all possible moves
		//current==true => max
		//evaluation of final matrix, compare to next,
		int evalmax=Integer.MIN_VALUE;
		int evalmin=Integer.MAX_VALUE;
		possiblemoves=GetPossibleMoves(gm, TileStack);
		int numberleft= TileStack.size();
		//make tile stack check to see number of remaining tiles
		//if 1=>eval
		//if 2=> 1 min and 1 max
		int x=0;
		if(numberleft==1 && current)
		{
			while(x<5 && x<possiblemoves.size()) //#of rows
			{
				int newevalmax;
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thisplayer);
				newevalmax=Evaluation(nextGame, possiblemoves.get(x));//send row
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmax; //set last column as evaluation number
				if(newevalmax>evalmax)//if there is new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves.get(x);//set move
				}
			x++;	
			}
			return move;
		}
		if(numberleft==1 && !current)
		{
			while(x<5 && x<possiblemoves.size())  //#of rows
			{
				int newevalmin;
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thatplayer);
				newevalmin=Evaluation(nextGame, possiblemoves.get(x));//send row
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmin; //set last column as evaluation number
				if(newevalmin<evalmax)//if there is new min
				{
					evalmax=newevalmin;//set min
					move=possiblemoves.get(x);//set move
				}
			x++;
			}
		}
		if(loop==2 && current)
		{
			while(x<5 && x<possiblemoves.size())  //#of rows
			{
				int newevalmax;
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thisplayer);
				newevalmax=Evaluation(nextGame, possiblemoves.get(x));//send row
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmax; //set last column as evaluation number
				if(newevalmax>evalmax)//if there is new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves.get(x);//set move
				}
			x++;	
			}
			return move;
		}
		if(loop==2 && !current)
		{
			while(x<5 && x<possiblemoves.size())  //#of rows
			{
				int newevalmin;
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thisplayer);
				newevalmin=Evaluation(nextGame, possiblemoves.get(x));//send row
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmin; //set last column as evaluation number
				if(newevalmin<evalmin)//if there is new max
				{
					evalmin=newevalmin;//set max
					move=possiblemoves.get(x);//set move
				}
			x++;	
			}
			return move;
		}
		boolean next= !current; //switch max to min && min to max
		loop++;
		if(current)
		{
			while(x<5 && x<possiblemoves.size())
			{
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thisplayer);
				//call MiniMax, new eval = last column
				int[] temp=MiniMax(nextGame, loop, next, TileStack); //get eval #
				int newevalmax=temp[temp.length-1];//set eval #
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmax; //set eval # for returning move
				if(newevalmax>evalmax) //if there is a new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves.get(x);//set move
				}
			x++;
			}
		return move;
		}
		else
		{
			while(x<5 && x<possiblemoves.size())
			{
				BoardCell[][] debug=Clone2D(gm);
				BoardCell[][] nextGame=apply(debug, possiblemoves.get(x), TileStack);
				//updateScore(possiblemoves.get(i)[0], possiblemoves.get(i)[1], thatplayer);
				//call MiniMax, new eval = last column
				int[] temp=MiniMax(nextGame, loop, next, TileStack);//get eval #
				int newevalmin=temp[temp.length-1];//set eval #
				possiblemoves.get(x)[possiblemoves.get(x).length-1]=newevalmin;//set eval # for move
				if(newevalmin<evalmin) //if there is new min
				{
					evalmin=newevalmin; //set min
					move=possiblemoves.get(x);//set move
				}
			x++;
			}
			return move;
		}
	}
	
	public int Evaluation(BoardCell[][] nextGame, int[] move)
	{
		//ai score-opponent score?
		int eval;
		int aiscore=0;//thisplayer.getPoints();//thisplayer=ai
		int oppscore=0;//thatplayer.getPoint();//thatplayer=opponent
		eval= aiscore-oppscore;
		return eval;
	}
	
	public ArrayList<int[]> GetPossibleMoves(BoardCell[][] tempgm, Stack<Tile> StackTile)
	{	
		BoardCell[][] gm=Clone2D(tempgm);
		Board brd=new Board(gm, game.getBoard().getPile(), game.getBoard().getOrigin());
		ArrayList<int[]> allpossiblemoves = new ArrayList<int[]>(); 
		//iterate through Game and find all legal moves
		PossibleMovesRule pmr = new PossibleMovesRule(brd, 0, 0, StackTile.peek(), false);
		pmr.evaluate();
		allpossiblemoves=pmr.getAllPossibleMoves();
		for(int i=0; i<allpossiblemoves.size(); i++)
		{
			int[] tempmove= new int[5];
			tempmove[0]=allpossiblemoves.get(i)[0];
			tempmove[1]=allpossiblemoves.get(i)[1];
			tempmove[2]=allpossiblemoves.get(i)[2];
			tempmove[3]=-1;
			allpossiblemoves.set(i, tempmove);
		}
		return allpossiblemoves;
	}


	public BoardCell[][] apply(BoardCell[][] gm, int[] move, Stack<Tile> StackTile)
	{
		BoardCell[][] NextGame= Clone2D(gm);
		Tile current = StackTile.peek();
		while (current.getRotation() != move[2]) {
			current.rotate();
		}
		NextGame[game.getBoard().getOrigin()+move[0]][game.getBoard().getOrigin()-move[1]].setTile(current);
		//apply move to Game
		return NextGame;
	}
	
	public BoardCell[][] Clone2D(BoardCell[][] Grid)
	{
		BoardCell[][] GameGrid = new BoardCell[Grid.length][Grid.length];
		for(int i=0; i<Grid.length; i++)
		{
			for(int j=0; j<Grid[i].length; j++)
			{
				GameGrid[i][j]=new BoardCell(Grid[i][j].getXCoord(), Grid[i][j].getYCoord());
				GameGrid[i][j].setTile(Grid[i][j].getTile());
			}
		}
		return GameGrid;
	}
}
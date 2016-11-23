package tiger_zone;

import java.util.Stack;

import tiger_zone.Game;
//tempmove[] consists of coordinates, rotation, tiger placement, evaluation number(default set to 0)--0 won't work need other
//move[] consists of coordinates: (x,y), rotation, tiger placement


public class AI extends Player
{
	private final Game game;

	public AI(int index, Game game){
		super(index);
		this.game = game;
	}

	/**
	 * makeMove takes in an array containing 3 tile paramters: x position,
	 * y position, and rotation. maveMove applies the rotation to the tile
	 * and adds it to the board at the x and y locations specified
	 *
	 */
	public int makeMove(int[] tileParams)//Board=current state of board + tigers + stack of remaining tiles
	{

		Stack<Tile> currStack = this.game.getBoard().getPile();
		Tile currTile = currStack.pop();
		int x = tileParams[0];
		int y = tileParams[1];
		int rot = tileParams[2];

		for(int i = 0; i < (rot/90); i++){
			currTile.rotate();
		}

		this.game.getBoard().addTile(x, y, currTile);
		return 0;
	}

}

/*
		Board temp=new Board(brd.getPile());
		temp=brd;
		int[] tempmove= MiniMax(temp, 0, true);
		int[] move= new int[tempmove.length-1];
		for(int i=0; i<move.length; i++)
		{
			move[i]=tempmove[i];
		}
		return move;
	}
	public int[] MiniMax(Board brd, int loop, boolean current)
	{
		int[] move= new int[5];//single move
		int[][] possiblemoves;//array of all possible moves
		//current==true => max
		//evaluation of final matrix, compare to next,
		int evalmax=Integer.MIN_VALUE;
		int evalmin=Integer.MAX_VALUE;
		possiblemoves=GetPossibleMoves(brd);
		Stack<Tile> RemainingTiles=brd.getPile();
		int numberleft= RemainingTiles.size();
		//make tile stack check to see number of remaining tiles
		//if 1=>eval
		//if 2=> 1 min and 1 max
		if(numberleft==1 && current)
		{
			for(int i=0; i<possiblemoves.length; i++) //#of rows
			{
				int newevalmax;
				newevalmax=Evaluation(brd, possiblemoves[i]);//send row
				possiblemoves[i][possiblemoves[i].length-1]=newevalmax; //set last column as evaluation number
				if(newevalmax>evalmax)//if there is new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves[i];//set move
				}

			}
			return move;
		}
		if(numberleft==1 && !current)
		{
			for(int i=0; i<possiblemoves.length; i++) //#of rows
			{
				int newevalmin;
				newevalmin=Evaluation(brd, possiblemoves[i]);//send row
				possiblemoves[i][possiblemoves[i].length-1]=newevalmin; //set last column as evaluation number
				if(newevalmin<evalmax)//if there is new min
				{
					evalmax=newevalmin;//set min
					move=possiblemoves[i];//set move
				}

			}
		}
		if(loop==2)
		{
			for(int i=0; i<possiblemoves.length; i++) //#of rows
			{
				int newevalmax;
				newevalmax=Evaluation(brd, possiblemoves[i]);//send row
				possiblemoves[i][possiblemoves[i].length-1]=newevalmax; //set last column as evaluation number
				if(newevalmax>evalmax)//if there is new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves[i];//set move
				}

			}
			return move;
		}
		boolean next= !current; //switch max to min && min to max
		loop++;
		if(current)
		{
			for(int i=0; i<possiblemoves.length; i++)
			{
				Board nextboard=apply(brd, possiblemoves[i]);
				//call MiniMax, new eval = last column
				int[] temp=MiniMax(nextboard, loop, next); //get eval #
				int newevalmax=temp[temp.length-1];//set eval #
				possiblemoves[i][possiblemoves[i].length-1]=newevalmax; //set eval # for returning move
				if(newevalmax>evalmax) //if there is a new max
				{
					evalmax=newevalmax;//set max
					move=possiblemoves[i];//set move
				}
			}
		return move;
		}
		else
		{
			for(int i=0; i<possiblemoves.length; i++)
			{
				Board nextboard=apply(brd, possiblemoves[i]);
				//call MiniMax, new eval = last column
				int[] temp=MiniMax(nextboard, loop, next);//get eval #
				int newevalmin=temp[temp.length-1];//set eval #
				possiblemoves[i][possiblemoves[i].length-1]=newevalmin;//set eval # for move
				if(newevalmin<evalmin) //if there is new min
				{
					evalmin=newevalmin; //set min
					move=possiblemoves[i];//set move
				}
			}
			return move;
		}
	}
	public int Evaluation(Board brd, int[] move)
	{
		//ai score-opponent score?
		int eval=0;
		return eval;
	}
	public int[][] GetPossibleMoves(Board brd)
	{
		int[][] allpossiblemoves; //size?
		//iterate through board and find all legal moves
		return allpossiblemoves;
	}
	public Board apply(Board brd, int[] move)
	{
		//apply move to board
		return brd;
	}
	*/

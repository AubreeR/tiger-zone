package tiger_zone.ai;

import tiger_zone.Game;
import tiger_zone.Position;
import tiger_zone.Tiger;
import tiger_zone.Tile;
import tiger_zone.Board;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import tiger_zone.Player;

public class BetterAiPlayer extends AiPlayer {
	/**
	 * Create a new instance of <code>BetterAiPlayer</code>.
	 * 
	 * @param game The game instance.
	 */
	public BetterAiPlayer(Game game) {
		super(game);
	}

	/**
	 * Have this AI player place a tile on the Game.
	 */
	public final void makeMove() {
		Tile current = this.game.getCurrentTile();
		
		//tempmove[] consists of coordinates, rotation, tiger placement, evaluation number(default set to 0)--0 won't work need other
		//move[] consists of coordinates: (x,y), rotation, tiger placement
		Board temp = this.game.getBoard().clone();
		Stack<Tile> temptile = temp.getPile();
		temptile.push(current.clone());
		
		/*System.out.print("empty: ");
		for (Position p : temp.getOpenPositions()) {
			System.out.printf("%s ", p);
		}
		System.out.println();*/
		
		final int[] move = MiniMax(temp, 0, 0, 0, this.getPlayer(), this.getOtherPlayer());
		if( move[3] ==0)
		{
			System.out.println("No moves, Skipping :(");
			return;
		}

		while (current.getRotation() != move[2]) {
			current.rotate();
		}
		
		System.out.println("BetterAiPlayer: placing tile at " + new Position(move[0], move[1]));
		this.game.getBoard().addTile(new Position(move[0], move[1]), current);
		if(move[3]>0)
		{
			this.game.getCurrentTile().addTiger(move[3], this.getPlayer().getTigers().pop());
		}
	}

	private final int[] MiniMax(final Board gm, int depth, int currentscore, int otherscore, Player a, Player o) {
		
		boolean current = depth % 2 == 0;
		Player AI =new Player(a.getIndex(), a.getPid(), (Stack<Tiger>)a.getTigers().clone());
		Player Opp =new Player(o.getIndex(), o.getPid(),(Stack<Tiger>) o.getTigers().clone());
		Stack<Tile> TileStack = gm.getPile();
		
		if (depth != 0) {
			TileStack.pop();
		}

		final int[] move= new int[5];//single move

		//array of all possible moves
		//current==true => max
		//evaluation of final matrix, compare to next,
		int evalmax = Integer.MIN_VALUE;
		int evalmin = Integer.MAX_VALUE;

		Map<Position, List<Integer>> possiblemoves = gm.getValidTilePlacements(TileStack.peek());
		
		/*System.out.print("possible: ");
		for (Position p : possiblemoves.keySet()) {
			System.out.print(p + " ");
		}
		System.out.println();*/
		
		if (possiblemoves.isEmpty()) {
			System.out.println("no possible moves :(");
			move[0] = 0;
			move[1] = 0;
			move[2] = 0;
			move[3] = 0;
			move[4] = 0;
			return move;
		}

		int numberleft = TileStack.size();
		//make tile stack check to see number of remaining tiles
		//if 1=>eval
		//if 2=> 1 min and 1 max
		int x=0;
		if (numberleft == 1 && current) {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int newscore= AI.updateScore(debug, debug.getLatest());
					if(newscore<0)
					{
						otherscore= otherscore-newscore;
					}
					if(newscore>0)
					{
						currentscore=currentscore+newscore;
					}
					int newevalmax =currentscore- otherscore;
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = -1;
						move[4] = evalmax;
					}
				}
				if(x==possiblemoves.size()/4)
				{
					break;
				}
				x++;
			}
			//System.out.println("1 left, current: " + new Position(move[0], move[1]));
			return move;
		}
		
		if (numberleft == 1 && !current) {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int newscore= Opp.updateScore(debug, debug.getLatest());
					if(newscore<0)
					{
						otherscore= otherscore-newscore;
					}
					if(newscore>0)
					{
						currentscore=currentscore+newscore;
					}
					int newevalmin =currentscore- otherscore;
					if (newevalmin < evalmin) {
						evalmin = newevalmin;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = -1;
						move[4] = evalmin;
					}
				}
				if(x==possiblemoves.size()/4)
				{
					break;
				}
				x++;
			}
			//System.out.println("1 left, !current: " + new Position(move[0], move[1]));
			return move;
		}
		
		if (depth == 2) {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int newscore= AI.updateScore(debug, debug.getLatest());
					if(newscore<0)
					{
						otherscore= otherscore-newscore;
					}
					if(newscore>0)
					{
						currentscore=currentscore+newscore;
					}
					int newevalmax =currentscore- otherscore;
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = -1;
						move[4] = evalmax;
					}
				}
				if(x==possiblemoves.size()/4)
				{
					break;
				}
				x++;
			}
			//System.out.println("2 depth: " + new Position(move[0], move[1]));
			return move;
		}

		depth++;
		
		if (current) {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int i=-1;
					if (TileStack.peek().hasDen() && AI.getTigers().size() > 0) {
						Tiger tiger = AI.getTigers().pop();
						TileStack.peek().addTiger(5, tiger);
						i = 5;
					}
					else if(TileStack.peek().hasAnimal()) {
						for (i = 1; i < 10; i++) {
							if (TileStack.peek().getZone(i) == 'l') {
								boolean isValid = debug.validTigerPlacement(debug.getLatest(), i, false);
								if (isValid && AI.getTigers().size() > 0) {
									Tiger tiger = AI.getTigers().pop();
									TileStack.peek().addTiger(i, tiger);
									break;
								}
							}
						}
						i = (i == 10) ? -1 : i;
					}
					int newscore= AI.updateScore(debug, debug.getLatest());
					if(newscore<0)
					{
						otherscore= otherscore-newscore;
					}
					if(newscore>0)
					{
						currentscore=currentscore+newscore;
					}
					int[] temp = MiniMax(debug, depth, otherscore, currentscore, AI, Opp);
					int newevalmax =currentscore- otherscore+ temp[temp.length - 1];
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = i;
						move[4] = evalmax;
					}
				
				}
				if(x==possiblemoves.size()/4)
				{
					break;
				}
				x++;
			}
			//System.out.println("current: " + new Position(move[0], move[1]));
			return move;
		}
		
		else {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int i=-1;
					if (TileStack.peek().hasDen() && Opp.getTigers().size() > 0) {
						Tiger tiger = Opp.getTigers().pop();
						TileStack.peek().addTiger(5, tiger);
						i = 5;
					}
					else if(TileStack.peek().hasAnimal()) {
						for (i = 1; i < 10; i++) {
							if (TileStack.peek().getZone(i) == 'l') {
								boolean isValid = debug.validTigerPlacement(debug.getLatest(), i, false);
								if (isValid && Opp.getTigers().size() > 0) {
									Tiger tiger = Opp.getTigers().pop();
									TileStack.peek().addTiger(i, tiger);
									break;
								}
							}
						}
						i = (i == 10) ? -1 : i;
					}
					int newscore= Opp.updateScore(debug, debug.getLatest());
					if(newscore<0)
					{
						otherscore= otherscore-newscore;
					}
					if(newscore>0)
					{
						currentscore=currentscore+newscore;
					}
					int[] temp = MiniMax(debug, depth, otherscore, currentscore, AI, Opp);
					int newevalmin =currentscore- otherscore+ temp[temp.length - 1];
					if (newevalmin < evalmin) {
						evalmin = newevalmin;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = i;
						move[4] = evalmin;
					}
				}
				if(x==possiblemoves.size()/4)
				{
					break;
				}
				x++;
			}
			//System.out.println("!current: " + new Position(move[0], move[1]));
			return move;
		}
	}

	private int Evaluation(Board nextGame) {
		// ai score-opponent score?
		int eval;
		int aiscore=0;//thisplayer.getPoints();//thisplayer=ai
		int oppscore=0;//thatplayer.getPoint();//thatplayer=opponent
		eval = aiscore-oppscore;
		return eval;
	}

	private boolean apply(final Board board, final Position position, final int orientation, Tile current) {
		final Tile current2 = current.clone();
		while (current2.getRotation() != orientation) {
			current2.rotate();
		}

		return board.addTile(position, current2);
	}
}
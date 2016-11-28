package tiger_zone.ai;

import tiger_zone.Game;
import tiger_zone.Position;
import tiger_zone.Tile;
import tiger_zone.Board;
import java.util.Stack;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		
		final int[] move = MiniMax(temp, 0);

		while (current.getRotation() != move[2]) {
			current.rotate();
		}
		
		System.out.println("BetterAiPlayer: placing tile at " + new Position(move[0], move[1]));
		this.game.getBoard().addTile(new Position(move[0], move[1]), current);
	}

	private final int[] MiniMax(final Board gm, int depth) {
		boolean current = depth % 2 == 0;
		
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
			return move;
		}

		int numberleft = TileStack.size();
		//make tile stack check to see number of remaining tiles
		//if 1=>eval
		//if 2=> 1 min and 1 max

		if (numberleft == 1 && current) {
			for (Entry<Position, List<Integer>> p : possiblemoves.entrySet()) {
				for (Integer rotation : p.getValue()) {
					Board debug = gm.clone();
					if (!apply(debug, p.getKey(), rotation, TileStack.peek())) {
						continue;
					}
					int newevalmax = Evaluation(debug);
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[4] = evalmax;
					}
				}
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
					int newevalmin = Evaluation(debug);
					if (newevalmin < evalmin) {
						evalmin = newevalmin;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[4] = evalmin;
					}
				}
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
					int newevalmax = Evaluation(debug);
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = -1;
						move[4] = evalmax;
					}
				}
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
					int[] temp = MiniMax(debug, depth);
					int newevalmax = temp[temp.length - 1];
					if (newevalmax > evalmax) {
						evalmax = newevalmax;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[3] = -1;
						move[4] = evalmax;
					}
				}
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
					int[] temp = MiniMax(debug.clone(), depth);
					int newevalmin = temp[temp.length - 1];
					if (newevalmin < evalmin) {
						evalmin = newevalmin;
						move[0] = p.getKey().getX();
						move[1] = p.getKey().getY();
						move[2] = rotation;
						move[4] = evalmin;
					}
				}
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
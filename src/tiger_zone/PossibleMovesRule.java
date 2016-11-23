package tiger_zone;



public class PossibleMovesRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	private boolean[][] north,west,south,east;//relative to all the different rotations
	private int length;
	private int firstMove[];
	
	public PossibleMovesRule(Board boardState,int cartX, int cartY, Tile nextTile, boolean trace)
	{
		//cartX, cartY
		super(boardState,cartX, cartY, trace);

		super.setRuleName("PossibleMoves Rule");
		this.nextTile = nextTile;
		length = boardState.getBoardLength();
		north = new boolean[length][length];
		west  = new boolean[length][length];
		south = new boolean[length][length];
		east  = new boolean[length][length];
		firstMove = new int[3];
		firstMove[0] = Integer.MAX_VALUE;
		firstMove[1] = Integer.MAX_VALUE;
		firstMove[2] = Integer.MAX_VALUE;

	}
	public void output()
	{
		System.out.println("Rotation 0 Degrees");
		System.out.print(north[boardState.getBoardPosX(-1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.print(north[boardState.getBoardPosX(0)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.println(north[boardState.getBoardPosX(1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		
		System.out.print(north[boardState.getBoardPosX(-1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.print(north[boardState.getBoardPosX(0)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.println(north[boardState.getBoardPosX(1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		
		System.out.print(north[boardState.getBoardPosX(-1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.print(north[boardState.getBoardPosX(0)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.println(north[boardState.getBoardPosX(1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		
		System.out.println("Rotation -90 Degrees");
		System.out.print(west[boardState.getBoardPosX(-1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.print(west[boardState.getBoardPosX(0)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.println(west[boardState.getBoardPosX(1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		
		System.out.print(west[boardState.getBoardPosX(-1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.print(west[boardState.getBoardPosX(0)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.println(west[boardState.getBoardPosX(1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		
		System.out.print(west[boardState.getBoardPosX(-1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.print(west[boardState.getBoardPosX(0)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.println(west[boardState.getBoardPosX(1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		
		System.out.println("Rotation -180 Degrees");
		System.out.print(south[boardState.getBoardPosX(-1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.print(south[boardState.getBoardPosX(0)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.println(south[boardState.getBoardPosX(1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		
		System.out.print(south[boardState.getBoardPosX(-1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.print(south[boardState.getBoardPosX(0)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.println(south[boardState.getBoardPosX(1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		
		System.out.print(south[boardState.getBoardPosX(-1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.print(south[boardState.getBoardPosX(0)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.println(south[boardState.getBoardPosX(1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		
		System.out.println("Rotation -270 Degrees");
		System.out.print(east[boardState.getBoardPosX(-1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.print(east[boardState.getBoardPosX(0)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		System.out.println(east[boardState.getBoardPosX(1)][boardState.getBoardPosY(1)] ? "T\t":"F\t");
		
		System.out.print(east[boardState.getBoardPosX(-1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.print(east[boardState.getBoardPosX(0)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		System.out.println(east[boardState.getBoardPosX(1)][boardState.getBoardPosY(0)] ? "T\t":"F\t");
		
		System.out.print(east[boardState.getBoardPosX(-1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.print(east[boardState.getBoardPosX(0)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		System.out.println(east[boardState.getBoardPosX(1)][boardState.getBoardPosY(-1)] ? "T\t":"F\t");
		
		System.out.println("First Possible Move: ");
		System.out.println("Cartesian Coordinate: (" + firstMove[0] + "," + firstMove[1] + ") Rotation: " + firstMove[2]);
		
		
	}
	@Override
	public boolean evaluate()
	{
	
			boolean ret = false;
			try{
				if(Math.abs(cartX) > length || (Math.abs(cartY) > length ))
					throw new Exception(super.getName() + " failed under condition: tile tile desired palcement out of bounds.");
			}
			catch(Exception ex)
			{
				if(super.trace)
					System.err.println(ex);
				return false;
			}
			boolean eval = false;
			while(this.nextTile.getRotation() != 0)
			{
				this.nextTile.rotate();
			}
			//iterate through each rotation
			for(int r = 0; r < 4; r++)
			{
			
				
				//go through all the tiles
				for(int i = -((length -1)/ 2); i < length/2; i++)
				{
					for(int j = -((length-1) / 2); j < length/2; j++)
					{
						switch(r)
						{
						case 0: eval= boardState.validTilePlacement(i, j, this.nextTile, false);
							north[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
							if(!ret && eval)//the first true value
							{
								firstMove[0] = i;
								firstMove[1] = j;
								firstMove[2] = this.nextTile.getRotation();
							}
							ret = ret || north[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)];
							break;
						case 1:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
							west[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
							if(!ret && eval)//the first true value
							{
								firstMove[0] = i;
								firstMove[1] = j;
								firstMove[2] = this.nextTile.getRotation();
							}
							ret = ret || west[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)];
							break;
						case 2:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
							south[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
							if(!ret && eval )//the first true value
							{
								firstMove[0] = i;
								firstMove[1] = j;
								firstMove[2] = this.nextTile.getRotation();
							}
						ret = ret || south[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)];
							break;
						case 3:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
							east[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
							if(!ret && eval)//the first true value
							{
								firstMove[0] = i;
								firstMove[1] = j;
								firstMove[2] = this.nextTile.getRotation();
							}
							ret = ret || east[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)];
							break;
						default:
							System.err.println(super.getName() + "failed under condition: unexpected rotation");
						}
					}
				}
				this.nextTile.rotate();
			}
			while(this.nextTile.getRotation() != 0)
			{
				this.nextTile.rotate();
			}
			return ret;

	}
	
	public int[] getFirstPossibleMove(){ return this.firstMove; }
	public boolean[][] get0Rotation() { return this.north; }
	public boolean[][] get_90Rotation(){ return this.west; }
	public boolean[][] get_180Rotation(){return this.south; }
	public boolean[][] get_270Rotation(){ return this.east; }
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: \"" + "\" does not equal \"Hello World\"");
	}
	
}


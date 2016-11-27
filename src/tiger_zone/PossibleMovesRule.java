package tiger_zone;

import java.util.ArrayList;

public class PossibleMovesRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	private boolean[][] north,west,south,east;//relative to all the different rotations
	private boolean foundQuad1, foundQuad2, foundQuad3, foundQuad4;
	private int length;
	private int firstMove[][];
	private ArrayList<int[]> possibleMoves;
	
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
		
		firstMove = new int[4][3];
		for(int i = 0; i < 4; i++)
		{
			firstMove[i][0] = Integer.MAX_VALUE;
			firstMove[i][1] = Integer.MAX_VALUE;
			firstMove[i][2] = Integer.MAX_VALUE;
		}
		possibleMoves = new ArrayList<int[]>();
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
		int rotationInit = this.nextTile.getRotation();
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
						if(eval){
							possibleMoves.add(new int[]{i,j,0});
							setArray(i, j);
						}
						ret = ret || eval;
						break;
					case 1:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
						west[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
						if(eval){
							possibleMoves.add(new int[]{i,j,90});
							setArray(i, j);
						}
						ret = ret || eval;
						break;
					case 2:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
						south[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
						if(eval){
							possibleMoves.add(new int[]{i,j,180});
							setArray(i, j);
						}
						ret = ret || eval;;
						break;
					case 3:eval = boardState.validTilePlacement(i, j, this.nextTile, false);
						east[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)] = eval;
						if(eval){
							possibleMoves.add(new int[]{i,j,270});
							setArray(i, j);
						}
						ret = ret || eval;
						break;
					default:
						System.err.println(super.getName() + "failed under condition: unexpected rotation");
					}
				}
			}
			this.nextTile.rotate();
		}
		while(this.nextTile.getRotation() != rotationInit)
		{
			this.nextTile.rotate();
		}
		//if(this.firstMove[0][0] == Integer.MAX_VALUE &&this.firstMove[1][0] == Integer.MAX_VALUE &&this.firstMove[2][0] == Integer.MAX_VALUE &&this.firstMove[3][0] == Integer.MAX_VALUE )
		return ret;

	}
	
	public void setArray(int x, int y)
	{
		
		int i = -1;
		if(x >= 0 && y >= 0 && !foundQuad1)
		{
			i = 0;
			foundQuad1 = true;
		}
		else if(x <= 0 && y >= 0 && !foundQuad2)
		{
			i = 1;
			foundQuad2 = true;
		}
		else if( x <= 0 && y <= 0 && !foundQuad3)
		{
			i = 2;
			foundQuad3 = true;
		}
		else if( x >= 0 && y <= 0 && !foundQuad4)
		{
			i = 3;
			foundQuad4 = true;
		}
		if( i >= 0)
		{
			firstMove[i][0] = x;
			firstMove[i][1] = y;
			firstMove[i][2] = this.nextTile.getRotation();
		}
		
	}
	
	public int[] getFirstPossibleMove()
	{ 
		int ind = (int)Math.random() * 4;
		for(int i = 0; i <4; i++)
		{
			if(ind == 4)
				ind = 0;
			if(this.firstMove[ind][0] != Integer.MAX_VALUE && (this.firstMove[ind][0]<5 && this.firstMove[ind][0] > -5) &&(this.firstMove[ind][1]<5 && this.firstMove[ind][1] > -5))
				return this.firstMove[ind]; 
			ind++;
		}
		for(int i = 0; i <4; i++)
		{
			if(ind == 4)
				ind = 0;
			if(this.firstMove[ind][0] != Integer.MAX_VALUE )
				return this.firstMove[ind]; 
			ind++;
		}
		return new int[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE};
	}
	
	public int[] getClosestToOriginEstimateMove()
	{
		int choice = -1;
		double distance = Double.MAX_VALUE;
		double temp = Double.MAX_VALUE;
		for(int i = 0; i < firstMove.length; i++)
		{
			if(this.firstMove[i][0] == Integer.MAX_VALUE)
				continue;
			temp = Math.sqrt(Math.pow(this.firstMove[i][0], 2) + Math.pow(this.firstMove[i][1], 2));
			if(temp < distance)
			{
				choice = i;
				distance = temp;
			}
		}
		return this.firstMove[choice];
	}
	
	public int[] getClosestToOriginMove()
	{
		int choice = -1;
		int rot = Integer.MAX_VALUE;
		int x = Integer.MAX_VALUE;
		int y = Integer.MAX_VALUE;
		double distance = Double.MAX_VALUE;
		double temp = Double.MAX_VALUE;
		
		for(int r = 0; r < 4; r++)
		{
			for(int i = -((length -1)/ 2); i < length/2; i++)
			{
				for(int j = -((length-1) / 2); j < length/2; j++)
				{
					switch(r)
					{
					case 0: if(north[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)])
						{
							temp = distanceFromOrigin(i,j);
							if(temp < distance)
							{
								distance = temp;
								x = i;
								y = j;
								rot = 0;
							}
						}
						break;
					case 1:
						if(west[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)])
						{
							temp = distanceFromOrigin(i,j);
							if(temp < distance)
							{
								distance = temp;
								x = i;
								y = j;
								rot = 0;
							}
						}
						break;
					case 2:
						if(south[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)])
						{
							temp = distanceFromOrigin(i,j);
							if(temp < distance)
							{
								distance = temp;
								x = i;
								y = j;
								rot = 0;
							}
						}
						break;
					case 3:
						if(east[boardState.getBoardPosX(i)][boardState.getBoardPosY(j)])
						{
							temp = distanceFromOrigin(i,j);
							if(temp < distance)
							{
								distance = temp;
								x = i;
								y = j;
								rot = 0;
							}
						}
						break;
					}
				
				}
			}
		}
		int[] ret = {x, y, rot};
		return ret;
	}
	public double distanceFromOrigin(int x, int y)
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(x, 2));
	}
	public ArrayList<int[]> getAllPossibleMoves() { return possibleMoves; }
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




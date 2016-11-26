package tiger_zone;

public class UnionFind//used to find what areas on a tile are connected adn the lowest index of that object
{
	private Tile tile;
	private char[] data;//what exists there
	private int[]  parent;//what index to get
	
	public UnionFind(Tile t)
	{
		this.tile = t;
		this.data = new char[9];
		for(int i = 0; i < 9; i++)
			data[i] = tile.getZone(i+1);
		this.parent = new int[data.length];
		for(int i = 0; i < parent.length; i++)
			parent[i] = i;
		this.evaluateAdjacentCase();
	}
	
	public void evaluateAdjacentCase()
	{
		for(int i = 0; i < 9; i++)
		{
			if(i + 1 < 9  && (i+1) % 3 != 0 &&data[i] == data[i+1])
				this.union(i, i+1);
			if(i - 1 >= 0 && (i+1) % 3 != 0 && data[i] == data[i-1])
				this.union(i,  i-1);
			if(i >= 3 && i <= 5)
			{
				if(data[i] == data[i-3])
					this.union(i,i-3);
				if(data[i] == data[i+3])
					this.union(i, i+3);
			}
		}
	}
	
	public void evaluateEdgeCase()
	{
		//	0	1	2
		//	3	4	5
		//	6	7	8
		if(tile.getSide(1) == 'l')
		
		for(int i = 1; i <= 7; i += 2)
		{
			if(tile.getZone(i) == 'l')
			{
				
			}
		}
	}
	
	public int findRoot(int index)
	{
		if(parent[index] == index)
			return index;
		else
			return findRoot(parent[index]);
	}
	
	public void union(int a, int b)
	{
		int aRoot = findRoot(a);
		int bRoot = findRoot(b);
		parent[b] = Math.min(aRoot, bRoot);
		parent[a] = Math.min(aRoot, bRoot);
	}
	
	
}
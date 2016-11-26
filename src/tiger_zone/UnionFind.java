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
		this.evaluateEdgeCase();
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
		if(tile.getOriginalSides().equals("jllj"))
		{
			for(int i = 0; i < 4; i++)
			{
				if(tile.getSide(i) == 'l')
				{
					int j = (i != 3) ? i+1 : 0;
					int k = (i != 0) ? i-1 : 3;
					int min = Math.min(j, k);
					if(tile.getSide(j) == 'l')
					{
						switch(j)
						{
						case 0:		// 1  5 shifted for array
							this.union(0, 4);
							break;
						case 1:
							this.union(2, 4);
							break;
						case 2:
							this.union(8, 4);
							break;
						case 3:
							this.union(6, 4);
							
						}
						evaluateAdjacentCase();
					}
				}
			}
		}
		
		for(int i = 0; i < 4; i++)
		{
			if(tile.getSide(i) == 'l')
			{
				int j = (i != 3) ? i+1 : 0;
				int k = (i != 0) ? i-1 : 3;
				if(tile.getSide(j) != 'l' && tile.getSide(k) != 'l')
				{
					int eight = (i == 2) ? 8 : 0;
					int four  = (i % 2 == 1) ? 4 : 0;
					int two   = (i < 2) ? 2 : 0;
					int one   = 0;
					int tot = eight + four + two + one;
					if(tot+1 >9 || tot -1 <1)break;
					if(i % 2 == 0 && tile.getZone(tot + 1) == 'j' && tile.getZone(tot - 1) == 'j')
						this.union(tot +1 - 1, tot - 1 - 1);
					else if(i % 2 == 1 && tile.getZone(tot + 3) == 'j' && tile.getZone(tot - 3) == 'j')
						this.union(tot + 3 - 1,  tot - 3- 1);
				}
				
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
	public int getLowestZone(int zone)
	{
		return findRoot(zone - 1) + 1;
	}
	public void union(int a, int b)
	{
		int aRoot = findRoot(a);
		int bRoot = findRoot(b);
		parent[b] = Math.min(aRoot, bRoot);
		parent[a] = Math.min(aRoot, bRoot);
	}
	
	
}
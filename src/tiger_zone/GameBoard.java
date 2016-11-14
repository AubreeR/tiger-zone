package tiger_zone;

class BoardCell {
	private final int x;
	private final int y;
	private Tile tile = null;

	public BoardCell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void setTile(final Tile tile) { 
		this.tile = tile;
	}

	public Tile getTile() {
		return this.tile;
	}

	public int getXCoord() {
		return this.x;
	}

	public int getYCoord() {
		return this.y;
	}
}

public class GameBoard {
	private final BoardCell[][] gameGrid;

	public GameBoard(final int n, final int m) {
		gameGrid = new BoardCell[n*2][m*2];
	}

	public void initializeBoard() {
		for (int i = 0; i < gameGrid.length; ++i) {
			for (int j = 0; j <  gameGrid.length; ++j) {
				gameGrid[i][j] = new BoardCell(i, j);
			}
		}
	}

	public void addTileToBoard(final int x, final int y, final Tile tile) {
		gameGrid[x][y].setTile(tile);
	}

	public Tile getTileFromBoard(final int x, final int y){
		return gameGrid[x][y].getTile();
	}
}
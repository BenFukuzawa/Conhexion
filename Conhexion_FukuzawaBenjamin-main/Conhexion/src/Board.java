import java.util.Arrays;

public class Board
{
	public static final int PLAYER_NONE = 0;
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	
	private int row;
	private int col;
	private int[][] board;
	//private int[][] surround;
	//private int[] convert;
	private WeightedQuickUnionUFCloneable uf;
	
	// Constructs a new board with the specified number of rows and columns
	public Board(int rows, int columns)
	{
		
		this.row = rows;
		this.col = columns;
		board = new int[rows][columns];
		
		for (int i = 0; i<row; i++)
		{
			for(int j = 0; j<col; j++)
			{
				board[i][j] = 0;
			}
		}
		/*
		
		*/
		uf = new WeightedQuickUnionUFCloneable(row*col+4); //+4 is for the virtual sites
		
	}

	// Constructs a new Board that clones the state of the specified Board
	public Board(Board original)
	{
		this.row = original.row;
		this.col = original.col;
		this.board = new int[this.row][this.col];
		for (int r = 0; r<row; r++)
		{
			for (int c = 0; c<col; c++)
			{
				this.board[r][c] = original.board[r][c];
			}
		}
		this.uf = new WeightedQuickUnionUFCloneable(original.uf);
	}

	// Returns the total number of rows in this Board
	public int getRows()
	{
		return row;
	}

	// Returns the total number of columns in this Board
	public int getColumns()
	{
		return col;
	}

	// Returns one of the three "player" ints defined on this class
	// representing which player, if any, occupies the specified
	// location on the board
	public int getPlayer(Location location)
	{
		return board[location.getRow()][location.getColumn()];
	}

	// Places a game piece from the specified player (represented by
	// one of the three "player" ints defined on this class) into the
	// specified location on the board
	public void setPlayer(Location location, int player)
	{
		board[location.getRow()][location.getColumn()] = player; 
		connectToNeighbor(location, player);
		
		
	}

	// Although the GameManager does not need to call this method, the
	// tests will call it to help verify that you're using the
	// union-find data structure correctly
	public boolean isConnected(Location location1, Location location2)
	{
		return uf.connected(conversion(location1), conversion(location2));
	}

	// Returns whether the specified location on the board contains
	// a game piece that is connected to one of the corresponding
	// player's sides.
	public int getSideConnection(Location location)
	{
		if (uf.connected(conversion(location), row*col) || uf.connected(conversion(location), row*col+1))
		{
			return 1;
		}
		else if (uf.connected(conversion(location), row*col+2) || uf.connected(conversion(location), row*col+3))
		{
			return 2;
		}
		return 0;
	}

	// Returns one of the three "player" ints indicating who is the winner
	// of the current Board.  PLAYER_NONE indicates no one has won yet.
	public int getCurrentWinner()
	{
		if (uf.connected(row*col, row*col+1))
		{
			return 1;
		}
		else if (uf.connected(row*col+2, row*col+3))
		{
			return 2;
		}
		return 0;
	}
	
	
	private int conversion(Location l)
	{
		int r = l.getRow();
		int c = l.getColumn();
		return col*r+c;
	}
	
	private void connectToNeighbor(Location l, int player)
	{
		int r = l.getRow();
		int c = l.getColumn();
		Location[] neighbor = {new Location(r+1, c), new Location(r-1, c), new Location(r, c+1), new Location(r, c-1), new Location(r+1, c-1), new Location(r-1, c+1)};
		//Location[] edge1 = {new Location(r+1, c), new Location(r, c+1)};
		//Location[] edge2 = {new Location(r-1, c), new Location(r, c-1)};
		int l2 = conversion(l);
	 	
		if (r== 0 && board[r][c] == 1)
		{
			uf.union(row*col,  l2);
		}
		else if (r== row-1 && board[r][c] == 1)
		{
			uf.union(row*col + 1,  l2);
		}
		else if (c==0 && board[r][c] == 2)
		{
			uf.union(row*col + 2,  l2);
		}
		else if (c== col-1 && board[r][c] == 2)
		{
			uf.union(row*col + 3,  l2);
		}
		/* 
		if ((l.getRow()==0 && l.getColumn()==0))
		{
			for (Location i:edge1)
				if (board[r][c] == board[i.getRow()][i.getColumn()])
				{
					uf.union(conversion(i), l2);
				}
		}

		else if ((l.getRow()==row-1 && l.getColumn()==col-1))
		{
			for (Location i:edge2)
				if (board[r][c] == board[i.getRow()][i.getColumn()])
				{
					uf.union(conversion(i), l2);
				}
		}
*/

		for (Location i:neighbor)
		{

			if (i.getRow()>=0 && i.getColumn()>=0 && i.getRow() <= row-1 && i.getColumn() <= col-1)
			{
				if (board[r][c] == board[i.getRow()][i.getColumn()])
				{
					uf.union(conversion(i), l2);
				}
			}
			
				
		}

		/*
		 * if (i.getRow()>=0 && i.getColumn()>=0 && i.getRow() <= row && i.getColumn() <= col)
			{
				if (board[r][c] == board[i.getRow()][i.getColumn()])
				{
					uf.union(conversion(i), l2);
				}
			}
		 */
	}
}

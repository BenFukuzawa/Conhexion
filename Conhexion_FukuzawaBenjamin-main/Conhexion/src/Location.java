
public class Location 
{
	private int row;
	private int col;
	// Constructs a new Location object with the specified row and column
	public Location(int rowP, int columnP)
	{
		
		row = rowP;
		col = columnP;
	}

	// Returns the row integer that was passed to the constructor
	public int getRow()
	{
		return row;
	}

	// Returns the column integer that was passed to the constructor
	public int getColumn()
	{
		return col;
	}
}


package backend;

/**
 * 
 * @author Jonathan Tshimpaka
 * 
 * Piece representation 
 *
 */
public class Piece {
	
	public Piece(Board.PieceColor c, int row, int col)
	{
		this.color = c;
		this.row = row;
		this.column = col;
		this.isKing = false;
	}

	/**
	 * @return the color
	 */
	public Board.PieceColor getColor() 
	{
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Board.PieceColor color) 
	{
		this.color = color;
	}
	
	
	/**
	 * @return the column
	 */
	public int getColumn() 
	{
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) 
	{
		this.column = column;
	}


	/**
	 * @return the row
	 */
	public int getRow() 
	{
		return row;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}


	/**
	 * @return the isKing
	 */
	public boolean isKing() 
	{
		return isKing;
	}
	
	/**
	 * make the piece a king
	 */
	public void makeking()
	{
		isKing = true;
	}

	/**
	 * @param isKing the isKing to set
	 */
	public void setKing(boolean isKing) 
	{
		this.isKing = isKing;
	}


	private Board.PieceColor color;
	private int row;
	private int column;
	private boolean isKing;

}

package backend;

/**
 * @author jonathantshimpaka
 * Move representation that keeps track where from where and to where a piece was move and if a piece was stolen
 */
public class Move {
	
	
	public Move(int startR, int startC, int endR, int endC, Piece piece)
	{
		setFromRow(startR);
		setFromColumn(startC);
		setToRow(endR);
		setToColumn(endC);
		setStolenPiece(piece);
		
	}
	
	
	/**
	 * @return the startingRow
	 */
	public int getFromRow() {
		return fromRow;
	}
	/**
	 * @param startingRow the startingRow to set
	 */
	public void setFromRow(int startingRow) {
		fromRow = startingRow;
	}


	/**
	 * @return the startingColumn
	 */
	public int getFromColumn() {
		return fromColumn;
	}


	/**
	 * @param startingColumn the startingColumn to set
	 */
	public void setFromColumn(int startingColumn) {
		fromColumn = startingColumn;
	}


	/**
	 * @return the endingRow
	 */
	public int getToRow() {
		return toRow;
	}


	/**
	 * @param endingRow the endingRow to set
	 */
	public void setToRow(int endingRow) {
		toRow = endingRow;
	}


	/**
	 * @return the endingColumn
	 */
	public int getToColumn() {
		return toColumn;
	}


	/**
	 * @param endingColumn the endingColumn to set
	 */
	public void setToColumn(int endingColumn) {
		toColumn = endingColumn;
	}

	public void setStolenPiece(Piece piece)
	{
		stolenPiece = piece;
	}
	
	public Piece getStolenPiece()
	{
		return stolenPiece;
	}

	/**
	 * @return the makeKing
	 */
	public boolean isKing() {
		return isKing;
	}


	/**
	 * @param makeKing the makeKing to set
	 */
	public void makeKing() {
		this.isKing = true;
	}

	private int fromRow;
	private int fromColumn;
	private int toRow;
	private int toColumn;
	private Piece stolenPiece;
	private boolean isKing = false;
	
	

}

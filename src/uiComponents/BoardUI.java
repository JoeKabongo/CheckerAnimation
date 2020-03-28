package uiComponents;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


/**

 * @author Jonathan Tshimpaka
 * Board UI representation
 */
public class BoardUI{
	
	/** 
	 * Constructor, initialize the board
	 */
	public BoardUI () 
	{		
		pieces = new PieceUI[BOARD_SIZE][BOARD_SIZE];
		boardSquares = new JButton[BOARD_SIZE][BOARD_SIZE];	
		initialize();
	}
	
	/**
	 *  Initialize board to a starting game state 
	 *  */
	public void initialize()
	{
		jpanelContainer = new JPanel();
		jpanelContainer.setLayout(new GridLayout(0, BOARD_SIZE));
		
		// color of the squares
		Color [] colors = {Color.BLACK, new Color(166,124,0)};
		int color = 0;


		for(int i=0; i<BOARD_SIZE; i++)
		{
			for(int j=0; j<BOARD_SIZE; j++)
			{
				  JButton button = new JButton();
				  button.setBackground(colors[color%colors.length]);
				  button.setBorderPainted(false);
				  button.setOpaque(true);
				  
				  //a piece need to be placed here
				  if(color%2 ==  0 && i != 3 && i!= 4) 
				  {
					  Color pieceColor;
					  if(i < 3) pieceColor = Color.YELLOW;
					  else pieceColor = Color.RED;
					  
					  PieceUI piece = new PieceUI(pieceColor, false);
					  button.setIcon(piece);
					  pieces[i][j] = piece;
					  
				  } else {
					  pieces[i][j] = null;
				  }
				  color++;
				  
				  // add button to our JPanel container
				  jpanelContainer.add(button);
				  boardSquares[i][j] = button;

			}
			color++;
		}
		
	}
	
	/**
	 *  return the Jpanel containing the board UI
	 *  */
	public JPanel getBoard()
	{
		return jpanelContainer;
	}
	
	/**
	 * move piece from [row][col] to [desinationRow][DestinationColumn]
	 */
	public void movePiece(boolean isKing, int row, int col, int destinationRow, int destinationColumn)
	{
		
		PieceUI piece = pieces[row][col];
		removePiece(row, col);
		
		pieces[destinationRow][destinationColumn] = piece;
		boardSquares[destinationRow][destinationColumn].setIcon(piece);
		
		
		if(isKing) {
			PieceUI newPiece = new PieceUI(piece.getColor(), true);
			boardSquares[destinationRow][destinationColumn].setIcon(newPiece);
			pieces[destinationRow][destinationColumn] = newPiece;

		}

	}
	
	
	/**
	 * remove piece at this position from the board
	 * @param row
	 * @param col
	 */
	public void removePiece(int row, int col)
	{
		pieces[row][col] = null;
		boardSquares[row][col].setIcon(null);
	}
	
	
	
	private final  int BOARD_SIZE = 8;
	private JPanel jpanelContainer;
	private JButton[][] boardSquares;
	private PieceUI[][] pieces;

}

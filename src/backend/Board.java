package backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * @author jonathantshimpaka
 *
 * Board representation that keeps track of the state of the board and of each piece
 * It is the controller to the UI
 *
 */
public class Board {
	
	
	public Board()
	{
		board = new Piece[BOARD_SIZE][BOARD_SIZE];
		initialize();
	}
	
	/**
	 * Initialize start game state, place all piece at appropriate spot
	 */
	public void initialize()
	{
		
		int color = 0;
		yellowPieces = new LinkedList<>();
		redPieces = new LinkedList<>(); 

		
		for(int i=0; i<BOARD_SIZE; i++)
		{
			for(int j=0; j<BOARD_SIZE; j++)
			{
				  
				  //a piece need to be placed here
				  if(color%2 ==  0 && i != 3 && i!= 4) 
				  {
					  Piece piece;
					  if(i < 3) 
					  {
						  piece = new Piece(PieceColor.YELLOW, i, j);
						  yellowPieces.add(piece);
					  }
					  else 
					  {
						  piece = new Piece(PieceColor.RED, i, j);
						  redPieces.add(piece);
					  }
					  board[i][j] = piece;
							  
					  
				  } else {
					  board[i][j] = null;
				  }
				  color++;
				  
			}
			color++;
		}
	}
	
	/**
	 * Decide whose turn it is to make a move
	 * @return a possible a Move to be made
	 */
	public Move makeMove()
	{
		
		ArrayList<Move> allMoves;
		ArrayList<Move> stolenPieceMoves = new ArrayList<>();
		Move chosenMove;
		Random rand = new Random();

		
		if(previousMove == null) {
			
			int randomNumber = rand.nextInt(4);
			if(randomNumber % 2 == 0) allMoves = redMove();
			else allMoves = yellowMove();
			
		} else {
			Piece piece = board[previousMove.getToRow()][previousMove.getToColumn()];

			if(previousMove.getStolenPiece() == null)
			{
				if(piece.getColor() == PieceColor.RED) allMoves = yellowMove();
				else allMoves = redMove();
			} 
			else
			{

				allMoves = stealPieceMove(piece);
				if(allMoves.size() != 0) chosenMove = allMoves.get(rand.nextInt(allMoves.size()));
				else {
					if(piece.getColor() == PieceColor.RED) allMoves = yellowMove();
					else allMoves = redMove();
				}
				
				
			}
		}
		 
		/* No possible move was found */
		if(allMoves.size() == 0) return null;
		
		/* Select move where pieces are stolen */
		for(Move move : allMoves)
		{
			if(move.getStolenPiece() != null)
				stolenPieceMoves.add(move); 
		}
		
		// Select a move to be executed(randomly), priority to move  where a piece can be stolen
		if(stolenPieceMoves.size() != 0)chosenMove = stolenPieceMoves.get(rand.nextInt(stolenPieceMoves.size()));
		else  chosenMove = allMoves.get(rand.nextInt(allMoves.size()));
		
		
		Piece piece = board[chosenMove.getFromRow()][chosenMove.getFromColumn()];
		
		// update our board with our new move
		movePiece(chosenMove.getFromRow(), chosenMove.getFromColumn(), chosenMove.getToRow(), chosenMove.getToColumn());
		
		// delete the stolen move
		Piece stolenPiece = chosenMove.getStolenPiece();
		if(stolenPiece != null)
			removePiece(stolenPiece.getRow(), stolenPiece.getColumn());
		
		
		
		// make our piece king if applicable
		if(!piece.isKing() && piece.getColor() == PieceColor.RED && chosenMove.getToRow() == 0 || 
				piece.getColor() == PieceColor.YELLOW && chosenMove.getToRow() == BOARD_SIZE - 1)
		{
			piece.makeking();
			chosenMove.makeKing();
		}
		
		// save this move as our previousMove
		previousMove = chosenMove;
		return chosenMove;
		
	}
	
	/**
	 * 
	 * @return the winner of the game
	 */
	public String getWinner()
	{		
		if(redMove().size() == 0) return "YELLOW";
		if(yellowMove().size() == 0) return "RED";
		
		// SOMETHING MUST HAVE WENT WRONG IN THIS CASE, BECASAUSE THERE MUST BE A WINNER
		return "DRAW";
	}
	
	/**
	 * Check all four way diagonally to see if this piece can steal opposite piece
	 * @param piece
	 * @return possible moves
	 */
	private ArrayList<Move> stealPieceMove(Piece piece)
	{
		ArrayList<Move> moves = new ArrayList<>();
		int row = piece.getRow();
		int column = piece.getColumn();
		
		// if the piece is a king
		if(piece.isKing())
		{
			ArrayList<Move> possibleMoves =  new ArrayList<>();
			addKingMoves(piece, possibleMoves);
			
			for(Move move: possibleMoves)
			{
				if(move.getStolenPiece() != null) moves.add(move);
			}
			return moves;

		}
		
		Piece diagonalPiece;
		
		
		if(row + 1 < BOARD_SIZE && column + 1 < BOARD_SIZE)
		{
			diagonalPiece = board[row + 1][column + 1];
			if(diagonalPiece != null && diagonalPiece.getColor() != piece.getColor() 
					&& isEmptySquare(row + 2, column +2) )
			{
				moves.add(new Move(row, column, row + 2, column + 2, diagonalPiece));
			}
		}
		
		if(row + 1 < BOARD_SIZE && column -  1 >= 0)
		{
			diagonalPiece = board[row + 1][column -  1];
			if(diagonalPiece != null && diagonalPiece.getColor() != piece.getColor() 
					&& isEmptySquare(row + 2, column - 2) )
			{
				moves.add(new Move(row, column, row + 2, column - 2, diagonalPiece));
			}
		}
		
		
		if(row - 1 >=0 && column + 1 < BOARD_SIZE)
		{
			diagonalPiece = board[row - 1][column + 1];
			if(diagonalPiece != null && diagonalPiece.getColor() != piece.getColor() 
					&& isEmptySquare(row - 2, column + 2) )
			{
				moves.add(new Move(row, column, row - 2, column + 2, diagonalPiece));
			}
		}
		
		if(row - 1  >= 0 && column - 1 >= 0)
		{
			diagonalPiece = board[row - 1][column - 1];
			if(diagonalPiece != null && diagonalPiece.getColor() != piece.getColor() 
					&& isEmptySquare(row - 2, column - 2) )
			{
				moves.add(new Move(row, column, row - 2, column - 2, diagonalPiece));
			}
		}
		
		
		
		return moves;
				
	}
	
	/**
	 * check if this spot is valid and empty
	 * @param row
	 * @param column
	 * @return 
	 */
	private boolean isEmptySquare(int row, int column)
	{
		if(row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE)
			return false;
		
		return board[row][column] == null;
	}
	
	/**
	 * return all possible move yellow piece can make
	 */
	private ArrayList<Move> yellowMove()
	{
		ArrayList<Move> moves = new ArrayList<>();
		Piece diagonalPiece;

		for(Piece piece : yellowPieces)
		{
			int row = piece.getRow();
			int col = piece.getColumn();
			
			
			if(piece.isKing())
			{
				addKingMoves(piece, moves);
			}
			else
			{
				/*
				 * move forward w
				 */
				if(row + 1 < 8 && col + 1 < 8)
				{
					diagonalPiece = board[row+1][col+1];
					if(diagonalPiece == null)		
						moves.add(new Move(row, col, row + 1, col + 1, null));
					
					else if(diagonalPiece.getColor() == PieceColor.RED)
					{
						if(isEmptySquare(row + 2, col + 2))
							moves.add(new Move(row, col, row + 2, col + 2, diagonalPiece));
					}
					
				}
				
				if(row + 1 < 8 && col -  1 >= 0)
				{
					diagonalPiece = board[row + 1][col - 1];
					if(diagonalPiece == null)
						moves.add(new Move(row, col, row + 1, col -1, null));
					
					else if(diagonalPiece.getColor() == PieceColor.RED)
					{
						if(isEmptySquare(row + 2, col - 2))
							moves.add(new Move(row, col, row + 2, col - 2, diagonalPiece));
					}
				}
				
				
				/*
				 * try to move backward only if a piece can be stolen
				 */
				if(row - 1 >= 0 && col + 1 < 8)
				{
					diagonalPiece = board[row - 1][col + 1];
					if(diagonalPiece != null  && diagonalPiece.getColor() == PieceColor.RED &&
							isEmptySquare(row - 2, col + 2)) {
						moves.add(new Move(row, col , row - 2, col + 2, diagonalPiece));
					}
				}
				
				if(row - 1 >= 0 && col - 1 >= 0)
				{
					diagonalPiece = board[row - 1][col - 1];
					if(diagonalPiece != null && diagonalPiece.getColor() == PieceColor.RED &&
							isEmptySquare(row - 2, col -2)) {
						moves.add(new Move(row, col, row - 2, col - 2, diagonalPiece));
					}
				}
			}
				
		}
		
		return moves;
	}
	
	/**
	 * return all Possible move red pieces can make
	 */
	private ArrayList<Move> redMove()
	{
		ArrayList<Move> moves = new ArrayList<>();
		Piece diagonalPiece;
		for(Piece piece : redPieces)
		{
			int row = piece.getRow();
			int col = piece.getColumn();
			
			
			if(piece.isKing())
			{
				addKingMoves(piece, moves);
			}
			else 
			{
				/*
				 * Move forward
				 */
				if(row - 1 >= 0 && col + 1 < 8)
				{
					diagonalPiece = board[row - 1][col + 1];
					
					/* if that square is empty*/
					if(diagonalPiece == null)
						moves.add(new Move(row, col, row - 1, col + 1, null));
					
					/* it contains a yellow piece*/
					else if(diagonalPiece.getColor() == PieceColor.YELLOW)
					{
						/* If this piece can jump over the yellow piece*/
						if(isEmptySquare(row - 2, col + 2)) 
							moves.add(new Move(row, col, row - 2, col + 2, diagonalPiece));
					}
				}
				
				if(row - 1 >= 0 && col -  1 >= 0)
				{
					diagonalPiece = board[row - 1][col - 1];
					/* Empty square */
					if(diagonalPiece == null)
						moves.add(new Move(row, col, row - 1, col - 1, null));
					
					/* square contains yellow piece, check if we can jump over it */
					else if(diagonalPiece.getColor() == PieceColor.YELLOW)
					{
						if(isEmptySquare(row - 2, col -2))						
							moves.add(new Move(row, col, row - 2, col - 2, diagonalPiece));
					}
				}
				
				/*
				 * Move backward but only if the piece can still a piece
				 */
				
				if(row + 1 < 8 && col + 1 < 8)
				{
					diagonalPiece = board[row + 1][col + 1];
					if(diagonalPiece != null && diagonalPiece.getColor() == PieceColor.YELLOW && 
							isEmptySquare(row + 2, col + 2)) {
						moves.add(new Move(row, col, row + 2, col + 2, diagonalPiece));
					}
				}
				
				if(row + 1 < 8 && col - 1 >= 0)
				{
					diagonalPiece = board[row + 1][col - 1];
					if(diagonalPiece != null && diagonalPiece.getColor() == PieceColor.YELLOW && 
							isEmptySquare(row + 2, col - 2)) {
						moves.add(new Move(row, col, row + 2, col - 2, diagonalPiece));
					}

				}
			}
		}
		
		return moves;
	}
	
	/**
	 * 
	 * Get all possible move for a king piece
	 * @param piece 
	 * @param moves
	 */
	private void addKingMoves(Piece kingPiece, ArrayList<Move> moves)
	{
		int R = kingPiece.getRow();
		int C = kingPiece.getColumn();
		
		int row = R + 1;
		int col = C + 1;
		while(row  < BOARD_SIZE && col < BOARD_SIZE)
		{
			if(isEmptySquare(row, col))
			{
				moves.add(new Move(R, C, row , col, null));
			}
			else if(board[row][col].getColor() != kingPiece.getColor() && isEmptySquare(row + 1, col + 1))
			{
				moves.add(new Move(R, C, row + 1 , col + 1, board[row][col]));
				break;
			} 
			else 
			{
				break;
			}
			row++;
			col++;
			
		}
		
		row = R + 1;
		col = C - 1;
		
		while(row < BOARD_SIZE && col >= 0)
		{
			if(isEmptySquare(row, col))
			{
				moves.add(new Move(R, C, row , col, null));
			}
			else if(board[row][col].getColor() != kingPiece.getColor() && isEmptySquare(row + 1, col - 1))
			{
				moves.add(new Move(R, C, row + 1 , col - 1, board[row][col]));
				break;
			} 
			else 
			{
				break;
			}
			row++;
			col--;
		}
		
		row = R - 1;
		col = C + 1;
		
		while(row >= 0 && col < BOARD_SIZE)
		{
			if(isEmptySquare(row, col))
			{
				moves.add(new Move(R, C, row , col, null));
			}
			else if(board[row][col].getColor() != kingPiece.getColor() && isEmptySquare(row - 1, col + 1))
			{
				moves.add(new Move(R, C, row - 1 , col + 1, board[row ][col]));
				break;
			} 
			else 
			{
				break;
			}
			row--;
			col++;
		}
		
		
		row = R - 1;
		col = C - 1;
		
		while(row >= 0 && col >= 0)
		{
			if(isEmptySquare(row, col))
			{
				moves.add(new Move(R, C, row , col, null));
			}
			else if(board[row][col].getColor() != kingPiece.getColor() && isEmptySquare(row - 1, col - 1))
			{
				moves.add(new Move(R, C, row - 1 , col - 1, board[row][col ]));
				break;
			} 
			else 
			{
				break;
			}
			row--;
			col--;
		}
		
		
	}
	
	
	/**
	 * Move a piece from board[fromRow][fromColumn] to board[toRow][toColumn]
	 * */
	private void movePiece(int fromRow, int fromColumn, int toRow, int toColumn)
	{
		assert board[fromRow][fromColumn] != null && board[toRow][toColumn] == null;
		
		Piece piece = board[fromRow][fromColumn];
		piece.setRow(toRow);
		piece.setColumn(toColumn);
		board[toRow][toColumn] = piece;
		board[fromRow][fromColumn] = null;
	}
	
	/**
	 * remove a piece from the board
	 * @param row
	 * @param col
	 */
	private void removePiece(int row, int col)
	{
		Piece piece= board[row][col];
		board[row][col] = null;
		if(piece.getColor() == PieceColor.RED)  redPieces.remove(piece);
		else yellowPieces.remove(piece);
		
	}
	
	
	enum PieceColor {
		RED,
		YELLOW
	}
	private final int BOARD_SIZE = 8;
	private Piece[][] board;
	private LinkedList<Piece> redPieces;
	private LinkedList<Piece> yellowPieces = new LinkedList<>(); 
	private Move previousMove = null;
	
}

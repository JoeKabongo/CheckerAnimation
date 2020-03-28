package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.Board;
import backend.Move;
import backend.Piece;
import uiComponents.BoardUI;

public class PlayAnimation {

	private static JFrame frame;
	private static BoardUI boardUI;
	private static Board boardLogic;
	private static Timer timer;
	private static JPanel animationController;
	
	public static void main(String[] args) {		
		
		
		initializeFrame();
		final int DELAY = 1000;
		timer = new Timer(DELAY, new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						updateBoardUI(boardLogic.makeMove());
						
					}
			
				});
	}
	
	/**
	 *  Initialize our frame and display
	 *  */
	public static void initializeFrame()
	{
		frame = new JFrame();
		frame.setSize(700, 700);
		frame.setTitle("Checker Animation");
		frame.setLayout(new BorderLayout());
		
		// boards, UI and the logic
		boardUI = new BoardUI();
		boardLogic = new Board();

		
		
		// Buttons and their listener to start or pause animation
		JButton startButton = new JButton("START");
		JButton stopButton = new JButton("PAUSE");
		startButton.addActionListener(new
		         ActionListener()
		         {
		            public void actionPerformed(ActionEvent event)
		            {
		               timer.start();
		            }
		         });
		
		stopButton.addActionListener(new
	         ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	               timer.stop();
	            }
	         });
		animationController = new JPanel();
		animationController.add(startButton);
		animationController.add(stopButton);
				
		
		frame.add(boardUI.getBoard(), BorderLayout.CENTER);
		frame.add(animationController, BorderLayout.NORTH);
		frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
	/**
	 *  update our board with a move
	 *  @param move: Move
	 */
	public static void updateBoardUI(Move move)
	{
		if(move == null) {
		    timer.stop();
			String winner = boardLogic.getWinner();
			JOptionPane.showMessageDialog(frame, winner + " has won the game");
			return;
		}
		
		boardUI.movePiece(move.isKing(), move.getFromRow(), move.getFromColumn(), move.getToRow(), move.getToColumn());
		Piece stolenPiece = move.getStolenPiece();
		if(stolenPiece != null) boardUI.removePiece(stolenPiece.getRow(), stolenPiece.getColumn());
		
	}
	

}

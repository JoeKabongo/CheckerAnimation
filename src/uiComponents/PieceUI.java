package uiComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.Icon;

/**
 * Class representation of a piece
 */
public class PieceUI implements Icon {
	
	/**
	 * Constructor
	 * @param c: color of the piece
	 * @param isKing: if the piece is a king
	 */
	public PieceUI(Color c, boolean isKing)
	{
		this.color = c;
		this.isKing = isKing;
	}
	
	/**
	 * Draw the icon
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) 
	{
		Graphics2D g2 = (Graphics2D) g;
		
		Ellipse2D.Double ellipse;
		
		// change the size of the piece depending if it is a king or not
		if(isKing) ellipse = new Ellipse2D.Double(x-30, y-30, 60, 60);
		else ellipse = new Ellipse2D.Double(x-20, y-20, 40, 40);
		
		g2.setColor(color);
		g2.fill(ellipse);
	}
	
	/**
	 * get the width of the piece
	 */
	@Override
	public int getIconWidth() 
	{
		// TODO Auto-generated method stub
		return width;
	}
	/**
	 * get the height of the icon
	 */
	@Override
	public int getIconHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}
	
	/**
	 * 
	 * @return the color of the piece
	 */
	public Color getColor()
	{
		return color;
	}
	
	private Color color;
	private boolean isKing;
	private int width;
	private int height;
}

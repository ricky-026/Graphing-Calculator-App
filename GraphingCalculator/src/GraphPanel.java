import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;

/**
 * 
 * @author Hamzah Zia
 * 
 * Description: This class is an extension of JPanel, and is mainly responsible for all
 * 		graphics on the mainframe, including: the grid, the plotted points, x and y axis
 * 		increments along x and y. 
 *
 */
public class GraphPanel extends JPanel{
	
	private int changedX = 0; // This variable determines how much to move when moving horizontally about the grid

	private int changedY = 0; // This variable determines how much to move when moving vertically about the grid
	
	private int zoom = 10; // How much zoomed into the grid we are, determines max X,
							// max Y, and how many pixels take up an x value of 1.
	
	ArrayList<Point> points = new ArrayList<Point>(); // ArrayList of the points plotted by a function

	/**
	 * 
	 * @author Hamzah Zia
	 * 
	 * Description: This is a constructor.
	 */
	public GraphPanel (){
		super();
	}
	
	
	/**
	 * 
	 * @author Hamzah Zia
	 * 
	 * Description: This paintComponent method is used to draw the axises, grids, and graphs. 
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		setBackground(Color.white);
		g.setColor(Color.black);
		Graphics2D g2 = (Graphics2D) g; // Allows us to set thickness of lines
        
		// Axis
		g2.setStroke(new BasicStroke(4)); // thickens lines when drawing axises
        g2.draw(new Line2D.Float(450 + changedX, 10000, 450+ changedX, -10000));
        g2.draw(new Line2D.Float(10000, 450 + changedY, -10000, 450 + changedY));
        
        // Grid Lines
        g2.setStroke(new BasicStroke(1)); // Makes lines thin to draw grid lines
        
        for (int x = -9000; x <= 9000 ; x+=45){
        	g.drawLine(x + changedX, -10000, x + changedX, 10000);
        }
        
        for (int y = -9000; y <= 9000 ; y+=45){
        	g.drawLine(-10000, y + changedY, 10000, y + changedY);
        }	
        
        // Numbers on grid
        g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        
        for (int z = -100; z <= 100; z++){
        	g2.drawString(String.valueOf((z*zoom/10)), (z * 45) + 440 + changedX, 470 + changedY);
        	if (z !=0)
        		g2.drawString(String.valueOf(z*zoom/-10), 430 + changedX, 460 + (z * 45) + changedY);
        }
        
        // Graphs points
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.red);
        
        points = CalculatorPanel.points;
        //System.out.println(points);
        for (Point p: points){
        	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Allows us to draw lines using subpixels (decimal values for pixels)
        	g2.draw(new Line2D.Double(p.getX()*(450 / zoom) + 450 + changedX, p.getY()*(-450 / zoom) + 450 + changedY,
        			p.getX()*(450 / zoom) + 450 + changedX, p.getY()*(-450 / zoom) + 450 + changedY));
        }
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * @return int
	 * 
	 * gets changedX
	 */
	public int getChangedX() {
		return changedX;
	}
	
	/**
	 * @author Hamzah Zia
	 * 
	 * sets changedX
	 */

	public void setChangedX(int changedX) {
		this.changedX = changedX;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * @return int
	 * 
	 * gets changedY
	 */
	public int getChangedY() {
		return changedY;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * sets changedY
	 */
	public void setChangedY(int changedY) {
		this.changedY = changedY;
	}
	
	/**
	 * @author Hamzah Zia
	 * 
	 * @return int
	 * 
	 * gets zoom
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * sets zoom, only 2 zooms available, 100 and 10
	 */
	public void setZoom(int zoom) {
		if (zoom <= 10)
			this.zoom = 10;
		else if (zoom >= 100)
			this.zoom = 100;
		else 
			this.zoom = zoom;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * @return ArrayList<Point>
	 * 
	 * gets points
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * sets points
	 */
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	/**
	 * @author Ricky Yue
	 * 
	 * Description: Clears out the arrayList of points
	 **/
	
	public void clearPoints() {
		points.clear();
	}
}
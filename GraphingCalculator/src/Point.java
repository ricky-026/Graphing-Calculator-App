/**
 * 
 * @author Hamzah Zia
 * 
 * Description: This point class defines the characteristics of each point object.
 * 		Point object stores an x coordinate and a respective y coordinate, later used for
 * 		plotting points.
 *
 */
public class Point {
	
	private double x;

	private double y;
	

	/**
	 * Hamzah Zia
	 * 
	 * @param xCoordinate
	 * @param yCoordinate
	 * 
	 * Description: Construction for Point
	 */
	public Point(double xCoordinate, double yCoordinate) {
		setX(xCoordinate);
		setY(yCoordinate);
	}
	
	
	/**
	 * @author Hamzah Zia
	 * 
	 * @return double
	 * 
	 * gets X-value
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @author Hamzah Zia
	 * 
	 * sets X-Value
	 */

	public void setX(double xCoordinate) {
		this.x = xCoordinate;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * @return
	 * 
	 * gets Y-Value
	 */
	public double getY() {
		return y;
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * sets Y Value
	 */
	public void setY(double yCoordinate) {
		this.y = yCoordinate;
	}
	
	/**
	 * @author Hamzah Zia
	 * 
	 * @return String
	 * 
	 * Description: formats the x and y coordinates of a point
	 */
	public String toString(){
		super.toString();
		return String.format("%f\t%f", getX(), getY());
	}
}
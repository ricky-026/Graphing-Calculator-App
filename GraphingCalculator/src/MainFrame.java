import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * 
 * @author Hamzah Zia
 * 
 * Description: This class is created to contain the graph panel. It includes a menu bar
 * for calculations related to the graph. It contains buttons for user to navigate through 
 * the grids. Most importantly, it contains zoom in and out buttons that allow users to zoom into
 * and out from the graph.
 */
public class MainFrame extends JFrame implements ActionListener{

	JMenuBar menubar = new JMenuBar ();
	JMenu menu = new JMenu("Calculations");
	JMenuItem xInt = new JMenuItem("x-intercepts");
	JMenuItem yInt = new JMenuItem("y-intercepts");
	JMenuItem derivative = new JMenuItem("Derivative");
	JMenuItem integral = new JMenuItem("Integral");

	JButton result = new JButton("");
	JTextField derInput = new JTextField("X-Value?");
	
	JButton up = new JButton("^");
	JButton down = new JButton("v");
	JButton right = new JButton(">");
	JButton left = new JButton("<");
	JButton defaultz = new JButton("D");
	
	JButton zoomIn = new JButton("+");
	JButton zoomOut = new JButton("-");
	JButton clear = new JButton ("Clear");
	
	final int MAXX = 917, MAXY = 939;

	GraphPanel gPanel; // creates panel for grid and graph
	CalculatorFrame cFrame; // creates calculator object to input functions

	/**
	 * 
	 * @author Hamzah Zia (This constructor method is mainly coded by Hamzah. However, Ricky 
	 * initialed the menu related components.)
	 * 
	 * Description: Initializes Mainframe, and initializes all the buttons and other items
	 * at various locations.
	 */
	public MainFrame () {

		setTitle ("Graph Frame");
		setSize (MAXX, MAXY);
		setVisible (true);


		setJMenuBar(menubar);
		menubar.add(menu);
		menu.add(xInt);
		xInt.addActionListener(this);
		menu.add(yInt);
		yInt.addActionListener(this);
		menu.add(derivative);
		derivative.addActionListener(this);
		menu.add(integral);
		integral.addActionListener(this);


		gPanel = new GraphPanel ();
		gPanel.setLayout(null);


		add(gPanel);

		gPanel.add(result);
		result.setBounds(10,30, 150, 30);
		result.addActionListener(this);
		result.setVisible(false);
		
		gPanel.add(derInput);
		derInput.setBounds(10, 65, 80, 30);
		derInput.addActionListener(this);
		derInput.setVisible(false);

		gPanel.add(up);
		up.setBounds(800, 20, 45, 45);
		up.addActionListener(this);

		gPanel.add(down);
		down.setBounds(800, 110, 45, 45);
		down.addActionListener(this);

		gPanel.add(right);
		right.setBounds(845, 65, 45, 45);
		right.addActionListener(this);

		gPanel.add(left);
		left.setBounds(755, 65, 45, 45);
		left.addActionListener(this);

		gPanel.add(defaultz);
		defaultz.setBounds(800, 65, 45, 45);
		defaultz.addActionListener(this);
		
		gPanel.add(zoomIn);
		zoomIn.setBounds(800, 170, 45, 45);
		zoomIn.addActionListener(this);
		
		gPanel.add(zoomOut);
		zoomOut.setBounds(800, 216, 45, 45);
		zoomOut.addActionListener(this);
		
		gPanel.add(clear);
		clear.setBounds(650, 35, 80, 30);
		clear.addActionListener(this);
		
		gPanel.setVisible(true);
		cFrame = new CalculatorFrame ();
		

	}
	
	/**
	 * 
	 * @author Hamzah Zia
	 * 
	 * Description: This method determines what is the source of an action. E.g Which button 
	 * is clicked? According to each action, a new action is invoked.
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		int changeIncrement = 45; // Determines how much to change each time a button is pressed to navigate the grid
									// set to 45 because Panel works out to have 45 pixels represent space between 2 grid lines

		if (e.getSource() == up)
			gPanel.setChangedY(gPanel.getChangedY() + changeIncrement);

		else if (e.getSource() == down)
			gPanel.setChangedY(gPanel.getChangedY() - changeIncrement);

		else if (e.getSource() == right)
			gPanel.setChangedX(gPanel.getChangedX() - changeIncrement);

		else if (e.getSource() == left)
			gPanel.setChangedX(gPanel.getChangedX() + changeIncrement);

		else if (e.getSource() == defaultz){ // Resets origin of grid back to centre of panel
			gPanel.setChangedX(0);
			gPanel.setChangedY(0);
		}
		
		else if (e.getSource() == zoomIn){
			gPanel.setZoom(gPanel.getZoom()/10);
			
		}
		
		else if (e.getSource() == zoomOut){
			gPanel.setZoom(gPanel.getZoom()*10);
		}

		cFrame.cPanel.setPoints(gPanel.getZoom(), gPanel.getX()); // each time the grid is navigated or zoomed, program replots points
														// to ensure all points are current screen are being plotted
		
		if (e.getSource() == clear){ // removes graphs, and resets function
			gPanel.clearPoints();
			gPanel.setZoom(100);
			gPanel.setChangedX(0);
			gPanel.setChangedY(0);
			cFrame.cPanel.function.clear();
			cFrame.cPanel.output.clear();
			result.setVisible(false);
			derInput.setVisible(false);
		}
		
		if (e.getSource() == yInt){ // calculates function at 0
			result.setText(String.valueOf(cFrame.cPanel.reversePolish(0)));
			result.setVisible(true);
		}
		
		else if (e.getSource() == derivative){ // Gives a prompt to enter an x value to calculate at
			derInput.setVisible(true);
			result.setVisible(false);
		}
		
		else if (e.getSource() == derInput){
			derInput.setVisible(false);
			result.setText(String.format("%.2f",
					calcDerivative(Double.parseDouble(derInput.getText()))));
			derInput.setText("X-value?");
			result.setVisible(true);
		}
		
		if (e.getSource() == xInt || e.getSource() == integral){ // Intergral and x int were not able to be implemented as of yet
			result.setText("Coming Soon :)");
			result.setVisible(true);
		}
		
		else if (e.getSource() == result) // click on the calculation to remove from screen
			result.setVisible(false);
		
		gPanel.repaint();

	}

	/**
	 * 
	 * @param x		
	 * @return double 
	 * 
	 * Description: Takes an x value as a parameter, apporximated derivative at that value
	 * 				and returns a double rounded to 2 decimal places.
	 */
	private double calcDerivative(double x) {
		Point [] secant = new Point[2]; // Finds secant between point, and a point very close to desired point to approximate derivative
		secant[0] = new Point(x, cFrame.cPanel.reversePolish(x));
		secant[1] = new Point(x + 0.0001, cFrame.cPanel.reversePolish(x + 0.0001));
		double val = (secant[1].getY() - secant[0].getY()) / 0.0001;
		return val;
	}

}
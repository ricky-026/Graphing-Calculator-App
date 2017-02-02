import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * 
 * @author Ricky Yue
 * 
 * Description: This class is a frame designed to contain the calculator panel.
 *  
 */
public class CalculatorFrame extends JFrame{
	
	CalculatorPanel cPanel;
	
	/**
	 * @Ricky Yue
	 * 
	 * Description: This is a constructor for the Calculator Frame. It initializes the
	 * dimensions of the Calculator Frame.
	 * 
	 */
	public CalculatorFrame(){
		
		
		setTitle ("Calculator Frame");
		setSize (340,500);
		
		setVisible (true);
		cPanel = new CalculatorPanel();
		add (cPanel);
	
	}
		
}
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;
import java.math.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Ricky Yue
 * 
 * Description: This class is mainly coded by Ricky. However, Hamzah contributed in 
 * coding a few methods. This class is panel that creates a calculator user interface. 
 * All the buttons on a calculator are defined on this class. It includes other calculation 
 * processing methods as well.
 *
 */
public class CalculatorPanel extends JPanel implements ActionListener {

	ArrayList function = new ArrayList<String>();
	String functionDisplay = "";
	JLabel functionD = new JLabel ("");
	int spacing = 0;

	JButton trig [] = new JButton [3];
	JButton number [] = new JButton [10];
	JButton operator [] = new JButton [4];
	JButton exponent [] = new JButton [2];

	JButton bracket [] = new JButton [2];
	JButton negative = new JButton("(-)");

	JButton enter  = new JButton ("Ent");
	JButton clear  = new JButton ("A/C");

	JButton equal = new JButton ("=");
	JButton x = new JButton ("X");
	JButton y = new JButton ("Y");
	JButton dot = new JButton (".");

	TreeMap <String, Integer> precedance  = new TreeMap<String, Integer>();
	Stack <String> stack = new Stack <String>();
	ArrayList <String> output = new ArrayList <String>();
	public static ArrayList<Point> points = new ArrayList<Point>();
	private Stack<String> stack2 = new Stack <String>();

	/** 
	 * 
	 * @author Ricky Yue
	 * 
	 * Description: This is a constructor method that sets the locations of each button on 
	 * the  calculator frame. All the buttons were added actionlistener in this method. The treemap
	 * return value of the operators were defined as well
	 */
	public CalculatorPanel(){

		precedance.put("(", 0);
		precedance.put("+", 2);
		precedance.put("-", 2);
		precedance.put("*", 3);
		precedance.put("/", 3);
		precedance.put("^", 4);
		precedance.put("sqrt", 4);
		precedance.put("sin", 5);
		precedance.put("cos", 5);
		precedance.put("tan", 5);

		setLayout (null);
		setSize (400, 600);
		setVisible (true);

		trig [0] = new JButton ("sin");
		trig [1] = new JButton ("cos");
		trig [2] = new JButton ("tan");

		for (int a = 0; a < 3; a ++){
			int space = 15;
			trig[a].setBounds( (a+1) *space + a * 67, 105, 70, 30);
			trig[a].addActionListener(this);			
			add (trig[a]);
		}

		// number pad
		int counter = 1;
		for (int x = 9; x >= 0; x --){

			number [x] = new JButton (String.valueOf(x));
			number[x].addActionListener(this);
			add(number [x]);

			if (x%3 == 1){
				number [x].setBounds(15, 200 + counter * 50, 60, 40);
				counter ++;
			}

			if (x%3 == 2)
				number [x].setBounds(90, 200 + counter * 50, 60, 40);

			if (x ==0){
				number[0].setBounds (15, 400, 135, 40);
			}
			else if (x%3 == 0)
				number [x].setBounds(165, 200 + counter * 50, 60, 40);

		}

		dot.setBounds(165, 400, 60 ,40);
		dot.addActionListener(this);
		add(dot);

		//operators
		operator[0] = new JButton("/");
		operator[1] = new JButton("*");
		operator[2] = new JButton("-");
		operator[3] = new JButton("+");

		for (int a = 0; a < 4; a ++){

			if (a==3)				
				operator[a].setBounds( 240, 250, 60, 45) ; 
			else 
				operator[a].setBounds( 90 + a *75, 200, 60, 40) ; 

			operator[a].addActionListener(this);
			add (operator[a]);
		}

		add (negative);
		negative.setBounds(240, 300, 60, 45);
		negative.addActionListener(this);

		clear.setBounds(240, 400, 60, 40);
		clear.addActionListener(this);
		add(clear);

		equal.setBounds(15, 200, 60, 40);
		equal.addActionListener(this);
		add(equal);

		enter.setBounds(240, 350, 60, 40);
		enter.addActionListener(this);
		add(enter);


		//Exponents

		exponent[0]= new JButton ("^");
		exponent[1]= new JButton ("sqrt");

		for (int a = 0; a < 2; a ++){
			exponent[a].setBounds(15 + a * 75,145, 60, 30 );
			exponent[a].addActionListener(this);
			add(exponent[a]);
		}

		//brackets
		bracket[0] = new JButton ("(");
		bracket[1] = new JButton (")");

		for (int a = 0; a < 2; a ++){
			bracket[a].setBounds(165+ a * 45, 145, 40, 30 );
			bracket[a].addActionListener(this);
			add(bracket[a]);
		}

		x.setBounds(255,105, 45 ,30);
		x.addActionListener(this);
		add(x);
		y.setBounds(255,145, 45 ,30);
		y.addActionListener(this);
		add(y);

		add(functionD);
		repaint();

	}


	//spacing = spacing + 15;
	//repaint();
	//revalidate();]



	/**
	 * 
	 * @author Ricky Yue
	 * 
	 * Description: This method checks which action is being invoked. E.g Which button is being 
	 * clicked. A new actions is then, triggered in result
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		for (int p = 0; p < trig.length; p ++){

			if(e.getSource() == trig[p]){
				display(trig[p]);			}

		}

		for (int p = 0; p < number.length; p ++){

			if(e.getSource() == number[p]){
				display(number[p]);
			}
		}

		for (int a = 0; a < operator.length; a ++){

			if (e.getSource() == operator [a]){
				display(operator [a]);
			}

		}

		if (e.getSource() == negative){
			display(negative);
		}

		if (e.getSource() == dot){
			display(dot);
		}

		if (e.getSource() == enter){
			//System.out.println(function);
			shuntingYard();
			setPoints(10, 0);

		}

		if (e.getSource() == equal){
			display(equal);
		}


		if (e.getSource() == clear){
			function.clear();
			functionDisplay = "";
			functionD.setText("");
			spacing = 0;
			output.clear();
			stack.clear();
		}


		if (e.getSource() == x){
			display(x);
		}

		if (e.getSource() == y){
			display(y);
		}

		for (int a = 0; a < exponent.length ; a ++){
			if (e.getSource() == exponent[a]){
				display(exponent[a]);
			}
		}

		for (int a = 0; a < bracket.length; a ++){
			if (e.getSource() == bracket[a]){
				display(bracket[a]);
			}
		}
	}

	
	/**
	 * @author Hamzah Zia
	 * @param zoom
	 * @param changedX
	 * 
	 * Description: This method adds more coordinates of points to be graphed into 
	 * the existing arrayList of points. It also modifies the coordinates of points 
	 * when the graph's scale is adjusted (zoom in an out) 
	 */
	public void setPoints(int zoom, int changedX) {
		double count = (-10 * zoom) + changedX;
		while (count <= (10 * zoom) + changedX){
			points.add(new Point(count, reversePolish(count)));
			count += 0.0001 * zoom;
		}
		//System.out.println(points);
	}

	/**
	 * @author Hamzah Zia
	 * 
	 * @return an arrayList of Points 
	 * 
	 * Description: This method returns an arrayList of points
	 * to be plotted or graphed on the graph panel interface
	 */
	static ArrayList<Point> getPoints() {
		return points;
	}


	/**
	 * @author Ricky yue 
	 * 
	 * @param button
	 * 
	 * Description: This method has 2 functions. First, it checks if the previous item and the current one are numbers.
	 * If there are numbers, chances are the user inputed a 2 or more digit number. Therefore the method will store
	 * the multi-digit number as one item in the arrayList. Second, it adds the operators or operands to the 
	 * arrayList, storing the equation (function). 
	 **/
	public void display (JButton button){	

		if (function.size() <= 1){
			function.add(button.getText());
		}

		else {
			String prev = (String) function.get(function.size() - 1);

			if (isInteger(button.getText()) && isInteger(prev))
				function.set(function.size() - 1, prev + button.getText());

			else if (button.getText().equals("(-)")){
				function.add("-1");
				function.add("*");
			}

			else {
				function.add(button.getText());
			}
		}

		functionDisplay = functionDisplay + button.getText();
		spacing = spacing + 15;
		functionD.setText(functionDisplay);
		functionD.setFont(new Font ("Calibri", Font.BOLD, 20));
		functionD.setBounds(280 - spacing, 60, 200, 30);
	}

	
	/**
	 * @author Hamzah Zia
	 * @param element
	 * @return true or false;
	 * 
	 * Description: This method checks if an element of the arrayList is an integer
	 * 
	 */
	private boolean isInteger(String element) { // When inputting the function, this method will help ensure decimal numbers and greater than 1 digit numbers
		for (int x = -9; x <=9; x++){                 // are stored as a single element
			if (element.equals(String.valueOf(x)))
				return true;
		}

		if (element.equals("."))
			return true;

		return false;
	}


	/**
	 * 
	 * @author Ricky Yue (assisted by Hamzah Zia)
	 * 
	 * Description: This method converts the equation inputed by the author to a Shunting-Yard Notation, 
	 * which is used later for calculation purposes using the method of Reverse Polish Algorithm
	 */
	public void shuntingYard(){

		for (int c = 2; c < function.size(); c++){ // c begins at 2 because the first 2 elements in the function are "Y ="
			String token = (String) function.get(c);

			if (token.equals("(")){ // Shunting Yard has special case for left parenthesis to be pushed onto stack no matter what
				stack.push(token);
			}

			else if (isOperator(token)){
				while(!stack.empty()){
					int sValue = precedance.get(stack.peek()); // this line checks the tree map for the precedance of the given operation
					int value = precedance.get(token);

					if (value <= sValue && !(token.equals("^") && stack.peek().equals("^"))) // because the power operation is right hand assosiative, it has a special case
						output.add(stack.pop());

					else
						break;
				}
				stack.push(token);
			}


			else if (token.equals(")")){ // Algorithm should clear pop everything from stack until left parenthesis is found, when right parenthesis is token
				while (stack.peek() != "(")
					output.add(stack.pop());
				stack.pop();
			}

			else output.add(token);
			//System.out.println(output);
			//System.out.println(stack);
		}

		while (!stack.empty()) // When input array is empty, clear everything in stack to output array
			output.add(stack.pop());

		//System.out.println(output);
	}

	/**
	 * @author Ricky Yue
	 * @param count
	 * @return double
	 * 
	 * Description: This method uses a certain type of logic called, Reverse Polish Alogrithm to calculate 
	 * the Shunting Yard Notation. It returns a double type result.
	 */
	public double reversePolish(double count){
		stack2.clear();
		for (int a = 0; a < output.size(); a ++){ 
			String temp = output.get(a);
			if (output.get(a) == "X"){
				output.set(a, String.valueOf(count));
			}

			if (!isOperator(output.get(a))){
				stack2.push(output.get(a));
				//System.out.println(stack2.peek());
				//System.out.println(stack2);
			}

			else if (output.get(a) == "+"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double v2 = (double) Double.parseDouble(stack2.pop());
				double result = v1 + v2;

				stack2.push(String.valueOf(result));
				//System.out.println(stack2.peek());
				//System.out.println(stack2);
			}

			else if (output.get(a) == "-"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double v2 = (double) Double.parseDouble(stack2.pop());
				double result = v2 - v1;

				stack2.push(String.valueOf(result));
				//System.out.println(stack2.peek());
				//System.out.println(stack2);
			}

			else if (output.get(a) == "*"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double v2 = (double) Double.parseDouble(stack2.pop());
				double result = v1 * v2;

				stack2.push(String.valueOf(result));
				//System.out.println(stack2.peek());
				//System.out.println(stack2);
			}

			else if (output.get(a) == "/"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double v2 = (double) Double.parseDouble(stack2.pop());
				double result = v2 / v1;

				stack2.push(String.valueOf(result));
				//System.out.println(stack2.peek());
				//System.out.println(stack2);
			}

			else if (output.get(a) == "sin"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double result = Math.sin(v1);

				stack2.push(String.valueOf(result)); 
			}

			else if (output.get(a) == "cos"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double result = Math.cos(v1);

				stack2.push(String.valueOf(result)); 
			}

			else if (output.get(a) == "tan"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double result = Math.tan(v1);

				stack2.push(String.valueOf(result)); 
			}

			else if (output.get(a) == "sqrt"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double result = Math.sqrt(v1);

				stack2.push(String.valueOf(result)); 
			}

			else if (output.get(a) == "^"){
				double v1 = (double) Double.parseDouble(stack2.pop());
				double v2 = (double) Double.parseDouble(stack2.pop());

				stack2.push(String.valueOf(Math.pow(v2, v1)));
			}

			output.set(a, temp);

		}

		//System.out.print(stack2.peek());
		//System.out.println(stack2);
		return Double.parseDouble(stack2.pop());

	}

	/**
	 * 
	 * @author Hamzah Zia
	 * @param token
	 * @return true or false
	 * 
	 * Description: This method checks if an element of the function (an arrayList) is an operator. 
	 */
	private boolean isOperator(String token) { // used in shunting yard algorithm to check if a given token is one of the operations
		return precedance.containsKey(token);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(76, 153, 0));
		g.fillRect(10,10,300,80); 
	}

}
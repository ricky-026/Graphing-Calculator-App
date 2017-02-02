import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;


public class Sandbox {

	static ArrayList function = new ArrayList<String>();
	static TreeMap precedance  = new TreeMap<String, Integer>();
	static Stack stack = new Stack <String>();
	static ArrayList output = new ArrayList <String>();

	public static void main(String[] args) {
		function.add("Y");
		function.add("=");
		function.add("3");
		function.add("+");
		function.add("4");
		function.add("*");
		function.add("2");
		function.add("/");
		function.add("(");
		function.add("1");
		function.add("-");
		function.add("5");
		function.add(")");
		function.add("^");
		function.add("2");
		function.add("^");
		function.add("3");

		
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

		for (int c = 2; c < function.size(); c++){
			String token = (String) function.get(c);
	
			if (token.equals("(")){
				stack.push(token);
			}
			
			else if (isOperator(token)){
				while(!stack.empty()){
					int sValue = Integer.parseInt(String.valueOf(precedance.get(stack.peek())));
					int value = Integer.parseInt(String.valueOf(precedance.get(token)));
					
					if (value <= sValue && !(token.equals("^") && stack.peek().equals("^")))
						output.add(stack.pop());
					
					else
						break;
				}
				stack.push(token);
			}
			
			
			else if (token.equals(")")){
				while (stack.peek() != "(")
					output.add(stack.pop());
				stack.pop();
			}
			
			else output.add(token);
			System.out.println(output);
			System.out.println(stack);
		}
		
		while (!stack.empty())
			output.add(stack.pop());
		
		System.out.println(output);
	}

	private static boolean isOperator(String token) {
		return precedance.containsKey(token);
	}
}

package DataStructure;
import java.util.*;

public class Main {
	
	static int N;
	static Stack<String> stack = new Stack<>();
	static boolean bool = true;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		//한 줄 띄기 -> (개행문자)
		sc.nextLine();
		
		for(int i=0; i<N; i++) {
			String input_line = sc.nextLine();
			String[] parsed_input_array = input_line.split("");
			System.out.println(solution(parsed_input_array));
			stack.removeAllElements();
		}
		
	}
	
	static String solution(String[] input) {
			
		for(int j=0; j<input.length; j++) {
			String c = input[j];
			
			if(c.equals("(")) {
				stack.push(c);
				
			}else if(c.equals(")")) {
				
				if(stack.size()==0) {
					return "NO";
				}else {
					stack.pop();
				}
			}
		}
		
		if(stack.size() ==0) {
			return "YES";
		}else {
			return "NO";
		}
		
	}
}

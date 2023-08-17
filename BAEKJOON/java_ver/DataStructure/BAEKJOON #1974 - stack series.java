package DataStructure;
import java.util.*;
import java.io.*;

public class Main {
	
	static int N;
	static Stack<Integer> stack = new Stack<>();
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		N = sc.nextInt();
		
		int start = 0;
		
		for(int i=0; i<N; i++) {
			
			int value = sc.nextInt();
			
			if(start<value) {
				for(int j=start+1; j<=value; j++) {
					stack.push(j);
					sb.append("+").append("\n");
				}
				start= value;
			}
			
			if(stack.peek() == value) {
				stack.pop();
				sb.append("-").append("\n");
			}else {
				System.out.println("NO");
				return;
			}
		}
		
		sb.deleteCharAt(sb.length()-1);
		System.out.println(sb);
		
	}
	
	static void solution() {
		
	}
}

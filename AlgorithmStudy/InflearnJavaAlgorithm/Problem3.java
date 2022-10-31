import java.util.*;
import java.io.*;

//using String.split(); method
public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		solution(input);
		
	}
	
	
	public static void solution(String input) {
		
		int len =0;
		String answer = "";

		String[] inputArray = input.split(" ");
		for(int i=0; i<inputArray.length; i++) {
			
			
			if(len < inputArray[i].length()) {
				len = inputArray[i].length();
				answer = inputArray[i];
			}
				
			
			
		}
		System.out.print(answer);
		
	}
}

import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String inputLine = sc.nextLine();
		
		solution(inputLine);
	}
	
	public static void solution(String inputLine) {
		
		for(int i=0; i<inputLine.length(); i++) {
			
			char element = inputLine.charAt(i);
			String stringChar = Character.toString(element);
			
			
			if(stringChar == stringChar.toLowerCase()) {
				//만약 소문자면
//				System.out.println("소문자");
//				System.out.println(stringChar);
				System.out.print(stringChar.toUpperCase());
			}else {
				//만약 대문자면
//				System.out.println("대문자");
//				System.out.println(stringChar);
				System.out.print(stringChar.toLowerCase());
			}
			

		}
		
	}

}

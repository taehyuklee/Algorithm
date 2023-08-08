import java.util.*;
import java.io.*;


public class Main {

	static int M, N;
	static String[][] board;
	
	
	static void print2D(String[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		M = sc.nextInt();
		N = sc.nextInt();
		sc.nextLine();
		
		board = new String[M][N];
		
		for(int i=0; i<M; i++) {
			
			String input_line = sc.nextLine();
			System.out.println(input_line);
			
			String[] string_line = input_line.split("");

			for(int j=0; j<N; j++) {
				System.out.println(string_line[j]);
				board[i][j] = string_line[j];
			}
		}
		
		print2D(board);
		
	}
}

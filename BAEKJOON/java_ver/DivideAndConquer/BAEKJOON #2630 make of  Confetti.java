import java.util.*;

public class Main {
	
	static int N, white_answer, blue_answer;
	static int[][] paper;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		paper = new int[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				paper[i][j] = sc.nextInt();
			}
		}
		
		divideAndConquer(N, paper);
		System.out.println(white_answer);
		System.out.println(blue_answer);
		
	}
	
	static void divideAndConquer(int size, int[][] board) {
		
		//종료조건 
		if(size==1) {
			if(board[0][0]==1) blue_answer++;
			else white_answer++;
			return;
		}
		
		int cnt_blue=0, cnt_white=0;
		for(int i=0; i<size; i++) { 
			for(int j=0; j<size; j++) {
				if(board[i][j]==1) cnt_blue++;
				else cnt_white++;
			}
		}
		
		if(cnt_blue == size*size) {
			blue_answer++;
			return;
		}else if(cnt_white == size*size) {
			white_answer++;
			return;
		}

		
		//분할 정복 
		int[][] new_board = new int[size/2][size/2];
		
		for(int i=0; i<size/2; i++) { //2사분면.
			for(int j=0; j<size/2; j++) {
				new_board[i][j] = board[i][j];
			}
		}
		divideAndConquer(size/2, new_board);
		
		for(int i=size/2; i<size; i++) {//3사분면.
			for(int j=0; j<size/2; j++) {
				new_board[i-size/2][j] = board[i][j];
			}
		}	
		divideAndConquer(size/2, new_board);
		
		for(int i=size/2; i<size; i++) {//4사분면.
			for(int j=size/2; j<size; j++) {
				new_board[i-size/2][j-size/2] = board[i][j];
			}
		}	
		divideAndConquer(size/2, new_board);
		
		for(int i=0; i<size/2; i++) {//1사분면.
			for(int j=size/2; j<size; j++) {
				new_board[i][j-size/2] = board[i][j];
			}
		}	
		divideAndConquer(size/2, new_board);
		
	}
	
}

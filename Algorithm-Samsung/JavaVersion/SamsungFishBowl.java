import java.util.*;
import java.io.*;

public class Main {
	
	static int N, K;
	static int[][] board;
	static int[] straight, straight2;
//	static List<List> listInt = new ArrayList<>();
	
	public static void print2D(int[][] array) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print1D(int[] array) {
		for(int j=0; j<N; j++) {
			System.out.print(array[j] + " ");
		}
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		K = sc.nextInt();
		
		board = new int[N][N];
		straight = new int[N];
		straight2 = new int[N];
		

		for(int j=0; j<N; j++) {
		
			straight[j] = sc.nextInt();
			straight2[j] = straight[j];
		}
		
		//print2D(board);
		
		//print1D(straight);
		
		solution();	
		
	}
	
	public static void solution() {
		
		//1. 물고기가 가장 적은 곳에 한마리씩 넣어주기
		putOne();
		
		//print1D(straight2);
		
		//2. board에 삽입하기
		insertBoard();
		
		//print2D(board);
		
		
		//3. 어항을 쌓는다 90도
		stack90();
		stack90();
		
	}
	
	public static void putOne() {
		
		//제일 작은수 뽑기
		Arrays.sort(straight2);
		int smallNum = straight2[0];
		
		//만약 가장 작은숫자라면 1씩 더해주기

		for(int j=0; j<N; j++) {
			if(straight[j] == smallNum) {
				straight[j] = straight[j]+1;
			}
			//여기서 straight2 복구해주자
			straight2[j] = straight[j];
		}
		
	}
	
	public static void insertBoard() {
		for(int j=0; j<N; j++) {
			board[N-1][j] = straight[j];
		}
	}
	
	public static void stack90() {
		
		//Index확인
		int fromInx = 0;
		int toInx = -1;
		int toRowInx = -1;
		
		for(int j=0; j<N; j++) {
			//맨 밑의 어항은 어디서부터 존재하는지? (어디서부터 공중부양해야하나?)
			if(board[N-1][j] !=0) {
				fromInx =j;
				break;
			}

		}
		
		for(int j=0; j<N; j++) {
			
			//윗 블록은 어디까지 존재하는지? (어디까지 공중부양시켜야 하나?)
			if(board[N-2][j] !=0) {
				toInx=j;
			}
		}
		
		
		//위에 쌓여 있는지에 대한 여부
		if(toInx != -1) {
			//위에 쌓여있다.
			
			//어디까지 쌓여 있는지?
			for(int i=N-1; i>=0; i--) {
				if(board[i][toInx] ==0) {
					toRowInx = i-1;
				}
			}
			
//			int[][] goUp = new int[(N-1)-(toRowInx-1)][toInx-fromInx+1];
//			
//			for(int i=(N-1)-(toRowInx-1); i>=0; i--) {
//				for(int j=0; j<toInx-fromInx+1 ; j++) {
//					goUp[i][j] = board[N-1-(i+(N-1)-(toRowInx-1))][fromInx+j];
//				}
//			}
//			
			
			rotate90();					
			
		}else {
			//직렬
			int inter = board[N-1][0];
			board[N-1][0] = 0;
			board[N-2][1] = inter;
		}
		
		print2D(board);

	}
	
	public static void rotate90() {
		
		
	}
	
	
}

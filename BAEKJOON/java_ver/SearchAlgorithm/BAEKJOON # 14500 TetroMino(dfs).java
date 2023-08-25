import java.util.*;

public class Main {
	
	static int N, M, answer;
	static int[][] board;
	static boolean[][] visit;
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		board = new int[N][M];
		visit = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		
		int blockNum = 1;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				visit[i][j] = true;
				dfs(i,j,board[i][j], blockNum);
				visit[i][j] = false;
			}
		}
		System.out.println(answer);
		
		
	}
	
	static void dfs(int x, int y, int sum, int blockNum) {
		
		//종료 조건 
		if(blockNum==4) {
			if(answer<=sum) {
				answer = sum;
			}
			return;
		}
		
		for(int i=0; i<4; i++) {
			
			int new_x = x + dx[i];
			int new_y = y + dy[i];
			
			if(new_x<0 || new_x>=N || new_y<0 || new_y>=M) continue;
			if(visit[new_x][new_y]== true) continue;
			
			if(blockNum==2) {
				visit[new_x][new_y] = true;
				dfs(x,y, sum+board[new_x][new_y], blockNum+1);
				visit[new_x][new_y] = false;
			}
			
			visit[new_x][new_y] = true;
			dfs(new_x, new_y, sum+board[new_x][new_y], blockNum+1);
			visit[new_x][new_y] = false;
		}
		
	}
}

import java.util.*;


class Node{
	int x, y, cnt;
	
	public Node(int x, int y, int cnt) {
		this.x = x;
		this.y = y;
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y;
	}
}

public class Main {
	
	private static int N, M;
	private static int[][] board, status, board2, status2;
	private static boolean[][] visit;
	static List<Node> virusList = new ArrayList<>();
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1}; //상하좌우
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		board = new int[N][N]; board2 = new int[N][N];
		status = new int[N][N]; status2 = new int[N][N];
		
		//0: 빈 공간, 1: 벽, 2: 바이러스
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				if(board[i][j] == 2) {
					virusList.add(new Node(i,j,0));
					status[i][j] = 2;
					status2[i][j] = 2;
				}
				if(board[i][j]==1) {
					status[i][j] = -1;
					status2[i][j] = -1;
				}
			}
		}
		
		solution();
		
	}
	
	static void solution() {
		
	}
	
	static void bfs(int[] x_array, int[] y_array , int[] cnt_array) {
		
		Queue<int[]> Q = new LinkedList<>();
		
		//시작점을 모두 한 번에 넣어두고 시작하면 순차적으로 되겠네 Q구조상. 
		for(int i=0; i<x_array.length; i++) {
			Q.add(new int[] {x_array[i], y_array[i], cnt_array[i]});
		}
		
		while(!Q.isEmpty()) {
			
			int[] node = Q.poll();
			
			for(int i=0; i<4; i++) {
				int new_x = node[0] + dx[i];
				int new_y = node[1] + dy[i];
				
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) continue;
				if(status2[new_x][new_y]!=0) continue;
				
				
				
			}
			
		}
		
	}

}

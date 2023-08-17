package SearchAlgorithm;
import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	int cnt;
	
	public Node(int x, int y, int cnt) {
		this.x = x;
		this.y = y;
		this.cnt = cnt;
	}
}

public class Main {
	
	static int N, M;
	static int[][] board, distance;
	static boolean[][] visit;
	static int[] dx = {0,0,1,-1}; //동서남북 
	static int[] dy = {1,-1,0,0};
	
	static void print2D(int[][] arr) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				if(visit[i][j] == false && board[i][j]==1) {
					sb.append(-1).append(" ");
				}else {
					sb.append(arr[i][j]).append(" ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		distance = new int[N][M];
		visit = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//시작점 찾기 
		int start_x = 0;
		int start_y = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] == 2) {
					start_x = i;
					start_y = j;
				}
			}
		}
		
		//bfs 탐색하기.
		bfs(start_x, start_y);
		
		print2D(distance);
		
	}
	
	static int bfs(int x, int y) {
		
		Queue<Node> Q = new LinkedList<>();
		int cnt=0;
		Node node = new Node(x, y, cnt);
		
		Q.add(node);
		visit[x][y] = true;
		
		while(Q.size() !=0) {
			
			Node old_node= Q.poll();
			
			for(int dir=0; dir<4; dir++) {
				int new_x = old_node.x + dx[dir];
				int new_y = old_node.y + dy[dir];
				int new_cnt = old_node.cnt;
				
				if(new_x>=N || new_x<0 || new_y>=M || new_y<0) continue;
				if(board[new_x][new_y] ==0 || visit[new_x][new_y] ==true) continue; 
				
				new_cnt++;
				//종료 조건 
				if(board[new_x][new_y] == 2) {
					return new_cnt;
				}
				
				Node new_node = new Node(new_x, new_y, new_cnt);
				distance[new_x][new_y] = new_cnt;
				Q.add(new_node);
				visit[new_x][new_y] = true;
			}
			
		}
		return -1;
	}
	
}

/*
System.out.println()과, StringBuilder로 출력하는거 자체에서 속도차이가 2400ms -> 708ms로 줄어든다
*/

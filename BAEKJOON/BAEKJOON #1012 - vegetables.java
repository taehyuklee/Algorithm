import java.io.*;
import java.util.*;

class Node{
	int x;
	int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return " " + this.x + " " + this.y + " |";
	}
}

public class Main {
	
	static int T, M, N, K;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	static int[][] board;
	static boolean[][] visit;
	static int answer;
	
	static void print2D(int[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	static void print2D(boolean[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		
		//Input해서 자료구조로 넣어주는 부분.
		Scanner sc = new Scanner(System.in);
		
		T = Integer.parseInt(sc.next());

		for(int turn=0; turn<T; turn++) {
			
			makeBoard(sc);
			
			//print2D(board);
			
			for(int i=0; i<M; i++) {
				for(int j=0; j<N; j++) {
					
					int checkNum= board[i][j];
					if(checkNum ==1 && visit[i][j] ==false) {
						bfs(i, j);
						answer++;
						//break;
					}
				}
			}
			
			//print2D(visit);
			System.out.println(answer);
			answer=0;

		}
		
	}

	
	static void makeBoard(Scanner sc) {
		
		M = Integer.parseInt(sc.next());
		N = Integer.parseInt(sc.next());
		K = Integer.parseInt(sc.next());
		
		board = new int[M][N];
		visit = new boolean[M][N];
		
		for(int i=0; i<K; i++) {
			int x = Integer.parseInt(sc.next());
			int y = Integer.parseInt(sc.next());
			board[x][y] = 1;
		}
		
	}

	
	static void bfs(int x, int y) {
		
		Queue<Node> Q = new LinkedList<>();
		Node node = new Node(x,y);
		Q.add(node);
		visit[x][y] = true;

		while(Q.size()!=0) {
			
			Node new_node = Q.poll();
			
			for(int dir=0; dir<4; dir++) {
				int new_x = new_node.x + dx[dir];
				int new_y = new_node.y + dy[dir];
				
				if(new_x>=0 && new_x<M && new_y>=0 && new_y<N){
					if(board[new_x][new_y]==1 && visit[new_x][new_y] != true) {
						visit[new_x][new_y] = true;
						Q.add(new Node(new_x, new_y));
					}
				}
			}
		}
	}
	
}

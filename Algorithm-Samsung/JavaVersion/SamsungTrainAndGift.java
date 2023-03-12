import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "("+"x: " + this.x + " y: " + this.y + ")";
	}
}

public class Main {

	static int N, M, K;
	static int[][] board, board2;
	static boolean[][] visit;
	static Map<Integer, Map<Integer, Node>> mapArray = new HashMap<>();
	
 	public static void print(int[][] array) {	
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		board = new int[N][N];
		board2 = new int[N][N];
		visit = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				board[i][j] = sc.nextInt();
				board2[i][j] = board[i][j];
				
			}
		}

		solution();
		
	}
	
	public static void solution() {
		
		//기차 각 팀에서 연결해서 만들어주기.
		makeTrain();
		
		//기차 움직이기
		moveTrain();
		
		//공 날리기
		
		//기차 꼬리, 머리 바꾸기 
		
		
	}
	
	public static void makeTrain() {
		
		int team = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(board[i][j] ==1) {
					mapArray.put(team, new HashMap<Integer, Node>());
					bfs(i, j, team);
					team+=1;
				}
				
			}
		}
		System.out.println(mapArray);
	}
	
	public static void bfs(int x, int y, int team) {
		
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		int lenIndx = 1;
		
		Queue<Node> queue = new LinkedList<>();
		Node node0 = new Node(x, y);
		mapArray.get(team).put(lenIndx, node0);
		lenIndx +=1;
		
		visit[x][y] = true;
		System.out.println("x:  " + x + "  y:    " + y);
		
		queue.add(node0);
		
		while(!queue.isEmpty()) {
			
			Node node = queue.poll();
			
			for(int dir=0; dir<4; dir++) {
				int newX = node.x + dx[dir];
				int newY = node.y + dy[dir];
				
				if(newX>=0 && newX<N && newY>=0 && newY<N  && visit[newX][newY] ==false) {
					if(board[newX][newY] ==2 || board[newX][newY] ==3) {
						Node newNode = new Node(newX, newY);
						mapArray.get(team).put(lenIndx, newNode);
						queue.add(newNode);
						visit[newX][newY] = true;
						lenIndx+=1;
					}
				}
			}
		}
	}
	
	
	public static void moveTrain() {
		for(int team=1; team<=M; team++) {
			
			//그 팀의 기차 길이만큼
			for(int len=1; len<=mapArray.get(team).size(); len++) {
				mapArray.get(team);
			}
			
		}
	}
}

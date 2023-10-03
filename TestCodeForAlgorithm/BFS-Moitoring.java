//백준 문제 - 연구실3에서
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
		return "x: " + this.x + " y: " + this.y + " cnt: " + this.cnt + "  ";
	}
	
}

public class Main {
	
	private static int N, M, emptySpace;
	private static int[][] board, testBoard;
	private static boolean[][] visit;
	private static List<Node> virusList = new ArrayList<>();
	private static List<Integer> answerList = new ArrayList<>();
	private static List<List<Node>> combinationList = new ArrayList<>();
	private static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};  //상하좌우
	
	
	public static void unitTest() {

		//Unit Test for BFS
		List<Node> testList = new ArrayList<>();
		Node node1 = new Node(0,0,0);
		Node node2 = new Node(1,5,0);
		Node node3 = new Node(4,3,0);
		testList.add(node1); testList.add(node2); testList.add(node3);
		bfs(testList);
	
	}
	
	public static void main(String[] args) {
		
		//0 빈칸, 1 벽, 2 비활성 바이러스
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		board = new int[N][N];
		testBoard = new int[N][N]; //test용 debugging용
		visit = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				
				if(board[i][j]==2) {
					virusList.add(new Node(i, j, 0));
					testBoard[i][j] = -101; 

				}
				if(board[i][j]==1) {
					testBoard[i][j] = -1;
					visit[i][j] = true; 				
				}
				if(board[i][j]==0) {
					emptySpace++;
				}
			}
		}
		
		//솔루션 들어가기 전에 unit test할수 있는 
		unitTest();

	}
	
	static void bfs(List<Node> partOfComb) {
		
		
		int[][] realBoard = initTestBoard(new int[N][N]);
		boolean[][] realVisit = initVisit(new boolean[N][N]); //만약, visit을 그냥 false로 초기화 할거면 이게 필요 없지.
		int zeroSpace = emptySpace;
		
		Queue<Node> Q = new LinkedList<>();
		List<Node> tempList = new ArrayList<>();
		
		for(int i=0; i<partOfComb.size(); i++) {
			Node node = partOfComb.get(i);
			realVisit[node.x][node.y] = true;
			realBoard[node.x][node.y] = -100;
			Q.add(partOfComb.get(i));
		}
		
		while(!Q.isEmpty()) {
			
			Node node = Q.poll();
			
			if(zeroSpace==0) {
				tempList.add(node);
				continue;
				//continue;
			}

			for(int i=0; i<4; i++) {
				
				int new_x = node.x + dx[i];
				int new_y = node.y + dy[i];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) continue; //범위 밖을 넘어갈때
				if(board[new_x][new_y] ==1 ) continue; //벽일때 
				if(realVisit[new_x][new_y] == true) continue; //방문한 곳일때
				
				if(board[new_x][new_y]==0) zeroSpace--;
				realVisit[new_x][new_y] = true;
				Q.add(new Node(new_x, new_y, node.cnt+1));
				realBoard[new_x][new_y] = node.cnt+1;
				
			}
			
			printAll(realBoard, realVisit);
			
		}
		
		Collections.sort(tempList, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o2.cnt - o1.cnt;
			}
		});
		
		if(tempList.size()!=0) {
			answerList.add(tempList.get(0).cnt);
		}
		
	}

	static void printAll(int[][] realBoard, boolean[][] realVisit) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("[%5d, %5b]",realBoard[i][j], realVisit[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static boolean check() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(visit[i][j]==false) {
					return false;
				}
			}
		}
		return true;
	}
	
	static boolean[][] initVisit(boolean[][] newVisit) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newVisit[i][j] = visit[i][j];
			}
		}
		return newVisit;
	}
	
	static int[][] initTestBoard(int[][] newBoard) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newBoard[i][j] = testBoard[i][j];
			}
		}
		return newBoard;
	}
	
}

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
	
	private static int N, M;
	private static int[][] board, testBoard;
	private static boolean[][] visit, visit2;
	private static List<Node> virusList = new ArrayList<>();
	private static List<List<Node>> combinationList = new ArrayList<>();
	private static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};  //상하좌우
	
	
	public static void main(String[] args) {
		
		//0 빈칸, 1 벽, 2 비활성 바이러스
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		board = new int[N][N];
		testBoard = new int[N][N];
		visit = new boolean[N][N];
		visit2 = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				
				if(board[i][j]==2) {
					virusList.add(new Node(i, j, 0));
				}
				if(board[i][j]==1) {
					testBoard[i][j] = -1;
					visit[i][j] = true;
					visit2[i][j] = true;
				}
			}
		}
		
		solution();
		
	}
	
	static void solution() {
		
		//바이러스 전체 개수중 M개 선택 (조합)
		/********** combination unit test *********/
		//int[] array= {1,2,3,4};
		
		boolean[] visit = new boolean[virusList.size()];
		int start =0;
		int depth=0;
		combination(virusList, visit, start, depth, M);
		//System.out.println(combinationList.size());

		
		//BFS로 최소 시간초 계산
		for(int i=0; i<combinationList.size(); i++) {
		//	bfs(combinationList.get(i)); 
		}
		
		
		List<Node> testList = new ArrayList<>();
		Node node1 = new Node(0,0,0);
		Node node2 = new Node(1,5,0);
		Node node3 = new Node(4,3,0);
		testList.add(node1); testList.add(node2); testList.add(node3);
		bfs(testList);
		
		
	}
	
	static void bfs(List<Node> partOfComb) {
		
		Queue<Node> Q = new LinkedList<>();
		List<Node> temp = new ArrayList<>();

		
		for(int i=0; i<partOfComb.size(); i++) {
			Node node = partOfComb.get(i);
			visit[node.x][node.y] = true;
			visit2[node.x][node.y] = true;
			Q.add(partOfComb.get(i));
		}
		
		while(!Q.isEmpty()) {
			
			Node node = Q.poll();
			if(check()) {
				temp.add(node);
				//System.out.println(node.cnt);
			}

			for(int i=0; i<4; i++) {
				
				int new_x = node.x + dx[i];
				int new_y = node.y + dy[i];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) continue; //범위 밖을 넘어갈때
				if(board[new_x][new_y] ==1) continue; //벽일때 
				if(visit[new_x][new_y] == true) continue; //방문한 곳일때
				
				visit[new_x][new_y] = true;
				Q.add(new Node(new_x, new_y, node.cnt+1));
				testBoard[new_x][new_y] = node.cnt+1;
				
			}
			
			print2D();
			
		}
		
		System.out.println(temp);
		
		//visit 초기화
		initvisit();
		
	}
	
	static void combination(List<Node> listNode, boolean[] visit, int start, int depth, int r) {
		
		//종료 조건
		if(depth ==r) {
			List<Node> combinations = new ArrayList<>();
			for(int i=0; i<listNode.size(); i++) {
				if(visit[i]==true) combinations.add(listNode.get(i)); // System.out.print(listNode.get(i) + " ");
			}
			combinationList.add(combinations);
			return;
		}
		
		//탐색 수행
		for(int i = start; i<listNode.size(); i++) {
			if(!visit[i]) {
				visit[i] = true;
				//combination(array,visit,start+1,depth+1,r); // 실수 이렇게하면 i는 진행되지만, start 라인은 계속 한 곳에 머물러 있게 된다.
				combination(listNode, visit,i+1,depth+1,r);
				visit[i] = false;
			}
		} 
		
	}
	
	static void print2D() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%3d",testBoard[i][j]);
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
	
	static void initvisit() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				visit[i][j] = visit2[i][j];
			}
		}
	}
	
}

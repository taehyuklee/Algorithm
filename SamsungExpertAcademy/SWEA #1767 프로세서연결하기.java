import java.util.*;
import java.io.FileInputStream;

class Status{
	int core, length;
	
	public Status(int core, int length) {
		this.core = core;
		this.length = length;
	}
	
	@Override
	public String toString() {
		return "core: " + core + " length: " + length+"  ";
	}
}

class Node{
	int x, y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y + "  ";
	}
}

class Solution{
	
	private static int N, T, answerCore=0, answerLength = Integer.MAX_VALUE;
	private static int[][] map;
	private static boolean[][] visit;
	private static List<Node> restNodes;
	private static int[] dx = {-1,1,0,0}, dy= {0,0,-1,1};
	
	public static void printVisit() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%8b", visit[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String args[]) throws Exception{

		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample_input.txt"));

		Scanner sc = new Scanner(System.in);
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++){
			
			N = sc.nextInt();
			restNodes = new ArrayList<>();
			map = new int[N][N];
			visit = new boolean[N][N];
			Status status = new Status(0,0);
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					map[i][j] = sc.nextInt();
					
					if(map[i][j]==1) {
						
						visit[i][j] = true;	
						if(i==0 || i==N-1 || j==0 || j==N-1) {
							status.core++;
							continue;
						}else{
							restNodes.add(new Node(i,j));
						}
						
					}
				}
			}

			//printVisit();
			solution(restNodes, status);

			System.out.printf("#%d %d", test_case, answerLength);
			System.out.println();
			
			//초기화 
			answerLength = Integer.MAX_VALUE;
			answerCore=0;
		}
	}
	
	public static void solution(List<Node> restNodes, Status status) {
		
		Node node = restNodes.get(0);
		dfs(node, status, 0);

		
	}
	
	public static void dfs(Node node, Status status, int depth) {
		
		//System.out.println(status.core + " " + status.length);
		
		int tempLength = status.length;
		int tempCore = status.core;
		
		if(status.core>answerCore) { //status.core>=answerCore 이 조건이면 안됐었음.
			answerCore = status.core;
			answerLength=status.length;
			
//			if(status.length!=0  && answerCore>=status.core && answerLength>=status.length) {
//				answerLength=status.length;
//			}else {
//				answerLength=status.length;
//			}

		}else if(status.core == answerCore) {
			if(status.length!=0  && answerLength>status.length) {
				answerLength=status.length;
			}
		}
		
		//마침 조건
		if(depth>=restNodes.size()) {
			return;
		}
		
//		System.out.println("depth ---------------" +   depth);
		//do connection
		for(int dir=0; dir<4; dir++) {

			//길이 연장.
			for(int l =1; l<N; l++) {
				
				int new_x = node.x + dx[dir]*l;
				int new_y = node.y + dy[dir]*l;
				
				if(visit[new_x][new_y] == true) {
					if(l!=1) {
						for(int k=1; k<l; k++) {
	
							int erase_x = node.x + dx[dir]*k; // l이 아니라 k로 해야함 
							int erase_y = node.y + dy[dir]*k;

							visit[erase_x][erase_y] = false;
						}
					}
					break;
					
				}else if(new_x==0 || new_x==N-1 || new_y==0 || new_y==N-1){
					visit[new_x][new_y] = true;
					int new_depth = depth+1;
					//여기서 더해지니까 -> depth는4일때 return해야 하는게 맞네.
					status.length +=l;
					status.core++;
					
					//다음 node 마지막 node에서 탐색을 못했었네.. 이런 detail한 부분이 잘 안됨.
					Node new_node =null;
					if(new_depth<=restNodes.size()-1) {
						new_node = restNodes.get(new_depth);
					}
					//printVisit();
					dfs(new_node, status, new_depth);
					
					//System.out.println("erase l   " + l);
					
					for(int k=1; k<=l; k++) {

						int erase_x = node.x + dx[dir]*k; // l이 아니라 k로 해야함 
						int erase_y = node.y + dy[dir]*k;

						visit[erase_x][erase_y] = false;
					}
					
					status.length-=l;
					status.core--;

					
					break;
					
				}else {
					visit[new_x][new_y] = true;
				}
				
//				printVisit();
//				System.out.println(status);
//				System.out.println("\n\n");
				
				
			
			}

		}
		
		
		
	}
	
	public static void eraseTrace(Node node, int dir, int l) {
		System.out.println("startErase");
		
		//기존에 visit 했던건 지워줘야지 
		for(int k=1; k<l; k++) {

			int erase_x = node.x + dx[dir]*k; // l이 아니라 k로 해야함 
			int erase_y = node.y + dy[dir]*k;
			
			System.out.println(erase_x + "   " + erase_y);
			visit[erase_x][erase_y] = false;
		}
		
	}

}

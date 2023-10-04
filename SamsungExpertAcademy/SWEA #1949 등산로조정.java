import java.util.*;
import java.util.Scanner;
import java.io.FileInputStream;

class Node{
	int x, y, num, cnt;
	boolean drill;
	
	public Node(int x, int y, int num, int cnt, boolean drill) {
		this.x = x;
		this.y = y;
		this.num = num;
		this.cnt = cnt;
		this.drill = false;
	}
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y + " num: " + num + "  ";
	}
}


class Solution{
	
	
	private static int N, K, T, answer;
	private static int[][] map;
	private static boolean[][] visit;
	private static List<Node> startNode;
	private static int[] dx = {-1,1,0,0}, dy= {0,0,-1,1}; //상하좌
	private static boolean drillStatus = false;
	
	public static void main(String args[]) throws Exception	{
		
		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample_input.txt"));
		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++){

			N = sc.nextInt();
			K = sc.nextInt();
			
			map = new int[N][N];
			visit = new boolean[N][N];
			startNode = new ArrayList<>();
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					map[i][j] = sc.nextInt();
					startNode.add(new Node(i,j,map[i][j],1, false));
				}
			}
			
			
			startNode = pickBiggest(startNode);

			solution(startNode);
			
			System.out.printf("#%d %d", test_case, answer);
			System.out.println();
			answer=0;
			
			
		}
	}
	
	public static List<Node> pickBiggest(List<Node> nodes) {
		
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o2.num - o1.num;
			}
		});
		
		int oldOne = nodes.get(0).num;
		for(int i=1; i<nodes.size(); i++) {
			int newOne = nodes.get(i).num;
			if(newOne == oldOne) {
				continue;
			}else {
				nodes.remove(nodes.get(i));
				i--;
			}
		}
		return nodes;	
	}
	
	public static void solution(List<Node> startNodes) {
		
//		visit[2][4] = true;
//		dfs(new Node(2,4,9,1, false));
//		System.out.println(answer);
		
		for(int i=0; i<startNodes.size(); i++) {
			Node node = startNodes.get(i);
			visit[node.x][node.y] = true;
			dfs(node);
			visit[node.x][node.y] = false;
		}		
		
	}
	
	
	public static void dfs(Node node) {
		
		
		//종료조건 (BackTracking 조건)
		if(answer<= node.cnt) {
//			System.out.println(node.cnt);
			answer = node.cnt;
		}
		
//		System.out.println("drill status   " + drillStatus);
//		printMap();
//		printVisit();
		
		for(int dir=0; dir<4; dir++) {
			
			int new_x = node.x + dx[dir];
			int new_y = node.y + dy[dir];
			
			if(new_x<0 || new_y<0 || new_x>=N || new_y>=N || visit[new_x][new_y] ==true) continue;
			
			//깎을수 있는 조건.
			if(map[new_x][new_y]>= map[node.x][node.y]) {
				if(drillStatus == false) {
					for(int d=1; d<=K; d++) {
						map[new_x][new_y] -= d;
						
						//깎았을때도 이러면.
						if(map[new_x][new_y]>= map[node.x][node.y]) {
							map[new_x][new_y] += d;
							continue;
						}
						else {
							drillStatus = true;
							visit[new_x][new_y] = true;
							dfs(new Node(new_x, new_y, map[new_x][new_y], node.cnt+1, drillStatus));
							visit[new_x][new_y] = false;
							map[new_x][new_y] += d;
							drillStatus = false;
							
						}	
					}

				}else {
					continue;
				}
			}else {
				visit[new_x][new_y] = true;
				dfs(new Node(new_x, new_y, map[new_x][new_y], node.cnt+1, node.drill));	
				visit[new_x][new_y] = false;
//				printMap();
//				printVisit();
			}
		}
		
		
	}
	
	public static void printVisit() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%10b" , visit[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printMap() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%10d" , map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
		
}

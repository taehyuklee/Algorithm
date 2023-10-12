/*
 * N x N
 * 꼬리잡기 머리 사람, 꼬리 사
 * 
 * 게임 프로세
 * 1. 각 팀의 머리 사람을 따라서 한 칸 이동한다.
 * 
 * 2. 각 라운드마다 공이 던져진다. (Rotation되며 )
 * 
 * 
 * 3. 공이 던져지는 경우 ->최초에 만나게 되는 사람만이 공을 얻게 된다. 
 *  머리 사람을 기점으로 꼬리사람까지 인덱싱을 하는데, 만약 공을 k번째 사람이 받으면 k*k 점수를 얻게 ㅗ딘다. 
 *  
 * 4. 만약 공을 맞은 사람이 있다면, 그 팀의 머리사람과 꼬리 사람은 방향이 바뀌게 된다.
 * 
 * -> 특정 라운드 이후 각 팀이 획득한 점수의 총합을 구하시오.
 * 
 *  Input
 *  N, M: 팀의 개수, K:라운드 수 
 *  0 - 빈칸
 *  1 - 머리 사람
 *  2 - 중간 사람.
 *  3 - 꼬리 사람
 *  4 - 이동 선
 *  
 * */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class Node{
	int x, y, num;
	
	public Node(int x, int y, int num) {
		this.x = x;
		this.y =y;
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "[num: " + num + " x: " + x + " y: " + y + "]";
	}
	
	public Node(Node node) {
		this.x = node.x;
		this.y = node.y;
		this.num = node.num;
		
	}
}

public class Main {
	
	static int N, M, K, answer;
	static int[][] map;
	static List<Map<Integer, Node>> teamList;
	static int[] dx = {-1,1,0,0} , dy= {0,0,-1,1};
	
	public static void main(String[] args) throws FileNotFoundException {
				
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		map = new int[N][N];
		teamList = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = sc.nextInt();		
			}
		}

		solution();
		
	}
	
	static void print2D(int[][] map) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static void solution() {
		
		//기차 만들기.
		makeTrain();
		
		/******** 모든 팀에 대해서 계산***********/
		//각 팀의 이동 
		for(int turn=0; turn<K; turn++) {

			map = moveTrain(teamList);

			//공 던지기 및 맞은 사람 좌표 구하기.			
			throwBall(turn); 
		}
		System.out.println(answer);
		
	}
	
	static void throwBall(int turn) {
		
		int[][] monitoring = new int[N][N];
		int rotate=0;
		
		rotate = (rotate + (int)turn/N)%4;
		turn = (turn)%N;
		
		
		if(rotate ==0) {
			for(int j =0; j<N; j++) {
				if(map[turn][j] !=4 && map[turn][j] !=0) {
					int num = findTrains(turn,j);
					answer += num*num;
					return;
				}
			}			
			
		}else if(rotate ==1) {
			
			for(int i =N-1; i>=0; i--) {
				if(map[i][turn] !=4 && map[i][turn] !=0) {
					int num = findTrains(i,turn);
					answer += num*num;		
					return;
				}
			}			
			
			
		}else if(rotate ==2) {
	
			
			for(int j =N-1; j>=0; j--) {
				
				if(map[N-1 - turn][j] !=4 && map[N-1 - turn][j] !=0) {
					int num = findTrains(N-1 - turn,j);
					answer += num*num;
					return;
					
					
				}
			}	
			
			
		}else if(rotate ==3) {
					
			for(int i =0; i<=N-1; i++) {

				if(map[i][N-1 - turn] !=4 && map[i][N-1 - turn] !=0) {
					int num = findTrains(i ,N-1 - turn);
					answer += num*num;
					return;
					
				}
			}	
		}	
		
	}
	
	static int findTrains(int target_x, int target_y) {
		for(int i=0; i<teamList.size(); i++) {
			Map<Integer, Node> team = teamList.get(i);
			for(int t =1; t<=team.size(); t++) {
				Node node = team.get(t);
				if(target_x == node.x && target_y == node.y) {
					int index = node.num;
					
					//머리 사람 꼬리 사람 위치 바꾸기.
					changeDir(team);
					
					return index;
				}
			}
		}
		return 0;
	}
	
	static void changeDir(Map<Integer, Node> team) {
		 Map<Integer, Node>  copied = copyTeam(team);
		 List<Node> list = new ArrayList<>();
		 Node dummyNode = new Node(999,999,999);
		 list.add(dummyNode);
		 
		 //List에 자료 옮기고 
		for(int i=1; i<=team.size(); i++) {
			Node targetNode = team.get(i);
			list.add(targetNode);
		}
		
		for(int i=1; i<=team.size(); i++) {
			Node reverseNode = list.get(team.size()-(i-1));
			reverseNode.num = i;
			
			//map 업데이트 
			if(i==1) {
				map[reverseNode.x][reverseNode.y] = 1;
			}
			if(i==team.size()) {
				map[reverseNode.x][reverseNode.y] = 3;
			}
			
			team.put(i, reverseNode);
		}
		
		
	}
	
	static Map<Integer, Node> copyTeam(Map<Integer, Node> team){
		
		Map<Integer, Node> copied = new HashMap<>();
		
		for(int i=1; i<=team.size(); i++) {
			Node node = team.get(i);
			copied.put(i, new Node(node));
		}
		return copied;
	}
	
	
	static void makeTrain() {
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] ==1) {
					Map<Integer, Node> map = makeBfs(i,j);
					teamList.add(map);
				}
			}
		}
		
	}
	
	
	
	static int[][] moveTrain(List<Map<Integer, Node>> teamList) {
		
		int[][] newMap = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newMap[i][j] = map[i][j];
			}
		}

		for(int t=0; t<teamList.size(); t++) {
			
			Map<Integer,Node> team = teamList.get(t);
			
			//첫 사람.
			Node node = team.get(1);
			int old_x =node.x, old_y=node.y;

			for(int dir=0; dir<4; dir++) {
				int new_x = node.x + dx[dir];
				int new_y = node.y + dy[dir];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) continue;
				if(map[new_x][new_y] ==4) {
					
					node.x = new_x; node.y = new_y;
					newMap[new_x][new_y] = 1;
					break; //break 제때 안하면 이상하게 된다.

				}else if(map[new_x][new_y]==3) {
					//이거 빼먹음 
					node.x = new_x; //node.y = node.y; //
					node.y = new_y;

					newMap[new_x][new_y] = 1;
					break; //break 제때 안하면 이상하게 된다.
					
				}
			}
				
			int new_fx = old_x;
			int new_fy = old_y;

			for(int p=2; p<=team.size(); p++) {
				Node f = team.get(p);
				old_x = f.x; 
				old_y = f.y;
				
				f.x = new_fx;
				f.y = new_fy;
				
				//마지막에는 3으로 
				newMap[new_fx][new_fy] = 2;
				if(p == team.size()) {
					newMap[new_fx][new_fy] = 3;
				}
				
				//여기서 옮기기 전에 처리해야함. 3으
				new_fx = old_x;
				new_fy = old_y;

			}
						
			if(newMap[old_x][old_y]==1) {
				newMap[old_x][old_y] = 1;
			}else {
				newMap[old_x][old_y] = 4;
			}
			
		}
		return newMap;
		
	}
	
	
	
	static Map<Integer, Node> makeBfs(int x, int y) {
		
		Map<Integer, Node> mapList = new HashMap<>();
		
		int index =1;
		boolean[][] visit = new boolean[N][N];
		Queue<Node> Q = new LinkedList<>();
		Node init_node = new Node(x,y,index);

		mapList.put(index, init_node);
		
		Q.add(init_node);
		visit[x][y] = true;
		boolean first_time = false; //2번을 먼저 찾아야 하는 것.
		
		
		while(!Q.isEmpty()) {
			
			Node node = Q.poll();
			
			for(int dir=0; dir<4; dir++) {
				
				int new_x = node.x + dx[dir];
				int new_y = node.y + dy[dir];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N || visit[new_x][new_y] == true) continue;
				if(map[new_x][new_y] ==4 || map[new_x][new_y] ==0) continue;
				if(node.num==1) { //처음일때는 무조건 2를 찾아야 함.
					if(map[new_x][new_y]==2) {
						visit[new_x][new_y] = true;
						index++;
						Node new_node = new Node(new_x, new_y, index);
						mapList.put(index, new_node);
						Q.add(new_node);
					}else continue;
				}else {		
					visit[new_x][new_y] = true;
					index++;
					Node new_node = new Node(new_x, new_y, index);
					mapList.put(index, new_node);
					Q.add(new_node);
				}
			}
			
		}

		return mapList;
	}
	
}

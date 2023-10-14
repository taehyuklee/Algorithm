package 삼성문제1;
/*
 * m 명의 사람 
 * 사람 index 순서가 있다 1, 2, 3, 4, 5 ... m
   각 각 1분, 2분, 3분, ... m분에 베이스 캠프에서 출발함.
   (목적지 편의점)
   
   출발 전까지는 모두격자 밖에 있음.
   
   사람의 행동 패턴 3가지 
   1분동안 진행 = 한 사이클이라는 거임.
   
   1. 격자에 있는 모든 사람이 본인의 목표 편의점을 향해 1칸 움직임.
     (최단거리로 움직임 -BFS)
     -> 우선순위 위, 왼, 오, 아래 dx={-1,0,0,1} dy={0,-1,1,0};
  
   2. 편의점에 도착한다면, 이때부터 다른 사람들은 편의점에 있는 칸을 지날수 없게 된다. 
   
   3. 현재 시각이 t분이고 t<=m을 만족한다면 -> 각 사람들은 남은 베이스 캠프중자신의 목표 지점 편의점과  
     가장 가까운 베이스캠프에서 출발 한다. 
     
     가장 가까운 베이스 캠프가 여러 가지 -> 우선순위 먹인다 (거리 같다면, 행 작은, 열이 작은) 
     - 각 사람이 베이스캠프에 위치하는데는 시간이 소요되지 않는다. 
     (사람이 배치된 이후에는 해당 베이스캠프도 다른 사람이 지나다닐수 없게 된다)
     -마찬가지로 격자에 있는 사람들이 모두 이동한 뒤에 해당 칸을 지나갈 수 없어짐에 유의합니다.?
 * 
 * Input 정보 
 * N 격자
 * M 사람 수 
 * 
 * N개의 줄에 걸쳐 격자의 정보가 주어진다. 0: 빈공간, 1: 베이스 캠프 
 * M개의 줄에 걸쳐 가고 싶은 편의점 위치가 주어진다.
 * 
 * 
 * */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class Person{
	int x, y;
	int target_x, target_y;
	List<int[]> tracks = new ArrayList<>();
	boolean inCoord, out;
	
	public Person(int x, int y, int target_x, int target_y) {
		this.x =x;
		this.y =y;
		this.target_x =target_x;
		this.target_y =target_y;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " target_x: " + target_x +
				" target_y: " + target_y + "]";
	}
}

class Node{
	int x, y, length;
	boolean space;
	
	public Node(int x, int y, boolean space) {
		this.x =x;
		this.y =y;
		this.space = space;
	}
	
	public Node(int x, int y, int length) {
		this.x =x;
		this.y =y;
		this.length = length;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " space: " + space + "]";
	}
}

public class Solution {
	
	static int N, M, time;
	static int[][] map;
	static List<Person> personList;
	static List<Node> baseCamp;
	static int[] dx={-1,0,0,1}, dy={0,-1,1,0};
	
	static void print2D(int[][] map) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static void print2D(boolean[][] map) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				System.out.printf("%5b ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample.txt"));
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		map = new int[N][N];
		personList = new ArrayList<>();
		baseCamp = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = sc.nextInt();
				if(map[i][j]==1) {
					baseCamp.add(new Node(i,j,false));
				}
			}
		}
		
		for(int i=0; i<M; i++) {
			int target_x = sc.nextInt()-1;
			int target_y = sc.nextInt()-1;
			personList.add(new Person(0,0, target_x, target_y));
		}
		
		
		solution();
		System.out.println(time);
		time=0;
		
	}
	
	static void solution() {
		
		time=0;
		while(true) {
//			System.out.println(time + "번째 time 입니다 \n");
			for(int p=0; p<M; p ++) {
				Person person = personList.get(p);
				//격자에 사람이 있다면 1, 2 진행 
				//사람이 이동한다.
				
//				System.out.println(p +"번째 사람: " + person);
	
//				print2D(map);
				if(person.inCoord && person.out==false) {
					Person nextTrack = moveToCamp(person.x, person.y, person.target_x, person.target_y);
					person.x = nextTrack.tracks.get(0)[0];
					person.y = nextTrack.tracks.get(0)[1];
					
					if(person.x == person.target_x && person.y == person.target_y) {
						//2번 작업
						map[person.x][person.y] = -1; //해당 편의점도 못지나 다니게 해줘야 함. 
						person.out = true;
					}
				}	
			}
		
			
			if(time<M) {
				Person person = personList.get(time);	
				//3. base camp 위치
				if(!person.inCoord) {
					Node baseNode = setBaseCamp(person);
//					System.out.println(baseCamp);
//					System.out.println("base위치 " + baseNode);
				}
			}
			time++;
			
			//편의점 check
			boolean go = false;
			for(int p =0; p<personList.size(); p++) {
				Person person = personList.get(p);
				if(person.out == false) {
					break;
				}
				if(p == personList.size()-1) {
					go = true;
				}
			}
			if(go) {
				break;
			}
			
		
		}
	
		
		

//		for(int i=0; i<nextTrack.tracks.size(); i++) {
//			int[] coord = nextTrack.tracks.get(i);
//			System.out.println(coord[0] + " " + coord[1]);
//		}

		
	}
	
	static Node setBaseCamp(Person person) {
		
		int target_x = person.target_x;
		int target_y = person.target_y;
		
		List<Node> targetBase = setBaseCampBfs(target_x, target_y);
//		System.out.println(targetBase);
		
		Collections.sort(targetBase, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.length - o2.length==0) {
					if(o1.x - o2.x==0) {
						return o1.y - o2.y;
					}
					
					return o1.x - o2.x;
				}
				return o1.length - o2.length;
			}
		});
		
		person.x = targetBase.get(0).x;
		person.y = targetBase.get(0).y;
		person.inCoord = true;
		map[person.x][person.y] = -1; //못가는 곳으로 표시.
		targetBase.get(0).space = true; //true로 
		
		return targetBase.get(0);

	}

	
	static List<Node> setBaseCampBfs(int x, int y) {
		
		boolean[][] visit = new boolean[N][N];
		int[] dx= {1,0,0,-1}, dy= {0,1,-1,0};
		List<Node> targetBase = new ArrayList<>();
		
		Queue<Node> Q = new LinkedList<>();
		visit[x][y] = true;
		Q.add(new Node(x,y,0));
		
		while(!Q.isEmpty()) {
			
			Node node = Q.poll();
//			System.out.println(node);
			
			if(map[node.x][node.y] == 1) {
				return targetBase;			
			}
			
			for(int dir=0; dir<4; dir++) {
				int new_x = node.x + dx[dir];
				int new_y = node.y + dy[dir];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N || visit[new_x][new_y] == true) continue; //visit 체크 
				if(map[new_x][new_y]<0) continue;
				
				visit[new_x][new_y] = true;
//				print2D(visit);
				Node newNode = new Node(new_x, new_y, node.length+1);
				Q.add(newNode);
				
				if(map[new_x][new_y] == 1) {
					//꺼낸거 넣어놓고 나머지들도 다 뒤져본다.
					targetBase.add(newNode);	
				}
	
			}
			
		}
		
		return targetBase;
		
		
	}
	
	
	static Person moveToCamp(int x, int y, int base_x, int base_y) {
		
		boolean[][] visit = new boolean[N][N];

		Queue<Person> Q = new LinkedList<>();
		visit[x][y] = true;
		Q.add(new Person(x,y,0,0));
		
		while(!Q.isEmpty()) {
			
			Person node = Q.poll();
			
			if(node.x == base_x && node.y == base_y) {
				return node;
			}
			
			for(int dir=0; dir<4; dir++) {
				int new_x = node.x + dx[dir];
				int new_y = node.y + dy[dir];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N || visit[new_x][new_y] ==true) continue;
				if(map[new_x][new_y]<0) continue;
				
				visit[new_x][new_y] = true;
				Person trackPerson = new Person(new_x, new_y,0,0);
				int[] newCoord = new int[] {new_x, new_y};
				trackPerson.tracks.addAll(node.tracks);
				trackPerson.tracks.add(newCoord);
				Q.add(trackPerson);
	
			}
			
		}
		return null;
	}
	
}

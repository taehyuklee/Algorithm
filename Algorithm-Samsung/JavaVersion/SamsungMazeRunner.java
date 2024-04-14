import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


class Node{
	int x, y, dist;
	List<int[]> track;
	
	public Node(int x, int y, int dist) {
		this.x = x;
		this.y = y;
		this.dist =dist;
	}
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y + "    ";
	}
}

public class Main {
	
	static int N, M, K, exit_x, exit_y, answer;
	static int[][] map;
	static boolean[][] visit;
	static List<Node> personList;
	static int[] dx = {-1,1,0,0}, dy= {0,0,-1,1}, squareDir = {1,-1};

	public static void main(String[] args) throws FileNotFoundException {
				
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		map = new int[N+1][N+1];
		visit = new boolean[N+1][N+1];
		personList = new ArrayList<>();
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			Node person = new Node(x,y, 0);
			personList.add(person);
			map[x][y] = -5;
		}
		
		exit_x = sc.nextInt();
		exit_y = sc.nextInt();
		map[exit_x][exit_y] = -1;
		Node exit = new Node(exit_x, exit_y, 0);

		solution();
		
	}
	
	public static void solution() {
		
		
		for(int turn=1; turn<=K; turn++) {
			
			//List 돌면서 사람 한 명씩 움직인다.
			for(int i=0; i<personList.size(); i++) {
				Node person = personList.get(i);
				if(moveStraight(person)) {
					i--;
				}
				//사람 빼면 동시
	
			}

			if(personList.size()==0) {
				System.out.println(answer);
				System.out.println(exit_x + " " + exit_y);
				return;
			}

			List<Node> square = getSquare(personList);
		
			rotation(square, map);
		

		}

		System.out.println(answer);
		System.out.println(exit_x + " " + exit_y);

	}
	
	//본연 입구부터 시작하는 사람들이 있을까?
	public static boolean moveStraight(Node person) {
		
		int distance_x = person.x - exit_x;
		int distance_y = person.y - exit_y;
		int new_x = person.x;
		int new_y = person.y;

		//착각했던 것 -> 상하 못움직이면 끝이 아니라 좌우로 한 번 더 움직였어야 한다.
		if(distance_x !=0) {
			new_x = person.x + (-1)*(distance_x)/Math.abs(distance_x);
		}
		if(distance_x !=0 && map[new_x][person.y]<=0) {
			map[person.x][person.y] = 0;
			if(map[person.x][new_y]!=-1) {
				map[new_x][person.y] = -5;
			}
			person.x = new_x;
			answer += 1;
		}else {
			if(distance_y !=0) {
				new_y = person.y + (-1)*(distance_y)/Math.abs(distance_y);
				if(map[person.x][new_y]<=0) {
					map[person.x][person.y] = 0;
					if(map[person.x][new_y]!=-1) {
						map[person.x][new_y] = -5;
					}
					person.y = new_y;
					answer += 1; //이게아니고 1칸씩 움직이니까 1씩 더해야지.. (여기 문제였음)
					
				}
			}
		}

		
		//사람을 빼는 곳 
		if(person.y == exit_y && person.x == exit_x) {
			personList.remove(person);
			return true;
		}
		return false;

	}
	
	public static List<Node> getSquare(List<Node> personList) {

		//정사각형 범위를 구해 보자 (4개의 점) 
		List<List<Node>> squares = new ArrayList<>();
		
		//get abs dist from exit
		for(int i=0; i<personList.size(); i++) {
			Node person = personList.get(i);
			if( Math.abs(person.x - exit_x) > Math.abs(person.y - exit_y)) {
				person.dist = Math.abs(person.x - exit_x);
			}else {
				person.dist = Math.abs(person.y - exit_y);
			}

			int dist1_x = Math.abs(exit_x - person.x);
			int dist2_y = Math.abs(exit_y - person.y);
			int dist=0;
			
			if(dist1_x>dist2_y) {
				dist = dist1_x;
				int refer_y=0;
				if(exit_y>person.y) {
					refer_y = exit_y;
				}else refer_y = person.y;
				
				//x 고정 y 변화 
				int slide = 0;
				int new_y = 0;
				while(true) {
					new_y = refer_y - dist + slide;
					
					if(new_y<1 || new_y>=N+1) {
						slide++;
						//이 부분 빼먹음 
					}else {
						break;
					}
				}

				Node node1 = new Node(exit_x, new_y,0);
				Node node2 = new Node(person.x, new_y,0);
				Node node3 = new Node(person.x, new_y+dist,0); //기존의 좌표에서도 slide 적용해줘야 함.
				Node node4 = new Node(exit_x, new_y+dist,0);
				
				List<Node> square = new ArrayList<>();
				square.add(node1); square.add(node2); square.add(node3); square.add(node4);

				squares.add(square);
				continue;
				
			}else if(dist1_x<dist2_y) {
				dist = dist2_y;
				
				//y 고정  x 변화 
				int slide = 0;
				int new_x = 0;
				int refer_x=0;
				
				if(exit_x>person.x) {
					refer_x = exit_x;
				}else refer_x = person.x;
				
				while(true) {
					new_x = refer_x - dist + slide;
					
					if(new_x<1 || new_x>=N+1) {
						slide++;
					}else {
						break;
					}
				}

				Node node1 = new Node(new_x, exit_y,0);
				Node node2 = new Node(new_x, person.y,0);
				Node node3 = new Node(new_x+dist, person.y,0);
				Node node4 = new Node(new_x+dist, exit_y,0);
				
				List<Node> square = new ArrayList<>();
				square.add(node1); square.add(node2); square.add(node3); square.add(node4);
				squares.add(square);
				continue;
	
			}else {

				Node node1 = new Node(person.x, exit_y, 0);
				Node node2 = new Node(exit_x, person.y, 0);
				Node node3 = new Node(person.x, person.y,0);
				Node node4 = new Node(exit_x, exit_y,0);
				
				List<Node> square = new ArrayList<>();
				square.add(node1); square.add(node2); square.add(node3); square.add(node4);
				squares.add(square);
				continue;

			}
			
		}
		
		Collections.sort(squares, new Comparator<List<Node>>() {
			@Override
			public int compare(List<Node> o1, List<Node> o2) {
				sortList(o1);
				sortList(o2);
				
				int byun1 = Math.abs(o1.get(1).y - o1.get(0).y);
				int byun2 = Math.abs(o2.get(1).y - o2.get(0).y);
				
				if(byun1 ==byun2) {
					if(o1.get(0).x == o2.get(0).x) {
						return o1.get(0).y - o2.get(0).y;
					}
					 return o1.get(0).x - o2.get(0).x;
				}
				return byun1 - byun2;
			}
		});
		
		return squares.get(0);
		
	}
	
	
	public static void rotation(List<Node> square, int[][] map) {
		
		Collections.sort(square, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.x == o2.x) {
					return o1.y-o2.y;
				}
				return o1.x - o2.x;
			}
		});

		int start_x = square.get(0).x;
		int start_y = square.get(0).y;
		
		int end_x = square.get(square.size()-1).x;
		int end_y = square.get(square.size()-1).y;
		
		int[][] copyMap = new int[N+1][N+1];
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		boolean exitCoord = false;
		boolean[] visit = new boolean[personList.size()];
		
		for(int i=start_x; i<=end_x; i++) {
			for(int j=start_y; j<=end_y; j++) {
				if(map[i][j]>0) map[i][j]--; //내구도 깎아 
				copyMap[start_x+(j-start_y)][end_y-(i-start_x)] = map[i][j];
				
				//rotation하면서 출구 바꿔주기.
				
				if(i==exit_x && j ==exit_y && exitCoord==false) {
					exit_x = start_x+(j-start_y);
					exit_y = end_y-(i-start_x); 

					exitCoord=true;
				}
				
				//사람 돌려주기 //동시성 문제 발생함 여기서 또.
				
				for(int p=0; p<personList.size(); p++) {
					Node person = personList.get(p);

					if(person.x ==i && person.y ==j && visit[p] ==false) {
						person.x = start_x+(j-start_y);
						person.y = end_y-(i-start_x); 
						visit[p] = true;
					}
				}
				
			}
		}	
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				map[i][j] = copyMap[i][j];
			}
		}
		
	}
	
	
	public static void sortList(List<Node> lists) {
		
		Collections.sort(lists, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.x == o2.x) {
					return o1.y - o2.y;
				}
				return o1.x-o2.x;
			}
		});
	}
	
	
}

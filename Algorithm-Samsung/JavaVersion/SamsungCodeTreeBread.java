import java.util.*;
import java.io.*;

class Person{
	
	int targetX, targetY;
	int baseX, baseY;
	int curX, curY;
	boolean fin = false;
	
	
	public Person(int targetX, int targetY) {
		super();
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public String toString() {
		return "Person [targetX=" + targetX + ", targetY=" + targetY + ", baseX=" + baseX + ", baseY=" + baseY
				+ ", curX=" + curX + ", curY=" + curY + "]";
	}

	
}

class BaseNode{
	
	 //기존 base 위치를 선정할때도 사용하지만, bfs에서도 이동할때 사용되는 객체임.
	int baseX;
	int baseY;
	int dept=0; //bfs에서 이걸로 base로부터 얼마나 떨어져 있는지를 확인하기 위해
	ArrayList<Node> traceList = new ArrayList<>();
	
	public BaseNode(int baseX, int baseY, int dept) {
		super();
		this.baseX = baseX;
		this.baseY = baseY;
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Base [baseX=" + baseX + ", baseY=" + baseY + ", dept=" + dept + "]";
	}
	
	public int getBaseX() {
		return baseX;
	}
	
	public int getBaseY() {
		return baseY;
	}
	
	public int getDept() {
		return dept;
	}
	
}

class Node{
	
	int x;
	int y;
	
	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + "]";
	}

	
}


public class Main {
	
	private static int M, N, answer=0;
	private static int[] dx = {-1,0,0,1};
	private static int[] dy = {0,-1,1,0};
	private static int[][] board;
	private static boolean[][] visit;
	private static ArrayList<Person> personList = new ArrayList<>(); //아직 baseCamp가길 기다리는 사람
	private static ArrayList<Person> onTheMap = new ArrayList<>(); //지도상에 있는 사람들을 관리.
	private static ArrayList<Person> onTheMap2 = new ArrayList<>(); 
	private static ArrayList<BaseNode> candidate = new ArrayList<>(); //출발한 basecamp 후보지
	private static ArrayList<BaseNode> baseList = new ArrayList<>();

	public static void print(int[][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		board = new int[N][N];
		visit = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				
				if(board[i][j] ==1) {
					baseList.add(new BaseNode(i,j,0));
				}
			}
		}
		
		for(int i=0; i<M; i++) {
			personList.add(new Person(sc.nextInt()-1, sc.nextInt()-1));
		}
	
		solution();
		
	}
	
	
	public static void solution() {
		
		while(true) {
			
			answer+=1;

			//if(map위에 사람이 있다면)
			if(onTheMap.size() !=0) {
				
				for(int i=0; i<onTheMap.size(); i++) {
					
					Person person = onTheMap.get(i);
			
					//1번 본인이 가고싶은 방향으로 1칸 움직임
					move(person);
					
				}
				
				copyOnMap(onTheMap, onTheMap2);
				for(int i=0; i<onTheMap2.size(); i++) {
					//2번 해당편의점에 도달 그 칸은 막힘
					Person person = onTheMap2.get(i); //두개를 한번에 못걸러냄 위에서 처럼 제거하면서 사이즈가 달라지는 현
					//걸러내기 전 전체 맵: [Person [targetX=0, targetY=2, baseX=0, baseY=0, curX=0, curY=2], Person [targetX=0, targetY=1, baseX=1, baseY=1, curX=0, curY=1]] 두개를 한꺼번에 걸러야
					finish(person);
				}
				
			}
			
			
			//if(대기중인 사람이 있다면)
			if(personList.size() !=0) {
			
				//3번 t<=m base로 이동 (남은 사람이 있으면)
				Person person = personList.remove(0);
				startBase(person);
	
				//시뮬레이션 종료 시점
				if(onTheMap.size() ==0 && personList.size()==0) {
					break;
				}

			}
		}
		
		System.out.println(answer);

		
	}
	
	
	public static void startBase(Person person) {
		//모든 base에서 탐험해야함.
		
		//Base originBase = new Base();
		for(int i=0; i< baseList.size(); i++) {
			BaseNode base = baseList.get(i);
			
			visit = new boolean[N][N]; //visitMap 초기화. 
			//get shortest coord
			bfs(person, base);
		}
		
		Collections.sort(candidate, Comparator.comparing(BaseNode::getDept).thenComparing(BaseNode::getBaseX).thenComparing(BaseNode::getBaseY));
		
		//baseCamp가 선택됨.
		BaseNode startBase = candidate.get(0);
		
		person.baseX = startBase.baseX;
		person.baseY = startBase.baseY;
		person.curX = person.baseX;
		person.curY = person.baseY;
		
		//그 자리에 점령함.
		board[person.baseX][person.baseY] = -1;
		
		//candidate 초기화 및 baseCamp 목록에서 삭제.
		candidate = new ArrayList<>();
		baseList.remove(startBase);
		visit = new boolean[N][N]; //visitMap 초기화. 
		
		for(int i=0; i<baseList.size(); i++) { //dept도 다시 초기화. (이거 안했다가 ㄷㄷ)
			baseList.get(i).dept=0;
		}
		
		//현재 목적이 정해진 사람을 list에 넣어준다.
		onTheMap.add(person);
	}
	
	
	public static void bfs(Person person, BaseNode base) {
		
		Queue<BaseNode> q = new LinkedList<>();
		q.add(base);
		
		while(!q.isEmpty()) {
			
			BaseNode oldBase = q.poll();
			
			//break문
			if(person.targetX == oldBase.baseX && person.targetY ==oldBase.baseY) {
				//이미 위의 조건에 도달했다면 거리를 기록한 것을 candidate list에 넣어준다.
				base.dept = oldBase.dept; //탐색을 끝낸 node의 dept를 기존 base에 넣어준다.
				candidate.add(base);
				break;
			}
			
			for(int i=0; i<4; i++) {
				
				int newX = oldBase.baseX + dx[i];
				int newY = oldBase.baseY + dy[i];

				
				if(newX>=0 && newX<N && newY>=0 && newY<N) {
					if(visit[newX][newY] == false && board[newX][newY] != -1) {

						visit[newX][newY] = true;
						q.add(new BaseNode(newX, newY, oldBase.dept+1));
						
						
					}

				}
			}	
		}	
	}
	
	public static void move(Person person) {
		
		visit = new boolean[N][N]; //visit을 초기화 안해서 또 틀렸음.
		BaseNode targetBaseNode = exploreBfs(person);
		targetBaseNode.traceList.get(0);

		if(targetBaseNode.traceList.size() != 0) {
			person.curX = targetBaseNode.traceList.get(0).x;
			person.curY = targetBaseNode.traceList.get(0).y;
		}

	}
	
	
	public static BaseNode exploreBfs(Person person) {
		
		Queue<BaseNode> q = new LinkedList<>();
		q.add(new BaseNode(person.curX, person.curY, 0));
		
		while(!q.isEmpty()) {
			
			BaseNode oldBase = q.poll();
			
			//break문
			if(person.targetX == oldBase.baseX && person.targetY ==oldBase.baseY) {
				return oldBase;
			}
			
			for(int i=0; i<4; i++) {
				
				int newX = oldBase.baseX + dx[i];
				int newY = oldBase.baseY + dy[i];

				
				if(newX>=0 && newX<N && newY>=0 && newY<N) {
					if(visit[newX][newY] == false && board[newX][newY] != -1) {
						
						BaseNode baseNode = new BaseNode(newX, newY, oldBase.dept+1);
						baseNode.traceList.addAll(oldBase.traceList); //이전 trace를 받아온
						baseNode.traceList.add(new Node(newX, newY)); //현재 trace Node를 추가한다.

						visit[newX][newY] = true;
						q.add(baseNode);
						
						
					}

				}
			}

		}
		return null;
		
	}
	
	public static void finish(Person person) {
		
		//완료된 사람들 걸러내기
		//목적지에 도착한다면 map에서 빼주자.
		if(person.curX == person.targetX && person.curY == person.targetY) {
			person.fin = true;
			//board -1로 업데이트.
			board[person.targetX][person.targetY] = -1;
			onTheMap.remove(person);
		}	
	}
	
	public static void copyOnMap(ArrayList<Person> source, ArrayList<Person> target) {
		//자료구조에서 넣고 빼기만할거라 굳이 딥카피 할 필요가 없다.
		for(int i=0; i<source.size(); i++) {
			target.add(source.get(i));
		}
	}
	
}

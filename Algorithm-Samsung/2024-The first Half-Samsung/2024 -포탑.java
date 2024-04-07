import java.util.*;

class Turret{
	
	int x, y, attack, time;
	boolean live;
	List<Turret> trace = new ArrayList<>();
	
	public Turret(int x, int y, int time, int attack) {
		this.x = x;
		this.y = y;
		this.time = time;
		this.attack = attack;
		
	}
	
	//복사 생성자 
	public Turret(Turret turret) {
		this.x = turret.x;
		this.y = turret.y;
		this.time = turret.time;
		this.attack = turret.attack;
	}

	
	@Override
	public String toString() {
		return "x: " + this.x + "  " + "y: " + this.y + "  " + "attack: " + this.attack + "  " + "time: " + this.time +" | ";
	}
	
}



public class Main {
	
	static int N, M, K;
	static Turret[][] board;
	static int[][] plusBoard;
	static List<Turret> turretList = new ArrayList<>(); //포탑이 죽으면 List에서 제거하는 식으로 가자.
//	static int[] dx = {0, 1,0,-1}, dy = {1,0,-1,0}; //우하좌상
//	static int[] dx2 = {-1,-1,-1, 0, 1, 1, 1, 0}, dy2 = {-1,0,1,1,1,0,-1,-1}; //8방향
    private static int[] dx = {0,1,0,-1,-1,-1,1,1}, dy = {1,0,-1,0,-1,1,-1,1};
	static List<int[]> involved;
	static int answer;



	public static void print2D(Turret[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.printf("%04d  ", board[i][j].attack);
			}
			System.out.println();
		}
	}
	
	public static void monitor(List<int[]> trace, int N, int M, Turret attacker, Turret target) {
		 int[][] monitor = new int[N][M];
		 
		 for(int i=0; i<trace.size() ; i++) {//주변 2 target 3 attack 1
			 int[] coord = trace.get(i);
			 monitor[coord[0]][coord[1]] =2;
		 }
		 monitor[attacker.x][attacker.y] = 1;
		 monitor[target.x][target.y] = 3;
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				
				System.out.printf("%01d  ", monitor[i][j]);

				
			}
			System.out.println();
		}
	}
	
	public static void printPlus(int[][] plusBoard) {
		for(int i=0; i<plusBoard.length; i++) {
			for(int j=0; j<plusBoard[0].length; j++) {
				System.out.printf("%01d  ", plusBoard[i][j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		
		
		//Input
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		board = new Turret[N][M];
		plusBoard = new int[N][M];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				int attack  = sc.nextInt();
				Turret turret = new Turret(i,j,0, attack);
				board[i][j] = turret;
				if(turret.attack != 0) turretList.add(turret);
			}
		}
		

		//원래는 여기 Test Case T가 붙어 있다. 
		
		//총 4가지 action이 K번 반복 (전체 횟수) 
		int time=1;
		for(int i=0; i<K; i++) {
			//공격자 선정
			Turret attacker = pickWeakest(board, turretList);
			attacker.attack += N+M;
			
			//공격자의 공격
			Turret strongest = pickStrongest(attacker, turretList);
			
			//선정 이후 레이저 공격
			boolean bomb = laserAttack(attacker, strongest);
			if(bomb) {
				explode(attacker, strongest);
			}
			
			//여기서 attack한 포탑 외에 모두 time을 +1을 해줘야 한다. 
			//포탑 정비
			organize(attacker, strongest);

			if(countLiveTurret()==1) break;
			
			attacker.time = time;
			
			time++;
		}


		Turret turret = turretList.get(0);
		System.out.println(turret.attack);
		
	}
	
	
	public static int countLiveTurret() {
		int cnt = 0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j].attack==0) {
					cnt++;
				}
			}
		}
		return N*M-cnt;
	}
	
	public static Turret pickWeakest(Turret[][] board, List<Turret> turretList) {
		//부서지지 않은 포탑 중 가장 약한 포탑 공격자로 선정 
		
		Collections.sort(turretList, new Comparator<Turret>(){
			
			public int compare(Turret o1, Turret o2) {
				
				if(o1.attack == o2.attack) {
					if(o1.time==o2.time) {
						if((o1.x+o1.y) == (o2.x+o2.y)) {
							return o2.y - o1.y;
						}
						return (o2.x+o2.y) - (o1.x+o1.y);
					}
					return o2.time- o1.time;
				}
				
				return o1.attack - o2.attack;
			}
			
		});
		Turret weakest = turretList.get(0);
//		System.out.println(weakest);
//		for(int i=0; i<turretList.size(); i++) {
//			Turret weakest = turretList.get(i);
//			if(weakest.attack !=0) {
//				return weakest;
//			}else {
//				continue;
//			}
//		}
//
//		return null;
		return weakest;
	}
	
	public static Turret pickStrongest(Turret attacker, List<Turret> turretList){
		
		// 자신을 제외한 가장 강한 포탑 선졍 (자신 제외)
		Collections.sort(turretList, new Comparator<Turret>(){
			
			public int compare(Turret o1, Turret o2) {
				
				if(o1.attack == o2.attack) {
					if(o1.time==o2.time) {
						if((o1.x+o1.y) == (o2.x+o2.y)) {
							return o1.y - o2.y;
						}
						return (o1.x+o1.y) - (o2.x+o2.y);
					}
					return o1.time- o2.time;
				}
				
				return o2.attack - o1.attack;
			}
			
		});
		
		//자기 자신 제외한 나머지 포탑중 
		Turret strongest;
		for(int i=0; i<turretList.size(); i++) {
			if(turretList.get(i) == attacker) {
				continue;
			}else if(turretList.get(i).attack == 0) {
				continue;
			}else {
				return turretList.get(i);
			}
		}
		return null;
	}
	
	
	public static void organize(Turret attacker, Turret reciver) {
		
//		System.out.println("trace표시");
//		for(int i=0 ;i< involved.size(); i++) {
//			int[] element = involved.get(i);
//			System.out.print(element[0] + " , " + element[1] + " | ");
//		}
//		System.out.println("  ");
//		System.out.println(turretList);
//		System.out.println("  ");
		
		//이 부분 다시 생각해 볼 필요가 있다.
//		for(int i=0; i<turretList.size(); i++) {
//			for(int j=0; j<involved.size(); j++) {
//				if(turretList.get(i).x == involved.get(j)[0] && turretList.get(i).y == involved.get(j)[1]) continue;
//				if(turretList.get(i) == attacker) continue;
//				if(turretList.get(i) == reciver) continue;
//				System.out.println(turretList.get(i));
//				System.out.println(involved.get(j)[0] + "  " + involved.get(j)[1] + "  ");
//				turretList.get(i).attack++;	//더하면 예를 들어 involved에서 2번째에 걸리면 이미 앞에 1번째에서는 더하게 된다. 따라서 true false로 두고 해야함.
//				break;
//			}
//		}
//		print2D(board);
		
		involved.add(new int[] {attacker.x, attacker.y});

		int[][] newBoard = new int[N][M];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				
				Turret turret = board[i][j];
				boolean add = true;		
				for(int k=0; k<involved.size(); k++) {
					//하나씩 다 해주는게 제일 안전함.	
					if(turret.x == involved.get(k)[0] && turret.y == involved.get(k)[1] ) {
						add = false;
						break; //말이 안됐던게 다 돌아보고 나서 했어야함. 로직이 잘못됨
					}
					
		
				}
				
				if(turret.attack <=0) {
					add = false;
				}

				if(add && involved.size()!=0) { //involved가 0일때
					turret.attack++;
					newBoard[i][j] = 1;
					
					add = false;
					//break; 여기서 break해버리면 2중 for loop에서 끊어져버림.
				}

				}
			
			}
		
		plusBoard = newBoard;
		
	
		Collections.sort(turretList, new Comparator<Turret>() {

			@Override
			public int compare(Turret o1, Turret o2) {
				return o2.attack - o1.attack;
			}
			
		});
		
		
	}
	
	public static void explode(Turret attacker, Turret target) {
		
		//여기서 new_x, new_y involved에 안넣었다.
		
		List<int[]> intArray = new ArrayList<>();
		
		for(int i=0; i<8; i++) { //여기서 자기 자신꺼 안뺌.. attacker꺼
			int new_x = (N+target.x + dx[i])%N;
			int new_y = (M+target.y + dy[i])%M;
			if(board[new_x][new_y] == attacker) continue; //이거 한 줄 이거 리뷰 한 번 써보자
			if(board[new_x][new_y].attack==0) continue;
			board[new_x][new_y].attack -= attacker.attack/2;
			if(board[new_x][new_y].attack<=0) board[new_x][new_y].attack=0;
			if(board[new_x][new_y].attack<=0) {
				board[new_x][new_y].attack = 0;
				turretList.remove(board[new_x][new_y]);
			}	
			intArray.add(new int[] {new_x, new_y});
		}
		
		target.attack-=attacker.attack;
		if(target.attack<=0) { //여기 설마..
			target.attack = 0;
			turretList.remove(target);
		}
		intArray.add(new int[] {target.x, target.y});
		
		involved = intArray;
		
	}
	
	public static boolean laserAttack(Turret attacker, Turret reciver) {
		
		List<int[]> attackTrace= bfs(attacker, reciver);
		involved = attackTrace;
		
//		System.out.println("trace표시");
//		for(int i=0 ;i< attackTrace.size(); i++) {
//			int[] element = attackTrace.get(i);
//			System.out.print(element[0] + " , " + element[1] + " | ");
//		}
		
		//Bomb 공격 여부
		if(attackTrace.size() ==0) {
			return true;
		}else {
			//Laser공격
			for(int i=0; i<attackTrace.size(); i++) {
				int[] coord = attackTrace.get(i);
				
				for(int j=0; j<turretList.size(); j++) {
					if(coord[0] == turretList.get(j).x && coord[1] == turretList.get(j).y) {
						Turret targetTurret  = turretList.get(j);
						
						if(i!=attackTrace.size()-1) {
							//1/2로 경로에 있는 애들 공격력 줄이고
							targetTurret.attack -= attacker.attack/2;
							if(targetTurret.attack<=0) {
								targetTurret.attack = 0;
								turretList.remove(targetTurret);
								i--;
							}
						}else {
							//target은 공격력 만큼 줄이고 
							targetTurret.attack -= attacker.attack;
							if(targetTurret.attack<=0) {
								targetTurret.attack = 0;
								turretList.remove(targetTurret);
								i--;
								
							}	
						}						
					}
				}
				
			}
			
			return false;
		}
	}
	
	public static List<int[]> bfs(Turret attacker, Turret reciver) {
		
		boolean[][] visit = new boolean[N][M];
		
		Queue<BfsNode> Q = new LinkedList<>();
		
		BfsNode bfsNode = new BfsNode(attacker.x, attacker.y);
		
		Q.add(bfsNode);
		visit[bfsNode.x][bfsNode.y] = true;
		
		while(!Q.isEmpty()) {
			
			BfsNode turret = Q.poll();
			
			//종료 조건
			if(turret.x==reciver.x && turret.y==reciver.y) {
				return turret.trace;
			}
			
			//탐색 
			for(int i=0; i<4; i++) {
				int new_x = (N+turret.x + dx[i])%N;
				int new_y = (M+turret.y + dy[i])%M;
				
				//여러 최단경로를 저장해서 비교하는거..
				//if(new_x<0 || new_x>N) new_x = (N+new_x)%N; //이 부분은 사실 위에서 합쳐도 된다
				//if(new_y<0 || new_y>M) new_y = (M+new_y)%M;
				if(visit[new_x][new_y] == true) continue;
				if(board[new_x][new_y].attack ==0) continue;
				
				//방문개록
				visit[new_x][new_y] = true;
				
				//새로 탐색한 Node를 만든다. 
				BfsNode newNode = new BfsNode(new_x, new_y);
				int[] newTrack = new int[] {new_x, new_y};
				newNode.copyTrack(turret.trace, newTrack); //여태까지 걸어온 길을 copy한다.
				Q.add(newNode);
				
			}
			
		}
		return new ArrayList<int[]>();
	}
	
	//아 내부에 static영역이라 static으로 해줘야 하는구나
//	static class BfsNode{
//		
//		int x, y;
//		List<BfsNode> track = new ArrayList<>();
//		
//		public BfsNode(int x, int y) {
//			this.x = x;
//			this.y = y;
//		}
//		
//		public void copyTrack() {
//			//이런식으로 그 전에 내가 문제 푼것처럼 Root를 모두 저장해도 되긴 한다.
//			
//		}
//
//	}
	


}

//외부 Class로 쓰자
class BfsNode{
	
	int x, y;
	List<int[]> trace = new ArrayList<>();
	
	public BfsNode(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void copyTrack(List<int[]> oldTrace, int[] targetTrack) {
		//이런식으로 그 전에 내가 문제 푼것처럼 Root를 모두 저장해도 되긴 한다.
		trace.addAll(oldTrace); //이전에는 for Loop돌렸지만
		trace.add(targetTrack);
		
	}

}

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
	static List<Turret> turretList = new ArrayList<>(); //포탑이 죽으면 List에서 제거하는 식으로 가자.
	static int[] dx = {0, 1,0,-1}, dy = {1,0,-1,0};



	public static void print2D(Turret[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j]);
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
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				int attack  = sc.nextInt();
				Turret turret = new Turret(i,j,0, attack);
				board[i][j] = turret;
				if(turret.attack != 0) turretList.add(turret);
			}
		}
		
		print2D(board);
		System.out.println(turretList);
		
		
		
		//원래는 여기 Test Case T가 붙어 있다. 
		
		//총 4가지 action이 K번 반복 (전체 횟수) 
		
		//공격자 선정
		Turret attacker = selectTurret(board, turretList);
		
		
		//공격자의 공격
		attackerAttack(attacker, turretList);
		
		
		//포탑 부서짐
		
		
		//포탑 정비
		
		
		
	}
	
	public static Turret selectTurret(Turret[][] board, List<Turret> turretList) {
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
					return o1.time- o2.time;
				}
				
				return o1.attack - o2.attack;
			}
			
		});
		
		Turret weakest = turretList.get(0);
		//System.out.println(weakest);
		
		
		//공격력 N+M 증가 
		weakest.attack += N+M;
		
		//print2D(board);
		return weakest;
	}
	
	public static void attackerAttack(Turret attacker, List<Turret> turretList){
		
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
					return o2.time- o1.time;
				}
				
				return o2.attack - o1.attack;
			}
			
		});
		
		//자기 자신 제외한 나머지 포탑중 
		Turret strongest;
		if(turretList.get(0) == attacker) {
			strongest = turretList.get(1);
		}else {
			strongest = turretList.get(0);
		}
		
		//확인 과정. 
		System.out.println(strongest);
		
		//선정 이후 레이저 공격
		laserAttack(attacker, strongest);
		
	}
	
	public static void laserAttack(Turret attacker, Turret reciver) {
		
		bfs(attacker, reciver);
		
		//
		
	}
	
	public static void bfs(Turret attacker, Turret reciver) {
		
		boolean[][] visit = new boolean[N][M];
		
		Queue<Turret> Q = new LinkedList<>();
		Q.add(attacker);
		visit[attacker.x][attacker.y] = true;
		
		while(!Q.isEmpty()) {
			
			Turret turret = Q.poll();
			
			//탐색 
			for(int i=0; i<4; i++) {
				int new_x = turret.x + dx[i];
				int new_y = turret.y + dy[i];
				
				//여러 최단경로를 저장해서 비교하는거..
				if(new_x<0 || new_x>N) new_x = (N+new_x)%N;
				if(new_y<0 || new_y>M) new_y = (M+new_y)%M;
				if(visit[new_x][new_y] == true) continue;
				if(board[new_x][new_y].attack ==0) continue;
				
				Turret newTurret = new Turret(board[new_x][new_y]);
				
				
				
			}
			
		}
		
	}
	
	public static void explode() {
		
		
	}
	

	
	
	
}

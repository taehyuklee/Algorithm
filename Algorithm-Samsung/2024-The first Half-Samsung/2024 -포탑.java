/*
 * N x M 격자
 * 각 포탑에 공격력 존재, if 포탑 공격력 <=0 포탑은 부서짐
 * 최초로 공격력이 0인 포탑이 존재할 수 있다.
 * 
 * 하나의 턴은 다음 4가지 액션을 순서대로 수행하며, 총 K번 반복된다.
 * 1. 공격자 선정
 * 가장 약한 포탑 (가장 작은 수) 선정 
 * 선정시 - N + M 만큼의 공격력이 증가된다. 
 * 
 * 가장 약한 포탑 선정 기준 
 *   1.1 공격력이 가장 낮은 포탑
 *   1.2 2개 이상이라면 가장 최근에 공격한 포탑 (모든 포탑은 시점 0에는 모두 공격한 경험이 있다)
 *   1.3 각 포탑의 위치의 행과 열의 합이 가장 큰 포탑
 *   1.4 열 값이 가장 큰 포탑이 약한 포탑
 *   
 *   
 * 2. 공격자의 공격
 * 자신을 제외한 가장 강한 포탑을 공격 
 *   
 * 가장 강한 포탑 선정 기준 
 *   2.1 공격력이 가장 높은 포탑이 가장 강한 포탑
 *   2.2 if 2개 이상 -> 공격한지 가장 오래된 포탑 time이 오래된 것
 *   2.3 행과 열의 합기 가장 작은 포탑
 *   2.4 열 값이 가장 작은 포탑
 *   
 * 공격에는 2 가지 공격 존재 
 * (1) 레이저 공격
 *  1. 상하좌우 4개의 방향으로 움직일 수 있다
 *  2. 부서진 포탑이 있는 위치는 지날 수 없다
 *  3. 가장자리에서 막힌 방향으로 진행하면 반대편으로 나온다 (주기 경계)
 * 
 * 레이저는 공격자의 위치에서 공격 대상까지 최단 경로로 공격
 * 만약 경로가 존재하지 않는다면 포탄 공격을 함
 * 
 * 그리고 만약 최단 경로가 2개 이상이라면, 우/하/좌/상 우선순위로 움직인다.
 * 대상은 공격력만큼 줄어들고
 * 경로에 있는 포탑은 공격자 공격력의 절반만큼 피해를 받음
 * 
 * 
 * (2) 포탄 공격
 *  1. 공격 대상에 포탄을 던진다. 공격 받은 대상 주의 8개 방향으로 포탑도 피해를 입는다
 *  (대신 주변 8개는 공격자의 절반 만큼 피해를 받는다) 
 *  * 단, 공격자는 해당 공격에 영향을 받지 않는다. 
 *  (여기도 주기적 경계조건이 됨)
 * 
 * 
 * 3. 포탑 부서짐
 *  공격을 받아 공격력이 0 이하가 된 포탑은 부서진다
 * 
 * 
 * 4. 포탑 정비
 *  공격이 끝나면, 부서지지 않은 포탑 중 공격과 무관했던 포탑은 공격력이 1씩 증가
 *  공격과 무관하다는 것은 공격자도 아니고 공격에 피해를 입은 포탑도 아니라는 뜻
 *  
 * 
 * */

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
	static int[] dx = {0, 1,0,-1}, dy = {1,0,-1,0}; //우하좌상



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
		
		List<int[]> attackTrace= bfs(attacker, reciver);
		
		System.out.println("trace표시");
		for(int i=0 ;i< attackTrace.size(); i++) {
			int[] element = attackTrace.get(i);
			System.out.print(element[0] + " , " + element[1] + " | ");
		}
		
		//
		
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
		return null;
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
	
	public static void explode() {
		
		
	}

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

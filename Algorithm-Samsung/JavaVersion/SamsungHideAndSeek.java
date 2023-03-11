import java.util.*;
import java.io.*;
import java.math.*;

class Runner{
	
	int dir;
	int[] path;
	int nowDir=0; //0일때 초기 방향 하, 우 -> 1일때는 그 다음 반대 방향 상, 좌
	
	public Runner(int dir) {
		this.dir = dir;
		
		if(dir ==2) { //dir 2 -> 하상.
			this.path = new int[] {0,1};
			
		}else if(dir ==1) { //dir 1 -> 우좌.
			this.path = new int[] {2,3};			
		}
		
	}
	
	@Override
	public String toString() {
		return this.dir + " runner";
	}
	
	public Runner(Runner runner) {
		this.dir = runner.dir;
		this.path = runner.path;
		this.nowDir = runner.nowDir;
	}
}


class Node{
	
	int x;
	int y;
	
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return this.x + " " + this.y + " ";
	}
}

public class Main {
	
	static int[][] treeBoard;
	static int[][] chaserBoard;
	static int N, M, H, K, lookX, lookY;
	static List<Runner>[][] runnerBoard, runnerBoard2;
	static Map<Integer, Node> spiralMap = new HashMap<>();
	static int chaserPos, answer=0;
	static int[] dxRunner = {1,-1,0,0}; //하, 상, 우, 좌
	static int[] dyRunner = {0,0,1,-1}; 
	static boolean reverse = false;
	
	public static void print(int[][] array) {
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void print(int[] array) {
		
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	public static void print(List<Runner> [][] array) {
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		H = sc.nextInt();
		K = sc.nextInt();
	
		chaserBoard = new int[N][N];
		treeBoard = new int[N][N];
		chaserBoard[N/2][N/2] = 1; //chaserBoard에 1로 사용한다. 
		runnerBoard = new ArrayList[N][N];
		runnerBoard2 = new ArrayList[N][N];
		
		//System.out.println(runnerBoard.length + " "+ runnerBoard[0].length);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				runnerBoard[i][j] = new ArrayList<Runner>();
				runnerBoard2[i][j] = new ArrayList<Runner>();
			}
		}
		
		for(int i=0; i<M; i++) {
			
			int xEsc = sc.nextInt()-1;
			int yEsc = sc.nextInt()-1;
			int d = sc.nextInt();
			runnerBoard[xEsc][yEsc].add(new Runner(d));
			
		}
		
		for(int i=0; i<H; i++) {
			
			int xTree = sc.nextInt()-1;
			int yTree = sc.nextInt()-1;
			
			treeBoard[xTree][yTree] = 1;
		}
				
		//make spiral coordinate
		makdSpiral();
		chaserPos = 0; //map에서 key로 관리 (chaser의 위치)
		print(runnerBoard);
		print(treeBoard);
		solution();
		
	}
	
	public static void makdSpiral() {
		
		int x_0 = N/2, y_0 = N/2;
		spiralMap.put(0, new Node(x_0, y_0));
		int count=0, refDept=1, currDept=0, dir=0;
		int [] dx = {-1,0,1,0};
		int [] dy = {0,1,0,-1};
		
		for(int i=1; i<N*N; i++) {
			
			int x_new = x_0 + dx[dir];
			int y_new = y_0 + dy[dir];
			
			currDept+=1;
			
			if(currDept == refDept) {
				
				dir = (dir+1)%4;
				count+=1; //방향이 바뀐 count
				
				if(count==2) {//2번 바뀔때마다 길이를 증가시켜준다
					refDept +=1;
					count=0;
				}
				currDept=0;

			}
					
			x_0 = x_new;
			y_0 = y_new;
					
			spiralMap.put(i, new Node(x_new, y_new));
			
		}
	}
	
	public static void solution() {

		for(int turn=1; turn<=K; turn++) {
			//술래와의 거리가 3이하인 도망자들만 움직인다. (격자내 규칙 외 규칙)
			
			System.out.println("========================================================");

			System.out.println(turn + " 차례입니다");
			moveRunner();
//			print(runnerBoard2);
			
			//술래가 움직인다. (방향 틀어지는거 확인)
			moveChaser();
	
			//술래가 보는 방향으로 3칸 내의 도망자 잡아 (나무랑 같이 있는 도망자는 제외)
			removeRunner(turn);
			
			transferBoard();
			
//			System.out.println();
			print(chaserBoard);
//			System.out.println(answer);
//			System.out.println();
			
		}
		
		
		System.out.println(answer);
		
	}
	
	public static void moveRunner() {
		
		//각 칸의 모든 도망자와 술래와의 거리를 측정
		//chaser의 위치
		Node chaserCoord = spiralMap.get(chaserPos);
		int chaserX = chaserCoord.x;
		int chaserY = chaserCoord.y;

		//술래의 위치
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(runnerBoard[i][j].size() !=0 ) {
					
					int distance = Math.abs(chaserX - i) + Math.abs(chaserY - j);
					
					if(distance<=3) {

						//3이하 거리인 술래들이면 움직인다.
						//만약 술래가 두 명 이상이라면
						for(int k=0; k<runnerBoard[i][j].size(); k++) { //runner : runnerBoard[i][j]) {
							
							Runner runner = runnerBoard[i][j].get(k);
							int runnerDir = runner.path[runner.nowDir];
							
							int newX = i + dxRunner[runnerDir];
							int newY = j + dyRunner[runnerDir];
							
							if(newX>=0 && newX<N && newY>=0 && newY<N) {//격자 이내 
								
								if(chaserX == newX && chaserY == newY ) { //술래가 있으면 안움직인다.
									continue;
								}else {//그외에 한칸 이동 (arraylist안에서 빼서 옮기는거 기술 필요)
									//runnerBoard[i][j].remove(runner);
									runnerBoard2[newX][newY].add(runner);
								}
								
							}else {//격자 이외 방향 반대로 틀어
								runner.nowDir = (runner.nowDir+1)%2;
								runnerDir = runner.path[runner.nowDir]; //너무 돌아가는것 같다 좀 더 생각해보자
								
								newX = i + dxRunner[runnerDir]; //기존에서 틀어준 방향으로 돌아간다
								newY = j + dyRunner[runnerDir];
								
								if(chaserX == newX && chaserY == newY ) { //술래 있으면 안움직
									continue;
								}else {//그외에 한 칸 이동
									//runnerBoard[i][j].remove(runner);
									runnerBoard2[newX][newY].add(runner);
								}
								
							}
							
						}
					
					}else {
						//안움직인건 그대로 있어줘야 한다 (내가 빼먹음)
						for(int k=0; k<runnerBoard[i][j].size(); k++) { //runner : runnerBoard[i][j]) {
							Runner runner = runnerBoard[i][j].get(k);
							runnerBoard2[i][j].add(runner);
						}
					}
				}
			}
		}
	}

	public static void moveChaser() {
		
		int chaserOld = chaserPos;
		
		if(reverse ==false) {
			chaserPos +=1;
		}else if (reverse == true) {
			chaserPos -=1;
		}
		
		if(chaserPos == N*N-1) {
			reverse = true;
		}else if(chaserPos == 0) {
			reverse = false;
		}
		
		//chaserBoard 동기화
		Node chaserNode = spiralMap.get(chaserOld);
		chaserBoard[chaserNode.x][chaserNode.y]=0;
		chaserBoard[spiralMap.get(chaserPos).x][spiralMap.get(chaserPos).y]=1;
		
		//바라보고 있는 방향 x,y로 말해주기 
		if(chaserPos+1 <N*N && reverse == false) {
			lookX = spiralMap.get(chaserPos+1).x - spiralMap.get(chaserPos).x; //한 칸 앞 - 최신
			lookY = spiralMap.get(chaserPos+1).y - spiralMap.get(chaserPos).y; 
		}else if(chaserPos == N*N-1 && reverse == true) { //N*N-1가자마자 reverse가 true로 바뀌게 해놓음
			lookX = 1; lookY =0;
		}else if(chaserPos-1>=0 && reverse ==true) {
			lookX = spiralMap.get(chaserPos-1).x - spiralMap.get(chaserPos).x; //한 칸 앞 - 최신
			lookY = spiralMap.get(chaserPos-1).y - spiralMap.get(chaserPos).y; 
		}else if(chaserPos == 0 && reverse == false) {//0에 가자마자 reverse가 flase로 바뀌게 해놓음
			lookX = -1; lookY=0;
		}
		
		//System.out.println(lookX + " " + lookY);
	}
	
	public static void removeRunner(int turn) {
		int chaserX = spiralMap.get(chaserPos).x;
		int chaserY = spiralMap.get(chaserPos).y;
		
		//chaser로부터 3칸까지 확인한다. (나무 있으면 그 곳의 도망자는 무사함)
		for(int i=0; i<3; i++) {
			int rangeX = chaserX + lookX*i;
			int rangeY = chaserY + lookY*i;
			
			//범위 설정 안했었다..
			if(rangeX>=0 && rangeX<N && rangeY>=0 && rangeY<N) {
				//System.out.println(rangeX + " " + rangeY);
//				System.out.println("이건 점수 판 확인");
//				System.out.println(turn*runnerBoard2[rangeX][rangeY].size());
//				System.out.println("chaserX: " + chaserX + " " + " chaserY: " + chaserY + " lookX: " + lookX + " lookY: " + lookY);
				if(treeBoard[rangeX][rangeY] !=1 && runnerBoard2[rangeX][rangeY].size() !=0) {
					//나무가 없을때만 runner가 잡히므로
					//점수 합산해주고
					answer += turn*runnerBoard2[rangeX][rangeY].size();
					//도망자 제거해주고
					runnerBoard2[rangeX][rangeY].clear();
				}
			}
		}
	}
	
	

	public static void transferBoard() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				runnerBoard[i][j].clear();
				if(runnerBoard2[i][j].size()!=0) {
					for(int k=0; k<runnerBoard2[i][j].size(); k++) {
						Runner runner = runnerBoard2[i][j].get(k);
						runnerBoard[i][j].add(new Runner(runner));
						runnerBoard2[i][j].clear();
					}
				}
			}
		}
	}

}

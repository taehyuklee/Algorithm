import java.util.*;
import java.io.*;

class Node{
	int x, y, number;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + ", number=" + number + "]";
	}
	
	
}


public class Main {

	static Map<Integer, Node> spiral = new HashMap<>();
	static int N, K;
	static int[][] map;
	static int[][] order;
	//static int[] inLine;
	static int score;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	public static void print2D(int[][] arr) {
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void print1D(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
	
	public static boolean checkOfBreak(int[] old, int[] newOne) {
		
		boolean breakOne = false;
		int count =0;
		for(int i=0; i<old.length; i++) {
			if(old[i] != newOne[i]) {
				count++;
			}
		}
		
		if(count!=0) {
			breakOne = false;
		}else {
			breakOne = true;
		}
		
		return breakOne;
		
	}
	
	public static void makeSpiral() {
		
		int[] ddx = {0,1,0,-1};
		int[] ddy = {-1,0,1,0};
		
		int initalX = (int) N/2;
		int initalY = (int) N/2;
		
		int d = 0;
		int depth = 1;
		int indx = 1;
		int count =0;
		int turn = 0;
		
		spiral.put(0, new Node((int) N/2, (int) N/2));
		
		while(indx<N*N) {
			
			int nx = initalX + ddx[d];
			int ny = initalY + ddy[d];
			
			spiral.put(indx, new Node(nx, ny));
			
			turn++;
			
			if(turn == depth) {
				d = (d+1)%4;
				turn =0;
				
				//turn을할때 depth를 증가시켜야 할지에 대한 여부
				if(count ==1) {
					depth++;
					count=-1;
				}
				count++;	
			}
			
			indx++;
			
			initalX = nx;
			initalY = ny;
			
	
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		
		//Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
//		System.out.print(N);
//		System.out.println(K);
		
		map = new int[N][N];
		
		for(int i=0; i<map.length; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<map[0].length; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//print2D(map);
		
		order = new int[K][2];
		
		for(int i=0; i<order.length; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<2; j++) {
				order[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//print2D(order);
		
		//나선형 꼬아서 관리
		makeSpiral();
		//System.out.println(spiral);
		
		solution();
		
		//print2D(map);
		System.out.println(score);
	}
	
	public static void solution() {
		

		
		//Loop
		for(int k=0; k<K; k++) {
			
			int d = order[k][0]-1;
			int s = order[k][1];
			//블리자드 마법시전
			blizzard(d, s);

			//map -> line형태로 바꾸기
			int[] inLine1 = converStraight();
			
			//Loop
			int[] oldForBreak = new int[N*N];
			oldForBreak = inLine1;
			while(true) {
				
				//빈공간 땡겨 이동시키기
				inLine1 = pulling(inLine1);
				
				//System.out.println();
				//print1D(pulledLine);
				
				//System.out.println(pulledLine);
				
				//폭발시키기
				explosion(inLine1);
				//System.out.println();
				//print1D(pulledLine);
				
				//break장치
				boolean go = checkOfBreak(oldForBreak, inLine1);
				
				if(go) {
					break;
				}
				
				oldForBreak = inLine1;
			}
			
//			System.out.println();
//			print1D(inLine1);
			
			
			//공간을 다 땡겼을때 변환시키기
			inLine1 = change(inLine1);
			
//			System.out.println();
//			print1D(inLine1);
			
			//다시 map형태로 변환시켜주자
			conversionToMap(inLine1);
			
			//print2D(map);
		}
		

	}
	
	
	
	public static void blizzard(int d, int s) {
		
		int sharkX = (int) N/2;
		int sharkY = (int) N/2;
		
		for(int i=1; i<=s; i++) {
			int nx = sharkX + dx[d]*i;
			int ny = sharkY + dy[d]*i;
			
//			if(map[nx][ny]==1) {
//				score+=1;
//			}else if(map[nx][ny] ==2) {
//				score+=2;
//			}else if(map[nx][ny] ==3) {
//				score+=3;
//			}
			
			map[nx][ny] = -1;
			
		}
		
		//print2D(map);		
		
	}
	
	public static int[] converStraight() {
		int[] inLine1 = new int[N*N];
		
		
		for(int i=0; i<N*N; i++) {
			
			Node node = spiral.get(i);
			
			inLine1[i] = map[node.x][node.y];
			
		}
		
		return inLine1;
	}
	
	public static int[] pulling(int[] inLine) {
		
		int[] newLine = new int[inLine.length];
		
		int newI =0;
		for(int i=0; i<inLine.length; i++) {
			if(inLine[i] !=-1) {
				newLine[newI] = inLine[i];
				newI++;
			}
		}

		return newLine;
	}
	
	public static void explosion(int[] inLine) {
		
		int oldNum = inLine[1];
		int count =1;
		
		for(int i=2; i<inLine.length; i++) {
			
			int curNum = inLine[i];
			
			if(curNum == oldNum) {
				count++;
			}else if(curNum != oldNum) {
				
				if(count>=4) {
					//여기서 모두 -1로 제거해주기
					for(int j= i-1; j>=i-1-(count-1); j--) {
						
						//점수 매겨주기
						if(inLine[j]==1) {
							score+=1;
						}else if(inLine[j] ==2) {
							score+=2;
						}else if(inLine[j] ==3) {
							score+=3;
						}
						
						inLine[j] = -1;
					}
				}
				
				oldNum = curNum;
				count =1;
				
			}

		}
		
		//위에서는 번호가 바뀌어야 trigger가 작동한다 하지만 마지막 꺼는 1, 1, 1, 1이 있어도 번호가 바뀌지 않기때문에 따로 해줘야 한다.
		//이 경우는 범위가 N*N을 넘길대만 해당된다 -> 중간에 안넘긴 상태에서는 0이 숫자를 바꿔주기 대문에 큰 상관이 없다.
		if(count>=4 && oldNum !=0) {
			//여기서 모두 -1로 제거해주기
			for(int j=N*N-1 ; j>=N*N-1-(count-1); j--) {
				
				//점수 매겨주기
				if(inLine[j]==1) {
					score+=1;
				}else if(inLine[j] ==2) {
					score+=2;
				}else if(inLine[j] ==3) {
					score+=3;
				}
				
				inLine[j] = -1;
			}
		}
		
	}
	
	public static int[] change(int[] inLine) {
		
		int oldNum = inLine[1];
		int count = 1;
		int[] newLine = new int[N*N];
		int newI = 1;
		
		for(int i=2; i<N*N; i++) {
			
			int curNum = inLine[i];
			
			if(curNum == oldNum) {
				count++;
			}else if(curNum != oldNum) {
				
				newLine[newI] = count;
				newI++;
				newLine[newI] = oldNum;
				newI++;
				
				//change되는게 만약 범위를 넘긴다면, break해준다.
				if(newI>=N*N) {
					break;
				}
				
				count =1;
				oldNum = curNum;
			}
		}

		
		return newLine;
		
	}
	
	
	public static void conversionToMap(int[] inLine) {
		
		for(int num=0; num<N*N; num++) {
			
			Node node = spiral.get(num);
			
			map[node.x][node.y] = inLine[num];
			
		}
		
	}
	
	
	
}

/*
 * R X C 격
 * - (r,c) 의 온도를 실시간 모니터링 하는 시스템 개발.
 * 처음에 모든 온도는 - 0 (모든 칸)
 * 
 * process
 * 1. 모든 온풍기에서 바람이 한 번 나옴
 *    - 분사되는거 구현해야 함.(벽도 구현해야 함)
 * 2. 온도가 조절 됨.
 *    - 모든 인접한 칸 -> 온도가 높은 칸에서 낮은 칸 (두 칸의 온도차이 /4) 만큼 온도 조절 됨.
 *    - map 두개 가지고 해야 함 (동시성 문제)
 *    - 벽이 있을 경우 온도조절이 되지 않음.
 *    
 * 3. 온도가 1 이상인 가장 바깥쪽 카느이 온도 1씩 감소.
 *    - 최 외곽에 있는거 모두 -1씩 하면 된다.
 *    
 * 4. 초콜릿 하나 먹는다.
 * 5. 모든 칸의 온도가 K 이상 되었는지 검사 (만야 그렇다면 마무리)
 *   -> 아니라면 1번 절차로 돌아간다.
 * 
 * 온풍기 바람의 방향 
 * - 분사되는 방향 - 처음에는 무조건 5로 시작. (더이상 칸이 없다면 전진 X)
 * - 분사될수록 -1씩 감소한다.
 * - 같은 칸에 같은 온풍기에서 나온 바람이 여러 번 도착해도 여러번 상승 X
 * 
 * 벽의 개념이 들어감.
 * (x,y) -> (x-1, y+1) 이동하기 위해서는 (x,
 * 
 * 바람의 이동을 다음과 같이 생각하면 된다. 
 * 
 * 
 * Input 정보 
 * R, C, K
 * 0: 빈 칸
 * 1: 방향이 오른쪽인 온풍기가 있음
 * 2: 방향이 왼쪽인 온풍기가 있음
 * 3: 방향이 위 온풍기 
 * 4: 방향이 아래 
 * 5: 조사 대상 칸 
 * 
 * 다음 
 * W : 벽의 개수 
 * x, y, t
 * t=0 -> (x,y) (x-1,y) 사이에 벽 
 * t=1 -> (x,y) (x,y+1) 사이에 벽 
 * 
 * */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class WallInfo{
	int x, y, x1, y1;
	
	public WallInfo(int x, int y, int x1, int y1) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + ", x1: " + x1 + " y1: " + y1 + " ] ";
	}
	
	
}


public class Main {
	
	static int R, C, K, W, answer;
	static int[][] map, newMap, map2;
	static List<WallInfo> wallList;
	static List<int[]> inspectList;
	
	static void print2D(int[][] map) {
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				System.out.print(map[i][j]+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		
//		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample.txt"));
		
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		K = sc.nextInt();
		
		inspectList = new ArrayList<>();
		
		map = new int[R][C];
		newMap = new int[R][C];
		map2 = new int[R][C];
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				map[i][j] = sc.nextInt();
				if(map[i][j]==5) {
					inspectList.add(new int[] {i,j});
				}
			}
		}
		
		W = sc.nextInt();
		wallList = new ArrayList<>();
		
		for(int w=0; w<W; w++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int t = sc.nextInt();
			int x1 = 0;
			int y1 = 0;
			
			if(t==0) {
				x1 = x-1; y1 = y;
			}
			else if(t==1) {
				x1= x; y1= y+1;
			}
			
			WallInfo wallInfo = new WallInfo(x,y,x1,y1);
			wallList.add(wallInfo);
		}

//		System.out.println(wallList);
		
		solution();
	}
	
	static void solution() {
	
		while(true) {
			
			//모든 온풍기에서 바람 한 번 나옴
			for(int i=0; i<R; i++) {
				for(int j=0; j<C; j++) {
					goWind(i,j);
				}
			}
		
			
			//온도 조절 됨
			newMap = mainpulationTemp(newMap);
			
			
			//온도가 1 이상인 바깥쪽 칸의 온도 1씩 감소 
			reduceTemp(newMap);

			//초콜릿 하나 먹는다.
			answer++;

			if(answer >100) {
				break;
			}
			
		
			//칸 조사. (inspectList)
			boolean go = true;
			for(int[] coord: inspectList) {
				int ref = newMap[coord[0]][coord[1]];
				
				if(ref<K) {
					go =false;
					break;
				}
			}
			if(go) {
				break;
			}
			
		
		}
		System.out.println(answer);
		
	}
	
	static void reduceTemp(int[][] map) {
		int cnt00=0;
		int cnt0C=0;
		int cntR0=0;
		int cntRC=0;
		
		for(int j=0; j<C; j++) {
			if(map[0][j] !=0) {
				map[0][j]--;
				
				if(j==0) {
					cnt00++;
				}
				if(j==C-1) {
					cnt0C++;
				}
				
			}
		}
		for(int j=0; j<C; j++) {
			if(map[R-1][j] !=0) {
				map[R-1][j]--;
				if(j==0) {
					cntR0++;
				}
				if(j==C-1) {
					cntRC++;
				}
			}
		}
		for(int j=0; j<R; j++) {
			if(map[j][0] !=0) {
				map[j][0]--;
				if(j==0) {
					cnt00++;
				}
				if(j==R-1) {
					cntR0++;
				}
			}
		}
		for(int j=0; j<R; j++) {
			if(map[j][C-1] !=0) {
				map[j][C-1]--;
				if(j==0) {
					cnt0C++;
				}
				if(j==R-1) {
					cntRC++;
				}
			}
		}

		if(cnt00 ==2) {
			map[0][0]++;
		}
		if(cnt0C==2) {
			map[0][C-1]++;
		}
		if(cntR0==2) {
			map[R-1][0]++;
		}
		if(cntRC==2) {
			map[R-1][C-1]++;
		}
	}
	
	static int[][] mainpulationTemp(int[][] map) {
//		int[][] map = new int[3][3];
//		map[0][0] = 5;
//		map[0][1] =18;
//		map[0][2] = 70;
//		map[1][0] = 23;
//		map[1][1] = 46;
//		map[2][1] = 2;
//		map[2][2] = 20;
//		wallList.add(new WallInfo(0,1,0,2));
//		wallList.add(new WallInfo(1,1,2,1));
//		wallList.add(new WallInfo(2,0,2,1));
		
		int[][] newMap = new int[R][C];
		int[] dx = {-1,1,0,0}, dy= {0,0,-1,1};
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		

		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				
				int divided=0;
				
				for(int dir=0; dir<4; dir++) {
					int base = map[i][j];
		
					int new_x = i + dx[dir];
					int new_y = j + dy[dir];
					if(new_x<0 || new_x>=R || new_y<0 || new_y>=C) continue;
					if(wallCheckMain(i,j,new_x,new_y)) continue;

					
					if(map[new_x][new_y]<map[i][j]) {
						divided =(int) (map[i][j]-map[new_x][new_y])/4;
						newMap[new_x][new_y] += divided;
						newMap[i][j] -= divided;
						
					}
					
				}
				
			}
			
			
		}
		return newMap;
		
	}
	
	
	
	static boolean wallCheckMain(int x, int y, int new_x, int new_y) {
		for(int i=0; i<wallList.size(); i++) {
			WallInfo wallInfo = wallList.get(i);
			if(wallInfo.x == x && wallInfo.y == y && wallInfo.x1 == new_x && wallInfo.y1 == new_y) {
				return true;
			}
			if(wallInfo.x == x && wallInfo.y == y && wallInfo.x1 == new_x && wallInfo.y1 == new_y) {
				return true;
			}
			
			if(wallInfo.x1 == x && wallInfo.y1 == y && wallInfo.x == new_x && wallInfo.y == new_y) {
				return true;
			}
			if(wallInfo.x1 == x && wallInfo.y1 == y && wallInfo.x == new_x && wallInfo.y == new_y) {
				return true;
			}
		}
		return false;
	}
	
	static void goWind(int x, int y) {
		
		int[][] testMap = new int[R][C];
				
		if(map[x][y]==1) {
			
			if(y+1>=0 && y+1<=C) {
				testMap[x][y+1] = 5;
			}
			
			int new_x = x;
			int new_y = y+1;
		
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				testMap = spreadRight(testMap, new_x, new_y, 4);	
			}
	
			
		}
		
		if(map[x][y]==3) {
			
			if(x-1>=0 && x-1<=R) {
				testMap[x-1][y] = 5;
			}
			
			int new_x = x-1;
			int new_y = y;
		
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				testMap = spreadUp(testMap, new_x, new_y, 4);	
			}
	
			
		}
		

		if(map[x][y]==4) {
			
			if(x+1>=0 && x+1<=R) {
				testMap[x+1][y] = 5;
			}
			
			int new_x = x+1;
			int new_y = y;
		
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				testMap =spreadDown(testMap, new_x, new_y, 4);	
			}
		}

		if(map[x][y]==2) {
			
			if(y-1>=0 && y-1<=C) {
				testMap[x][y-1] = 5;
			}
			
			int new_x = x;
			int new_y = y-1;
		
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				testMap =spreadLeft(testMap, new_x, new_y, 4);	
			}
			
		}
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				newMap[i][j] += testMap[i][j];
			}
		}
		
	}
	

	static int[][] spreadRight(int[][] map, int old_x, int old_y, int i) {
		
		if(i==0) return null;
		
		int new_x = old_x-1;
		int new_y = old_y+1;

		if(wallCheckRight(old_x, old_y, new_x, new_y, 0) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadRight(map, new_x, new_y, i-1);
			}
		}
		

		
		new_x = old_x;
		new_y = old_y+1;
		//System.out.println(wallCheck(old_x, old_y, new_x, new_y, 1));
		if(wallCheckRight(old_x, old_y, new_x, new_y, 1) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				
				map[new_x][new_y] = i;
				spreadRight(map, new_x, new_y, i-1);
			}
		}
		
	
		
		new_x = old_x +1;
		new_y = old_y +1;
		if(wallCheckRight(old_x, old_y, new_x, new_y, 2) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadRight(map, new_x, new_y, i-1);
			}
		}
		return map;
		
	}
	

	static boolean wallCheckRight(int old_x, int old_y, int new_x, int new_y, int dir) {
		
		if(dir==0) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x == old_x-1 && wallInfo.y == old_y && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y+1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x-1 && wallInfo.y == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x-1 && wallInfo.y1== old_y && wallInfo.x == old_x-1 && wallInfo.y == old_y+1) {
					return true;
				}
			}
		}
		
		
		if(dir==1) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y+1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y+1) {
					return true;
				}
			}
	
		}
		
		if(dir==2) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x == old_x+1 && wallInfo.y == old_y && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y+1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x+1 && wallInfo.y == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y && wallInfo.x == old_x+1 && wallInfo.y == old_y+1) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	static int[][] spreadUp(int[][] map, int old_x, int old_y, int i) {

		if(i==0) return null;
		
		int new_x = old_x-1;
		int new_y = old_y-1;

		if(wallCheckUp(old_x, old_y, new_x, new_y, 0) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadUp(map, new_x, new_y, i-1);
			}
		}
		

		
		new_x = old_x-1;
		new_y = old_y;
		//System.out.println(wallCheck(old_x, old_y, new_x, new_y, 1));
		if(wallCheckUp(old_x, old_y, new_x, new_y, 1) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				
				map[new_x][new_y] = i;
				spreadUp(map, new_x, new_y, i-1);
			}
		}
		
	
		
		new_x = old_x-1;
		new_y = old_y+1;
		if(wallCheckUp(old_x, old_y, new_x, new_y, 2) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadUp(map, new_x, new_y, i-1);
			}
		}
		
		return map;
	}
	
	
static boolean wallCheckUp(int old_x, int old_y, int new_x, int new_y, int dir) {
		
		if(dir==0) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y-1) {
					return true;
				}
				if(wallInfo.x == old_x && wallInfo.y == old_y-1 && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y-1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y-1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y-1 && wallInfo.x == old_x-1 && wallInfo.y == old_y-1) {
					return true;
				}
			}
		}
		
		
		if(dir==1) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x-1 && wallInfo.y == old_y) {
					return true;
				}
			}
	
		}
		
		if(dir==2) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y+1) {
					return true;
				}
				if(wallInfo.x == old_x && wallInfo.y == old_y+1 && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y+1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y+1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y+1 && wallInfo.x == old_x-1 && wallInfo.y == old_y+1) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	static int[][] spreadDown(int[][] map, int old_x, int old_y, int i) {
//		print2D(map);
//		System.out.println(wallList);
		if(i==0) return null;
		
		int new_x = old_x+1;
		int new_y = old_y+1;

		if(wallCheckDown(old_x, old_y, new_x, new_y, 0) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadDown(map, new_x, new_y, i-1);
			}
		}
		

		
		new_x = old_x+1;
		new_y = old_y;
		//System.out.println(wallCheck(old_x, old_y, new_x, new_y, 1));
		if(wallCheckDown(old_x, old_y, new_x, new_y, 1) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				
				map[new_x][new_y] = i;
				spreadDown(map, new_x, new_y, i-1);
			}
		}
	
		
		new_x = old_x+1;
		new_y = old_y-1;
		if(wallCheckDown(old_x, old_y, new_x, new_y, 2) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadDown(map, new_x, new_y, i-1);
			}
		}
		return map;
		
	}
	

	static boolean wallCheckDown(int old_x, int old_y, int new_x, int new_y, int dir) {
		
		
		if(dir==0) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y+1) {
					return true;
				}
				if(wallInfo.x == old_x && wallInfo.y == old_y+1 && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y+1) {
					return true; 
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y+1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y+1 && wallInfo.x == old_x+1 && wallInfo.y == old_y+1) {
					return true; 
				}
			}
		}
		
		
		if(dir==1) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x+1 && wallInfo.y == old_y) {
					return true; //순서가 중요한 경우 
				}
			}
	
		}
		
		if(dir==2) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y-1) {
					return true;
				}
				if(wallInfo.x == old_x && wallInfo.y == old_y-1 && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y-1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y-1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y-1 && wallInfo.x == old_x+1 && wallInfo.y == old_y-1) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	
	static int[][] spreadLeft(int[][] map, int old_x, int old_y, int i) {
		
		if(i==0) {
			return null;
		}
		
		int new_x = old_x-1;
		int new_y = old_y-1;

		if(wallCheckLeft(old_x, old_y, new_x, new_y, 0) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadLeft(map, new_x, new_y, i-1);
			}
		}
		

		
		new_x = old_x;
		new_y = old_y-1;
		//System.out.println(wallCheck(old_x, old_y, new_x, new_y, 1));
		if(wallCheckLeft(old_x, old_y, new_x, new_y, 1) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				
				map[new_x][new_y] = i;
				spreadLeft(map, new_x, new_y, i-1);
			}
		}
		
	
		
		new_x = old_x+1;
		new_y = old_y-1;
		if(wallCheckLeft(old_x, old_y, new_x, new_y, 2) == false) {
			if(new_x>=0 && new_x<R && new_y>=0 && new_y<C) {
				map[new_x][new_y] = i;
				spreadLeft(map, new_x, new_y, i-1);
			}
		}
		
		return map;
		
	}
	

	static boolean wallCheckLeft(int old_x, int old_y, int new_x, int new_y, int dir) {
		
		if(dir==0) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x == old_x-1 && wallInfo.y == old_y && wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y-1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x-1 && wallInfo.y == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x-1 && wallInfo.y1 == old_y && wallInfo.x == old_x-1 && wallInfo.y == old_y-1) {
					return true;
				}
			}
		}
		
		
		if(dir==1) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x && wallInfo.y1 == old_y-1) {
					return true;
				}
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x && wallInfo.y == old_y-1) {
					return true;
				}
			}
	
		}
		
		if(dir==2) {
			for(int w=0; w<wallList.size(); w++) {
				WallInfo wallInfo = wallList.get(w);
				if(wallInfo.x == old_x && wallInfo.y == old_y && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y) {
					return true;
				}
				if(wallInfo.x == old_x+1 && wallInfo.y == old_y && wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y-1) {
					return true;
				}
				
				if(wallInfo.x1 == old_x && wallInfo.y1 == old_y && wallInfo.x == old_x+1 && wallInfo.y == old_y) {
					return true;
				}
				if(wallInfo.x1 == old_x+1 && wallInfo.y1 == old_y && wallInfo.x == old_x+1 && wallInfo.y == old_y-1) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	

}

// 3시간 30분.

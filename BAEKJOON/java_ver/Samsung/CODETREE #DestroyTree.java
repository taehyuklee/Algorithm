package 삼성1번;

/*
 * N X N 격자 
 * 
 * 제초 -> k 범위 만큼 대각선으로 퍼진다. (벽은 통과 못함) 
 * 1년 사이클 - 나무의 성장과 억제는같이 이루어진다. 
 * 
 * 1. 인접한 네 개의 칸 중 나무가 있는 칸의 수만큼성장. 
 * ( 성장은 동시에 일어난다.)
 * 
 * 2. 기존에 있던 나무들은 인접한 4개의 칸 중 (벽, 다른 나무, 제초제 모두 없는 칸에 번식 가능)
 *   - 
 *   
 * 3. 나무가 가장 많이 박멸되는 칸에 제초제를 뿌린다. (bfs 구현)
 * - 나무가 없는 칸에 제초제를 뿌리면 박멸되는 나무가 전혀 X
 * - 나무가 있는 칸 -> 4개의 대각선으로 k만큼 전파 
 * (단, 벽, 나무 아예 없는 칸 -> 그 칸까지는 젳초제가 뿌려짐) 
 * 
 * - 제초제는 c년만큼 제초제가 남아 있다가 c+1년째가 될때 사라진다. 
 * 
 * - 제초제가 뿌려진 곳에 다시 뿌려지면, 새로 뿌려진 해로부터 c년 동안 
 * 
 * 
 * 
 * 입력 형식
 * N 격자 , M 진행되는  수, K 제초제의 확산 범위, C: 제초제가 남아 있는 년 수 
 * 
 * 나무 그루수 1~100
 * 빈 칸 0
 * 벽 -1 
 * 
 * */



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Solution {
	
	static int N, M, K, C, killAnswer;
	static int[][] map;
	static boolean[][] wall;
	static int[] cdx = {-1,-1,1,1}, cdy= {-1,1,1,-1};
	static int[] gdx = {-1,1,0,0}, gdy= {0,0,-1,1};
	
	
	static void print2D(int[][] map) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%6d", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		
	}
	static void print2D(boolean[][] map) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%6b", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample_input.txt"));
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		C = sc.nextInt();
		
		map = new int[N][N];
		wall = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = sc.nextInt();
				if(map[i][j]==-1) {
					map[i][j]=0;
					wall[i][j] = true;
				}
			}
		}
		
		solution();

		
	}
	
	static void solution() {
		
		for(int year=1; year<=M; year++) {
			
//			System.out.println("현재 년도 " + year);
			
			
//			System.out.println("나무 자라");
//			print2D(map);
			
			//나무의 성장 
			grow(map);
			

			
			//나무의 번식 
			map = baby(map);
//			System.out.println("나무 번식");
//			print2D(map);
//			print2D(wall);
			
			//제초제 뿌림 
			if(killTree(map)) {
				System.out.println(killAnswer);
				return;
			};
			
//			System.out.println("나무 죽여");
//			print2D(map);
			

			
		}
		
		System.out.println(killAnswer);

	}
	
	static void reduceDrug(int[][] map) {
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j]<0) {
					map[i][j]++;
				}
			}
		}
		
	}
	
	static void grow(int[][] map) {

		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j]>0) {
					
					int space_cnt=0;
					
					for(int dir=0; dir<4; dir++) {
						
						int new_x = i + gdx[dir];
						int new_y = j + gdy[dir];
						
						if(new_x<0 || new_x>=N || new_y<0 || new_y>=N || wall[new_x][new_y] == true) continue;
						
						if(map[new_x][new_y]>0) {
							space_cnt++;
						}

					}
					map[i][j]+=space_cnt;
				}
			}
		}
		
	}
	
	
	
	static int[][] baby(int[][] map) {
		
		int[][] newMap = new int[N][N];
		
		//copy map
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(map[i][j]>0) {
					
					int space_cnt=0;
					List<int[]> space = new ArrayList<>();
					
					for(int dir=0; dir<4; dir++) {
						
						int new_x = i + gdx[dir];
						int new_y = j + gdy[dir];
						
						if(new_x<0 || new_x>=N || new_y<0 || new_y>=N || wall[new_x][new_y]==true) continue;
						
						if(map[new_x][new_y]==0) {
							space_cnt++;
							space.add(new int[] {new_x, new_y});
						}

					}
					
					if(space_cnt!=0) {
						int baby_num = map[i][j]/space_cnt;
						
						for(int b=0; b<space.size(); b++) {
							int[] coord = space.get(b);
							newMap[coord[0]][coord[1]] += baby_num;
						}
					}
					
				}
				
			}
		}
		return newMap;
		
	}
	
	

	static boolean killTree(int[][] map) {
		
		int[] killLoc = getManyKillLocation(map);
		
		
		if(killLoc == null) {
			return true;
		}
		
		//제초제- 사라
		reduceDrug(map);
		
		goToKillTre(killLoc, map);

		return false;
		
	}
	
	
	static void goToKillTre(int[] killLoc, int[][] map) {
		
		int target_x = killLoc[0];
		int target_y = killLoc[1];
		
		for(int dir=0; dir<4; dir++) {
			
			map[target_x][target_y] = -C; //죽이고 (이것때문에 동시성 문제 발생 그냥 이전껄로 )
			
			for(int k=1; k<=K; k++) {
				int new_x = target_x + cdx[dir]*k;
				int new_y = target_y + cdy[dir]*k; //복붙하다가 cdy가 됨.
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) break;
				
				if(map[new_x][new_y]<=0  || wall[new_x][new_y] ==true) {
					//제초제를 남겨야 한다. 제초제를 뿌리는 시점은 이 함수가 돌아가는 시점 따라서 -C를 넣어주면 그 다음 해부터 카운트 된다.
					//아 벽이 -1이니까 
					map[new_x][new_y] = -C;
					break;
				}

				map[new_x][new_y] = -C;
				
			}

		}
		killAnswer += killLoc[2];
		
	}
	

	
	static int[] getManyKillLocation(int[][] map) {
		
		List<int[]> tempList = new ArrayList<>();
		
		//copy map
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(map[i][j]>0) {
					int kill=0;
					kill += map[i][j];
//					System.out.println("현재 위치 : " + i + "  " + j);
					for(int dir=0; dir<4; dir++) {
						
						for(int k=1; k<=K; k++) {
							int new_x = i + cdx[dir]*k;
							int new_y = j + cdy[dir]*k; //복붙하다가 cdy가 됨.
							
							if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) break;
							
							if(map[new_x][new_y]<=0 || wall[new_x][new_y]==true) { // =이거 하나 <=0 00일때를빼먹음..
								//continue; //가로 막히거나 제초제가 있으면 다른 방향을 알아본다.
								break;
							}
							if(map[new_x][new_y]>0) {
								kill+= map[new_x][new_y];
							}
							
//							System.out.println("k: " + k + " new_x:  " + new_x  +" new_y: " + new_y + " 나무 : " + map[new_x][new_y]);
						}

					}
					tempList.add(new int[] {i,j,kill});
					//0: x위치, 1: y 위치, 2: kill 수			
				}	
				
			}
		}
		
		//확인
//		for(int i=0; i<tempList.size(); i++) {
//			int[] coord = tempList.get(i);
//			System.out.println(coord[0] + " " + coord[1] + "  kill:  " + coord[2]);
//		}
		
		Collections.sort(tempList, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o2[2] - o1[2] ==0) {
					if(o1[0] - o2[0]==0) {
						return o1[1] - o2[1];
					}
					
					return o1[0] - o2[0];
				}
				return o2[2] - o1[2];
			}
		});
		
		if(tempList.size()==0) {
			return null;
		}
		int[] killLoc = tempList.get(0);
//		System.out.println(killLoc[0] + "  " + killLoc[1]);
	
		return killLoc;
	}
	
	
}

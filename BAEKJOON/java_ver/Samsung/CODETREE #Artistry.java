/*
 * 격자 NXN
 * 각 칸 1 ~ 10 숫자 
 * 상하 좌우로 인접해 있는 경우 동일한 그룹
 * 
 * Step1) 
 * 예술 점수 
 * = (그룹 a 칸의 수 + 그룹 b 캄의 수) x 그룹 a 숫자 x 그룹 b 의 숫 x 그룹 a와 b가 서로 맞닿아 있는 변의 수
 * 
 * ? 맞닿아 있는 변의 수는? 어떻게 구할까?
 * 
 * Step2) 
 * 초기 예술 점수 
 * = 그룹 쌍 간의 조화로움 값이 (0점 이상) 인조합의 전체 값을 더한 것 
 * 
 * Step3)
 * 초기 예술 점수 구한 이후 그림에 대한 회전을 진행한다. 
 * - 정 중앙에 크로스 선을 두고 
 * 
 * 3-1) 크로스가 회전을 한다 반시계 방향으로 90도 회전한다.
 * 
 * 3-2) 나머지 정사각형들이 시계방향으로 90도 회전한다.
 * 
 * - 요구사항 초기 예술점수 + 1회전 이후 예술 점수 + 2회전 이후 예술 점수 
 *  +3회전 이후 예술 점수 
 *  
 * 첫 번째 줄에 n이 주어진다. n은 반드시 홀수 
 * 이후 n개의 줄에 걸쳐 각 행에
 * 
 * */


import java.util.*;
import java.util.Map.*;


public class Main {
	
	static int N, answer;
	static int[][] map, map2, group;
	static int[] dx= {-1,1,0,0}, dy= {0,0,-1,1};
	static boolean[][] visit;
	static int returnCnt=1;

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		
		map = new int[N][N];
		map2 = new int[N][N];
		group = new int[N][N];
		visit = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				int element = sc.nextInt();
				map[i][j] = element;
				map2[i][j] = element;
				
				
			}
		}
		
		
		unitTest();
		//solution();
		
		
	}
	/*
	 * 
	 * 
5
1 2 2 3 3
2 2 2 3 3
2 2 1 3 1
2 2 1 1 1
2 2 1 1 1

	 
	 * */
	
	static void print2D(int[][] map) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				System.out.print(map[i][j] + " ");
				
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static void print2D(boolean[][] map) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				System.out.print(map[i][j] + " ");
				
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static void unitTest() {
		
		
		for(int turn=1; turn<=4; turn++) {
			List<List<Integer>> groupList = new ArrayList<>();
			List<Map<Integer,Integer>> groupMap = new ArrayList<>();
			
			grouping(groupList,groupMap);
//			System.out.println(groupList);
//			System.out.println(groupMap);
//			print2D(group);
//	
			//조화로움 점수 계산 
			answer+= getScore(groupList,groupMap);
		
			
			
			//rotate 회전 시키기.
			map = roatateCross(map);
			map = rotateSquare(map);
//			print2D(map);
//			print2D(map);
			
			
		}
		System.out.println(answer);
		

		
	}
	
	
	static int[][] rotateSquare(int[][] map){
		
		int[][] newMap = new int[N][N];
		
		//copy
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		
		//2사분면 
		int start_x = 0, end_x = N/2;
		int start_y =0, end_y = N/2;

		//90도 회전 구현 
		for(int i=start_x; i<end_x; i++) {
			for(int j=start_y; j<end_y; j++) {
				
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}
		
		//1사분면 
		start_x = 0;  end_x = N/2;
		start_y = N/2+1; end_y = N;
		
		for(int i=start_x; i<end_x; i++) {
			for(int j=start_y; j<end_y; j++) {
				
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}
		
		//3사분면 
		start_x = N/2+1;  end_x = N;
		start_y = 0; end_y = N/2;
		
		for(int i=start_x; i<end_x; i++) {
			for(int j=start_y; j<end_y; j++) {
				
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}
		
		//4사분면 
		start_x = N/2+1;  end_x = N;
		start_y = N/2+1; end_y = N;
		for(int i=start_x; i<end_x; i++) {
			for(int j=start_y; j<end_y; j++) {
				
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}

		
		return newMap;
	}
			
	static int[][] roatateCross(int[][] map) {
		
		int[][] newMap = new int[N][N];
		
		//copy
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				newMap[i][j] = map[i][j];
			}
		}
				
		for(int i=0; i<N/2; i++) { //윗 날게 90도 회
			newMap[N/2][i] = map[i][N/2];
		}
		
		for(int i=N/2+1; i<N; i++) { //오른 날게 90도 회
			newMap[N/2-1-(i-(N/2+1))][N/2] = map[N/2][i];
		}
		
		for(int i=N/2+1; i<N; i++) { //아래 날게 90도 회
			newMap[N/2][i] = map[i][N/2];
		}
		
		for(int i=0; i<N/2; i++) { //왼 날게 90도 회
			newMap[N-1-i][N/2] = map[N/2][i];
		}
		return newMap;
		
	}
	
	static int getScore(List<List<Integer>> groupList , List<Map<Integer,Integer>> groupMap) {
//		System.out.println(groupList);
//		System.out.println(groupMap);
		
		//조화로움 점수 계산 
		int element_score =0;
		
		for(int i=0; i<=groupMap.size()-1; i++) {
			Map<Integer, Integer> maps = groupMap.get(i);
			int space_nums=0;
			int cur_grp_num=0;
			int nxt_grp_num=0;
			int lines =0;
		
	
			Set<Integer> grpSet = maps.keySet();
//			System.out.println(grpSet);
//			System.out.println(grpSet.size());
			
			for(int nxtGrpKey: grpSet) {
//				System.out.println("www"+nxtGrpKey);
				space_nums = groupList.get(i).get(1) + groupList.get(nxtGrpKey).get(1);
				cur_grp_num= groupList.get(i).get(0);
				nxt_grp_num = groupList.get(nxtGrpKey).get(0);
				
//				System.out.println(space_nums + "  "+cur_grp_num + "  "+ nxt_grp_num);
				
//				System.out.println(maps);
//				System.out.println(nextGrp+1);
				if(maps.containsKey(nxtGrpKey)) {
					lines = maps.get(nxtGrpKey);
				}
				element_score += space_nums * cur_grp_num * nxt_grp_num * lines;	
			}
		

		}
		return element_score;
	}
	
	static void grouping(List<List<Integer>> groupList, List<Map<Integer,Integer>> groupMap ) {
		
		int grp=0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(visit[i][j] !=true) {
					bfs(i,j,map[i][j], grp);
					List<Integer> pair = new ArrayList<>();
					pair.add(map[i][j]); pair.add(returnCnt);
					groupList.add(pair); //첫 번째가 map의 숫자, 두 번째가 갯수.
					returnCnt=1;
					grp++;
					
				}
				
				
			}
		}
		
//		print2D(group);
		
		visit = new boolean[N][N]; //초기화 
		
		grp=0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
//				print2D(visit);
				if(visit[i][j] !=true) {
					Map<Integer, Integer> accessMap = grpBfsLines(i,j, grp);
					groupMap.add(accessMap);
					grp++;
				}
				
				
			}
		}
		
		group = new int[N][N];
		visit = new boolean[N][N]; //초기화 
	}
	
	static void bfs(int x, int y, int num, int grp) {
		
		 //자기 자신 포함 1 
		Queue<int[]> Q = new LinkedList<>();
		int[] init_coord = new int[] {x,y, num};
		Q.add(init_coord);
		visit[x][y] = true;
		group[x][y] = grp;
		
		while(!Q.isEmpty()) {
			
			int[] coord = Q.poll();
			
			for(int dir=0; dir<4; dir++) {
				
				int new_x = coord[0] + dx[dir];
				int new_y = coord[1] + dy[dir];
				
				if(new_x<0 || new_x>=N ||new_y<0 || new_y>=N || visit[new_x][new_y]==true) continue;
				if(map[new_x][new_y] != num) continue;
				
				visit[new_x][new_y] = true;
				group[new_x][new_y] = grp;
				int[] new_coord = new int[] {new_x, new_y, num};
				returnCnt++;
				Q.add(new_coord);
				
				
			}
			
		}
		
		return;
		
		
	}

	
	static Map<Integer, Integer> grpBfsLines(int x, int y, int grp) {

		
		 //자기 자신 포함 1 
		Queue<int[]> Q = new LinkedList<>();
		Map<Integer, Integer> acessMap = new LinkedHashMap<>();
		int[] init_coord = new int[] {x,y, grp};
		Q.add(init_coord);
		visit[x][y] = true;
		
		while(!Q.isEmpty()) {
			
			int[] coord = Q.poll();
			
			for(int dir=0; dir<4; dir++) {
				
				int new_x = coord[0] + dx[dir];
				int new_y = coord[1] + dy[dir];
			
				
				if(new_x<0 || new_x>=N ||new_y<0 || new_y>=N || visit[new_x][new_y]==true) continue;

				if(group[new_x][new_y] != grp) {
					if(acessMap.containsKey(group[new_x][new_y])) {
						int nums = acessMap.get(group[new_x][new_y]);
						nums++;
						acessMap.put(group[new_x][new_y], nums);
					}else {
						acessMap.put(group[new_x][new_y], 1);
					}
					continue;
				}else {
				
				visit[new_x][new_y] = true;

				int[] new_coord = new int[] {new_x, new_y, grp};
				Q.add(new_coord);
				
				//나중에 꼭 visit 초기화 해줘야 한다.
				}
				
			}
			
		}
//		System.out.println(acessMap);
		
		return acessMap;
		
		
	}
}

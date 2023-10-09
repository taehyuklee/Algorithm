/*
 * 
 * N X N 격자에서 진행
 * 술래는 정중앙에 존재 
 * m명의 도망자가 있다 (중앙에서 시작하지 않는다) 좌우 유형, 상하 유형 존
 * 좌우 술래 -> 처음에 오른쪽만 보고 있다 
 * 상하술래 -> 항상 아래쪽을 보고 있다.
 * h개의 나무가 존재한다. - 나무는 도망자와 겹쳐 있는 것이 가능. 
 * 
 * process
 * (K번 반복)
 * 1. m명의 도망자가 먼저 동시에 움직인다.
 *    (도망자가 움직일때 술래와의 거리가 3이하인 도망자만 움직인다
 *    -도망자의 정의 절대 x1-x2 + 절대 y1-y2로 정의돤다.
 *    
 *    1.1 - 현재 바라보고 있는 방향으로 1칸 움직인다. 
 *       (격자 안벗어남)
 *        -> 움직이려는 칸에 술래가 있는 경우 움직이지 않는다. 
 *        -> 술래 x -> 해당 칸으로 움직인다. (나무는 괜찮아) 
 *        
 *        (격자 벗어남)
 *        -> 방향을 반대로 틀어준다. 바라보고 있는 방향으로 1칸 움직인다. (술래 없을때)
 *        
 *    
 * 2. 술래가 움직인다.
 *   술래는 달팽이 모양으로 움직인다. Spiral 좌표계 
 *   
 *   이때 바라보고 있는 방향으로 3칸 안에 있는 술래는 모두 잡히게 된다. 
 *   (이때 나무에 겹쳐 있는 술래는 잡히지 않는다)
 *   점수 계산 = t턴 X 술래의 수 
 * 
 *  Input
 *  격자, 술래 수, 나무 수, 턴 수 
 *  M줄에 걸쳐 도망자의 위치 
 *  x,y, d(이동 방향) -> d==1 좌우, d==2 상하로 움직임
 *  {2,3}, {0,1}
 *  dx={1,-1,0,0}, dy={0,0,1,-1}
 *  d==1 -> 좌우 시작은 realDir =1부터 (우)
 *  d==2 -> 상하 시작은 realDir =0부터 (하)
 *  
 *  이후 h개의 줄에 걸쳐 나무의 위치 x, y가 나옴 
 * 
 * */

import java.util.*;
import java.io.*;

class Person{
	int x, y, dir, realDir;
	
	public Person(int x, int y, int dir, int realDir) {
		this.x =x;
		this.y =y;
		this.dir =dir;
		this.realDir = realDir;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " dir: " + dir +  " readir: " + realDir + " ] "; 
	}
}

class Node{
	int x, y, dir;
	public Node(int x, int y, int dir) {
		this.x =x;
		this.y =y;
		this.dir = dir;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " dir: " + dir + " ] "; 
	}
}

public class Main {
	
	static int N,M,H,K,T, answer; //격자, 술래수, 나무 수, 턴 수 
	static Map<Integer, Node> spiralCoord;
	static List<Person> domangList;
	static List<Node> treeList;
	static boolean reverse;
	static int sulae_loc;
	static Node sulae;
	static int[] dx={1,-1,0,0}, dy={0,0,1,-1}; //1일때 -> 2,3 dir ==2일때 0,1 상하 
	static int[] sdx= {-1,0,1,0}, sdy= {0,1,0,-1}; //상 우 하 좌 
	        
	//급하게 쓰는거
	static Map<Integer, Integer> dirList;
	static Map<Integer, Integer> rDirList;

	
	public static void main(String[] args) throws FileNotFoundException {
				
		Scanner sc = new Scanner(System.in);
	
		N = sc.nextInt();
		M = sc.nextInt();
		H = sc.nextInt();
		K = sc.nextInt();
		
		domangList = new ArrayList<>();
		spiralCoord = new HashMap<>();
		treeList = new ArrayList<>();
		sulae = new Node(N/2, N/2,0);
		dirList =new HashMap<>(); rDirList  =new HashMap<>();
		reverse  = false;
		sulae_loc=1; //이거 초기하 안해서;;
		answer=0;
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int dir = sc.nextInt();
			int realDir = 0;
			
				/*  d==1 -> 좌우 시작은 realDir =2부터 (우)
				*  d==2 -> 상하 시작은 realDir =0부터 (하)*/
			if(dir==1) realDir =2;
			if(dir==2) realDir =0;
			domangList.add(new Person(x,y,dir,realDir));
		}
		
		for(int h=0; h<H; h++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			treeList.add(new Node(x,y,0));
		}
		
		makeSpiral();
		System.out.println(dirList);
		System.out.println(rDirList);
		

		solution();
		
		System.out.printf("%d", answer);
		
	}
	
	static void solution() {
		
		for(int turn=1; turn<=K; turn++) {
			//도망자  움직이기. 
			moveEscaper();
			
			//술래가 움직인다.
			Node sulae = moveSulae();
//			System.out.println(sulae);
//			int[][] map = new int[N][N];
//			map[sulae.x][sulae.y]=1;
//			for(int i=0; i<N; i++) {
//				for(int j=0; j<N; j++) {
//					System.out.print(map[i][j] + " ");
//
//				}
//				System.out.println();
//			}
//			System.out.println();
			
			catchEscaper(sulae, turn);
			
			if(domangList.size()==0) {
				break;
			}
		}

	}
	
	static void catchEscaper(Node sulae, int turn) {
		
		int tempScore = 0;
		
		for(int i=0; i<3; i++) {
			//기존에 spiral만들때 (돌아갈때) - sulae.dir이 잘못되어 있다.
			int new_x = sulae.x + sdx[sulae.dir]*i; 
			int new_y = sulae.y + sdy[sulae.dir]*i;
			
			if(ifTree(new_x, new_y)) continue;
			if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) continue;
			
			List<Person> escaperList = findEscaper(new_x, new_y);
			tempScore = turn*escaperList.size();
			answer += tempScore;
			
		}

		
		
	}
	
	static boolean ifTree(int x, int y) {
		
		for(int i=0; i<treeList.size(); i++) {
			Node tree = treeList.get(i);
			if(tree.x ==x && tree.y ==y) {
				return true;
			}
		}
		return false;
		
	}
	
	static List<Person> findEscaper(int x, int y) {
		
		List<Person> tempList = new ArrayList<>();
		for(int i=0; i<domangList.size(); i++) {
			Person domang = domangList.get(i);
			if(domang.x ==x && domang.y ==y) {
				tempList.add(domang);
				domangList.remove(domang);
			}
		}
		return tempList;
	}
	
	static Node moveSulae() {
		//static int[] sdx= {-1,0,1,0}, sdy= {0,1,0,-1}; //상 우 하 좌 
        //0  1  2  3
		int dir;

		if(reverse) {
			sulae_loc = (sulae_loc-1); //1부터지... 
			System.out.println(sulae_loc);
			dir = dirList.get(sulae_loc);
			if(sulae_loc != 1 || sulae_loc != N*N) {
				dir = rDirList.get(sulae_loc);
			}
			if(sulae_loc ==1) reverse = false;

		}else {
			sulae_loc = (sulae_loc+1); 
			dir = dirList.get(sulae_loc);
			if(sulae_loc ==N*N) reverse =true;

		}
		Node local_sulae = spiralCoord.get(sulae_loc); //여기에 sulae_loc +1을 남겨놨었다..
		local_sulae.dir = dir;
		sulae = local_sulae;
		return local_sulae;
		
	}
		
	
	static void moveEscaper() {
		
		for(int i=0; i<domangList.size(); i++) {
			Person escaper = domangList.get(i);
			
			int distance = Math.abs(escaper.x - sulae.x) + Math.abs(escaper.y - sulae.y);
			
			if(distance>3) continue; //거리가 3이하인 도망자만 움직인다.
			
			while(true) {
				int new_x = escaper.x + dx[escaper.realDir];
				int new_y = escaper.y + dy[escaper.realDir];
				
				if(new_x<0 || new_x>=N || new_y<0 || new_y>=N) {
					if(escaper.realDir==0) escaper.realDir=1;
					else if(escaper.realDir==1) escaper.realDir=0;
					else if(escaper.realDir==2) escaper.realDir=3;
					else if(escaper.realDir==3) escaper.realDir=2;
					continue;
				}
				
				if(new_x ==sulae.x && new_y ==sulae.y) {
					break; //술래가 있으면 안바꾸고 그대로 있는다. 방향만 전환됨.
				}
				
				escaper.x = new_x;
				escaper.y = new_y;
				break;

			}

		}
		
	}
	
	
	
	static void makeSpiral() { //이것만 40분 걸린듯..
		
		//착각했어 올라갈때랑 내려갈때랑 방향이 다르다는 것을...
		
		int x = N/2;
		int y = N/2;
		int index = 1;
		int dir=0;
		int cur_depth=0;
		int ref_depth=1;
		int num_rotate=0;
		//rotate 2번 되면 -> 길이가 증가한다. 1
		
		spiralCoord.put(index, new Node(N/2, N/2,0));
		dirList.put(index, dir);
		rDirList.put(index, dir);
		
		while(true) {

			int new_x = x + sdx[dir];
			int new_y = y + sdy[dir];
			
			index++;
//			System.out.println(ref_depth + " and curdepth:" + cur_depth);
			cur_depth++;
			
			spiralCoord.put(index, new Node(new_x, new_y, dir));
			rDirList.put(index, (dir+2)%4);
			dirList.put(index, dir);
			
			if(cur_depth == ref_depth) {
				
				dir = (dir+1)%4; //방향을 꺾어야지.
				spiralCoord.put(index, new Node(new_x, new_y, dir));
				dirList.put(index, dir);
				deciderDir(index, (dir+2)%4);
				num_rotate++;
				
				//방향을 꺾고나서 depth를 늘릴지는 생각해봐야 한다.
				if(num_rotate ==2) {
					ref_depth++;
					num_rotate=0;
				}

				cur_depth=0;
				
			}
			
			x = new_x;
			y = new_y;
			
			if(index ==N*N) {
				spiralCoord.put(index, new Node(new_x, new_y, 1));
				break;
			}

		}

	}
	
	static void deciderDir(int index, int dir) {

		//우 1 -> 상 0 ,  하 2 -> 우 1,  상 0 -> 좌 3,  좌 3 -> 하 2
		//static int[] sdx= {-1,0,1,0}, sdy= {0,1,0,-1}; //상 우 하 좌 
		//stack은 복사되지만 heap은 ㄴㄴ 안됨
		if(dir ==0) dir = 3;
		else if(dir ==1) dir = 0;
		else if(dir ==2) dir = 1;
		else if(dir ==3) dir = 2;
		rDirList.put(index, dir);
	}
	

}

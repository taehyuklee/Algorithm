import java.util.*;
/*
 * L X L 체스판
 * 자신의 마력으로 상대방을 밀쳐 낸다 
 * 각 기사의 초기 위치 (r,c) 방패 h x w, 체력 k
 * 
 * 1) 기사 이동 - 상하좌우 이동 가능 
 * -> 이동하려는 칸에 다른 기사가 있다면 그 기사도 함께 연쇄적으로 한 칸 밀려나가게 된다 
 * -> 옆에 또 기사가 있다면 연쇄적으로 한 칸씩 밀리게 된다 
 * -> (벽에서는 이동 불가) 
 * -> 체스판에서 사라진 기사에게 명령은 불가
 * 
 * 2) 대결 대미지 
 * -> 명령 받은 기사가 다른 기사 밀치면 피해를 입게 된다 
 * -> 기사가 이동한 곳에서 w x h 직사각형 내에 놓여 있는 함정의 수만큼 피해를 입게 된다
 * -> 각 기사마다 피해를 받은 만큼 체력이 깎이게 되며, 체력 0되면 체스판에서 사라짐
 * -> 명령 받은 기사는 피해 x
 * -> (함정 벗으면 피해 x)
 * 
 * Q번 명령이 주어짐 
 * Q번 이후 생존한 기사들이 총 받은 대미지의 합을 출력 
 * 
 *  주어진 격자에서 0 - 빈칸, 1- 함정, 2 - 벽 을 의미한다
 *  N개의 줄에 걸쳐 초기 기사들의 정보가 주어진다 (r, c, h, w, k) 
 *  (입력은 1번 기사부터 N번 기사까지 순서대로 정보가 주어진다)
 *  초기 기사들의 위치는 기사끼리 또는 벽과 겹치지 않는다.
 *  
 *  다음 Q개의 줄에 걸쳐 왕의 명령이 주어진다 (i, d)  i 번 기사에게 방향 d로 한 칸 움직이라 
 *  1<= i <= N (이미 사라진 기사 번호가 주어질 수도 있다) 
 *  d = {0,1,2,3} 위 오 아 왼 
 * */

class Kight{
	int id, r, c, h, w, k;
	boolean out;
	
	public Kight(int id, int r, int c, int h, int w, int k) {
		this.id = id;
		this.r = r;
		this.c = c;
		this.h = h;
		this.w = w;
		this.k = k;
		this.out = false;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", r: " + r + ", c: " + c + ", h: " + h + ", w : " + w + ", k: " + k + " + " + ", out : " + out + " ||";
	}
	
}

public class Main {
	
	static int L, N, Q;
	static int[][] envMap;
	static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
	static int[][] kightMap, kightMap2;
	static List<Kight> listKight;
	static List<int[]> orderList;
	
	public static void print1D(List<int[]> array) {
		for(int i=0; i<array.size(); i++) {
			System.out.println(array.get(i)[0] + " , " +array.get(i)[1]);
		}
	}

	public static void print2D(int[][] map) {
		for(int i=0; i<kightMap.length; i++) {
			for(int j=0; j<kightMap[0].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		L = sc.nextInt(); //격자 크기
		N = sc.nextInt(); //기사의 숫자
		Q = sc.nextInt(); //왕의 명령 횟수
		
		envMap = new int[L][L];
		kightMap = new int[L][L];
		kightMap2 = new int[L][L];
		listKight = new ArrayList<>();
		orderList = new ArrayList<>();
		
		for(int i=0; i<L; i++) {
			for(int j=0; j<L; j++) {
				envMap[i][j] = sc.nextInt();
			}
		}
		
		for(int i=1; i<=N; i++) {
			int r = sc.nextInt()-1; 
			int c = sc.nextInt()-1; 
			int h = sc.nextInt(); 
			int w = sc.nextInt();
			int k = sc.nextInt();
			Kight kight = new Kight(i,r,c,h,w,k);
			listKight.add(kight);
		}
		
		for(int q=0; q<Q; q++) {
			int i = sc.nextInt(); 
			int d = sc.nextInt(); 
			int[] order = new int[] {i,d};
			orderList.add(order);
		}
		
		drawKightMap();
		//print2D(kightMap);

		solution();
		
	}
	
	public static void solution() {
		
		//order
		for(int turn=0; turn<1; turn++) {
			
			int[] order = orderList.get(turn);
			int i = order[0];
			int d = order[1];
			
			//Phase1. 왕의 명령			
			//기사 선택
			Kight pickedKight = findKightById(i);
			if(pickedKight.out == true) continue; //pickedKight가 out이라면 다음 order로 넘긴다.
			
			
			//기사 이동
			moveKight(i, d);
			
			
			//대결 대미지
			
			
			
			
		}

	}
	
	static Kight findKightById(int id) {
		for(int i=0; i<listKight.size(); i++) {
			if(listKight.get(i).id == id) {
				return listKight.get(i);
			}
		}
		return null;
	}
	
	static void drawKightMap() {
		for(int i=0; i<listKight.size(); i++) {
			Kight kight = listKight.get(i);
			
			for(int j=0; j<kight.h; j++) {
				for(int k=0; k<kight.w; k++) {
					kightMap[kight.r+j][kight.c+k] = kight.id;
				}
			}
			
		}
	}
	
	static void moveKight(int id, int dir) {
		
		List<int[]> listed = moving(id, dir);
		
		//움직였을때 그 위치에 혹시나 그 기사가 아닌 다른 기사가 있으면 모두 밀린다. 

		
		
	}
	
	static List<int[]> moving(int id, int dir) {
		List<int[]> listInt = new ArrayList<>();
		for(int i=0; i<L; i++) {
			for(int j=0; j<L; j++) {
				if(i ==kightMap[i][j]) {
					int newI = i+dx[dir];
					int newJ = j+dy[dir];
					kightMap2[newI][newJ] = kightMap[i][j];
					listInt.add(new int[] {newI, newJ}) ;
				}
			}
		}
		return listInt;
	}
	
	
}




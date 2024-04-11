import java.util.*;

class Kight{
	int id, r, c, h, w, k, origin_k;
	boolean out;
	
	public Kight(int id, int r, int c, int h, int w, int k, int origin_k) {
		this.id = id;
		this.r = r;
		this.c = c;
		this.h = h;
		this.w = w;
		this.k = k;
		this.origin_k = origin_k;
		this.out = false;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", r: " + r + ", c: " + c + ", h: " + h + ", w : " + w + ", k: " + k + " + " + ", out : " + out + " ||";
	}
	
}

public class Main {
	
	static int L, N, Q, answer;
	static int[][] envMap, kightMap;
	static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
	static List<Kight> listKight;
	static List<int[]> orderList;
	static Set<Integer> attackedKight;
	static boolean returnToFirst;
	
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
		listKight = new ArrayList<>();
		orderList = new ArrayList<>();
		answer=0;
		
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
			Kight kight = new Kight(i,r,c,h,w,k,k);
			listKight.add(kight);
		}
		
		for(int q=0; q<Q; q++) {
			int i = sc.nextInt(); 
			int d = sc.nextInt(); 
			int[] order = new int[] {i,d};
			orderList.add(order);
		}

		solution();
		
	}
	
	public static void solution() {
		
		//order
		for(int turn=0; turn<Q; turn++) {
      
			int[] order = orderList.get(turn);
			int i = order[0];
			int d = order[1];
      
			//Phase1. 왕의 명령			
			//기사 선택
			Kight pickedKight = findKightById(i);
			if(pickedKight.out == true) continue; //pickedKight가 out이라면 다음 order로 넘긴다.
			
			//기사 이동 (drawKightMap을 그려야 checkAvaialbeMove에서 확인이 가능하다)
			drawKightMap();
			if(checkAvailableMove(pickedKight, d)) continue;

			//가능성 여부를 Check했으니 실제로 움직이자
			moveKight(pickedKight, d);

			//대결 대미지
			battle(pickedKight);
			
		}
		
		getAnswer();
		System.out.println(answer);

	}
	
	/*-------------- 기사 찾고 그리기 logic ---------------*/
	static Kight findKightById(int id) {
		for(int i=0; i<listKight.size(); i++) {
			if(listKight.get(i).id == id) {
				return listKight.get(i);
			}
		}
		return null;
	}
	
	static void drawKightMap() {	
		kightMap = new int[L][L]; //초기화 하고 그려주자
		for(int i=0; i<listKight.size(); i++) {
			Kight kight = listKight.get(i);
			if(kight.out == true) continue;
			for(int j=0; j<kight.h; j++) {
				for(int k=0; k<kight.w; k++) {
					kightMap[kight.r+j][kight.c+k] = kight.id;
				}
			}	
		}
	}
	
	
	/*------------- 움직임 Check Logic --------------------*/
	static boolean checkAvailableMove(Kight orderedKight, int dir) {
				
		attackedKight = new HashSet<>();
		returnToFirst = false; //return관련 초기화
		
		//움직였을때 그 위치에 혹시나 그 기사가 아닌 다른 기사가 있으면 모두 밀린다. 
		recursiveKight(orderedKight, dir);
		
		if(attackedKight.size() ==0) {
			//여기서 자기자신을 빼고 했으니, 자기 자신만 움직일수 있음에도 불구하고 움직이지 않게 된다.
			//본인도 움직일 수 있는지 확인 (recursive 마지막에 반영함)
			return true;
		}
		
		return false;
	}
	
	static void recursiveKight(Kight kight, int dir) {
		
		List<int[]> movedArray = new ArrayList<>();
		
		if(dir ==0) {
			//위
			for(int i=0; i<kight.w; i++) {
				int[] array = new int[] {kight.r-1, kight.c+i};
				movedArray.add(array);
			}
			
		}else if(dir==1) {
			//오
			for(int i=0; i<kight.h; i++) {
				int[] array = new int[] {kight.r+i, kight.c+kight.w};
				movedArray.add(array);
			}
			
		}else if(dir==2) {
			//아
			for(int i=0; i<kight.w; i++) {
				int[] array = new int[] {kight.r+kight.h, kight.c+i};
				movedArray.add(array);
			}
			
		}else if(dir==3) {
			//왼
			for(int i=0; i<kight.h; i++) {
				int[] array = new int[] {kight.r+i, kight.c-1};
				movedArray.add(array);
			}
			
		}
		
		if(wallCheck(movedArray)) return;
		//밀린 곳에 다른 kight가 있는 것인가?
		Set<Integer> otherKight = new HashSet<>();

    //첫 번째 명령 받은 kight를 위해서 넣는 것.
		for(int i=0; i<movedArray.size(); i++) {
			int[] element = movedArray.get(i);
			if(kightMap[element[0]][element[1]] != kight.id && kightMap[element[0]][element[1]]!=0) {
				otherKight.add(kightMap[element[0]][element[1]]);
				attackedKight.add(kightMap[element[0]][element[1]]);
			};
		}
		
		List<Integer> otherKinghtList = new ArrayList<>(otherKight);
		for(int i=0; i<otherKinghtList.size(); i++) {
			int id = otherKinghtList.get(i);

			Kight pickedKight = findKightById(id);
			recursiveKight(pickedKight, dir);
			
			//만약 wallCheck가 해당된다면 Listing하던거 날리고 return해준다.
			if(returnToFirst == true) {
				attackedKight = new HashSet<>();
				return;
			}
			
		}

		//마지막에 wallcheck 다 끝난다음 여기서는 자기 자신을 추가해도 된다. (자기는 움직일 수 있는 거니까)
		attackedKight.add(kight.id);
		
	}
	
	static boolean wallCheck(List<int[]> movedArray) {
		//out of bound랑 
		for(int i=0; i<movedArray.size(); i++) {
			int[] element = movedArray.get(i);
			if(element[0]<0 || element[1]>=L || element[0]>=L || element[1]<0) {
				returnToFirst = true;
				return true;
			}
			
			//wall check
			if(envMap[element[0]][element[1]] == 2) {
				returnToFirst = true;
				return true;
			}
			
		}
		return false;
	}
	
	/*------------- 실제 움직임 Logic --------------------*/
	static void moveKight(Kight pickedKight, int d) {
		//d방향으로 한칸씩 움직여야 할 kight들의 id값
		//attackedKight (r,c 기준점만 움직여 주면 된다) 
		attackedKight.add(pickedKight.id); //자기 자신 포함 (하지만 대미지는 안받는다) recursive에서 그냥 너
		List<Integer> candidateList = new ArrayList<>(attackedKight);
		
		for(int m=0; m<candidateList.size(); m++) {
			int id = candidateList.get(m);
			Kight movingKight = findKightById(id);
			if(movingKight.out == true) continue; //out이면 안함.
			movingKight.r += dx[d];
			movingKight.c += dy[d];
		}

	}
	
	/*------------- 대결 Logic --------------------*/
	static void battle(Kight pickedKight) {
		List<Integer> candidateList = new ArrayList<>(attackedKight);
		for(int i=0; i<candidateList.size(); i++) {
			int id = candidateList.get(i);
			Kight kight = findKightById(id);
			if(kight.out == true || kight == pickedKight) continue;
			minusLife(kight);			
		}
	}
	
	static void minusLife(Kight kight) {
		int trapCnt = 0;
		for(int i=0; i<kight.h; i++) {
			for(int j=0; j<kight.w; j++) {
				if(envMap[kight.r+i][kight.c+j]==1) trapCnt++;
			}
		}
		kight.k -= trapCnt;
		if(kight.k<=0) {
			kight.out = true;
		}
	}
	
	/*------------- getAnswer --------------------*/
	static void getAnswer() {
		for(int i=0; i<listKight.size(); i++) {
			Kight kight = listKight.get(i);
			if(kight.out != true) {
				answer+= kight.origin_k - kight.k;
			}
		}
	}
}

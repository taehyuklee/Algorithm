package SamsungStartTaxi;

import java.util.*;
import java.io.*;

class Taxi{
	
	int taxiX, taxiY, movement;

	public Taxi(int taxiX, int taxiY, int movement) {
		super();
		this.taxiX = taxiX;
		this.taxiY = taxiY;
		this.movement = movement;
	}

	@Override
	public String toString() {
		return "Taxi [taxiX=" + taxiX + ", taxiY=" + taxiY + ", movement=" + movement + "]";
	}

}

class Passenger{
	
	int startX, startY, endX, endY, index, dist;

	public Passenger(int startX, int startY, int endX, int endY, int index) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.index = index;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getDist() {
		return dist;
	}

	@Override
	public String toString() {
		return "Passenger [startX=" + startX + ", startY=" + startY + ", endX=" + endX + ", endY=" + endY + ", index="
				+ index + ", dist=" + dist + "]";
	}


}


public class SamsungStartTaxi {
	
	
	static int [][] map = new int[21][21];
	static int taxiX, taxiY;
	static int passStartX, passStartY, passEndX, passEndY;
	static int N, M, fuel;
	static Passenger taken;
	static List<Passenger> passList = new ArrayList<>();
	static Map<Integer, Passenger> passMap = new HashMap<>();
	static List<Passenger> waitList = new ArrayList<>();
	
	
	public static void print2D(int[][] arr) {
		for(int i=1; i<N+1; i++) {
			for(int j=1; j<N+1; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print2D(boolean[][] arr) {
		for(int i=1; i<N+1; i++) {
			for(int j=1; j<N+1; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static int bfs() {
	
		Queue<Taxi> q = new LinkedList<>();
		
		//visit array를 만든다.
		boolean [][] visit = new boolean[21][21];
	
//		for(int i=1; i<N+1; i++) {
//			Arrays.fill(visit[i], false);
//		}
		
		//System.out.println(Arrays.deepToString(visit));
		//Passenger passenger= passList.remove(0);
		
		//첫 위치에 있는 taxi 객체를 만들어서
		Taxi taxi = new Taxi(taxiX, taxiY, 0);
		visit[taxiX][taxiY] = true;
		
		//q에 넣어준다
		q.add(taxi);
		
		//동서남북 정해준다 (상하좌우)로
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		
		int movement = 0;
		
		while(!q.isEmpty()) {
			
			Taxi tempTaxi = q.poll();
			taxiX = tempTaxi.taxiX; 
			taxiY = tempTaxi.taxiY;
			movement = tempTaxi.movement;
			
			int newTaxiX = 0;
			int newTaxiY = 0;
			//첫번째 위치 true로 해준다. 이걸 빼먹었었다.

			//만약 그 위치가 승객이 있다면 
			if(taken ==null) {
				if(passMap.get(map[taxiX][taxiY]) !=null) {
					if(map[taxiX][taxiY] >1) {
						//System.out.println(taxiX  + " "+ taxiY + " "+ movement);
						//승객이 존재한다는 의미 -> 태워야 한다
						passMap.get(map[taxiX][taxiY]).dist = movement;
						waitList.add(passMap.get(map[taxiX][taxiY]));
						//System.out.println(waitList.size());
						
					}
				}
			}else {
				if(map[taxiX][taxiY] == - taken.index) {
					map[taxiX][taxiY] = 0;
					return movement;
				}
			}
			
			//waitList에 사람이 없을때만 그대로 진행
			if(waitList.size() ==0) { //이런 조건에서 헷갈리면 안된다 <- 어떻게하면 실수나 생각의 시간을 줄일수 있을까?
				for(int i=0; i<4; i++) {
					newTaxiX = taxiX + dx[i];
					newTaxiY = taxiY + dy[i];

						if(newTaxiX>=1 && newTaxiX<N+1 && newTaxiY>=1 && newTaxiY<N+1 && visit[newTaxiX][newTaxiY] == false) {
							if(map[newTaxiX][newTaxiY] !=1) {
								visit[newTaxiX][newTaxiY] = true;
								Taxi newTaxi = new Taxi(newTaxiX, newTaxiY, movement+1);
								q.add(newTaxi);
							}
					}
				}
			}
		}//while문 마지막
		//print2D(visit);
		return movement;
	}
	
	public static void solution() {
		
		//아래의 과정을 passList가 0이 될때까지 (승객을 모두 데려다 줄때까지 반복한다)
		boolean success = true;
		for(int i=0; i<M; i++) {
			//현재 택시에서 가장 가까운 승객까지\
			//System.out.println("count" + " " + i);
			int consumesPass  = bfs();		
			//System.out.println(waitList);
			
			if(waitList.size()>1) {
				Collections.sort(waitList, Comparator.comparing(Passenger::getDist).reversed().thenComparing(Passenger::getStartX)
						.thenComparing(Passenger::getStartY));
			}
			
			if(waitList.size() !=0) {
				taken = waitList.remove(0); //첫번째꺼 빼내고
				fuel -= taken.dist; // 앞서 마지막에 반환되는 값으로 그냥 넣었더니 안됨 문제점 노션에 기록함.
				passMap.remove(map[taken.startX][taken.startY]);
				map[taken.startX][taken.startY] = 0;
				waitList.clear(); // waitList는 초기화 해줘야 한다.
				//현재 위치가 taxi의 위치가 된다
				taxiX = taken.startX;
				taxiY = taken.startY;
			}else {
				//승객이 없다는 얘기가 됨 -> 실패
				success = false;
				break;
			}
	
			if(fuel <0) {
				success = false;
				break;
			}else {
				//승객부터 그 승객의 목적지까지
				int consumesDest = bfs();
				if(consumesDest ==0) {
					success = false;
					break;
				}
				//System.out.println(consumesDest);
				fuel -= consumesDest;
				//System.out.println("after dest" + fuel);
				
				if(fuel <0) {
					success = false;
					break;
				}else {
					fuel += consumesDest*2;
				}
				
			}
			
			taken = null; // taken은 한 바퀴 돌면 다시 초기화 해줘야 한다.
		}
		if(success) {
			System.out.println(fuel);
		}else {
			System.out.println(-1);
		}
		
		
	}
	

	public static void main(String[] args) throws IOException {
		
		//Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 한 번쯤만 파보자
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		//br.readLine(); <- 한 줄을 읽어서 String으로 반환해준다 6 3 15 -> "6 3 15"
		//그것을 Tokenizer 토큰화하는 객체에 넣고 nextToken을하면서 빼준다
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		
		for(int i=1; i<N+1; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<N+1; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		taxiX = Integer.parseInt(st.nextToken());
		taxiY = Integer.parseInt(st.nextToken());
		
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			passStartX = Integer.parseInt(st.nextToken());
			passStartY = Integer.parseInt(st.nextToken());
			passEndX = Integer.parseInt(st.nextToken());
			passEndY = Integer.parseInt(st.nextToken());
			//map에 표기해줘야 한다 -> BFS사용할때 알아야 하니까 (map을 탐험하게 될테니까)
			map[passStartX][passStartY] = i+2;
			map[passEndX][passEndY] = -(i+2);
			Passenger passenger = new Passenger(passStartX, passStartY, passEndX, passEndY, i+2); //마지막에 승객 번호를 부여해준다 (2부터)
			passMap.put(i+2, passenger);
			//passList.add(passenger);
		}
		
		print2D(map);
		
		solution();
			
	}
		
}

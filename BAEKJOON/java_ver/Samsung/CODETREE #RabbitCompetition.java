import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


class Node{
	int r, c, d, pid, jump;
	long score;
	boolean jumpBool;

	public Node(int r, int c, long score, int d, int pid, int jump) {
		this.r = r;
		this.c = c;
		this.score = score;
		this.d = d;
		this.pid = pid;
		this.jump = jump;
	}
	
	public String toString() {
		return "[pid: " + pid + ", r: " + r + ", c: " + c + ", score: " + score  + ", jump: " + jump  + ", d: " +d +  " 점프 여부: " + jumpBool +"]";
	}
}

public class Main {
	
	static int Q, N, M, P;
	static long minusScore;
	static List<Node> rabbitList;
	static Map<Integer, Node> rabbitMap;
//	static PriorityQueue<Node> pickRabbitPq, biggerRabbitPq;
	static int[][] map;
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	
	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(System.in);
		
		Q = sc.nextInt();
		sc.nextLine();

		for(int turn=0; turn<Q; turn++) {
			String[] orders = sc.nextLine().split(" ");
						
			if("100".equals(orders[0]))	order100(orders);
	
			if("200".equals(orders[0])) order200(orders);
			
			if("300".equals(orders[0]))	order300(orders);

			if("400".equals(orders[0]))	order400(orders);
			
		}
	}
	
	public static void order100(String... orders) {
		rabbitList = new ArrayList<>();
		rabbitMap = new HashMap<>();
		
		N = Integer.valueOf(orders[1]); //valueOf랑 parse Int의 차이는?
		M = Integer.valueOf(orders[2]); 
		P = Integer.valueOf(orders[3]); 
		
		for(int i=0; i<P; i++) {
			
			int p_i = 4+i*2;
			int d_i = 5+i*2;
			int p = Integer.parseInt(orders[p_i]);
			int d = Integer.parseInt(orders[d_i]);
			
			Node node = new Node(1,1,0,d,p,0);
			rabbitMap.put(p, node);
			rabbitList.add(node);
		}
	}
	
	public static void order200(String...orders ) {

		minusScore=0;
		int K = Integer.parseInt(orders[1]);
		int S = Integer.parseInt(orders[2]);
		
		for(int k=0; k<K; k++) {
			
			Collections.sort(rabbitList, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					
					if(o1.jump -o2.jump==0) {
						
						if((o1.r+o1.c) - (o2.r + o2.c)==0) {
							
							if(o1.r - o2.r==0) {
								
								if(o1.c - o2.c==0) {
									return o1.pid - o2.pid;
								}
								return o1.c - o2.c;
							}
							return o1.r - o2.r;
						}
						return (o1.r+o1.c) - (o2.r + o2.c);
					}

					return o1.jump - o2.jump;
				}
			});
			
			
			Node rabbit = rabbitList.get(0);
			
			//이동 d만큼 
			List<int[]> coordList = new ArrayList<>();
			for(int dir=0; dir<4; dir++) {
				
				int new_r = rabbit.r + dx[dir]*rabbit.d;
				int new_c = rabbit.c + dy[dir]*rabbit.d;
				
				//격자 밖으로 나갔을때, 
				if(new_r<1 && dir==0) { //상 
					//r=2로 이동
					int len1 = rabbit.d- (rabbit.r-1);
					int rest = len1%(N-1);
					int hae = (int) len1/(N-1);
					
					
					if(hae%2==0) {
						new_r = 1+rest;
					}else {
						new_r = N-rest;
					}
					
				}else if(new_r>=N+1 &&  dir==1){ // 하 
					//r=N-1로 이
					int len1 = rabbit.d- (N - rabbit.r);
					int rest = len1%(N-1);
					int hae = (int) len1/(N-1);
					if(hae%2==0) {
						new_r = N-rest;
					}else {
						new_r = 1+rest; 
					}


				}else if(new_c<1 &&  dir==2) { // 좌 
					//c=1로 이동 
					int len1 = rabbit.d- (rabbit.c-1);
					int rest = len1%(M-1);
					int hae = (int) len1/(M-1);
					

					if(hae%2==0) {
						new_c = 1+rest;
					}else {
						new_c = M-rest; 
					}
	
				}else if(new_c>=M+1 &&  dir==3){ //우 
					//c=N-1로 이
					int len1 = rabbit.d- (M-rabbit.c);
					int rest = len1%(M-1);
					int hae = (int) len1/(M-1);
										
					if(hae%2==0) {
						new_c = M-rest; 
					}else {
						new_c = 1+rest;
					}

				}
				
				int[] newCoord = new int[] {new_r, new_c};
				coordList.add(newCoord);	
			
			}

			Collections.sort(coordList, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					
					if((o2[0] + o2[1]) - (o1[0] + o1[1])==0){
						
						if(o2[0]-o1[0]==0) {
							return o2[1]-o1[1];
						}
						
						return o2[0]-o1[0];
					}
					
					return (o2[0] + o2[1]) - (o1[0] + o1[1]);
				}
			});
			
			int[] new_location = coordList.get(0);
			
			/***  4개중에 위치 뽑음. ****/
			//위치 이동.
			rabbit.r = new_location[0];
			rabbit.c = new_location[1];
			rabbit.jumpBool = true; //jump 여부 바꿔줌.
			rabbit.jump +=1;

			//P-1마리 토끼 모두 점수 더해주기 
			for(int i=0; i<rabbitList.size(); i++) {
				Node node = rabbitList.get(i);
				if(node == rabbit) continue;
					node.score += rabbit.r +rabbit.c;
			}
			minusScore = rabbit.r + rabbit.c;
		}
		
		//K번 명령 이후 
		Collections.sort(rabbitList, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
					
					if((o2.r+o2.c) - (o1.r + o1.c)==0) {
						
						if(o2.r - o1.r==0) {
							
							if(o2.c - o1.c==0) {
								return o2.pid - o1.pid;
							}
							return o2.c - o1.c;
						}
						return o2.r - o1.r;
					}
					return (o2.r+o2.c) - (o1.r + o1.c);
			}
		});
		
		boolean first = false;
		for(int r = 0; r<rabbitList.size(); r++) {
			Node rabbit = rabbitList.get(r);
			
			if(rabbit.jumpBool==true && first == false) {
				rabbit.score+=S;
				first = true;
			}
			rabbit.jumpBool = false;
		}	
	}
	
	public static void order300(String...orders ) {
		int pid = Integer.valueOf(orders[1]); 
		int L =  Integer.valueOf(orders[2]); 
		Node rabbit = rabbitMap.get(pid);
		rabbit.d *=L;
	}
	
	
	public static void order400(String...orders ) {
		Collections.sort(rabbitList, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				long diff = o2.score - o1.score;				
				return (int) diff;

			}
		});
		System.out.println(rabbitList.get(0).score);

	}

}

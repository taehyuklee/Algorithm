import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "("+"x: " + this.x + " y: " + this.y + ")";
	}
	
	public Node(Node node) {
		this.x = node.x;
		this.y = node.y;
	}
}

public class Main {

	static int N, M, K, answer, dirCount;
	static int[][] board, board2;
	static boolean[][] visit;
	static Map<Integer, Map<Integer, Node>> mapArray = new HashMap<>();
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
 	public static void print(int[][] array) {	
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
		K = sc.nextInt();
		
		board = new int[N][N];
		board2 = new int[N][N];
		visit = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				board[i][j] = sc.nextInt();
				board2[i][j] = board[i][j];
				
			}
		}

		solution();
		
	}
	
	public static void solution() {
		
		//기차 각 팀에서 연결해서 만들어주기.
		makeTrain();
		
		for(int turn=0; turn<K; turn++) {
			//기차 움직이기
			moveTrain();

			//공 날리기
			throwGift(turn);

		}
		
		System.out.println(answer);

	}
	
	public static void makeTrain() {
		
		int team = 1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(board[i][j] ==1) {
					mapArray.put(team, new HashMap<Integer, Node>());
					bfs(i, j, team);
					team+=1;
				}
				
			}
		}
		//System.out.println(mapArray);
	}
	
	public static void bfs(int x, int y, int team) {
		

		int lenIndx = 1;
		
		Queue<Node> queue = new LinkedList<>();
		Node node0 = new Node(x, y);
		mapArray.get(team).put(lenIndx, node0);
		lenIndx +=1;
		
		visit[x][y] = true;

		queue.add(node0);
		
		while(!queue.isEmpty()) {
			
			Node node = queue.poll();
			
			if(lenIndx==2) {
				for(int dir=0; dir<4; dir++) {
					int newX = node.x + dx[dir];
					int newY = node.y + dy[dir];
					
					if(newX>=0 && newX<N && newY>=0 && newY<N  && visit[newX][newY] ==false) {
						if(board[newX][newY] ==2) {
							Node newNode = new Node(newX, newY);
							mapArray.get(team).put(lenIndx, newNode);
							queue.add(newNode);
							visit[newX][newY] = true;
							lenIndx+=1;
						}
						}
					}
				}else {
					for(int dir=0; dir<4; dir++) {
						int newX = node.x + dx[dir];
						int newY = node.y + dy[dir];
						
						if(newX>=0 && newX<N && newY>=0 && newY<N  && visit[newX][newY] ==false) {
								
							if(board[newX][newY] ==2 || board[newX][newY] ==3) {
								Node newNode = new Node(newX, newY);
								mapArray.get(team).put(lenIndx, newNode);
								queue.add(newNode);
								visit[newX][newY] = true;
								lenIndx+=1;
							}
						}
					}
				}
		}
	}
	
	
	public static void moveTrain() {
		for(int team=1; team<=M; team++) {
			Map<Integer, Node> temaNodes = mapArray.get(team);
			
			int headX_o=0;
			int headY_o=0;
			int goHX=0;
			int goHY=0;
			boolean lastFlag = false;
			
			//그 팀의 기차 길이만큼
			for(int len=1; len<=temaNodes.size(); len++) {
				Node headNode = temaNodes.get(len);

				//선두주자 움직이고
				if(len==1) {
					headX_o = headNode.x;
					headY_o = headNode.y;

					for(int dir=0; dir<4; dir++) {
						int newHX = headX_o + dx[dir];
						int newHY = headY_o + dy[dir];
						
						if(newHX>=0 && newHX<N && newHY>=0 && newHY<N) {
							if(board[newHX][newHY] ==4 ) {
								goHX = newHX;
								goHY = newHY;
							}else if(board[newHX][newHY] ==3) {
								//꼬리 물기를 한다면?
								goHX = newHX;
								goHY = newHY;
								lastFlag = true;
							}
						}
					}
					
					headNode.x = goHX;
					headNode.y = goHY;
					//board 동기화
					board[goHX][goHY] = 1;

					
				}else {
					//다음주자들 온 길을 따라 붙는다.
					//앞으로 나아갈길에 이전 선두주자에 있었던 좌표 -> goHX가 앞으로 갈 데이터가됨
					goHX = headX_o;
					goHY = headY_o;
					
					//과거 데이터 업데이트 현재 기차칸으로 -> headX_o이 과거 데이터로 됨
					headX_o = headNode.x;
					headY_o = headNode.y;
					
					//Map update & board 업데이트 (현재 기차칸을 앞으로 옮기기)
					headNode.x = goHX;
					headNode.y = goHY;

					board[goHX][goHY] = board[headX_o][headY_o];

					
					//과거 좌표를 현재의 좌표로 Update 다음 iteration을 위해서.
					if(len == temaNodes.size() && !lastFlag) {
						//마지막일때는 4로 update
						board[headX_o][headY_o] =4;	
					}
					
					//마지막 꼬리 물기
					if(len == temaNodes.size() && lastFlag) {
						board[goHX][goHY] = 3;
						board[headX_o][headY_o] =1;
						lastFlag = false;
					}

				}
				
			}
			
		}
	}

	public static void throwGift(int turn) {
		
		//방향전환 부분
		turn = turn%N;

		if(dirCount == 0) {
			for(int j=0; j<N; j++) {
				if(board[turn][j] !=4 && board[turn][j] !=0) {
					int order = findTrainOrder(turn, j);
					
					answer += order*order;
					break;
				}
			}
			
		}
		else if(dirCount == 1) {
			for(int i=0; i<N; i++) {
				if(board[N-1-i][turn] !=4 && board[N-1-i][turn] !=0) {
					int order = findTrainOrder(N-1-i, turn);
					answer += order*order;
					break;
				}
			}
			
		}
		else if(dirCount == 2) {
			for(int j=0; j<N; j++) {
				if(board[N-1-turn][N-1-j] !=4 && board[N-1-turn][N-1-j] !=0) {
					int order = findTrainOrder(N-1-turn, N-1-j);
					answer += order*order;
					break;
				}
			}
			
		}
		else if(dirCount == 3) {
			for(int i=0; i<N; i++) {
				if(board[i][N-1-turn] !=4 && board[i][N-1-turn] !=0) {
					int order = findTrainOrder(i, N-1-turn);
					answer += order*order;
					break;
				}
			}
			
		}
		
		//side방향 전환
		if(turn == N-1) {
			dirCount = (dirCount+1)%4;
		}
		
	}
	
	public static int findTrainOrder(int x, int y) {
		
		int keyTrain = 0;
		
		//team을 찾고 그 team안에서 member를 찾는다.
		for(int team=1; team<=mapArray.size(); team++) {
			Map<Integer, Node> train = mapArray.get(team);
			
			for(int len=1; len<=train.size(); len++) {

				if(train.get(len).x ==x && train.get(len).y ==y) {
					//좌표가 일치한다면 그때의 key를 가져온다.
					keyTrain = len;
					
					//기차 꼬리, 머리 바꾸기 
					makdeReverse(train);
					break;
				}
			}
			
		}
		
		return keyTrain;
	}

	
	public static void makdeReverse(Map<Integer, Node> targetTrain) {
		
		Map<Integer, Node> copyTrain = new HashMap<>();

		//기차 copy하는 부분
		for(int len =1; len<=targetTrain.size(); len++) {
			copyTrain.put(len, new Node(targetTrain.get(len)));
		}

		int start =0, startX=0, startY=0;
		int end = 0, endX=0, endY=0;
		
		//역으로 바꿔주기
		for(int len=1; len<=targetTrain.size(); len++) {
			
			if(len==1) {
				start = board[copyTrain.get(targetTrain.size()+1-len).x][copyTrain.get(targetTrain.size()+1-len).y];
				startX = targetTrain.get(len).x;
				startY = targetTrain.get(len).y;
			}else if(len ==targetTrain.size()) {
				end = board[copyTrain.get(targetTrain.size()+1-len).x][copyTrain.get(targetTrain.size()+1-len).y];
				endX = targetTrain.get(len).x;
				endY = targetTrain.get(len).y;
			}
			
			targetTrain.get(len).x = copyTrain.get(targetTrain.size()+1-len).x;
			targetTrain.get(len).y = copyTrain.get(targetTrain.size()+1-len).y;
			

		}
		board[startX][startY] = start;
		board[endX][endY] = end;

	}

}

import java.util.*;
import java.io.*;

class Node{
	int x;
	int y;
	int rmTree;
	
	public Node(int x, int y, int rmTree) {
		this.x = x;
		this.y = y;
		this.rmTree = rmTree;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + " , " + this.y + ")";
	}
	
	public Node(Node node) {
		this.x = node.x;
		this.y = node.y;
		this.rmTree = node.rmTree;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getRmTree() {
		return this.rmTree;
	}
}

public class Main {

	static int N, M, K, C, answer=0;
	static int[][] board, board2, rmBoard, restBoard, testBoard;
	static int[] dx = {-1,1,0,0}, dx_c = {-1,-1,1,1};
	static int[] dy = {0,0,-1,1}, dy_c = {1,-1,-1,1};
	
	public static void print(int[][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + "   ");
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
		C = sc.nextInt();
		
		board = new int[N][N];
		board2 = new int[N][N];
		rmBoard = new int[N][N];
		restBoard = new int[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				
			}
		}

		solution();
	}
	
	public static void solution() {
		
		for(int turn=0; turn<M; turn++) {
			//나무의 성장 (4방향에 나무 수만큼)
			gorwTree();
			
			//나무의 번식 (자기 숫자/빈자리)
			babyTree();		

			//제초제 뿌리기 (대각선 k만큼 전파)
			List<Node> curRvPoints = getRemovedBoard();

			//1년이 지나서 현재 뿌려진거 말고 그 외에 땅 +1 회복
			recovery(curRvPoints);

			rmBoard = new int[N][N];
			
		}
		System.out.println(answer);
		
		
	}
	
	public static void gorwTree() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(restBoard[i][j]>0) {
					continue;
				}
				
				int treeCnt = 0;
				for(int dir=0; dir<4; dir++) {
					
					int newX = i + dx[dir];
					int newY = j + dy[dir];
					
					if(newX>=0 && newX<N && newY>=0 && newY<N) {
						if(board[i][j] !=0 && board[i][j] != -1 && board[newX][newY] != 0 && board[newX][newY] != -1 && restBoard[newX][newY]==0) {		
							// board[newX][newY]>0 이조건은 아직 오염지역인 부분 제외
							treeCnt+=1;
						}
					}
				}
				board[i][j] += treeCnt;
				
			}
		}
	}
	
	public static void babyTree() {
		
		copyBoard(board, board2);
		List<Node> emptyNodes;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(board2[i][j] !=0 && board2[i][j] != -1 && restBoard[i][j]==0) {
					//board2[i][j]>0은 오염지역 때문에 
					int empty=0;
					emptyNodes = new ArrayList<Node>();

					for(int dir=0; dir<4; dir++) {
						
						int newX = i + dx[dir];
						int newY = j + dy[dir];
						
						if(newX>=0 && newX<N && newY>=0 && newY<N) {
							if(board2[newX][newY] ==0 && restBoard[newX][newY]==0) {
								empty +=1;
								emptyNodes.add(new Node(newX,newY,0));
							}
						}
					}
					if(empty==0) {
						continue;
					}
					int babyTree = (int) board2[i][j]/empty;
					
					//나무 번식 꽂아주기
					for(int e=0; e<emptyNodes.size(); e++) {
				
						board[emptyNodes.get(e).x][emptyNodes.get(e).y] += babyTree;

					}
				}
			}
		}
		
	}
	
	public static List<Node> getRemovedBoard() {
		
		copyBoard(board, board2);
		
		//가장 많이 삭제될 것 같은 곳
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				if(board2[i][j] != -1 && board2[i][j] !=0 && restBoard[i][j] ==0) {
					//board2[i][j]>0 오염지역 
					int removeCnt =0;
					
					for(int dir = 0; dir<4; dir++) {

						for(int len =1; len<=K; len++) {
							//자기 자신 포함해야하니까 len =0부터 시작하면 된다. (4번 중복돼서 안됨)
							int newX = i + dx_c[dir]*len;
							int newY = j + dy_c[dir]*len;
							
							if(newX>=0 && newX<N && newY>=0 && newY<N) {
								
								if(board2[newX][newY] ==0 || board2[newX][newY] == -1 || board2[newX][newY] <0) {
									// board2[newX][newY] <0 이거 빼먹어서 개 고생 (오염 전파된 곳도 더하면 안됨)
									//벽이나 빈 공간 만나면 더 이상 전파가 안된다.
									break;
								}else {
								//나무가 얼마나 제거되는지 볼수 있다.
									if(board2[newX][newY]>0) {//오염지역은 더하면 안된다.
										removeCnt += board2[newX][newY];
										
									}
								}
							}
						}
	
					}
					//마지막에 자기 자신 더해준다
					removeCnt += board2[i][j];
					rmBoard[i][j] = removeCnt;
				}
	
			}
		}
		
		//좌표 골라오기
		List<Node> returnedPoints= getRmvPoint();
		
		if(returnedPoints.size() != 0) {
			Node trgetNode= returnedPoints.get(0);
	
			//진짜 나무 제거및 점수 +
			List<Node> removedPoints = removeTree(trgetNode);
			
			
			return removedPoints;
		}
		return null;
	}
	
	
	public static List<Node> getRmvPoint() {
		
		List<Node> points = new ArrayList<Node>();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(rmBoard[i][j] !=0) {
					points.add(new Node(i, j, rmBoard[i][j]));
				}
			}
		}
		
		Collections.sort(points, Comparator.comparing(Node::getRmTree).reversed().thenComparing(Node::getX).thenComparing(Node::getY));

		return points;
		
	}
	
	public static List<Node> removeTree(Node trgetNode) {
		int removeX = trgetNode.x;
		int removeY = trgetNode.y;
		List<Node> remvoedPoitns = new ArrayList<Node>();
		
		//제거 점수 올리기
		answer +=  trgetNode.rmTree;
		
		//좌표 동기화 시키기
		for(int dir = 0; dir<4; dir++) {
			boolean endProp = false;
			for(int len =1; len<=K; len++) {
				
				//자기 자신 포함해야하니까 len =0부터 시작하면 된다. (4번 중복돼서 안됨)
				int newX = removeX + dx_c[dir]*len;
				int newY = removeY + dy_c[dir]*len;
				
				if(newX>=0 && newX<N && newY>=0 && newY<N) {
					
					//성은 그냥 바로 차단
					if(board2[newX][newY] == -1) {
						break;
						
					}else if(board2[newX][newY] ==0 || board2[newX][newY]<0 && board2[newX][newY] != -1) {
						//그 장소까지만 약이 뿌려짐 (오염지역은 바녕이 안되어 있음)
						//board2[newX][newY] != -1 이 오염지역이 반영이 안되어 있었음
						endProp  = true;
					}
					//나무가 얼마나 제거되는지 볼수 있다.
					restBoard[newX][newY] = C+1;
					board[newX][newY] = -10;
					remvoedPoitns.add(new Node(newX, newY, 0));
					
					if(endProp) {
						break;
					}
				}
			}

		}
		//자기 자신도 
		restBoard[removeX][removeY] = C+1;
		board[removeX][removeY] = -10;
		remvoedPoitns.add(new Node(removeX, removeY, 0));
		
		return remvoedPoitns;
	}
	
	public static void recovery(List<Node> curRvPoints) {
		//System.out.println(curRvPoints);
		boolean con = false;
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {

				if(restBoard[i][j]>0) {
					restBoard[i][j]-=1;
					
					if(restBoard[i][j]==0) {
						board[i][j]=0; //땅이 회복되면 board도 회복시켜준다.
					}
				}
			}
		}
	}
	
	public static int[][] copyBoard(int[][] src, int[][] target) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				target[i][j] = src[i][j];
			} 
		}
		return target;
	}
	

}

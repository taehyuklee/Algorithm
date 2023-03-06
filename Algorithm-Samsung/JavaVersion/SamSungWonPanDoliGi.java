import java.util.*;
import java.io.*;


public class Main {
	
	static int N, M, T;
	static int[] x, d, k;
	static int[][] board, board2;
	static List<Integer> numList = new ArrayList<Integer>();
	static int solution;
	
	public static void print2D(int[][] array) {
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int sum2D(int[][] array) {
		
		int summation=0;
		
		for(int i=0; i<N;i++) {
			for(int j=0; j<M; j++) {
				summation += array[i][j];
			}
		}
		return summation;
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		T = sc.nextInt();
		
		board = new int[N][M];
		board2 = new int[N][M];
		
		
		for(int i =0; i<N; i++) {
			for(int j=0; j<M; j++) {
				
				board[i][j] = sc.nextInt();
				board2[i][j] = board[i][j];
				
			}
		}

		//이걸 빼먹음
		x = new int[T];
		d = new int[T];
		k = new int[T];
		
		for(int t=0; t<T; t++) {
			x[t] = sc.nextInt();
			d[t] = sc.nextInt();
			k[t] = sc.nextInt();
		}
		
		//scanner는 닫아준다
		sc.close();

		solution();
	}
	
	public static void solution() {
		
		for(int t=0; t<T; t++) {
			//돌릴 판 index구하기
			getNumList(x[t]);
				
			//원판 돌리기
			rotation(d[t], k[t]);
	
			//인접한 숫자들 제거 (제거여부 true, false 반환)
			boolean adYn = adjacent();
			
			if(adYn == true) {
				//인접한게 있으면 지워진거에서 모두 합
	
			}else if(adYn == false) {
        //인접한게 없을때
				noAdjacent();
			}
			
			//초기화 및 update
			copy2D(board2, board);
			numList = new ArrayList<Integer>();
		}

		//solution 출력
		solution = sum2D(board2);
		System.out.println(solution);
		
	}
	
	public static void noAdjacent() {
		//인접한게 없으면 평균내고 평균보다 큰 수는 -1 작은수는 +1을 더하고나서 합
		//0 이 제거된것을 내가 빼먹고 계속 +1, -1해주고 있었음
		float summ = sum2D(board2);
		
		//0을 제외한것을 count해줘야 한다.
		int nums = countExceptZero(board2);
		
		float avg = summ/(float) nums;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				
				if(board2[i][j]>avg && board2[i][j] !=0) {
					board2[i][j] -= 1;
				}else if(board2[i][j] < avg && board2[i][j] !=0) {
					board2[i][j] += 1;
				}
				
			}
		}
	}
	
	public static int countExceptZero(int[][] board) {
		int count=0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] !=0) {
					count +=1;
				}
			}
		}
		
		return count;
	}
	
	public static boolean adjacent() {
		
		boolean adYn = false;
		
		//북남서동
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		
		//하나씩보면서 동서남북 주변에 같은 숫자가 있는지 확인
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				
				//범위만 넘어가지 않으면 (4방향)
				for(int dir=0; dir<4; dir++) {
					
					//y만 periodic condition
					int new_x = i+dx[dir];
					int new_y = (j+dy[dir]+M)%M;
					
					//이 조건이 아니라 periodic condition으로 해줘야 한다
					if(new_x>=0 && new_x<N) {
		
							//만약 같으면
							if(board[new_x][new_y] == board[i][j] && board[i][j] !=0) {
								board2[new_x][new_y] = 0;
								adYn = true;
							}				
						}
					}
				}
				
		}
		return adYn;
	}
	
	public static void rotation(int d, int k) {
		
		//시계방향인지 아닌지 판단
		int dir=0;
		
		if(d==1) {
			dir = 1; 
		}else if(d==0) {
			dir = -1;
		}
		
		//해당 원판을 k번만큼 돌린다.
		for(int l=0; l<k; l++) {
			
			for(int i=0; i<N; i++) {
				
				if(numList.contains(i)) {
					
					for(int j=0; j<M; j++) {
						
						board2[i][j] = board[i][(j+dir+M)%M];
						
					}
				}
			}
			copy2D(board2, board);
			
		}
	}
	
	public static void copy2D(int[][] boardSrc, int[][] boardTarget) {
		
		for(int i=0; i<N; i++) {
			
			for(int j=0; j<M; j++) {
				
				boardTarget[i][j] = boardSrc[i][j];
			}
		}
	}

	public static void getNumList(int x){
		
		int i=1;
		int x_new = 0;
		
		//몇 번째 판을 돌릴지 index를 구하는 것
		while(true) {
			
			x_new = x*i;
			
			if(x_new>N+1) {
				break;
			}
			
			numList.add(x_new-1);
			i+=1;
			
		}
		return;
	}
}

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	
	static int N, K;
	static int[][] board, board2;
	static int[] straight, straight2;
	static int[] rangeArr = new int[4];
	static int stInx_c, endInx_c, stInx_r, endInx_r;
	
	public static void print2D(int[][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print1D(int[] array) {
		for(int j=0; j<array.length; j++) {
			System.out.print(array[j] + " ");
		}
	}

	public static void copyBoard(int[][] source, int[][] target) {
		for(int i=0; i<source.length; i++) {
			for(int j=0; j<source[0].length; j++) {
				target[i][j] = source[i][j];
			}
		}
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		K = sc.nextInt();
		
		board = new int[N][N];
		board2 = new int[N][N];
		straight = new int[N];
		straight2 = new int[N];
		

		for(int j=0; j<N; j++) {
		
			straight[j] = sc.nextInt();
			straight2[j] = straight[j];
		}
		
		//print2D(board);
		
		//print1D(straight);
		
		solution();	
		
	}
	
	public static void solution() {
		
		//1. 물고기가 가장 적은 곳에 한마리씩 넣어주기
		putOne();
		
		//print1D(straight2);
		
		//2. board에 삽입하기
		insertBoard();
		
		//print2D(board);
		
		
		//3. 어항을 쌓는다 90도
		stack90();
		
		//4. 물고기 수 조절
		controlFish();

		//5. 일렬화 하기.
		straighten(board);

		//6. 반 접어서 180도 공중부양.
		half180();
		

		
	}
	
	public static void putOne() {
		
		//제일 작은수 뽑기
		Arrays.sort(straight2);
		int smallNum = straight2[0];
		
		//만약 가장 작은숫자라면 1씩 더해주기

		for(int j=0; j<N; j++) {
			if(straight[j] == smallNum) {
				straight[j] = straight[j]+1;
			}
			//여기서 straight2 복구해주자
			straight2[j] = straight[j];
		}
		
	}
	
	public static void insertBoard() {
		board = new int[N][N];
		board2 = new int[N][N];
		
		for(int j=0; j<N; j++) {
			board[N-1][j] = straight[j];
		}
	}
	
	public static void stack90() {
		
		//쌓인 어항이 없을때 
		firstStack();
		
		while(true) {
			//공중부양할 range를 구한 {return column (start, end) row (start, end)}
		//for(int i=0; i<2; i++) {
			get2DRange();
			
			//넘어가면 바로 stop
			if(Math.abs(endInx_r - stInx_r) + (endInx_c+1) >N-1) {
				break;
			}
			
			//공중 부양시켜서 90도 회전한다음 board위에 stack을 쌓는다.
			rotate90Stack();
//			print2D(board);
//			System.out.println();
			
			//}
		}
		
		print2D(board);

	}
	
	public static void firstStack() {
		//위에 쌓여있는게 없을
		int inter = board[N-1][0];
		board[N-1][0] = 0;
		board[N-2][1] = inter;
		
	}
	
	public static void get2DRange() {
		//Index확인
		stInx_c = 0;
		endInx_c = -1;
		stInx_r = N-1;
		endInx_r = -1;
		
		
		//column에 대한 범위를 구하는 부
		for(int j=0; j<N; j++) {
			//맨 밑의 어항은 어디서부터 존재하는지? (어디서부터 공중부양해야하나?)
			if(board[N-1][j] !=0) {
				stInx_c =j;
				break;
			}

		}
		
		for(int j=0; j<N; j++) {
			
			//윗 블록은 어디까지 존재하는지? (어디까지 공중부양시켜야 하나?)
			if(board[N-2][j] !=0) {
				endInx_c=j;
			}
		}
		
		
		//위에 쌓여 있는지에 대한 여부
		if(endInx_c != -1) {
			//위에 쌓여있다.
			
			//어디까지 쌓여 있는지?
			for(int i=N-1; i>=0; i--) {
				if(board[i][endInx_c] ==0) {
					endInx_r = i+1;
					break;
				}
			}
			
		}
		
		//array에 정리해서 넣어주기
		//int[] rangeArr = new int[4];

//		rangeArr[0] = stInx_c;
//		rangeArr[1] = endInx_c;
//		rangeArr[2] = stInx_r; //맨 아
//		rangeArr[3] = endInx_r; // 위로 
//		
//		
//		return rangeArr;
		
	}
	
	public static void rotate90Stack() {
		
		//부양하는 사각형에 맞춰 새로운 array 생
		int newC = endInx_c-stInx_c+1;
		int newR = stInx_r-endInx_r+1;
		int[][] goUp = new int[newR][newC];
		
		//copy board part to new goUp board
		for(int i=newR-1; i>=0; i--) {
			for(int j=0; j<newC; j++) {
				int newRow = i+(N-newR);
				goUp[i][j] = board[i+(N-newR)][stInx_c+j];
				//공중부양 시킨 부분은 모두 0으로
				board[i+(N-newR)][stInx_c+j]=0;
				//System.out.println(board[i+(N-newR)][stInx_c+j]);
			}
		}
		
		//print2D(goUp);
		
		//여기서 90도 돌린다. 앞서 부양시킴.
		int[][] rotate90 = new int[newC][newR];
		
		for(int i=0; i<goUp.length; i++) {
			for(int j=0; j<goUp[0].length; j++) {
				rotate90[j][(newR-1)-i] = goUp[i][j];
				
			}
		}
		
		//System.out.println();
		//print2D(rotate90);
		//print2D(board);
		
		//그 다음 위에 stack을 쌓는다 
		for(int i=0; i<rotate90.length; i++) {
			for(int j=0; j<rotate90[0].length; j++) {
				board[(N-2)-(rotate90.length-1)+i][stInx_c+j+goUp[0].length] = rotate90[i][j];
				//goUp.length 이만큼 차이 이후에 쌓아야 한다.
			}
		}
		
		//print2D(board);		
		
	}

	public static void controlFish() {
		
		//조절할 board 두개를 만들어 놓는다.
		copyBoard(board, board2);
		
		boolean[][] visit = new boolean[N][N];
		
		int[] dr = {-1,1,0,0};
		int[] dc = {0,0,-1,1};
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				
				if(board[i][j] !=0) {
				
					//4방향 탐색.
					for(int dir=0; dir<4; dir++) {
						int new_r = i +dr[dir];
						int new_c = j +dc[dir];
						
						if(new_r>=0 && new_r<N && new_c>=0 && new_c<N) {
							if(board[new_r][new_c] !=0 && visit[new_r][new_c] != true) {
								
								int absDiff = Math.abs(board[new_r][new_c] - board[i][j]);
								int share = (int) absDiff/5;
								
								//System.out.println(share);
								
								if(share >0) {
									
									//많은 곳에서 적은곳으로 d만큼 이동 board2에 적
									if(board[i][j] > board[new_r][new_c]) {
										
										board2[i][j] -= share;
										board2[new_r][new_c] += share;
										
									}else if(board[i][j] < board[new_r][new_c]){
									
										board2[i][j] += share;
										board2[new_r][new_c] -= share;
										
									}
									
									
								}else if(share <0) {
									continue;
								}
								
							}
						}
						
					}
					visit[i][j] = true;
					
				}
				
			}
		}
		
		copyBoard(board2, board);
		
	}
	
	public static void straighten(int[][] array) {
		
		int order =0;
		
		for(int j=0; j<N; j++) {
			for(int i=N-1; i>=0; i--) {
				
				
				if(board[i][j] != 0) {
					
					straight[order] = array[i][j];
					
					order+=1;
					
				}
				
			}
		}
	}
	
	public static void half180() {
		
		//board 초기화 
		board = new int[N][N];
		board2 = new int[N][N];
		
		int halfStInx = (int) N/2;

		
		for(int i=0; i< (int) N/2; i++) {
			//180도 회전
			board[N-2][halfStInx+i] = straight[(halfStInx-1)-i];
		}
		
		for(int i=0; i< (int) N/2; i++) {
			board[N-1][halfStInx+i] = straight[halfStInx+i];
		}
		
		get2DRange();
		
		int[][] rotatedBoard = get2D();
		
		//반 더 접어.
		int[][] rotate2D = new int[1*4][(int) N/4];
		
		int halAndHalf = (int) rotatedBoard[0].length/2;
		print2D(rotatedBoard);
		

		for(int i=0; i< 2; i++) {
			for(int j=halAndHalf; j<rotatedBoard[0].length; j++) {
				//180도 회전
				System.out.println(rotate2D.length-1 -i);
				rotate2D[j][rotate2D.length-1 -i] = rotatedBoard[rotatedBoard.length-1 -i][j];
			}
		}
		print2D(rotate2D);
		
		for(int i=0; i< 2; i++) {
			for(int j=0; j<halAndHalf; j++) {
				//180도 회전
				System.out.println(N/4-1-j + " ////");
				rotate2D[N-3-i][N/4-1-j] = rotatedBoard[i][j];
			}
		}
		
		print2D(rotatedBoard);
		print2D(rotate2D);
		
	}

	public static int[][] get2D() {

		//부양하는 사각형에 맞춰 새로운 array 생
		int newC = endInx_c-stInx_c+1;
		int newR = stInx_r-endInx_r+1;
		int[][] goUp = new int[newR][newC];
		
		//copy board part to new goUp board
		for(int i=newR-1; i>=0; i--) {
			for(int j=0; j<newC; j++) {
				int newRow = i+(N-newR);
				goUp[i][j] = board[i+(N-newR)][stInx_c+j];
				//공중부양 시킨 부분은 모두 0으로
				board[i+(N-newR)][stInx_c+j]=0;
				//System.out.println(board[i+(N-newR)][stInx_c+j]);
			}
		}
		
		return goUp;

	}
}

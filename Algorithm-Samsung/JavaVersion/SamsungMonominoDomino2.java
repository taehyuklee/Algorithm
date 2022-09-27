import java.util.*;
import java.io.*;

class Block{
	int t, x, y;

	public Block(int t, int x, int y) {
		super();
		this.t = t;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Block [t=" + t + ", x=" + x + ", y=" + y + "]";
	}
}

public class Main {
	
	static int [][] red = new int[4][4];
	static int [][] green = new int[6][4];
	static int [][] blue = new int[4][6];
	static Queue<Block> blockQ = new LinkedList<Block>();
	static List<Integer> eraseListGreen = new ArrayList<>();
	static List<Integer> eraseListBlue= new ArrayList<>();
	static int N, score=0;
	
	public static void print2DArray(int[][] arr) {
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void initRed() {
		for(int i=0; i<red.length; i++) {
			for(int j=0; j<red[0].length; j++) {
				red[i][j] = 0;
			}
		}
	}
	
	public static int countAnswer() {
		
		int countBlock =0;
		
		for(int i=0; i<blue.length; i++) {
			for(int j=0; j<blue[0].length; j++) {
				if(blue[i][j] ==1) {
					countBlock+=1;
				}
			}
		}
		
		for(int i=0; i<green.length; i++) {
			for(int j=0; j<green[0].length; j++) {
				if(green[i][j] ==1) {
					countBlock+=1;
				}
			}
		}
		
		return countBlock;
	}
	
	public static void main(String[] args) throws IOException {
		

		//Input
		int t,x,y;
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());

			t = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			blockQ.add(new Block(t,x,y));
				
			
		}
		//System.out.println(blockQ);
		solution();
	}
	
	public static void solution() {
		
		int breakSize=0;
		while(!blockQ.isEmpty()) {
			//빨간색 보드에 대입
			Block block = blockQ.poll();
			if(block.t ==1) {
				
				red[block.x][block.y] = 1;
				
			}else if(block.t ==2) {
				
				red[block.x][block.y] = 1;
				red[block.x][block.y+1] = 1;
				
			}else if(block.t ==3) {
				
				red[block.x][block.y] = 1;
				red[block.x+1][block.y] = 1;
				
			}
//			System.out.println("red");
//			print2DArray(red);
//			System.out.println();
			
			//초록색 이동
			moveGreen(block);
//			print2DArray(green);
//			System.out.println();
			
			//파란색 이동
			moveBlue(block);
//			print2DArray(blue);
//			System.out.println();
			
			//red보드는 초기화
			initRed();
			
			//이동후 칸이 모두 찬 줄이 있는지 확인
			checkOfLine();
			
			//파괴하고 줄을 지운다
			erase4Count();
			
			//이후 연한 부분 없애자
			eraseLightPart();
			


//			print2DArray(red);
//			System.out.println();
			
		}
		
		System.out.println(score);
		System.out.print(countAnswer());

	}
	
	public static void eraseOperation(int greenCount, int blueCount) {
		
		//초록 부분 지우고
		if(greenCount>=1) {
			for(int i=5-greenCount; i>=0; i--) {
				for(int j=0; j<4; j++) {
					green[i+greenCount][j] = green[i][j];
					if(i==1 || i==0) {
						green[i][j] = 0; //연한부부은 모두 0으로 해줘야 한다.
					}
				}
			}
		}
		
		//파란 부분 지워준다
		if(blueCount>=1) {
			for(int j=5-blueCount; j>=0; j--) {
				for(int i=0; i<4; i++) {
					blue[i][j+blueCount] = blue[i][j];
					if(j==1 || j==0) {
						blue[i][j] = 0; //연한부부은 모두 0으로 해줘야 한다.
					}
				}
			}
		}
		
	}
	
	public static void eraseLightPart() {
		
		//초록색 부분 지우기
		int greenCount = 0;
		for(int i=1; i>=0; i--) {
			for(int j=0; j<4; j++) {
				if(green[i][j] ==1) {
					greenCount+=1;
					break;
				}
			}
		}
			
		//의문 안에것만 break되는지 밖에것도 같이 break되는지 꼭 확인하기 (된다)	
			
		//파란색 부분 지우기
		int blueCount = 0;
		for(int j=1; j>=0; j--) {
			for(int i=0; i<4; i++) {
				if(blue[i][j] ==1) {
					blueCount+=1;
					break;
				}
			}
		}
		//System.out.println("greenCount, blueCount" + " " + greenCount + " " + blueCount);
		//여기서 지워주자
		eraseOperation(greenCount, blueCount);
		
	}
	
	
	public static void erase4Count() {
		
		//초록색 지우기
		for(Integer eraseRow: eraseListGreen) {
			
			for(int i=eraseRow-1; i>=0; i--) {
				for(int j=0; j<4; j++) {
					green[i+1][j] = green[i][j];
					if(i==0) {
						green[i][j] = 0;
					}
				}

			}
		}
		eraseListGreen.clear();
		
		//파란색 지우기
		for(Integer eraseCol: eraseListBlue) {
			
			for(int j=eraseCol-1; j>=0; j--) {
				for(int i=0; i<4; i++) {
					green[i][j+1] = green[i][j];
					if(j==0) {
						blue[i][j] = 0;
					}
				}
			}
		}
		eraseListBlue.clear();
		
	}
	
	public static void checkOfLine() {
		
		int count=0;
		
		//초록색 줄 확인
		for(int i=5; i>=2; i--) {
			count =0;
			for(int j=0; j<4; j++) {
				if(green[i][j]==1) {
					count+=1;
				}
			}
			if(count==4) {
				score+=1;
				eraseListGreen.add(i);
			}
			
		}
		
		
		
		//파란색 줄 확인
		for(int j=5; j>=2; j--) {
			count =0;
			for(int i=0; i<4; i++) {
				if(blue[i][j]==1) {
					count+=1;
				}
			}
			if(count==4) {
				score+=1;
				eraseListBlue.add(j);
			}
			
		}
		
		
	}
	
	
	public static void moveGreen(Block block) {
		
		//초기 초건 (green영역 처음 부분에 red에서 떨어진 블록 구현)
		if(block.t==1) {
			green[0][block.y] = red[block.x][block.y];
		}else if(block.t==2) {
			green[0][block.y] = red[block.x][block.y];
			green[0][block.y+1] = red[block.x][block.y+1];
		}else if(block.t==3) {
			green[0][block.y] = red[block.x][block.y];
			green[1][block.y] = red[block.x][block.y];
		}
		
		for(int i=0; i<5; i++) {
			
			if(block.t==1 && i+1<=5) {
				if(green[i+1][block.y] !=1) {
					green[i+1][block.y] = green[i][block.y];
					//지나간 부분은 0으로 바꿔주기
					green[i][block.y] = 0;
				}else {
					break;
				}
			}
			
			else if(block.t==2 && i+1<=5) {
				if(green[i+1][block.y] !=1 && green[i+1][block.y+1] !=1) {
					green[i+1][block.y] = green[i][block.y];
					green[i+1][block.y+1] = green[i][block.y+1];
					//지나간 부분은 0으로 바꿔주기
					green[i][block.y] = 0;
					green[i][block.y+1] = 0;
				}else {
					break;
				}
			}
			
			else if(block.t==3) {
				if(i+2<=5) {
					if(green[i+2][block.y] !=1) {
						green[i+1][block.y] = green[i][block.y];
						green[i+2][block.y] = green[i+1][block.y];
						//지나간 부분은 0으로 바꿔주기
						green[i][block.y] = 0;
					}else {
						break;
					}
				}
			}
		}

	}
	
	public static void moveBlue(Block block) {
		//초기 초건 (blue영역 처음 부분에 red에서 떨어진 블록 구현)

		if(block.t==1) {
			blue[block.x][0] = red[block.x][block.y];
		}else if(block.t==2) {
			blue[block.x][0] = red[block.x][block.y];
			blue[block.x][1] = red[block.x][block.y];
		}else if(block.t==3) {
			blue[block.x][0] = red[block.x][block.y];
			blue[block.x+1][0] = red[block.x+1][block.y];
		}
		
		for(int j=0; j<5; j++) {
			
			if(block.t==1 && j+1<=5) {
				if(blue[block.x][j+1] !=1) {
					blue[block.x][j+1] = blue[block.x][j];
					//지나간 부분은 0으로 바꿔주기
					blue[block.x][j] = 0;
				}else {
					break;
				}
			}
			
			else if(block.t==2) {
				if(j+2<=5) { 
					if(blue[block.x][j+2] !=1){
						//System.out.println("j" + j );
						blue[block.x][j+1]  = blue[block.x][j];
						blue[block.x][j+2]  = blue[block.x][j+1];
						//지나간 부분은 0으로 바꿔주기
						blue[block.x][j] = 0;
					}else {
						break;
					}
				}
			}
			
			else if(block.t==3 && j+1<=5) {
				if(blue[block.x][j+1] !=1 && blue[block.x+1][j+1] !=1) {
					blue[block.x][j+1] = blue[block.x][j];
					blue[block.x+1][j+1] = blue[block.x+1][j];
					
					blue[block.x][j] = 0;
					blue[block.x+1][j] = 0;
				}else {
					break;
				}
			}
		}
	}
	
}

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
}

public class Main {
	
	static int [][] red = new int[4][4];
	static int [][] green = new int[6][4];
	static int [][] blue = new int[4][6];
	static Queue<Block> blockQ = new LinkedList<Block>();
	static int N;
	
	public static void main(String[] args) throws IOException {
		
		
		
		//Input
		int t,x,y;
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<3; j++){
				t = Integer.parseInt(st.nextToken());
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());
				blockQ.add(new Block(t,x,y));
				
			}
		}

		solution();
	}
	
	public static void solution() {
		
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
		
		//초록색 이동
		moveGreen(block);
		
		//파란색 이동
		moveBlue(block);
		
		//이동후 칸이 모두 찬 줄이 있는지 확인
		checkOfLine();
		
	}
	
	public static void checkOfLine() {
		
	}
	
	
	public static void moveGreen(Block block) {
		
		//초기 초건 (green영역 처음 부분에 red에서 떨어진 블록 구현)
		green[0][block.y] = red[block.x][block.y];
		
		for(int i=0; i<6; i++) {
			
			if(block.t==1) {
				if(green[i+1][block.y] !=1) {
					green[i+1][block.y] = green[i][block.y];
					//지나간 부분은 0으로 바꿔주기
					green[i][block.y] = 0;
				}else {
					break;
				}
			}
			
			if(block.t==2) {
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
			
			if(block.t==3) {
				if(green[i+2][block.y] !=1 && green[i+3][block.y] !=1) {
					green[i+2][block.y] = green[i][block.y];
					green[i+3][block.y] = green[i+1][block.y];
					//지나간 부분은 0으로 바꿔주기
					green[i][block.y] = 0;
					green[i+1][block.y] = 0;
				}else {
					break;
				}
			}
		}

	}
	
	public static void moveBlue(Block block) {
		//초기 초건 (blue영역 처음 부분에 red에서 떨어진 블록 구현)
		blue[block.x][0] = red[block.x][block.y];
		
		for(int j=0; j<6; j++) {
			
			if(block.t==1) {
				if(blue[block.x][j+1] !=1) {
					blue[block.x][j+1] = blue[j][block.y];
					//지나간 부분은 0으로 바꿔주기
					blue[j][block.y] = 0;
				}else {
					break;
				}
			}
			
			if(block.t==2) {
				if(blue[block.x][j+2] !=1 && blue[block.x][j+3] !=1) {
					blue[block.x][j+2]  = blue[block.x][j];
					blue[block.x][j+3]  = blue[block.x][j+1];
					//지나간 부분은 0으로 바꿔주기
					blue[block.x][j] = 0;
					blue[block.x][j+1] = 0;
				}else {
					break;
				}
			}
			
			if(block.t==3) {
				if(blue[block.x][j+1] !=1 && blue[block.x+1][j+1] !=1) {
					blue[block.x][j+1] = blue[block.x][j];
					blue[block.x+1][j+1] = blue[block.x+1][j];
				}else {
					break;
				}
			}
		}
	}
	
}

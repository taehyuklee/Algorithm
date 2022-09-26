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
		
	}
	
	
	public static void moveGreen(Block block) {
		
		//초기 초건 (green영역 처음 부분에 red에서 떨어진 블록 구현)
		green[0][block.y] = red[block.x][block.y];
		
		for(int i=0; i<6; i++) {
			
		}

	}
	
	public static void moveBlue(Block block) {
		
	}
	
}

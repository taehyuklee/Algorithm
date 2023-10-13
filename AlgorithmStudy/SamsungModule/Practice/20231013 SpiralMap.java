import java.util.*;
import java.util.Map.*;

class Node{
	int x, y, dir, rDir;

	public Node(int x, int y, int dir, int rDir) {
		this.x =x;
		this.y= y;
		this.dir = dir;
		this.rDir = rDir;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " dir: " + dir + " rDir " + rDir + "] ";
	}
}

public class Main {
	
	static Map<Integer, Node> spiralMap;
	static int N;
	
	static void setting() {
		int[][] map =new int[N][N];
		for(Entry<Integer, Node> entry : spiralMap.entrySet()) {
			
			for(int i=0; i<map.length; i++) {
				for(int j=0; j<map[0].length; j++) {
					if(entry.getValue().x ==i && entry.getValue().y ==j) {
						map[i][j]=1;
					}	
				}
			}
			System.out.println(entry.getValue());
			print2D(map);
		}

	}
	static void print2D(int[][] map) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		spiralMap = new HashMap<>();
		N = 5;
		
		makeSpiral();
		System.out.println(spiralMap);
		setting();
		
	}
	
	static void makeSpiral() {
		
		int[] dx = {0,1,0,-1};
		int[] dy = {-1,0,1,0};
		
		int index = 0;
		int dir = 0, rDir=2;
		int cur_depth = 0;
		int ref_depth = 1;
		int turn=0;
		
		int x_0 = N/2;
		int y_0 = N/2;
		spiralMap.put(index, new Node(x_0, y_0, dir, rDir));
		
		while(true) {
			
			index++;
			if(index==N*N) {
				break;
			}
			
			int new_x = x_0 + dx[dir];
			int new_y = y_0 + dy[dir];
			
			spiralMap.put(index, new Node(new_x, new_y, dir, rDir));
			cur_depth++;
			
			if(cur_depth == ref_depth) {
				
				dir = (dir+1)%4;
				rDir = (dir+2)%4;
				cur_depth=0;
				turn ++;
				spiralMap.put(index, new Node(new_x, new_y, dir,rDir));
				if(turn==2) {
					ref_depth++;
					turn=0;
				}

			}
			
			x_0 = new_x;
			y_0 = new_y;

		}
		
		
	}
	
}

import java.util.*;
import java.util.Map.*;

class Node{
	int x, y, dir, rDir;
	
	public Node(int x, int y, int dir, int rDir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.rDir =rDir;
	}
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y + " dir: " + dir +  " rDir: " + rDir +"] ";
	}
	
}
public class Main {
	
	static int N;
	static Map<Integer, Node> spiralMap;
	
	
	public static void print2D(int[][] map) {
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static int[][] makeMapToArray() {
		int[][] newMap = new int[N][N];
		//index가 Key로 작용하니까 for loop돌리면 되긴 함 여기서는. 일반적으로는 안됨. key를 모르니까
//		for(int i=0; i<spiralMap.size(); i++) {
//			spiralMap.get(map);
//		}
		
		//일반적으로 하기 위해서는 java.util.Map.Entry를 사용해야 한다.
		for(Entry<Integer, Node> entry : spiralMap.entrySet()) {
			System.out.println(entry);
			Node node = entry.getValue();
			newMap[node.x][node.y] = 1;
			print2D(newMap);
		}

		return newMap;
		
	}
	
	public static void main(String[] args) {
		
		N=5;
		spiralMap = new HashMap<>();
		
		makeSpiral();
		
		makeMapToArray();
		
		
	}
	
	static void makeSpiral() {
		
		int[] dx = {-1,0,1,0};	int[] dy = {0,-1,0,1};
		int index=0;
		int cur_depth = 0, ref_depth=1, chg_dir =0, dir=0;
		int x_0=N/2, y_0=N/2;
		
		
		spiralMap.put(index, new Node(x_0, y_0, dir, dir)); // 만약 바로 방향 전환 한다고 한다면.
		while(index<N*N) {
			int new_x = x_0 + dx[dir];
			int new_y = y_0 + dy[dir];
			
			index++; 
			cur_depth++;
			spiralMap.put(index, new Node(new_x,new_y,dir, (dir+2)%4));
			
			
			if(cur_depth == ref_depth) {
				//방향 전환
				dir = (dir+1)%4;
				chg_dir++;
				cur_depth=0;
				
				if(chg_dir==2) {
					ref_depth++;
					chg_dir=0;
				}
				
			}
			
		}
		
		
		
	}
	
	
}

import java.util.*;

public class Main {
	
	static int[][] map, map1;
	static int N;
	static int[] dx= {-1,1,0,0}, dy= {0,0,-1,1};
	
	public static void print2D(int[][] map) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				System.out.printf("%5d", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printOneIndex2D(int[][] map) {
		for(int i=1; i<map.length; i++) {
			for(int j=1; j<map[0].length; j++) {
				System.out.printf("%5d", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	public static void main(String[] args) {
		N =7;
		int x = (int) N/2;
		int y0 = 0;
		int y1 = 1;
		
		map = new int[N][N];
		map1 = new int[N+1][N+1];
		
		map[x][y0] = 1;
		map1[x+1][y1] = 1;
		
		//print2D(map);
		
		int dir=2;
		//fromZeroIndexOneStep(x, y0, dir);
		
		//fromZeroIndexDistanceStep(x,y0,dir);
		
		//fromOneIndexOneStep(x,y1,dir);
		
		fromOneIndexDistanceStep(x,y1,dir);
	
	}
	
	static void fromOneIndexDistanceStep(int x, int y, int dir) {
		
		
		int distance = 2;
		for(int d=0; d<100; d++) {
			
			int new_y = Math.abs(N + (y-1 +distance*dy[dir]))%N +1;
			//Math.abs((N + (y +distance*dy[dir])))%N; 
			
			map1[x+1][y] =0;
			map1[x+1][new_y]=1;
			
			y=new_y;
			
			printOneIndex2D(map1);
			
		}
	}
	
	static void fromOneIndexOneStep(int x, int y, int dir) {
		
		printOneIndex2D(map1);
		
		for(int d=0; d<100; d++) {
			
			int new_y = Math.abs(N + (y-1 +dy[dir]))%N + 1;
			
			map1[x+1][y] =0;
			map1[x+1][new_y]=1;
			
			y=new_y;
			
			printOneIndex2D(map1);
			
		}
	}
	
	
	static void fromZeroIndexDistanceStep(int x, int y, int dir) {
		
		print2D(map);
		int distance = 11;
		for(int d=0; d<5; d++) {
			
			int new_y = Math.abs((N + (y +distance*dy[dir])))%N; 
			
			map[x][y] =0;
			map[x][new_y]=1;
			
			y=new_y;
			
			print2D(map);
			
			
		}
	}
	
	
	static void fromZeroIndexOneStep(int x, int y, int dir) {
		
		print2D(map);
		for(int d=0; d<100; d++) {
			
			int new_y = (N + (y +dy[dir]))%N;
			
			map[x][y] =0;
			map[x][new_y]=1;
			
			y=new_y;
			
			print2D(map);
			
		}
	}
	
}


/*
 
for(int i=0; i<N; i++) {
	for(int j=0; j<N; j++) {
		
	}
}

 */

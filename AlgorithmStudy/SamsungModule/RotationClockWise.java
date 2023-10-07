import java.util.*;

public class Main {
	
	private static int N =5;
	static int[][] map;
	
	public static  void print2D(int[][] map) {
		
 		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.printf("%5d", map[i][j]);
			}
			System.out.println();
		}
 		System.out.println();
	}
	
	public static void main(String[] args) {
		
		map = new int[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(i==j) {
					map[i][j] =1;
				}
			}
		}
		
		print2D(map);
		for(int i=0; i<10; i++) {
			rotation(map);
			print2D(map);
		}

		
	}

	
	public static void rotation(int[][] map) {
		
		int[][] newMap = new int[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {	
				newMap[0+(j-0)][(N-1)-(i-0)] = map[i][j];
			}
		}
		
 		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = newMap[i][j];
			}
		}
	}
}


/*
 * 
 * 		
 		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
			}
		}
 * 
 * */
 

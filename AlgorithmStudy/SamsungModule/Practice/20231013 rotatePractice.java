import java.util.*;

public class Main {
	
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
		
		int N=5;
		int[][] map = new int[N][N];
		for(int i=0; i<5; i++) {
			map[i][0]=1;
		}
		print2D(map);
		
        //기존에 map이 있다고 가정.
		int start_x = 0, end_x = N;
		int start_y = 0, end_y = N;
		
		//90도 시계방향 회전
		int[][] newMap = new int[N][N];
		  
		for(int i=start_x; i<end_x; i++){
			for(int j=start_y; j<end_y; j++){
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}
		  
		print2D(newMap);
	
		
	}
}

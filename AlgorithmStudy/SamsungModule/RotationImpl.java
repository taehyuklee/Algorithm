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
				
				if(j==0) {
					map[i][j] =1;
				}
				
			}		
		}
		
		int rotateNums = 10;
		
		System.out.println("시계방향 - 시작");
		print2D(map);
		for(int i=0; i<rotateNums; i++) {
			cwRotationClockWise(map);
			print2D(map);
		}

		System.out.println("반시계방향 - 시작");
		for(int i=0; i<rotateNums; i++) {
			ccwRotationClockWise(map);
			print2D(map);
		}


	}

	//ClockWise
	public static void cwRotationClockWise(int[][] map) {
		
		int[][] newMap = new int[N][N];
		
		int endX = N-1;
		int startX = 0;
		
		int endY = N-1;
		int startY = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {	
				newMap[startX+(j-startY)][endY-(i-startX)] = map[i][j];
			}
		}
		
 		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = newMap[i][j];
			}
		}
	}
	
	//CounterClockWise
	public static void ccwRotationClockWise(int[][] map) {
		
		int[][] newMap = new int[N][N];
		
		int endX = N-1;
		int startX = 0;
		
		int endY = N-1;
		int startY = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {	
				newMap[endX-(j-startY)][startY+(i-startX)] = map[i][j];
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
 

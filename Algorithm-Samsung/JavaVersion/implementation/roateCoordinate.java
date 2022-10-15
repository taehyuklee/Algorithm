import java.util.*;


public class Main {
	
	static int[][] map = new int[4][4];
	static int[][] roateMap = new int[4][4];
	
	public static void print2D(int[][] arr) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static int[][] countClockRoate90(int[][] originMap){
		
		int[][] returnMap = new int[4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				returnMap[i][j] = originMap[j][ (originMap[0].length-1) - i];
			}
		}
		
		return returnMap;
		
	}
	
	
	public static int[][] clockRoate90(int[][] originMap){
		
		int[][] returnMap = new int[4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				returnMap[j][(originMap[0].length-1) - i] = originMap[i][j];
			}
		}
		
		return returnMap;
		
	}
	
	public static void main(String[] args) {
		//intput
		Scanner sc = new Scanner(System.in);
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		
		//print2D(map);
		System.out.println();
		int[][] rotatedMap = countClockRoate90(map);
		print2D(rotatedMap);
		
		System.out.println();
		
		rotatedMap = countClockRoate90(rotatedMap);
		print2D(rotatedMap);

		
		System.out.println();
		rotatedMap = countClockRoate90(rotatedMap);
		print2D(rotatedMap);
		
		System.out.println();
		rotatedMap = countClockRoate90(rotatedMap);
		print2D(rotatedMap);
		
		System.out.println();
		System.out.println("clockWise Rotate start");
		System.out.println();
		rotatedMap = clockRoate90(rotatedMap);
		print2D(rotatedMap);
		
		System.out.println();
		rotatedMap = clockRoate90(rotatedMap);
		print2D(rotatedMap);
		
		System.out.println();
		rotatedMap = clockRoate90(rotatedMap);
		print2D(rotatedMap);
		
		System.out.println();
		rotatedMap = clockRoate90(rotatedMap);
		print2D(rotatedMap);
		
	}

}

//input
/*
1 2 3 4
1 2 3 4
1 2 3 4
1 2 3 4
*/

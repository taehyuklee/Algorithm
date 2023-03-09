import java.util.*;
import java.io.*;


class Runner{
	
	int dir;
	int[] path;
	
	public Runner(int dir) {
		this.dir = dir;
		
		if(dir ==2) { //dir 2 -> 하상.
			this.path = new int[] {0,1};
			
		}else if(dir ==1) { //dir 1 -> 우좌.
			this.path = new int[] {2,3};			
		}
		
	}
	
	@Override
	public String toString() {
		return this.dir + " runner";
	}
}

public class Main {
	
	static int [][] treeBoard;
	static int [][] chaserBoard;
	static int N, M, H, K;
	static List<Runner> [][] runnerBoard;
	static Map<Integer, ArrayList<Integer>> spiralMap = new HashMap<>();
	
	public static void print(int[][] array) {
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print(int[] array) {
		
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void print(List<Runner> [][] array) {
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		H = sc.nextInt();
		K = sc.nextInt();
	
		chaserBoard = new int[N][N];
		treeBoard = new int[N][N];
		chaserBoard[N/2][N/2] = 1; //chaserBoard에 1로 사용한다. 
		runnerBoard = new ArrayList[N][N];
		
		System.out.println(runnerBoard.length + " "+ runnerBoard[0].length);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				runnerBoard[i][j] = new ArrayList<Runner>();
			}
		}
		
		for(int i=0; i<M; i++) {
			
			int xEsc = sc.nextInt()-1;
			int yEsc = sc.nextInt()-1;
			int d = sc.nextInt();
			runnerBoard[xEsc][yEsc].add(new Runner(d));
			
		}
		
		for(int i=0; i<H; i++) {
			
			int xTree = sc.nextInt()-1;
			int yTree = sc.nextInt();
			
			treeBoard[xTree][yTree] = 1;
		}
		
		//make spiral coordinate
		makdSpiral();
		
//		print(treeBoard);
//		System.out.println();
//		print(chaserBoard);
//		
//		System.out.println();
//		print(runnerBoard);
		
		solution();
		
	}
	
	public static void makdSpiral() {
		
		int x0 = N/2;
		int y0 = N/2;
		
		for(int i=0; i<N*N; i++) {
			
			ArrayList<Integer> coord = new ArrayList<>(2);
			
			spiralMap.put(i, coord);
			
		}
	}
	
	public static void solution() {

		
	}

}

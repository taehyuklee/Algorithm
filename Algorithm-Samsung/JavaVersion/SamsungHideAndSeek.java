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


class Node{
	
	int x;
	int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return this.x + " " + this.y + " ";
	}
}

public class Main {
	
	static int [][] treeBoard;
	static int [][] chaserBoard;
	static int N, M, H, K;
	static List<Runner> [][] runnerBoard;
	static Map<Integer, Node> spiralMap = new HashMap<>();
	
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
		
		System.out.println(spiralMap);
		
		print(treeBoard);
		System.out.println();
		print(chaserBoard);
		
		System.out.println();
		print(runnerBoard);
		
		solution();
		
	}
	
	public static void makdSpiral() {
		
		int x_0 = N/2, y_0 = N/2;
		spiralMap.put(0, new Node(x_0, y_0));
		int count=0, refDept=1, currDept=0, dir=0;
		int [] dx = {-1,0,1,0};
		int [] dy = {0,1,0,-1};
		
		for(int i=1; i<N*N; i++) {
			
			int x_new = x_0 + dx[dir];
			int y_new = y_0 + dy[dir];
			
			currDept+=1;
			
			if(currDept == refDept) {
				
				dir = (dir+1)%4;
				count+=1; //방향이 바뀐 count
				
				if(count==2) {//2번 바뀔때마다 길이를 증가시켜준다
					refDept +=1;
					count=0;
				}
				currDept=0;

			}
					
			x_0 = x_new;
			y_0 = y_new;
					
			spiralMap.put(i, new Node(x_new, y_new));
			
		}
	}
	
	public static void solution() {

		
	}

}

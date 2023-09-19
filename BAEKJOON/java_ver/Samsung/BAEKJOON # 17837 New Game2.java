import java.util.*;
import java.io.*;

class Horse{
	int x, y, dir;
	
	public Horse(int x, int y, int dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
}

public class Main {
	
	static int N, K;
	static int[][] board;
	static int[] dx = {0,0,-1,1}, dy = {1,-1,0,0};
	static List<Horse> horseList= new ArrayList<Horse>();

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		K = sc.nextInt();
		
		board = new int[N][N];
		
		for(int i=0; i<N ; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		
		for(int i=0; i<K; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int dir = sc.nextInt()-1;
			Horse horse = new Horse(x,y,dir);
			horseList.add(horse);
		}
		
		solution();
		
	}
	
	static void solution(){
		
		for(int i=0 ;i<K; i++) {
			Horse horse = horseList.get(i);
			
			int new_x = horse.x +dx[horse.dir];
			int new_y = horse.y +dy[horse.dir];
			
			
			
		}
		
	}
	
}

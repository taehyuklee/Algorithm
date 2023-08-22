import java.util.*;

public class Main {
	
	static int N, r, c;
	static int[][] board;
	static int[] dx = {0,0,1,1}, dy= {0,1,0,1};
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		r = sc.nextInt();
		c = sc.nextInt();
		
		int length = (int) Math.pow(2, N);
		board = new int[length][length];
		
		int x=0;
		int y=0;
		int num = 1;
		
		Z(N, x, y, num);

	}
	
	static void Z(int size, int x, int y, int num) {
		


		
		for(int i=0; i<4; i++) {
			
			int new_x = (int) (x+dx[i]*Math.pow(2, size));
			int new_y = (int) (y+dy[i]*Math.pow(2, size));

			if(size>0) {
				Z(size-1, new_x, new_y, num);
			}
			
			System.out.println(new_x + " " + new_y + " ");
			num++;
		}
		
		
	}

}

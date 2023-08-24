import java.util.*;

public class Main {
	static int N, M;
	static int[][] snake,ladder, board; 
	static boolean[] visit;
 	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		ladder = new int[N][2];
		snake = new int[M][2];
		visit = new boolean[110];
		
		for(int i=0; i<N; i++) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			ladder[i] = new int[] {start, end};
			if(start==1 && end==100) {
				System.out.println(0);
				return;
			}
		}
		
		for(int i=0; i<M; i++) {
			int start = sc.nextInt();
			int end = sc.nextInt();
			snake[i] = new int[] {start, end};
		}

		
		bfs(1);
		
	}

	static void bfs(int start) {
		
		Queue<int[]> Q = new LinkedList<>();
		Q.add(new int[] {1,0});
		visit[start] = true;
		
		
		while(true) {
			
			int[] old_num = Q.poll();
			
			if(old_num[0]>=100) {
				System.out.println(old_num[1]);
				break;
			}
			
			for(int dice=1; dice<=6; dice++) {
				int new_num = old_num[0] + dice;
				int cnt = old_num[1];
				
				if(visit[new_num] !=false) continue;
				
				//사다리 타기전이나 뱀 타기 전에 visit 처리를 한 번 해줘야 한다 방문은 한거니까
				visit[new_num] = true;
				
				//사다리 체크
				int new_num2 = checkLadder(new_num);
				if(new_num2 !=0) {
					new_num = new_num2;
					visit[new_num] = true;
				}
				
				//뱀 체크 
				int new_num3 = checkSnake(new_num);
				if(new_num3 !=0) {
					new_num = new_num3;
					visit[new_num] = true;
				}

				//이후에 Queue에 넣는다. 
				Q.add(new int[] {new_num, cnt+1});
			}
		}
	}
	
	static int checkLadder(int num) {
		for(int i=0; i<ladder.length; i++) {
			if(num == ladder[i][0]) {
				return ladder[i][1];
			}
		}
		return 0;
	}
	
	static int checkSnake(int num) {
		for(int i=0; i<snake.length; i++) {
			if(num == snake[i][0]) {
				return snake[i][1];
			}
		}
		return 0;
	}
}

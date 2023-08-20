import java.util.*;

public class Main {
	
	static int N, K
	static boolean[] visit;
	static int[] cases = {0,-1,1}; //이 부분만 순서가 바뀜 이전의 숨바꼭질2문제와 다르게
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		visit = new boolean[100001];
		
		bfs(N);
		
	}
	
	static void bfs(int N) {
		
		Queue<int[]> Q = new LinkedList<>();
		Q.add(new int[] {N,0});
		visit[N] = true;
		
		while(Q.size() !=0) {
			
			int[] cur = Q.poll();
			
			if(cur[0] == K) {
				System.out.println(cur[1]);
				return;
			}
			
			for(int num=0; num<3; num++) {
				
				int move = 0;
				if(cases[num]==0) {
					move = cur[0];
				}else {
					move = cases[num];
				}
				int new_num = cur[0] + move;
				
				//탐색 조
				if(new_num<0 || new_num >100000) continue;
				if(visit[new_num] == true) continue;
				
				visit[new_num] = true;
				if(cases[num]==0) {
					Q.add(new int[] {new_num, cur[1]});
				}else {
					Q.add(new int[] {new_num, cur[1]+1});
				}
				
			}
			
		}
	}
}

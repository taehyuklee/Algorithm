import java.util.*;

public class Main {
	
	static int N, K, answer;
	static long[] coins;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		K = sc.nextInt();
		
		coins = new long[N];
		
		for(int i=0; i<N; i++) {
			coins[i] = sc.nextLong();
		}
		
		for(int i=N-1; i>=0; i--) {
			
			if(coins[i]<=K) {
				
				answer += (int) (K/coins[i]);
				K = (int) (K%coins[i]);
				
			}
			
		}
		System.out.println(answer);
		
	}
}

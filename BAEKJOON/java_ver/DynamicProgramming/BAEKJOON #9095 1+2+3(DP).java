package DynamicProgramming;
import java.util.*;

public class Main {
	
	static int N;
	static int[] dp;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		for(int i=0; i<N; i++) {
			int num = sc.nextInt();
			
			dp = new int[11];
			dp[1] = 1;
			dp[2] = 2;
			dp[3] = 4;
			
			for(int n=4; n<=num; n++) {
				dp[n] = dp[n-1] + dp[n-2] + dp[n-3];
			}
			System.out.println(dp[num]);
		}
		
	}
}

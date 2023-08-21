import java.util.*;

public class Main {
	
	static int N;
	static int[] coins = new int[] {5,2};
	
	//greedy 알고리즘으로 풀이.
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		int cnt=0;
		
		while(true) {
			if(N%5==0) {
				cnt += (int) N/5;
				System.out.println(cnt);
				break;
				
			}else {
				N -=2;
				cnt = cnt+1;
			}
			if(N<0) {
				System.out.println(-1);
				break;
			}
		}
		
	}

}

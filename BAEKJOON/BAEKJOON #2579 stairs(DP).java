import java.util.*;

public class Main {
	
	static int N;
	static int[] stairs;
	static int[] maximum;
	
	public static void main(String[] args) {
		//f(n) = max[arr[n-1] + f(n-3), f(n-2)] + arr[n] - 점화식
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
//		stairs = new int[N+1]; //만약 계단이 3개, 2개,1개면 이미 정해져 있다 -> 이에 따라서 여기서 N이 2, 3이면 문제가 생김
//		maximum = new int[N+1];
		
		stairs = new int[301];
		maximum = new int[301];
		
		for(int i=1; i<=N; i++) {
			stairs[i] = sc.nextInt();
		}
		
		maximum[1] = stairs[1];
		maximum[2] = stairs[1] + stairs[2]; //최대니까 두 개 더한게 최대겠지
		maximum[3] = Math.max(stairs[1], stairs[2]) + stairs[3];
		
		for(int i=4; i<=N; i++) {
			maximum[i] = Math.max(maximum[i-3] + stairs[i-1] , maximum[i-2]) + stairs[i];
		}
		
		System.out.println(maximum[N]);
		
	}
}

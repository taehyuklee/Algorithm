import java.util.*;

public class Main {
	
	static int N;
	static int table[];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		table = new int[N+1];
		
		table[0] = 0;
		table[1] = 0;
		
		for(int i=2; i<=N; i++) {
			table[i] = table[i-1]+1;
			if(i%2==0) table[i] = Math.min(table[i/2]+1, table[i]);
			if(i%3==0) table[i] = Math.min(table[i/3]+1, table[i]);
		}
		
		System.out.println(table[N]);
		
	}
}

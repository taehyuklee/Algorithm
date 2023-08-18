import java.util.*;
import java.io.*;

public class Main {
	
	static int N, M;
	static int[] num_arr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		num_arr = new int[N];
		
		//받을때부터 누적합을 구하는 것임.
		st = new StringTokenizer(br.readLine());
		num_arr[0] = Integer.parseInt(st.nextToken());
		for(int i=1; i<N; i++) {
			num_arr[i] = Integer.parseInt(st.nextToken())+ num_arr[i-1];
		}
		
		for(int i=0; i<M; i++) {
			
			st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			int result = 0;
			
			if(start>0) {
				result = num_arr[end] - num_arr[start-1];
			}else if (start==0) {
				result = num_arr[end];
			}
			System.out.println(result);
		}
	}
}

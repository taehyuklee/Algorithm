import java.util.*;
import java.io.*;

public class Main {
	
	static int N;
	static long[][] time_arr;
	
	static void print2D(long[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		time_arr = new long[N][2];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			time_arr[i][0] = Long.parseLong(st.nextToken());
			time_arr[i][1] = Long.parseLong(st.nextToken());
		}
		
		//정렬 끝나는 시각을 기준으로 
		Arrays.sort(time_arr, new Comparator<long[]>(){
			@Override
			public int compare(long[] o1, long[] o2){
				if(o1[1] == o2[1]) return (int) (o1[0]-o2[0]);
				return (int) (o1[1] - o2[1]);
			}
		});
		
		
		int cnt=1;
		long endTime = time_arr[0][1];
		for(int i=1; i<time_arr.length; i++) {
			
			if(endTime<=time_arr[i][0]) { //startTime이 이전 endTime보다 앞서 있으
				endTime = time_arr[i][1];
				cnt++;
			}
				
		}
		System.out.println(cnt);
	}
}

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class Main {
	
	static int N, M;
	static long[] first_array, second_array; 
	static Map<Long, Boolean> first_map = new HashMap<>();
	
	static void print1D(long[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	static void print1D(Map<Long, Boolean> map) {
		for(Entry<Long, Boolean> entry : map.entrySet()) {
			System.out.print(entry.getKey() + " " + entry.getValue() + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		N = Integer.parseInt(st.nextToken());
		first_array = new long[N];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			long input = Long.parseLong(st.nextToken());
			first_map.put(input, true);
		}
		
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		second_array = new long[M];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<M; i++) {
			second_array[i] = Long.parseLong(st.nextToken());
		}
		
//		print1D(first_map);
//		print1D(second_array);	
		
		solution();
		
	}
	
	static void solution() {
		for(int i=0; i<M; i++) {
			if(first_map.get(second_array[i]) != null) {
				System.out.println(1);
			}else {
				System.out.println(0);
			}
		}

	}
	
}

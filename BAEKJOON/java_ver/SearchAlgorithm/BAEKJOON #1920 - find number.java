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
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		first_array = new long[N];
		for(int i=0; i<N; i++) {
			long input = sc.nextLong();
			first_map.put(input, true);
		}
		
		M = sc.nextInt();
		second_array = new long[M];
		for(int i=0; i<M; i++) {
			second_array[i] = sc.nextLong();
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

import java.util.*;
import java.io.*;

public class Main {
	static int N;

	static void print1D(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		boolean error = false;
					
		for(int i=0; i<N; i++) {
			
			//order 받아오기
			st = new StringTokenizer(br.readLine());
			String order = st.nextToken();
			String[] eachOrder = order.split("");

			
			//배열의 갯수 받아와서 배열 만들기.
			st = new StringTokenizer(br.readLine());
			int nums =  Integer.parseInt(st.nextToken());
			
			
			//수열 받아오기.
			String input_nums_string = br.readLine();
			String[] nums_arr_string = input_nums_string.substring(1,input_nums_string.length()-1).split(",");
			
			int[] num_arr = new int[nums];
			for(int j=0; j<nums; j++) {
				num_arr[j] = Integer.parseInt(nums_arr_string[j]);
			}
			
			//명령 수행 
			for(int j=0; j<eachOrder.length; j++) {
				if(eachOrder[j].equals("R")) {
					num_arr = R(num_arr);
				}else {
					if(num_arr.length != 0) {
						num_arr = D(num_arr);
					}else {
						error = true;
						break;
					}
				}
			}
			
			if(error) {
				System.out.println("error");
				error = false;
			}else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				for(int j=0; j<num_arr.length; j++) {
					sb.append(num_arr[j]).append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("]");
				
				System.out.println(sb);
			}
			
			
		}
	}
	
	static int[] R(int[] arr) {
		int[] new_one = new int[arr.length];
		for(int i=0; i<arr.length; i++) {
			new_one[i] = arr[arr.length-1-i];
		}
		return new_one;
	}
	
	static int[] D(int[] arr) {
		int[] new_one = new int[arr.length-1];
		for(int i=1; i<arr.length; i++) {
			new_one[i-1] = arr[i];
		}
		return new_one;
	}
}

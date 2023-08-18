import java.util.*;
import java.io.*;

public class Main {
	static int N;
	static ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
	static boolean right = false;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		boolean error = false;
					
		for(int i=0; i<N; i++) {
			
			right =false;
			error = false;
			
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
				arrayDeque.add(Integer.parseInt(nums_arr_string[j]));
			}
			
			//명령 수행 
			for(int j=0; j<eachOrder.length; j++) {
				if(eachOrder[j].equals("R")) {
					R();
				}else if(eachOrder[j].equals("D")){
					if(arrayDeque.size() != 0) {
						D();
					}else {
						error = true;
						break;
					}
				}
			}
			
			if(error) {
				System.out.println("error");
			}else if(arrayDeque.size()==0) {
				System.out.println("[]"); //이 부분이 빠져서 틀렸었음 (비어 있을때의 출력)
			}else{
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				
				if(right==true) {
					while(arrayDeque.size()!=0) {
						sb.append(arrayDeque.removeLast()).append(",");
					}
				}else {
					while(arrayDeque.size()!=0) {
						sb.append(arrayDeque.removeFirst()).append(",");
					}
				}	

				sb.deleteCharAt(sb.length()-1);
				sb.append("]");
				
				System.out.println(sb);
			}
			
			
		}
	}
	
	static void R() {
		if(right == true) {
			right = false;
		}else {
			right = true;
		}
	}
	
	static void D() {
		if(right == true) {
			arrayDeque.removeLast();
		}else {
			arrayDeque.removeFirst();
		}
		
	}
}

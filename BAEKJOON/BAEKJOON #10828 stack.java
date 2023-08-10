import java.util.*;
import java.io.*;

public class Main {

	static int N;
	static List<Integer> list = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		//Scanner로 읽으면 시간 초과가 남
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<N; i++) {
			String input = br.readLine();
			String[] string_array = input.split(" ");

			if(string_array.length ==1) {
				String order = string_array[0];		
				
				if(order.equals("pop")) {
					pop();
				}else if(order.equals("size")) {
					size();
				}else if(order.equals("empty")) {
					empty();
				}else if(order.equals("top")) {
					top();
				}
					
			}else {
				String order = string_array[0];
				int num = Integer.parseInt(string_array[1]);
				//명령 Comment
				if(order.equals("push")) {
					push(num);
				}
			}
		}
	}
	
	static void push(int num) {
		list.add(0, num);
	}
	
	static void pop() {
		if(list.size()!=0) {
			System.out.println(list.remove(0));
		}else{
			System.out.println(-1);
		}
	}
	
	static void size() {
		System.out.println(list.size());
	}
	
	static void empty() {
		if(list.size()==0) {
			System.out.println(1);
		}else {
			System.out.println(0);
		}
	}
	
	static void top() {
		if(list.size()!=0) {
			System.out.println(list.get(0));
		}else {
			System.out.println(-1);
		}
	}
}

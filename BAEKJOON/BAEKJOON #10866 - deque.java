import java.util.*;
import java.io.*;

public class Main {
	
	static int N;
	static List<Integer> deque = new ArrayList<>();
	
	
	static void print1D(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		/*
		 * deque.add(3);
		 * deque.add(4);
		 * deque.add(5);
		 * 
		 * 뒷 부분에 넣는 로직
		 * deque.add(deque.size(), 6);
		 */
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//Scanner를 쓰면 시간 초과가 남
		
		N = Integer.parseInt(br.readLine());
	
		
		//main 로직
		for(int i=0; i<N; i++) {
			
			String order_line = br.readLine();
			String[] order_split = order_line.split(" ");
			
			//여기서 list가 2개인지 1개인지 분기처리 한 번 해야한다.
			if(order_split.length == 1) {
				String order = order_split[0];
				
				if(order.equals("pop_front")) {
					pop_front();
				}else if(order.equals("pop_back")) {
					pop_back();
				}else if(order.equals("size")) {
					size();
				}else if(order.equals("empty")) {
					empty();
				}else if(order.equals("front")) {
					front();
				}else if(order.equals("back")) {
					back();
				}

			}else {
				String order = order_split[0];
				int num = Integer.parseInt(order_split[1]);
				
				if(order.equals("push_front")) {
					push_front(num);
				}else if(order.equals("push_back")) {
					push_back(num);
				}				
			}		
		}
	}
	
	static void push_front(int num) {
		deque.add(0, num);
	}
	
	static void push_back(int num) {
		deque.add(deque.size(), num);
	}
	
	static void pop_front() {
		if(deque.size()!=0) {
			System.out.println(deque.remove(0));
		}else {
			System.out.println(-1);
		}
	}
	
	static void pop_back() {
		if(deque.size()!=0) {
			System.out.println(deque.remove(deque.size()-1));
		}else {
			System.out.println(-1);
		}
	}
	
	static void size() {
		System.out.println(deque.size());
	}
	
	static void empty() {
		if(deque.size()!=0) {
			System.out.println(0);
		}else {
			System.out.println(1);
		}
	}
	
	static void front() {
		if(deque.size()!=0) {
			System.out.println(deque.get(0));
		}else {
			System.out.println(-1);
		}
	}
	
	static void back() {
		if(deque.size()!=0) {
			System.out.println(deque.get(deque.size()-1));
		}else {
			System.out.println(-1);
		}
	}

}

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {

	static int N;
	static List<String> string_list;
	
	static void print1D(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		
		string_list = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			string_list.add(sc.next());
		}
		
		//정렬
		Collections.sort(string_list, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				int return_result = o1.length() - o2.length();
				
				if(return_result == 0){
					return o1.compareTo(o2); // 알파벳 순서로 정렬
				}
				return return_result;
			}
		});
		
		List<String> asnwer_list = string_list.stream().distinct().collect(Collectors.toList());
		
		//answer
		for(int i=0; i<asnwer_list.size(); i++) {
			System.out.println(asnwer_list.get(i));
		}
		
	}
}

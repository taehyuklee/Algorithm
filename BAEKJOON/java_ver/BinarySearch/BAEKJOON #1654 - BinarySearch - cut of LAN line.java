package java_ver.BinarySearch;
import java.util.*;
import java.io.*;

public class Main {
	
	static int K, N;
	static long max;
	static int[] len_line;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		K = sc.nextInt();
		N = sc.nextInt();
		
		len_line = new int[K];
		
		//동시에 max값 구하기
		for(int i=0; i<K; i++) {
			int component =  sc.nextInt();
			len_line[i] = component;
			
			if(max <= component) {
				max = component;
			}
		}


		//index가 아니라 길이 자체로 시작하므로 -> start = 1로 시작한다
		long end = max+1;
		long start = 1;
		
		binarySearch(start, end);
		
		
	}
	
	static void binarySearch(long start, long end) {

		
		while(start<end) {
			
			long mid = (end+start)/2;
			
			long cnt = 0;
			
			//조건 구하기
			for(int i=0; i<len_line.length; i++) {
				cnt += (int) len_line[i]/mid;
			}

			if(cnt < N) {
				end = mid;
			}else {
				start = mid+1;
			}
		}
		
		System.out.println(start-1);
	}
	
}

/*
 * 앞서 long형이 아닌 int형으로 했었는데, 아무래도 범위가 int의 마지막 범위까지 존재하다보니까 중간에 연산 하는 인자들이 그 범위를 넘어 서는 것 같다
 * 이에 따라 mid, start, end, cnt 모두 long type으로 안해줘서 계속 틀렸던거다.
 * */
 

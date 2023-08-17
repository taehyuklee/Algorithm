import java.util.*;
import java.io.*;

public class Main {
	
	static int N, M;
	static Map<Integer, Integer> map = new HashMap<>();
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++) {
			Integer key = Integer.parseInt(st.nextToken());
			map.put(key, map.getOrDefault(key, 0)+1);
		}
		
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());

    //출력을 StringBuilder로 하지 않으면 시간초과가 난다. (단순히 System.out.println())으로만 하면 시간 초과
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());		
		for(int i=0; i<M; i++) {
			Integer target_key = Integer.parseInt(st.nextToken());
			Integer value = map.get(target_key);
			
			sb.append(map.getOrDefault(target_key, 0)).append(' ');
		}	
		
		System.out.println(sb);
	}
}

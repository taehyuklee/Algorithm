package SearchAlgorithm;
import java.util.*;
import java.io.*;

public class Main {
	
	static int N, M, cnt;
	static int[][] graph;
	static boolean[] visit;

	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		
		graph = new int[N+1][N+1];
		visit = new boolean[N+1];
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			graph[x][y] = graph[y][x] = 1;
			
		}
		
		int answer = dfs(1);

		System.out.println(answer-1);
		
		
	}
	
	static int dfs(int node) {
		

		//종료 조건
		if(visit[node]==true) {
			return -1; //어떻게 보면 backTracking임.
		}
		
		//탐색 
		visit[node] = true;
		cnt++;
		for(int j=1; j<=N; j++) {
			if(graph[node][j]==1) { //여기서 cnt더하면 안되는 이유 ->이미 visit한걸 check를 위에서 하기때문에, 여기서는 중복해서 더해짐.
//				cnt++;
				dfs(j);
			}
		}
		
		return cnt;
	}
}

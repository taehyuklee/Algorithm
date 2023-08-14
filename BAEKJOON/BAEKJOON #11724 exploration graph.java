import java.util.*;
import java.io.*;

public class Main {
	
	static int N, M;
	static int[][] graph;
	static boolean[] visit;
	
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		//명시적으로 숫자가 쓰여 있는건 헷갈리니까 0부터 index가 아니라 그냥 숫자에 맞추
		graph = new int[N+1][N+1];
		visit = new boolean[N+1];
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			graph[x][y] = graph[y][x] = 1;
		}
		
		int answer = 0;
		for(int i=1; i<=N; i++) {
			if(visit[i]!=true) {
				dfs(i);
				answer++;
			}
		}
		
		System.out.println(answer);
		
	}
	
	
	static void dfs(int node) {
		
		//종료 조건 - 아래에서 visit[node]==false조건이 없어도 되는 이유 
		if(visit[node] == true) {
			//시작지점에서 한 바퀴 돌아서 돌아오면 끝낸다.
			return;
		}
		
		visit[node] = true; //처음꺼도 visit 처리해야하니
		for(int j=1; j<=N; j++) {
			if(graph[node][j] == 1) {
				dfs(j);
			}
		}
	}
}

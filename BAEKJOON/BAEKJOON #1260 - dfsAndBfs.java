import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {
	
	static int N;
	static int M;
	static int V;
	static ArrayList<Integer>[] array;
	static HashMap<Integer, Boolean> visitMap = new HashMap<>();
	static HashSet<Integer> visit;
	static ArrayList<Integer> answerDfs;
	
	public static void print(ArrayList<Integer>[] array) {
		
		for(int i=0; i<array.length; i++) {
			System.out.println(array[i]);
		}
		
	}
	
	public static void printVisit(ArrayList<Boolean>[] array) {
		
		for(int i=0; i<array.length; i++) {
			System.out.println(array[i]);
		}
		
	}
	
	//DFS를 Stack으로 구현하기
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		V = sc.nextInt()-1;
		visit = new HashSet<Integer>();
		
		array = new ArrayList[N]; //array 껍데기를 만들었으니

		for(int i=0; i<N; i++) {
			array[i] = new ArrayList<Integer>(); //array 냅에 ArrayList를 하나씩 생성해야한다.
		}
		
		for(int i=0; i<M; i++) {
			int node1 = sc.nextInt()-1;
			int node2 = sc.nextInt()-1;
			array[node1].add(node2);
			array[node2].add(node1);
			visitMap.put(node1, false);
			visitMap.put(node2, false);

		}
		
		
		//sort로 정렬해준다 낮은 번호로부터
		for(int i=0; i<N; i++) {
			Collections.sort(array[i]);	
		}
		
		print(array);
		
		//visitList = visit.stream().collect(Collectors.toList());

		solution();
	}
	
	
	public static void solution() {
		
		stackDfs();
		
		
		int dept=1;
		int init = V;
		recursiveDfs(dept, init);
		
		BFS();
		
	}
	
	
	public static void stackDfs() {
		//처음 시작 V임.
		//ArrayList<Integer> first = array[V];
		
		Stack<Integer> stack = new Stack<Integer>();
		
		stack.add(V);
		
		while(!stack.isEmpty()) {
			
			int node = stack.pop();
			
			ArrayList<Integer> nodeList = array[node];
			
			for(int j=0; j<nodeList.size(); j++) {
				
				int node2 = nodeList.get(j);
				
				if(visitMap.get(node2) != true) {
					stack.add(node2);
					visitMap.put(node2, true);
				}
			}
		}	
	}
	
	
	public static void recursiveDfs(int dept, int init) {
		
		if(dept ==3) { // 끝나는 조건
			return;
		}

		ArrayList<Integer> exploreArray = array[init];
		answerDfs.add(init);
		visitMap.put(init, true);
		
		for(int i=0; i<exploreArray.size(); i++) {
			
			int newNum = exploreArray.get(i);
			int newDept = dept+1;		
			
			recursiveDfs(newNum, newDept);
			
			visitMap.put(newNum, false);
			
		}
		
		

	}
	
	public static void BFS() {
		
		
		
	}
	

}

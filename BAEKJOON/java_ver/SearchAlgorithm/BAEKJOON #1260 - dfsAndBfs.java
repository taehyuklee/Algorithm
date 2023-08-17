package SearchAlgorithm;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

public class Main {
	
	static int N;
	static int M;
	static int V;
	static ArrayList<Integer>[] array;
	static HashMap<Integer, Boolean> visitMap = new HashMap<>();
	static HashMap<Integer, Boolean> visitMap2 = new HashMap<>();
	static ArrayList<Integer> answerDfs = new ArrayList<>();
	static ArrayList<Integer> answerBfs = new ArrayList<>();
	
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
		
		if(V + 1 >N) {
			N = V+1;
		}
		
		array = new ArrayList[N]; //array 껍데기를 만들었으니

		for(int i=0; i<N; i++) {
			array[i] = new ArrayList<Integer>(); //array 냅에 ArrayList를 하나씩 생성해야한다.
		}
		
		for(int i=0; i<M; i++) {
			int node1 = sc.nextInt()-1;
			int node2 = sc.nextInt()-1;
			array[node1].add(node2);
			array[node2].add(node1);
			visitMap.put(node1, false); visitMap2.put(node1, false);
			visitMap.put(node2, false); visitMap2.put(node2, false);
		}
		
		
		//sort로 정렬해준다 낮은 번호로부터
		for(int i=0; i<N; i++) {
			Collections.sort(array[i]);	
		}
		
		//print(array);
		
		//visitList = visit.stream().collect(Collectors.toList());

		solution();
	}
	
	
	public static void solution() {
		
		//System.out.println(visitMap);
		
		//stackDfs();
		int dept=1; int init = V;
		answerDfs.add(V); visitMap.put(V, true);
		recursiveDfs(dept, init);
		
	
		bfs(init);

		for(int i=0; i<answerDfs.size(); i++) {
			answerDfs.set(i, answerDfs.get(i)+1); //set을 이렇게 사용하네;;
			answerBfs.set(i, answerBfs.get(i)+1);
		}
		
		//답 출력 형식이 틀렸었다 (그냥 answerDfs, answerBfs 두개를 쌩으로 출력했다)
		for(int j=0; j<answerDfs.size(); j++) {
			System.out.print(answerDfs.get(j) +" ");
		}
		System.out.println();
		for(int j=0; j<answerBfs.size(); j++) {
			System.out.print(answerBfs.get(j) + " ");
		}
		
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
		
		//종료 조건
		int cnt = 0;
		
		//visitMap안에 내용들을 모두 보고싶어도 index가 없기때문에 볼수가 없다.
		Set<Integer> keys = visitMap.keySet();
//		System.out.println(keys);
		
//		for(int i=0; i<visitMap.size(); i++) {
//			Iterator iter = keys.iterator();
//			int key = (int) iter.next();
//			System.out.println(visitMap.get(key));
//			if(visitMap.get(key) == true) {
//				cnt +=1;
//			}
//		}
	
		while(keys.iterator().hasNext()) {
			int key = keys.iterator().next();
//			System.out.println(visitMap.get(key));
			if(visitMap.get(key) == true) {
				cnt +=1;
			}
		
		if(cnt == visitMap.size()) { // 끝나는 조건
			return;
		}
		
//		System.out.println("in it :    " + init);
		ArrayList<Integer> exploreArray = array[init];
//		System.out.println(visitMap);
//		System.out.println("answer " + answerDfs);
//		System.out.println(exploreArray);
		
		for(int i=0; i<exploreArray.size(); i++) {
			
			if(visitMap.get(exploreArray.get(i)) == false) {
				int newNum = exploreArray.get(i);
				int newDept = dept+1;		
				
				answerDfs.add(newNum);
				visitMap.put(newNum, true);
				
				recursiveDfs(newDept, newNum); //recursive args를 반대로 넣었다;;

				//원래 끝의 조건이 확실한 DFS같은 경우 이렇게 처리해준다
//				answerDfs.remove(answerDfs.size()-1);
//				visitMap.put(newNum, false);
			}
		}
		}
	}
	
	
	public static void bfs(int init) {
		
		Queue<Integer> q = new LinkedList<>();
		q.add(init);
		answerBfs.add(init);
		
		visitMap2.put(init, true);
		
		while(!q.isEmpty()) {
			
			int curInt = q.poll();
			
			ArrayList<Integer> arrayListInt = array[curInt];
			
			for(int i=0; i<arrayListInt.size(); i++) {
				
				int newInt = arrayListInt.get(i);
				
				if(visitMap2.get(newInt) != true) {
					q.add(newInt);
					visitMap2.put(newInt, true);
					answerBfs.add(newInt);
				}
				
			}	
		}
		
		
		
	}
	

}

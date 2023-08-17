package DataStructure;
import java.util.*;

public class Main {
	
	static int N;
	static List<Integer> deque = new ArrayList<>();
	static Queue<Integer> Q = new LinkedList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		for(int i=1; i<=N; i++) {
			Q.add(i); 
		}
		
		while(Q.size() !=1) {
			throwAway();
			topToDown();
		}
		
		System.out.println(Q.peek());
	}
	
	static void throwAway() {
		Q.poll();
	}
	
	static void topToDown() {
		int tempInt = Q.poll();
		Q.add(tempInt);
	}
	
}

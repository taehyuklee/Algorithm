import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		int [] array = new int[] {1,2,3};
		int [] output = new int[array.length];
		boolean [] visit = new boolean[array.length];
		int r = 3;
		int depth = 0;
		
		
		perm(array, output, visit, r, depth);
		
	}

	
	static void perm(int[] array,int[] output, boolean[] visit, int r, int depth) {
		
		//종료조건 
		if(depth == r) {
			for(int i=0; i<output.length; i++) {
				System.out.print(output[i]+ " ");
				
			}
			System.out.println();
			return;
		}
		
		
		//탐색 
		for(int i=0; i<array.length; i++) {
			if(visit[i] ==false) {
				visit[i] = true;
				output[depth] = array[i];
				perm(array, output, visit, r, depth+1);
				visit[i] = false;
			}

		}
		
	}
}

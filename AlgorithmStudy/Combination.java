import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		int[] array = {1,2,3,4};
		boolean[] visit = new boolean[array.length];
		int depth=0;
		int start=0;
		
		for(int i=0; i<array.length; i++) {
			combination(array, visit, start, depth, i+1);
		}
		
		
	}
	
	public static void combination(int[] array, boolean[] visit, int start, int depth, int r) {
		
		//종료 조건
		if(depth == r) {
			for(int i=0; i<array.length; i++) {
				if(visit[i]==true) System.out.print(array[i] + " ");
			}
			System.out.println();
			return;
		}
		
		
		//탐색 수행
		for(int i=start; i<array.length; i++) {
			if(!visit[i]) {
				visit[i]=true;
				//여기서 i+1을 하는 것은 permutation에서 output[depth] = array[i];하는 행위와 같은 행위다. 이거 하고 다음 층으로 내려가니까
				combination(array, visit , i+1, depth+1, r);
				visit[i] = false;
			}
		}
		
	}
	
}

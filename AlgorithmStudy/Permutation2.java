/*이해한 내용을 기반으로 스스로 한 번 짜본 Permutation*/

public class Main {
	
	public static void main(String[] args) {
		
		int[] array= {1,2,3};
		int[] output = new int[array.length];
		boolean[] visit = new boolean[array.length];
		
		int depth = 0;
		
		//i+1부터 시작하는 이유는 r=0이면 어차피 0이니까 그냥 1부터 시작하는거
		for(int i=0; i<array.length; i++) {
			permutation(array, output, visit, depth, i+1);
		}
		
	}
	
	static void permutation(int[] array, int[] output, boolean[] visit, int depth, int r) {
		
		//종료 조건
		if(depth == r) {
			for(int i=0; i<output.length; i++) {
				if(output[i]==0) continue;
				System.out.print(output[i] + " ");
			}
			System.out.println();
			return;
		}
		
		
		//탐색 수행
		for(int i=0; i<output.length; i++) {
			
			if(visit[i] == false) {
				output[depth] = array[i];
				visit[i] = true;
				permutation(array, output, visit, depth+1, r);
				visit[i] = false;
			}
			
		}
		
		
	}
	
}

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		
		int [] array = new int[] {1,2,3,4};
		int [] output = new int[array.length];
		boolean [] visit = new boolean[array.length];
		int r = 3;
		int depth = 0;
		int start = 0;
		
		comb(array,visit,output, start,r,depth);
		
	}
	
	static void comb(int[] array, boolean[] visit, int[] output, int start, int r, int depth) {
		
		//종료 조건
		if(depth==r) {
			for(int i=0; i<output.length; i++) {
				
				System.out.print(output[i] + " ");
					
			}
			System.out.println();
			return;
		}
		
		/*탐색 (start의 의미 -> tree node에서 자기 자신보다 이전 숫자들은 이미 탐색이 끝났기때문에 자기 이상의 조합만 보면 된다_
		*순열의 경우는 순서가 있기때문에 0부터 다 봐야 하지만,조합은 이미 앞에서 1, 2를 봤기에 2, 1을 다시 볼 필요가 없이 2, 3만 보면 되기에 
		*start =1 로 잡아서 2부터 시작하게 하는 것이다. (그리고 output은 true인 것들만 출력하면 된다) 
		*여기서도 뭐 이렇게 output을 굳이 쓸수 있는 것 같지만, 안쓰는 이유는 확연한 거 같다. 순서가 없기때문에 true, false 들어가아 안들어가냐로 충분히 
		*판단 가능하기때문이다.*/
		for(int i=start; i<array.length; i++) {
			
			if(visit[i] == false) {
				visit[i] = true;
				output[depth] = array[i];
				comb(array, visit, output, i+1, r, depth+1);
				visit[i] = false;
			}
			
		}
		
	}

}

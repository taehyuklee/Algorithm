import java.util.*;

public class Main {
	
	static int N, r, c, answer_num;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		r = sc.nextInt();
		c = sc.nextInt();
		
		int length = (int) Math.pow(2, N);
		
		Z(length, 0, 0);

	}
	
	static void Z(int size, int new_r, int new_c) {
		
		//종료 조건
		if(size==1) {
			System.out.println(answer_num);
			return;
		}
		
		//분할 1사분면, 2사분면, 3사분면, 4사분면 
		if(r<size/2 + new_r && c<size/2 + new_c) {
			//2사분면 
			Z(size/2, new_r, new_c);
			
		}else if(r<size/2 + new_r && c>=size/2 + new_c) { 
			//사분면으로 움직임에 따라 좌표도 옮겨져야 함. 그걸 반영한게 new_r+size/2 이런식으로 recursive하게 된
			//1사분면 
			answer_num += (size/2)*(size/2);
			Z(size/2, new_r, new_c+ size/2);
			
		}else if(r>=size/2 + new_r && c<size/2 + new_c) {
			//3사분면 
			answer_num += 2*(size/2)*(size/2);
			Z(size/2, new_r+size/2, new_c);
			
		}else if(r>=size/2 + new_r && c>=size/2 + new_c) {
			//4사분면 
			answer_num += 3*(size/2)*(size/2);
			Z(size/2, new_r + size/2, new_c+size/2);
			
		}
		
		
	}

}

/*divide & conquer로 풀어야 시간내에 들어올수 있고 어차피 분할정복을 위해서는 재귀를 이용하게 된다.*/

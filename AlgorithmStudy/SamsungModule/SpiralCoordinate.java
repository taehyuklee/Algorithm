import java.util.*;

class Node{
	int x, y, dir;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "[x: " + x + " y: " + y  + " ]";
	}
	
}

public class Main {
	
	static Map<Integer, Node> spiralMap;
	static int N;
	
	public static void main(String[] args) {
		
		N = 5;
		spiralMap = new HashMap<>();
		
		makdSpiral();
		System.out.println(spiralMap);
		
	}
	
	
	public static void makdSpiral() {
		
		int x_0 = N/2, y_0 = N/2;
		spiralMap.put(0, new Node(x_0, y_0));
		int count=0, refDept=1, currDept=0, dir=0;
		int [] dx = {-1,0,1,0};
		int [] dy = {0,1,0,-1};
		
		for(int i=1; i<N*N; i++) {
			
			int x_new = x_0 + dx[dir];
			int y_new = y_0 + dy[dir];
			
			currDept+=1;
			
			if(currDept == refDept) {
				
				dir = (dir+1)%4;
				count+=1; //방향이 바뀐 count
				
				if(count==2) {//2번 바뀔때마다 길이를 증가시켜준다
					refDept +=1;
					count=0;
				}
				currDept=0;
	
			}
					
			x_0 = x_new;
			y_0 = y_new;
					
			spiralMap.put(i, new Node(x_new, y_new));
			
		}
	}

}


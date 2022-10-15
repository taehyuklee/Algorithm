import java.util.*;

class Node{
	int x, y;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + "]";
	}
}

public class Main {
	
	public static Map<Integer, Node> makeSpiralCoord() {
		
		int[] dx = {0,1,0,-1};
		int[] dy = {-1,0,1,0};
		
		Map<Integer, Node> spiralMap = new HashMap<Integer, Node>();
		
		int N = 5;
		int xOld= 2; int yOld = 2;
		int turn=0; int depth =0; int length =1; int lenCount=0;
		int num=0;
		
		spiralMap.put(num, new Node(xOld, yOld));
		
		while(num<N*N-1) {
			
			
			int nx = xOld + dx[turn];
			int ny = yOld + dy[turn];
			
			depth += 1;
			num +=1;
			
			spiralMap.put(num, new Node(nx, ny));
			
		
			if(depth == length) {
				turn = (turn+1)%4;
				depth=0;
				lenCount++;
				
				//turn할때 length도 같이 올려야 하는지 확인하기
				if(lenCount==2) {
					length++;
					lenCount=0;
				}
				
			}
			
			xOld = nx;
			yOld = ny;
			
			
		}
		
		return spiralMap;
		
	}
	
	public static void main(String[] args) {
		
		Map<Integer, Node> spiralMap = makeSpiralCoord();
		
		
		for(int i=0; i<spiralMap.size(); i++) {
			System.out.println("key: " +i + " " + "value: " +spiralMap.get(i));
		}
		
		
	}

}

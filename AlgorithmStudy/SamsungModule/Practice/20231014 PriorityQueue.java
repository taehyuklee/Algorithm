import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class Node{
	int x, y, z, length;
	boolean bool;
	
	public Node(int x, int y, int z, int length, boolean bool) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = length;
		this.bool = bool;
		
	}
	
	@Override
	public String toString() {
		return "[bool: " + bool + " x: " + x + " y: " + y + " z: " + z + " length: " + length + "] \n";
	}
}

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		System.setIn(new FileInputStream("C:\\Users\\user\\Desktop\\input.txt"));
		Scanner sc = new Scanner(System.in);
		
		PriorityQueue<Node> Q = new PriorityQueue<>(new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				
				if(o1.bool ==true) {
					if(o2.bool ==true) {
						return 0;
					}else {
						return 1;
					}
				}else if(o2.bool==true){
					return -1;
				}else {
					if(o2.length-o1.length==0) {
						if(o1.x - o2.x==0) {
							
							if(o1.y-o2.y==0) {
								return o1.z-o2.z;
							}
							
							return o1.y - o2.y;
						}
						
						return o1.x - o2.x;
					}
					
					return  o2.length-o1.length;
				}
			}
			
		});
		
		//x 5, y 8, z 10, length 100
		//x 5, y 7, z 10, length 100
		//x 3, y 9, z 3, length 80
		//x 8, y 3, z 5, length 40
		//x 3, y 3, z 2, length 40
		//x 3, y 3, z 1, length 40
		//x 3, y 7, z 10, length 100
		Node node1 = new Node(5,8,10,100, false);
		Node node2 = new Node(5,7,10,100, false);
		Node node3 = new Node(3,9,3,80, false);
		Node node4 = new Node(8,3,5,40, false);
		Node node5 = new Node(3,3,2,40, false);
		Node node6 = new Node(3,3,1,40, false);
		Node node7 = new Node(3,3,2,100, false);
		Node node8 = new Node(3,3,1,100, true);
		Node node9 = new Node(3,3,2,70, true);
		Node node10 = new Node(5,3,2,100, false);

		
		Q.add(node1);
		Q.add(node2);
		Q.add(node3);
		Q.add(node4);
		Q.add(node5);
		Q.add(node6);
		Q.add(node7);
		Q.add(node8);
		Q.add(node9);
		Q.add(node10);
		
		for(int i=0; i<Q.size(); i++) {
			Node outNode = Q.poll();
			System.out.println(outNode);
			i--;
		}
		
		System.out.println(Q.size());
		
		
		
		
	}
	
}

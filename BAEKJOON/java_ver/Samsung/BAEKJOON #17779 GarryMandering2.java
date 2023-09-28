import java.util.*;

class Node{
	int x, y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y + "  ";
	}
}

public class Main {
	
	private static int N, area1, area2, area3, area4, area5;
	private static int[][] board;
	private static List<Node> boarderNode = new ArrayList<>();
	private static List<Integer> answerList = new ArrayList<>();

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		
		board = new int[N+1][N+1];
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		
		//단위 Test
		//unitTest();
		
		 solution();
		 answerList.sort(Comparator.naturalOrder());
		 System.out.println(answerList.get(0));

	}
	
	static void solution() {
		
		for(int d1=1; d1<=N; d1++) {
			for(int d2=1; d2<=N-d1; d2++) {
				
				/*x, y좌표 관련해서 넣어보자*/
				for(int x=1; x<=N-(d1+d2); x++) {
					for(int y=1+d1; y<=N-d2; y++) {
						getArea5(makeBoarder(d1,d2,x,y));
						getAreaFirstToFourth(d1,d2,x,y);
						getSmallest();
						area1=0; area2=0; area3=0; area4=0; area5=0;
						boarderNode.clear();
						
					}
				}
			}
		}
	}
	
	static void getSmallest() {
		List<Integer> partSmall = new ArrayList<>();
		partSmall.addAll(Arrays.asList(area1, area2, area3, area4, area5));
		
		Collections.sort(partSmall);
		answerList.add(Math.abs(partSmall.get(partSmall.size()-1)) - partSmall.get(0));
	}
	

	
	static void getAreaFirstToFourth(int d1, int d2, int x, int y) {
		
		//특정 범위의 Node에서 이전에 존재하는 일부 Node를 제외할때 어떻게 구현하면 좋을까? 
		
		//firstArea
		for(int i=1; i<x+d1; i++) {
			for(int j=1; j<=y; j++) {
				if(checkArea5(i,j)) continue;
				area1 += board[i][j];
			}
		}
		//6+24+5 = 35
		
		//secondArea
		for(int i=1; i<=x+d2; i++) {
			for(int j=y+1; j<=N; j++) {
				if(checkArea5(i,j)) continue;
				area2 += board[i][j];
			}
		}
		//23+13 = 36
		
		//thirdArea
		for(int i=x+d1; i<=N; i++) {
			for(int j=1; j<y-d1+d2; j++) {
				if(checkArea5(i,j)) continue;
				area3 += board[i][j];
			}
		}	
		//18
		
		//fourthdArea
		for(int i=x+d2+1; i<=N; i++) {
			for(int j=y-d1+d2; j<=N; j++) {
				if(checkArea5(i,j)) continue;
				area4 += board[i][j];
			}
		}	
		//35
	}
	
	static boolean checkArea5(int i, int j) {
		for(int k=0; k<boarderNode.size(); k++) {
			Node targetNode = boarderNode.get(k);
			if(targetNode.x ==i && targetNode.y==j) {
				return true;
			}
		}
		return false;
	}
	
	
	static void getArea5(List<Node> lists) {
		
		List<Node> extraNodes = new ArrayList<>();

		//first node
		Node fistNode = lists.get(0);
		area5 += board[fistNode.x][fistNode.y];
				
		//last node
		Node lastNode = lists.get(lists.size()-1);
		area5 += board[lastNode.x][lastNode.y];
		
		for(int i=1; i<lists.size()-1; i++) {
			Node startNode = lists.get(i);
			i++; //여기서 i에 1을 더 더해준다
			Node endNode = lists.get(i);
			
			for(int y=startNode.y; y<=endNode.y; y++) {
				extraNodes.add(new Node(startNode.x, y));
				area5 += board[startNode.x][y];
			}	
		}
		lists.addAll(extraNodes); // 이렇게 마지막에 추가해주는게 맞다.

	}

	
	static List<Node> makeBoarder(int d1, int d2, int x, int y) {
		
		//first line
		for(int i=0; i<=d1; i++) {
			boarderNode.add(new Node(x+i,y-i));
		}
		
		//second line (처음에 중복되니까 1더해줌)
		for(int i=1; i<=d2; i++) {
			boarderNode.add(new Node(x+i,y+i));
		}
		
		//third line (처음에 중복되니까 1 더해줌)
		for(int i=1; i<=d2; i++) {
			boarderNode.add(new Node(x+d1+i, y-d1+i));
		}
		
		//fourth line (처음과 마지막 모두 중복돼서 제외)
		for(int i=1; i<d1; i++) {
			boarderNode.add(new Node(x+d2+i, y+d2-i));
		}
		
		//x로 순서대로 정렬 만약 x가 같으면 y순으로 정렬 -> 나중에 내부 색칠하기 편하게 하기 위해서
		Collections.sort(boarderNode, new Comparator<Node>() {
			
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.x==o2.x) {
					return o1.y-o2.y;
				}else {
					return o1.x-o2.x;
				}
			}
			
		});
		
		return boarderNode;

	}
	
	/*Unit Test*/
	static void unitTest() {
		//borderLine test (unitTest)
		//static List<Node> makeBoarder(int d1, int d2, int x, int y) 
		int d1 = 2; int d2 = 1; int x = 3; int y = 5;
		print2D(board);
		System.out.println(makeBoarder(d1, d2, x, y));  
		getArea5(makeBoarder(d1, d2, x, y));
		System.out.println(area5);
		getAreaFirstToFourth(d1, d2, x, y);
		System.out.println(area1 + " " + area2 + " " + area3 + " " + area4);
		//7+7+8+9+7+8+9+9 = 14+16+27+7 = 57+7 = 64
	}
	
	
	public static void print2D(int[][] board) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}

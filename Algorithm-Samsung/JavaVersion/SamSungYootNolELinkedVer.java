import java.util.*;
import java.io.*;

class Node{
	int score, nodeInx, blueNext, redNext, horseNum;
	boolean isBlue, visit;
	
	public Node(int score, int nodeInx, int blueNext, int redNext, boolean isBlue) {
		this.score = score;
		this.nodeInx = nodeInx;
		this.blueNext = blueNext;
		this.redNext = redNext;
		this.isBlue = isBlue;
	}

	@Override
	public String toString() {
		return "Node [score=" + score + ", nodeInx=" + nodeInx + ", blueNext=" + blueNext + ", redNext=" + redNext
				+ ", isBlue=" + isBlue + ", visit=" + visit + ", horseNum=" + horseNum + "]";
	}
	
	public Node(Node node) {
		this.score = node.score;
		this.nodeInx = node.nodeInx;
		this.blueNext = node.blueNext;
		this.redNext = node.redNext;
		this.isBlue = node.isBlue;
		this.visit = node.visit;
		this.horseNum = node.horseNum;
	}
	
}


class Horse{
	int currScore, nodeInx, horseNum;
	
	public Horse(int currScore, int nodeInx, int horseNum) {
		this.currScore = currScore;
		this.nodeInx = nodeInx;
		this.horseNum = horseNum;
	}

	@Override
	public String toString() {
		return "Horse [currScore=" + currScore + ", nodeInx=" + nodeInx + ", horseNum=" + horseNum + "]";
	}
	
	public Horse(Horse horse) {
		this.currScore = horse.currScore;
		this.nodeInx = horse.nodeInx;
		this.horseNum = horse.horseNum;
	}
	
}


public class Main {
	
	static int[] order = new int[10];
	static int maxScore=0;

	
	public static void main(String[] args) throws IOException {
		
		//Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<10; i++) {
			order[i] = Integer.parseInt(st.nextToken());
		}
		
		solution();
		
	}
	
	public static void solution() {
		
		Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
		Map<Integer, Horse> horseMap = new HashMap<Integer, Horse>();
		
		nodeSetting(nodeMap);
		
		horseSetting(horseMap);
		
		int turn = 0;
		int score = 0;

		dfs(nodeMap, horseMap, turn, score);
		System.out.println(maxScore);
		
	}
	
	
	
	public static void dfs(Map<Integer, Node> nodeMap, Map<Integer, Horse> horseMap, int turn, int score) {
		
		if(maxScore<=score) {
			maxScore=score;
		}
		
		if(turn==10) {
			return;
		}
		
		for(int i=1; i<=4; i++) {
			
			Map<Integer, Node> copiedNode = deepCopyNode(nodeMap);
			
			Map<Integer, Horse> copiedHorse = deepCopyHorse(horseMap);
			
			//System.out.println(copiedHorse);
			
			//새로운 말을 꺼낸
			Horse horse = copiedHorse.get(i);
			
			if(horse.nodeInx != 32) {
				
				//현재 말이 어느 노드에 있는지를 꺼내온다.
				int nodeInx = horse.nodeInx;
//				System.out.println(nodeInx);
				Node currNode = copiedNode.get(nodeInx);
				
//				System.out.println(currNode);
				currNode.visit = false;
				currNode.horseNum =0;
				
				//말의 위치가 blue위에 있는지 Red위에 있는지 확인하기
				boolean isBlue = currNode.isBlue;
				
				int diceNum = order[turn];
				
				for(int j=1; j<=diceNum; j++) {
						if(isBlue==false) {
							int newInx = currNode.redNext; //다음 node index받아온
							if(newInx == -100) {
								break;
							}
							currNode = copiedNode.get(newInx);
							
						}else {
							int newInx = currNode.blueNext;
							if(newInx == -100) {
								break;
							}
							currNode = copiedNode.get(newInx);
							isBlue = false;
						}
				}
				
				
				//System.out.println(currNode);
				//움직이고 나서 도착지인지 확인하기
				if(currNode.nodeInx>=32) {
					horse.currScore = -1;
					horse.nodeInx =32;
				}
				
				//그 앞에 말이 있는가?
				if(currNode.visit == true) {
					continue;
				}
				
				//다 통과했으면 업데이트해주자
				horse.currScore = currNode.score;
				horse.nodeInx = currNode.nodeInx;
				if(currNode.nodeInx !=32) { //도착지점도 내가 True로 해놔서 문제가 됐었
					currNode.visit = true;
				}
				currNode.horseNum = horse.horseNum;
				
				int newTurn = turn + 1;
				int newScore = score + horse.currScore;
				
//				System.out.println(newTurn);
//				System.out.println(newScore);
//				
//				System.out.println(copiedNode);
//				System.out.println(copiedHorse);
				
				dfs(copiedNode, copiedHorse, newTurn, newScore);
				
			}
		}
	}

	public static void horseSetting(Map<Integer, Horse> horseMap) {
		for(int i=1; i<=4; i++) {
			horseMap.put(i, new Horse(0,0,i));
		}
		
		/**
		 *		this.currScore = currScore;
				this.nodeInx = nodeInx;
				this.horseNum = horseNum; 
		 */
	}
	
	public static void nodeSetting(Map<Integer, Node> nodeMap) {
		
		for(int i=0; i<20; i++) {
			
			boolean isBlue =false;
			int blueNext = -10;
			if(i==5 || i==10 || i ==15) {
				isBlue=true;
				if(i==5)
					blueNext = 20;
				if(i==10)
					blueNext = 23;
				if(i==15)
					blueNext = 25;
			}
			
			nodeMap.put(i, new Node(2*i, i, blueNext, i+1, isBlue));
		}
		//19일때만 따로 처리해준다.
		nodeMap.get(19).redNext = 31;
		
		/*
		 * 		this.score = score;
				this.nodeInx = nodeInx;
				this.blueNext = blueNext;
				this.redNext = redNext;
				this.isBlue = isBlue;
		 * */
		
		//외에는 그냥 메뉴얼하게 넣어준다
		nodeMap.put(20, new Node(13, 20, -10, 21, false));
		nodeMap.put(21, new Node(16, 21, -10, 22, false));
		nodeMap.put(22, new Node(19, 22, -10, 28, false));
		nodeMap.put(23, new Node(22, 23, -10, 24, false));
		nodeMap.put(24, new Node(24, 24, -10, 28, false));
		nodeMap.put(25, new Node(28, 25, -10, 26, false));
		nodeMap.put(26, new Node(27, 26, -10, 27, false));
		nodeMap.put(27, new Node(26, 27, -10, 28, false));
		nodeMap.put(28, new Node(25, 28, -10, 29, false));
		nodeMap.put(29, new Node(30, 29, -10, 30, false));
		nodeMap.put(30, new Node(35, 30, -10, 31, false));
		nodeMap.put(31, new Node(40, 31, -10, 32, false));
		nodeMap.put(32, new Node(0, 32, -10, -100, false));
		
	}
	
	public static Map<Integer, Node> deepCopyNode(Map<Integer, Node> NodeMap){
		
		Map<Integer, Node> newMap = new HashMap<>();
		for(int i=0; i<=32; i++) {
			Node copiedNode = new Node(NodeMap.get(i));
			newMap.put(i, copiedNode);
		}
		
		return newMap;
	}
	
	public static Map<Integer, Horse> deepCopyHorse(Map<Integer, Horse> HorseMap){
		
		Map<Integer, Horse> newMap = new HashMap<>();
		for(int i=1; i<=4; i++) {
			Horse copiedHorse = new Horse(HorseMap.get(i));
			newMap.put(i, copiedHorse);
		}
		
		return newMap;
		
	}

}

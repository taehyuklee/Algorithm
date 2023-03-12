import java.util.*;
import java.io.*;

class Node{

    int group;
    int x;
    int y;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(x: " + this.x + " y: " + this.y + ")"; //"group: " + this.group + 
    }
}

public class Main {

    static Map<Integer, ArrayList<Node>> groupMap = new HashMap<Integer, ArrayList<Node>>();
    static int N, answer=0;
    static int[][] board, board2;
    static ArrayList<Node> refGrp = new ArrayList<Node>();
    static ArrayList<Node> compareGrp = new ArrayList<Node>();
    static ArrayList<int[]> combNum = new ArrayList<int[]>(); 

    public static void print(int[][] array){
        for(int i=0; i<array.length; i++){
            for(int j=0; j<array[0].length; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void print(ArrayList<int[]> comList) {
    	
    	for(int i=0; i<comList.size(); i++) {
    		//for(int j=0; j<comList.get(i).length; j++) {
    			System.out.println("("+ comList.get(i)[0] + " " + comList.get(i)[1] +")" );
    		//}
    	}
    	
    }
    
    
    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        board = new int[N][N];
        board2 = new int[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                board[i][j] = sc.nextInt();
                board2[i][j] = board[i][j];
            }
        }

        solution();

    }

    public static void solution(){

    	//아래 로직을 iteration 돌린다.
    	for(int turn=0; turn<4; turn++) {
	        //group 탐험해오기.
	        bfs();

	        //조합 만들어오
	        combination();
	        
	        //조합을 가지고 뽑아내서 점수를 계산한다.
	        getScore();
	        
	        //돌린다
	        rotateCross();
	        
	        //네모를 돌린다.
	        rotateSquare();

	        //자료구조 초기화 
	        initialization();
    	}
        
    	System.out.println(answer);

    }

    public static void bfs(){
        
        boolean[][] visit = new boolean[N][N];
        Queue<Node> queue = new LinkedList<Node>();
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};

        //group 관리
        int groupNum = 0;
        
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
            	
            	if(visit[i][j] == true) {
            		continue;
            	}
            	
                //group을 새로 만든
                groupMap.put(groupNum, new ArrayList<>());
                

                Node node = new Node(i, j);
                queue.add(node);
                addNode(groupNum, node);

                visit[i][j] = true;

                while(!queue.isEmpty()){
                    node = queue.poll();

                    for(int dir=0; dir<4; dir++){
                        int newX = node.x + dx[dir];
                        int newY = node.y + dy[dir];

                        if(newX>=0 && newX<N && newY>=0 && newY<N){
                            if(board[newX][newY] == board[i][j] && visit[newX][newY] == false){

                                Node inputNode = new Node(newX, newY);
                                queue.add(inputNode);
                                visit[newX][newY] = true;
                                
                                //Map group에 넣는
                                addNode(groupNum, inputNode);
                            }
                        }

                    }

                }
                
                //다음 그룹.
                groupNum +=1;

            }
        }
    }

    public static void addNode(int groupNum, Node node) {
        ArrayList<Node> groupList = groupMap.get(groupNum);
        groupList.add(node);
    }

    
    
    public static void combination() {
    	
    	Set<Integer> keySet = groupMap.keySet();

    	int indx = 0, num=0;
    	//기준 group 따오기.
    	while(indx<keySet.size()) {
	
	    	for(int i=indx+1; i<keySet.size(); i++) {
	    		combNum.add(new int[2]);
	    		combNum.get(num)[0] = indx;
	    		combNum.get(num)[1] = i;
	    		num+=1;
	    		
	    	}
	    	
	    	indx+=1;
    	}
    	
    	
    }

    
    public static void getScore() {
    	
    	int score =0;

    	
    	for(int i=0; i<combNum.size(); i++) {
    		
    		int grpNum1 = combNum.get(i)[0];
    		int grpNum2 = combNum.get(i)[1];

    		//data형 ArrayList<Node>
    		refGrp = groupMap.get(grpNum1);
    		compareGrp = groupMap.get(grpNum2);
    		
    		int tempX = refGrp.get(0).x;
    		int tempY = refGrp.get(0).y;

    		int countSide = getSideNum(i);	
    		
    		score = (refGrp.size()+compareGrp.size())*countSide
    				*(board[tempX][tempY]*board[compareGrp.get(0).x][compareGrp.get(0).y]);

    		answer += score;
    	
    	}

    }
    
    public static int getSideNum(int i) {
    	
    	int[] dx = {-1,1,0,0};
    	int[] dy = {0,0,-1,1};
    	int countSide = 0;
	

		int grpNum1 = combNum.get(i)[0];
		int grpNum2 = combNum.get(i)[1];

		//data형 ArrayList<Node>
		refGrp = groupMap.get(grpNum1);
		compareGrp = groupMap.get(grpNum2);
		
		for(int j=0; j<refGrp.size(); j++) {
			Node nodeElement = refGrp.get(j);
			int originX = nodeElement.x;
			int originY = nodeElement.y;
			
			//4방향으로 돌아가면서 경계선을 알아내야 한다.
			for(int dir=0; dir<4; dir++) {
				
				int newX = nodeElement.x + dx[dir];
				int newY = nodeElement.y + dy[dir];
				
				if(newX>=0 && newX<N && newY>=0 && newY<N) {
					
					if(board[newX][newY] != board[originX][originY] && belongGrp(newX, newY, compareGrp)) {
						
						countSide+=1;
						
					}
					
				}
			}
		}	
    	
    	return countSide;
    }
    
    public static boolean belongGrp(int x, int y, ArrayList<Node> listNodes) {
    	
    	boolean belong = false;
    	
    	for(int i=0; i<listNodes.size(); i++) {
    		
    		Node targetNode = listNodes.get(i);
    		if(targetNode.x == x && targetNode.y ==y) {
    			belong = true;
    		}
    	}
    	
    	return belong;
    }
    
    
    
    public static void rotateCross() {
    	
    	//일단 한바퀴 board를 돌리고 보자.
    	for(int i=0; i<N; i++) {
    		for(int j=0; j<N; j++) {
    			//board2[i][j] = board[N-1-j][i];
    			board2[N-1-j][i] = board[i][j];
    		}
    	}

    	//cross부분만 board1에 끼워 넣어주자
    	for(int i=0; i<N; i++) {
    		board[i][N/2] = board2[i][N/2];
    		board[N/2][i] = board2[N/2][i];
    	}
    }
   
    public static void rotateSquare() {
    	//4부분으로 나눠야 한다.
    	int[][] tempSquare = new int[(N-1)/2][(N-1)/2];
    	int[][] tempSquare2 = new int[(N-1)/2][(N-1)/2];
    	
    	//2사분
    	for(int i=0; i<(N-1)/2; i++) {
    		for(int j=0; j<(N-1)/2; j++) {
    			tempSquare[i][j] =board[i][j];
    		}
    	}
    	
    	int[][] rslt = rotateSub(tempSquare);
    	
    	for(int i=0; i<(N-1)/2; i++) {
    		for(int j=0; j<(N-1)/2; j++) {
    			board[i][j] =rslt[i][j];
    		}
    	}
    	
    	//4사분
    	for(int i=(N+1)/2; i<N; i++) {
    		for(int j=(N+1)/2; j<N; j++) {
    			tempSquare[i-(N+1)/2][j-(N+1)/2] =board[i][j];
    		}
    	}
    	
    	rslt = rotateSub(tempSquare);
    	
    	for(int i=(N+1)/2; i<N; i++) {
    		for(int j=(N+1)/2; j<N; j++) {
    			board[i][j] =rslt[i-(N+1)/2][j-(N+1)/2];
    		}
    	}
    	
    	//3사분면
    	for(int i=(N+1)/2; i<N; i++) {
    		for(int j=0; j<(N-1)/2; j++) {
    			tempSquare[i-(N+1)/2][j] =board[i][j];
    		}
    	}
    	
    	rslt = rotateSub(tempSquare);

    	for(int i=(N+1)/2; i<N; i++) {
    		for(int j=0; j<(N-1)/2; j++) {
    			board[i][j] =rslt[i-(N+1)/2][j];
    		}
    	}
    	
    	
    	//4사분면.
    	for(int i=0; i<(N-1)/2; i++) {
    		for(int j=(N+1)/2; j<N; j++) {
    			tempSquare[i][j-(N+1)/2] =board[i][j];
    		}
    	}
    	
    	rslt = rotateSub(tempSquare);

    	for(int i=0; i<(N-1)/2; i++) {
    		for(int j=(N+1)/2; j<N; j++) {
    			board[i][j] =rslt[i][j-(N+1)/2];
    		}
    	}
    }
   
    public static int[][] rotateSub(int[][] src) {
    	
    	int[][] rslt = new int[src.length][src[0].length];
    	//90도 돌린.
    	for(int i=0; i<src.length; i++) {
    		for(int j=0; j<src[0].length; j++) {
    			 rslt[i][j] = src[(N-1)/2-1-j][i]; //이렇게 반대로 잘못 돌리고 있었다. (반대로)
    		}
    	}
    	return rslt;
    }

    
    public static void initialization() {
    	groupMap = new HashMap<Integer, ArrayList<Node>>();
    	combNum = new ArrayList<int[]>(); 
        refGrp = new ArrayList<Node>();
        compareGrp = new ArrayList<Node>();
    }
}

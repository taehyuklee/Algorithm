import java.util.*;
import java.io.*;

class Node{

    int group;
    int x;
    int y;

    public Node(int x, int y, int group){
        this.x = x;
        this.y = y;
        this.group = group;
    }

    @Override
    public String toString(){
        return "group: " + this.group + " x: " + this.x + " y: " + this.y;
    }
}

public class Main {

    static Map<Integer, ArrayList<Node>> groupMap = new HashMap<Integer, ArrayList<Node>>();
    static int N;
    static int[][] board, board2;

    public static void print(int[][] array){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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

        //group하 시켜오기
        bfs();
        
        System.out.println(groupMap);

    }

    public static void bfs(){
        
        boolean[][] visit = new boolean[N][N];
        Queue<Node> queue = new LinkedList<Node>();
        int[] dx = {-1,1,0,0};
        int[] dy = {0,0,-1,1};

        //group 관리
        int groupNum = 1;
        
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
            	
            	if(visit[i][j] == true) {
            		continue;
            	}
            	
                //group을 새로 만든
                groupMap.put(groupNum, new ArrayList<>());
                

                Node node = new Node(i, j, board[i][j]);
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

                                Node inputNode = new Node(newX, newY, board[newX][newY]);
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



}

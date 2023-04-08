import java.util.*;
import java.io.*;

class Node{

    int x, y, dir, tCnt=0, order=0;
    String trace;

    public Node(int x, int y, int dir, int tCnt, int order){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tCnt = tCnt;
        this.order = order;
    }

    @Override
    public String toString(){
        return "x: " + this.x + " " + "y: " + this.y;
    }

    public int getOrder(){
        return this.order;
    }

}


public class Main
{

    private static int H, W, sCnt=0;
    private static String[][] board;
    private static boolean[][] visit;
    private static int[] dx = {1,-1,0,0}, dy = {0,0,1,-1};
    private static String[] action = {"L", "R", "A"};
    private static ArrayList<Node> candidate = new ArrayList<>();
    

    public static void print(int a){
        System.out.println(a);
    }

    public static void print(String b){
        System.out.println(b);
    }

    public static void print(String[] stringArray){
        for(int i=0; i<stringArray.length; i++){
            System.out.println(stringArray[i]);
        }
    }

    public static void print(String[][] board){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int settingsDir(int dir, String rDir){

        int newDir =0;

        if(dir ==0){
            if(rDir.equals("R")){
                newDir = 3;
            }else{
                newDir = 2;
            }
        }else if(dir ==1){
            if(rDir.equals("R")){
                newDir = 2;
            }else{
                newDir = 3;
            }
        }else if(dir ==2){
            if(rDir.equals("R")){
                newDir = 0;
            }else{
                newDir = 1;
            }
        }else if(dir ==3){
            if(rDir.equals("R")){
                newDir = 1;
            }else{
                newDir = 0;
            }
        }

        return newDir;
    }

    public static void main(String args[])
    {

        Scanner sc = new Scanner(System.in);

        H = sc.nextInt(); 
        W = sc.nextInt();   

        print(H);
        print(W);   
        board = new String[H][W]; //이걸 까먹었다;;
        visit = new boolean[H][W];
        
        //한칸 띄어야 한다.
        String spaceLine = sc.nextLine();

        for(int i=0; i<H; i++){
            String inputLine = sc.nextLine();
            String[] inputArray = inputLine.split("");

            for(int j=0; j<W; j++){
                board[i][j] = inputArray[j];
                if(inputArray[j].equals("#")){
                    sCnt+=1;
                }
            }
        }
        //print(board);
 
        solution();

    }


    public static void solution(){

        //한바퀴 돌면서 #지점은 모두 시작지점이 될수 있다.

        for(int i=0; i<H; i++){
            for(int j=0; j<W; j++){

                //4가지 방향으로 시작한다.
                for(int dir=0; dir<4; dir++){
                    
                    //시작지점을 골랐으면, bfs로 탐색한다.
                    bfs(i,j, dir);

                }  
            }
        }
    }



    public static void bfs(int x, int y, int dir){

        Queue<Node> queue = new LinkedList<>();
        Node node = new Node(x, y, dir, 0, 0);

        queue.add(node);

        while(queue.size()!=0){

            node = queue.poll();

            //조건에 맞는 node들 모아
            if(node.tCnt == sCnt){
                candidate.add(node);
                break;
            }

            for(int act=0; act<3; act++){

                String curAct = action[act];

                if(curAct.equals("A")){

                    int newX = node.x + dx[dir]*2;
                    int newY = node.y + dy[dir]*2;

                    //여기서 조건
                    if(newX>0 && newX<H && newY>0 && newY<W){
                        Node newNode = new Node(newX, newY, node.dir, node.tCnt+2, node.order + 1);
                        newNode.trace += "A";
                        
                        //방문 처리
                        makeVisit(node.x, node.y, dir);

                        //Queue에 넣어라
                        queue.add(newNode);
                    }


                }else if(curAct.equals("R")){
                    //방향 바꿔줘야 한다.
                    int newDir = settingsDir(dir, "R");
                    Node newNode = new Node(node.x, node.y, newDir, node.tCnt, node.order+1);
                    newNode.trace += "R";
                    queue.add(newNode);


                }else if(curAct.equals("L")){
                    int newDir = settingsDir(dir, "L");
                    Node newNode = new Node(node.x, node.y, newDir, node.tCnt, node.order+1);
                    newNode.trace += "L";
                    queue.add(newNode);

                }
            }

        }

    }

    public static void makeVisit(int oldX, int oldY, int dir){
        
        if(dx[dir] !=0 ){
            for(int i=1; i<=2; i++){
                int newX = oldX + dx[dir]*i;
                visit[newX][oldY] = true;
            }
        }

        if(dy[dir] !=0){
            for(int j=1; j<=2; j++){
                int newY = oldY + dy[dir]*j;
                visit[oldX][newY] = true;
            }
        }

    }

}

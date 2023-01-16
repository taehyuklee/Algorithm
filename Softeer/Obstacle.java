import java.util.*;
import java.io.*;

class Coord{
    int x;
    int y;

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "x : " + this.x + "y : " + this.y;
    }

}

public class Main
{

    static int N;
    static int[][] board;
    static boolean[][] visit;
    static List<Integer> answer = new ArrayList<>();

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void print2D(int arr[][]){

        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void print2D(boolean arr[][]){

        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        
        N = sc.nextInt();
        //System.out.println(N);
        sc.nextLine();

        board = new int[N][N];
        visit = new boolean[N][N];

        for(int i=0; i<N; i++){

            String inputLine =sc.nextLine();
            String[] strArr = inputLine.split("");

            for(int j=0; j<N; j++){
                board[i][j] = Integer.parseInt(strArr[j]);
            }
        }

        //print2D(board);

        solution();

    }

    public static void solution(){

        for(int i=0; i<N; i++){

            for(int j=0; j<N; j++){

                if(visit[i][j]!=true && board[i][j] !=0){
                    //System.out.println(i + " " + j );
                    int result = bfs(i,j);
                    answer.add(result);
                    // print2D(visit);
                }

            }
        }
        //print2D(visit);
        //System.out.println(answer);
        
        //print Answer
        System.out.println(answer.size());

        Collections.sort(answer, Comparator.naturalOrder());
        for(int i=0; i<answer.size(); i++){
            System.out.println(answer.get(i));
        }

    }


    public static int bfs(int x, int y){

        Queue<Coord> queue = new LinkedList<>();

        int count =1;

        queue.add(new Coord(x,y));
        visit[x][y] = true;

        while(queue.size()!=0){

            Coord coord = queue.poll();

            for(int i=0; i<4; i++){
                int newX = coord.x + dx[i];
                int newY = coord.y + dy[i];

                if(newX>=0 && newX<N && newY>=0 && newY <N){
                    if(board[newX][newY] !=0 && visit[newX][newY] != true){
                        visit[newX][newY] = true;
                        queue.add(new Coord(newX, newY));
                        count +=1;
                    }
                }

            }

        }

        return count;


    }

}

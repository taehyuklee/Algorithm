import java.util.*;

//완전탐색문제이자 bfs문제

class Coord{
    int x;
    int y; 

    public Coord(int x, int y){
        this.x = y;
        this.x = y;
    }

    @Override
    public String toString(){
        return "x: " + x + ", " + "y: " + y + " | ";
    }
}

class Solution {

    public static boolean[][] visit;

    public static int[] dx = {-1,1,0,0};
    public static int[] dy = {0,0,-1,1};

    public void printVisit(boolean[][] boolArr){
        for(int i=0; i<boolArr.length; i++){
            for(int j=0; j<boolArr[0].length; j++){
                System.out.print(boolArr[i][j] + " ");
            }
            System.out.println();
        }
    }


    public int solution(int n, int[][] water) {
        int answer = 0;

        visit = new boolean[n][n];


        //모든 격자를 start점으로서 한바퀴 돌린다. (아래에 예외처리 해주기)
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){


                //방문했던 곳이면 탐험을 시작하지 않는다. 
                if(visit[i][j]==false){
                    //물웅덩이는 시작점으로 제외해야 한다.
                    for(int w=0; w<water.length; w++){
                        if(i == water[w][0]-1 && j == water[w][1]-1){
                            continue;
                        }else{
                            //이때만 출발지점으로 선택할수가 있다. 
                            System.out.println(i + " " + j + " ");
                            bfs(i,j, water, n);
                            answer +=1;
                            //printVisit(visit);
                        }
                    }
                }

            }
        }

        return answer;
    }


    public void bfs(int x, int y, int[][] waterArray, int n){

        Queue<Coord> queue = new LinkedList<>();

        queue.add(new Coord(x,y));
        visit[x][y] = true;

        while(queue.size()!=0){
            Coord coor = queue.poll();

            for(int i=0; i<4; i++){
                int nx = coor.x + dx[i];
                int ny = coor.y + dy[i];

                if(nx>=0 && nx<n && ny>=0 && ny<n){
                    if(visit[nx][ny] == false){
                        for(int w=0; w<waterArray.length; w++){
                            if(nx == waterArray[w][0]-1 && ny == waterArray[w][1]-1){
                                continue;
                            }else{
                                visit[nx][ny] = true;
                                queue.add(new Coord(nx,ny));
                            }
                        }
                    }                
                }
            }
        }

    }
}

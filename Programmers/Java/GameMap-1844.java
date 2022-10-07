import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

class Runner{
    int x, y, distance;
    
    public Runner(int x, int y, int distance){
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
    
    public int getDistance(){
        return this.distance;
    }
}


class Solution {
    
    static int targetX;
    static int targetY;
    static int answer = 0;
    static Queue<Runner> queue = new LinkedList<>();
    static List<Runner> answerList = new ArrayList<>();
    
    public int solution(int[][] maps) {
        
        targetX = maps.length;
        targetY = maps[0].length;
        
        queue.add(new Runner(0,0,1));
        
        int anw = bfs(maps);
        
        return anw;
    }
    
    public int bfs(int[][] maps){
        
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        
        boolean[][] visitTable = new boolean[maps.length][maps[0].length];
        fillFase(visitTable);

        while(!queue.isEmpty()){
                        
            Runner runner = queue.poll();
            
            visitTable[runner.x][runner.y] = true;
            
            
            if(runner.x == (targetX-1) && runner.y == (targetY-1) ){
                return runner.distance;
            }
            
            for(int i=0; i<4; i++){
                
                int newX = runner.x + dx[i];
                int newY = runner.y + dy[i];
                
                
                if(newX>=0 && newX<maps.length){
                    if(newY>=0 && newY<maps[0].length){
                        if(visitTable[newX][newY] == false && maps[newX][newY]==1){
                            visitTable[newX][newY] = true;
                            int newCount = runner.distance+1;
                            //System.out.println(newX +" " + newY + " " + newCount);

                            queue.add(new Runner(newX, newY, newCount));
                        }
                    }
                }
                
            }
            
            
        }
        
        Collections.sort(answerList, Comparator.comparing(Runner::getDistance));
        
        if(answerList.size() ==0){
            return -1;
        }else{
            Runner ansRunner = answerList.get(0);
            System.out.println(ansRunner);
            return ansRunner.distance;   
        }
    }
    
    public void fillFase(boolean[][] visit){
        
        for(int i=0; i<visit.length; i++){
            for(int j=0; j<visit[0].length; j++){
                visit[i][j] = false;
            }
        }
        
    }
    
    
}

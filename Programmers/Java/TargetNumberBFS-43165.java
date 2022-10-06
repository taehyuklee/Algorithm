import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.util.Map.*;

class State{
    
    int turn, score;
    
    public State(int turn, int score){
        this.turn = turn;
        this.score =score;
    }

}
    
class Solution {
    
    static int staticTarget;
    static int staticLength;
    static int[] staticNumbers;
    static int answer = 0;
    static Queue<State> queue = new LinkedList<>();
    
    public int solution(int[] numbers, int target) {
        
        //Input
        staticTarget = target;
        staticLength = numbers.length;
        staticNumbers = new int[numbers.length];
        staticNumbers = numbers;

        queue.add(new State(0,0));
        
        bfs();
    
        return answer;
    }
    
    public void bfs(){
        
        while(!queue.isEmpty()){
        
            State state = queue.poll();
            
            
            
            int number = staticNumbers[state.turn];

            for(int i=0; i<2; i++){
                if(i==0) number = number;
                if(i==1) number = number*(-1);

                int newScore = state.score + number;
                int newTurn = state.turn+1; 

                if(newTurn <=staticLength){
                    
                    //System.out.println(state.turn + " "+ (staticLength-1));
                    
                    if(state.turn == staticLength-1){
                        // System.out.println("/" + newScore);

                        if(staticTarget == newScore)
                            answer+=1;
                        }
                }
                   
                if(newTurn<staticLength){
                    State newState = new State(newTurn, newScore);
                    queue.add(newState);
                }
                

            }

        }
        
    }
}

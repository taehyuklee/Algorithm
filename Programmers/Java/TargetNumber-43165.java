import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.util.Map.*;

class Solution {
    
    static int staticTarget;
    static int staticLength;
    static int[] staticNumbers;
    static int answer = 0;
    
    public int solution(int[] numbers, int target) {
        
        //Input
        staticTarget = target;
        staticLength = numbers.length;
        staticNumbers = new int[numbers.length];
        staticNumbers = numbers;
        
        // System.out.println(staticTarget);
        // System.out.println(staticLength);
        // System.out.println(staticNumbers);
        // for(int i=0; i<staticNumbers.length; i++){
        //     System.out.print(staticNumbers[i]);
        // }
        
        
        int turn=0; int score=0;
        dfs(turn, score);
    
        return answer;
    }
    
    public void dfs(int turn, int score){
        
        if(turn == staticLength){
            return;
        }
        
        int number = staticNumbers[turn];
        
        for(int i=0; i<2; i++){
            if(i==0) number = number;
            if(i==1) number = number*(-1);
            
            int newScore = score + number;
            int newTurn = turn+1;
            
            if(newScore == staticTarget){
                if(turn ==staticLength-1)
                    answer +=1;
            }
            
            dfs(newTurn, newScore);
        }
        
    }
}

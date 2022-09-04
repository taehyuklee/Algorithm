import java.util.*;
import java.io.*;
import java.math.*;

class Solution {
    
    public void printStringArray(String[] Array){
        
        for(int i=0; i<Array.length; i++){
            System.out.print(Array[i] + " ");
        }
        System.out.println("\n");
        
    }

    
    
    public String solution(String[] participant, String[] completion) {
        
        String answer = "";
        
        // printStringArray(participant);
        // printStringArray(completion);
        
        //완전탐색 처럼 풀수도 있고 O(N^2)
        
        
        //O(N) Sorted해서 풀수도 있다.
        Arrays.sort(participant);
        Arrays.sort(completion);
        
        for(int i=0; i<participant.length; i++){
            
            //completion이 더 짧으니까 이에 대한 예외처리 해줘야한다
            //java.lang.ArrayIndexOutOfBoundsException 이거 뜸
            if(i == participant.length-1){
                answer = participant[participant.length-1];
                break;
            }
            
            if(!participant[i].equals(completion[i])){
                answer = participant[i];
                break;
            }
        }
        
//         printStringArray(participant);
//         printStringArray(completion);
//         System.out.println(answer);
        
        
        return answer;
    }
}

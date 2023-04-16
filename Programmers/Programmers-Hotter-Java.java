import java.util.*;
import java.io.*;

class Solution {
    
    public void print(int[] arr){
        
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
    }
    
    public int solution(int[] scoville, int K) {
        int answer = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for(int i=0; i<scoville.length; i++){
            pq.offer(scoville[i]);
        }
        
        while(!pq.isEmpty()){
            int element = pq.poll();
            if(element< K){
                
                if(pq.isEmpty()){
                    return -1;
                }
                
                int element2 = pq.poll();
                int newScovil = element + (element2*2);
                pq.offer(newScovil);
                answer+=1;
            }
        }
        
        
        return answer;
    }
}

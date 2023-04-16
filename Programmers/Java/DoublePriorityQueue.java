import java.util.*;
import java.io.*;

class Solution {
    
    public static PriorityQueue<Integer> pqRev = new PriorityQueue<>(Comparator.reverseOrder());
    public static PriorityQueue<Integer> pqNat = new PriorityQueue<>(Comparator.naturalOrder());
    
    public void print(String[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    
    public int[] solution(String[] operations) {
        int[] answer = {};
        
        //print(operations);//operations.length
        for(int i=0; i<operations.length; i++){
            String[] parsed = operations[i].split(" ");
            String order = parsed[0];
            
            String numString = parsed[1];
            //추가 또는 삭제
            int num = Integer.parseInt(numString);            
            
            // System.out.print(order +" "+ numString);
            // System.out.println();

            
            if(order.equals("I")){    
                
                pqNat.offer(num);
                pqRev.offer(num);
                
            }else if(order.equals("D")){
                
                if(num>0){             
                    if(pqRev.peek() != null){
                        int target = pqRev.poll();
                        pqNat.remove(target);
                    }
                    
                }else if(num<0){
                    if(pqNat.peek() != null){
                        int target = pqNat.poll();
                        pqRev.remove(target);  
                    }
                }
                
            }           
            
        }
        
        if(pqNat.isEmpty() && pqRev.isEmpty()){
            return new int[] {0,0};
        }else{
            int max = pqRev.poll();
            int min = pqNat.poll();
            return new int[] {max, min};
        }

    }
    
    
    
}

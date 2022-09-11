import java.util.*;
import java.util.stream.*;
import java.util.Map.*;

class Print{
    
    private Integer priority;
    private Integer location;
    
    public Print(Integer priority, Integer location){
        this.priority = priority;
        this.location = location;
    }
    
    public Integer getPriority(){
        return this.priority;
    }
    
    public Integer getLocation(){
        return this.location;
    }
    
}


class Solution {
    
    public int solution(int[] priorities, int location) {
        int answer = 1;
        
        //System.out.println(Arrays.toString(priorities));
        
        List<Print> priList = new ArrayList<Print>();
        
        for(int i=0; i<priorities.length; i++){
            Print print = new Print(priorities[i], i);
            priList.add(print);
        }
        
        boolean go = true;
        
        while(!priList.isEmpty()){
            
            Integer ref = priList.get(0).getPriority();
            go = true;
            
            for(int i=1; i<priList.size(); i++){
                if(ref < priList.get(i).getPriority()){
                    Print popedInt = priList.remove(0); //첫번째꺼를 빼준다
                    priList.add(popedInt); //마지막에 넣어준다
                    go = false;
                    break;
                }
            }
            
            //만약 맨 앞이라면
            if(go == true){
                if(priList.get(0).getLocation() != location){
                    priList.remove(0); //제거 
                    answer +=1;
                }else{
                    break;
                }
            }
            
        }
        
        System.out.println(answer);
        
        return answer;
    }
}

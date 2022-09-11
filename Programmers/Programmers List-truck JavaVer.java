import java.util.*;
import java.util.Map;
import java.util.stream.*;

class Solution {
    
    public Integer sumTruck(List<Integer> bridge){
        Integer sum = 0;
        for(int i=0; i<bridge.size(); i++){
            sum += bridge.get(i);
        }
        return sum;
    }
    
    public void fillArrayList(List<Integer> bridge, int bridge_length){
        for(int i=0; i<bridge_length; i++){
            bridge.add(0);
        }
    }
    
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
        
        List<Integer> truckList = Arrays.stream(truck_weights).boxed().collect(Collectors.toList());
        // List<Integer> bridge = new ArrayList<Integer>(Collections.nCopies(bridge_length, 0));
        List<Integer> bridge = new ArrayList<Integer>();
        fillArrayList(bridge, bridge_length);

         int time = 0;
         while(bridge.size() !=0){
            
            //새로운 트럭 추가
            bridge.remove(bridge.size()-1);
            
            //대기 트럭 무게좀 측정해보자
            if(truckList.size() !=0){
                Integer truckW = truckList.get(0);
                if(truckList.size() !=0 && sumTruck(bridge)+truckW <=weight){                          
                    Integer truck = truckList.remove(0);
                    //System.out.println(truck);
                    bridge.add(0, truck);
                }else{
                    bridge.add(0, 0);
                }
            }

            // if(time ==110){
            //     break;
            // }
            
            time +=1;
         }
             
        answer = time;
        // System.out.println(truckList);
        // System.out.println(bridge);
        
        return answer;
    }
}

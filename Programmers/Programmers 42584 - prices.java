import java.util.*;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = {};
        boolean go = false;
        //int time = 0;
        
        List<Integer> answerList = new ArrayList<Integer>();
        
        for(int i=0; i<prices.length; i++){
            int time = 0;
            go = true;
            for(int j=i+1; j<prices.length; j++){
                time +=1;
                if(prices[i]>prices[j]){
                    answerList.add(time);
                    go = false;
                    break;
                }
            }
            
            //끝까지 갔는데 없다?를 알기 위해서는 아래와 같이 boolean으로 판단하는 것도 하나의 방법
            if(go == true){
                answerList.add(time);
            }
        }
        //System.out.println(answerList);
        answer = answerList.stream().mapToInt(i->i).toArray();
        
        return answer;
    }
}

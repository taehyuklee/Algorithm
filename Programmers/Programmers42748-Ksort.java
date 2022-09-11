import java.util.*;
import java.util.stream.*;
import java.util.Map.*;
import java.util.Collections;

class Solution {
    
    public List<Integer> getSubList(int[] array, int st, int end){
        List<Integer> returnList = new ArrayList<Integer>();
        for(int i=0; i< (end-st)+1; i++){
            returnList.add(array[st+i-1]); //index와 문제의 number번째를 좀 제대로 구분
        }
        
        return returnList;
    }
    
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = {};
        
        List<Integer> arrayList = Arrays.stream(array).boxed().collect(Collectors.toList());
        
        List<Integer> answerList = new ArrayList<Integer>();
        
        for(int[] command : commands){
            //System.out.println(Arrays.toString(command));
            int i = command[0]; int j = command[1]; int k = command[2];

            List<Integer> sub = getSubList(array, i, j);
            Collections.sort(sub, Comparator.naturalOrder());
            //System.out.println(sub);
            answerList.add(sub.get(k-1));
        }
        //System.out.println(answerList);
        answer = answerList.stream().mapToInt(i->i).toArray();
        return answer;
    }
}

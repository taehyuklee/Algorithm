//같은 숫자는 싫어요 - rough한 풀이

import java.util.*;
import java.util.stream.*; //이거 기억해두자 stream
import java.util.Map.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};
        
        List<Integer> intList = Arrays.stream(arr).boxed().collect(Collectors.toList());
        
        List<Integer> answerList = new ArrayList<Integer>();
        
        //for문 돌면서 처음 인덱스 값을 저장해두자
        int temp = 0;
        answerList.add(intList.get(0)); //처음꺼는 넣고 시작
        
        for(int i=1; i<intList.size(); i++){
            temp = intList.get(i-1);
            if(temp ==intList.get(i)){
                continue;
            }else if(temp != intList.get(i)){
                temp = intList.get(i);
                answerList.add(temp);
            }
        }
        System.out.println(answerList);
        
        //변환
        answer = answerList.stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }
}

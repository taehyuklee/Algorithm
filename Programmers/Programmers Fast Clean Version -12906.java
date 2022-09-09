import java.util.*;
import java.util.stream.*; //이거 기억해두자 stream
import java.util.Map.*;

public class Solution {
    public int[] solution(int []arr) {
        int[] answer = {};
        
        // List<Integer> intList = Arrays.stream(arr).boxed().collect(Collectors.toList()); (제거하거나 동적으로 늘어나는게 아니라 그냥 조회해서 answerList에 넣는거면 이거 굳이 동적 배열로 넣을 필요가 없다.)
        
        List<Integer> answerList = new ArrayList<Integer>();
        
        //for문 돌면서 처음 인덱스 값을 저장해두자
        int temp = 0;
        answerList.add(arr[0]); //처음꺼는 넣고 시작
        
        for(int i=1; i<arr.length; i++){
            if(arr[i-1]!= arr[i]){
                answerList.add(arr[i]);
            }
        }
        
        //변환
        answer = answerList.stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }
}

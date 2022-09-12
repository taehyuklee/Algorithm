import java.util.*;
import java.lang.Math.*;

//System.out.println();
class Solution {
    
    public List<List<Integer>> convert(int[][] arr){
        
        List<List<Integer>> arrList = new ArrayList<List<Integer>>();
        
        for(int i=0; i<arr.length; i++){
            List<Integer> list = new ArrayList<>();
            for(int j=0; j<arr[0].length; j++){
                list.add(arr[i][j]);
            }
            arrList.add(list);
        }
        return arrList;
    }
    
    public int solution(int[][] sizes) {
        int answer = 0;
        int[] temp = {};
        
        //System.out.println(Arrays.deepToString(sizes));
        List<List<Integer>> arrList = convert(sizes);
        List<Integer> maxList = new ArrayList<Integer>();
        List<Integer> minList = new ArrayList<Integer>();
        //System.out.println(arrList);
        
        for(int i=0; i<arrList.size(); i++){
            List<Integer> element = arrList.get(i);
            //System.out.print(Collections.max(element) +" ");
            maxList.add(Collections.max(element));
            //System.out.print(Collections.min(element) +" ");
            minList.add(Collections.min(element));
            //System.out.println();
        }
        
        answer = Collections.max(maxList)*Collections.max(minList);
        //answer = answerClass;
        
        return answer;
    }
}

//2D ArrayList로 변환해서 풀기 (외에 더 간단하게 풀수 있다)

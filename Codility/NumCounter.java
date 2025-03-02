// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");
import java.util.Arrays;
import java.util.Collections;

class Solution {

    public void printArray(int[] A){
        for(int i=0; i<A.length; i++){
            System.out.print(A[i] + " ");
        }
    }

    public int[] solution(int N, int[] A){
        // printArray(A);
        int[] answerArr = new int[N];

        for(int i=0; i<A.length; i++){
            if(A[i]<N+1){
                int count_num = A[i]-1;
                answerArr[count_num]++;
            }else{
                Arrays.sort(answerArr);
                int max_value = answerArr[N-1];
                for(int j=0; j<answerArr.length; j++){
                    answerArr[j] = max_value;
                }
            }
        }

        return answerArr;
    }
}

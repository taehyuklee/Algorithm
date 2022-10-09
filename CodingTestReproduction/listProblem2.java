import java.util.*;

class Solution {

    public void print1D(int[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }

    public boolean checkAll(int[] arr){
        int count=0;

        for(int i=0; i<arr.length; i++){
            if(arr[i] == 1){
                count++;
            }
        }
        boolean go = false;
        if(count == arr.length){
            go = true;
        }

        return go;

    }
    public int solution(int[] user_times, int T) {
        int answer = -1;

        int[] restTime = new int[user_times.length];

        for(int i=0; i<user_times.length; i++){
            restTime[i] = (((int) user_times[i]/T)+1)*T - user_times[i];
        }

        int count=0;
        for(int i=0; i<user_times.length; i++){
            if(user_times[i]%T ==0){
                count++;
            }
        }

        if(count == user_times.length){
            return 0;
        }

        Arrays.sort(restTime);

        answer = restTime[restTime.length-1];


        return answer;
    }
}

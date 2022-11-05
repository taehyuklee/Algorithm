class Solution {
    public long solution(long n) {
        long answer = 0;
        long nat = 0;

        for(int i=0; i<n; i++){
            nat = n*i+i;
            answer += nat;
        }

        return answer;
    }
}

import java.util.*;

class Solution {

    static List<Integer> moneyList = new ArrayList<>();
    static List<Integer> answerList = new ArrayList<>();

    public int solution(int[][] money) {
        int answer = -1;

        //Input 작업
        for(int i=0; i<money.length; i++){
            for(int j=0; j<money[i][1]; j++){

                moneyList.add(money[i][0]);

            }
        }

        //System.out.println(moneyList);

        int turn=0;
        int depth=0;
        int score1=0;
        int score2=0;

        dfs(score1, score2, turn, depth);

        //System.out.println(answerList);
        Collections.sort(answerList);
        // System.out.println(answerList);

        answer = answerList.get(0);
        return answer;
    }

    public void dfs(int score1, int score2, int turn, int depth){

        if(depth == moneyList.size()){
            return;
        }

        int currMoney = moneyList.get(turn);

        //System.out.println(currMoney);

        for(int i=0; i<2; i++){
            int first =0;
            int second = 0;

            if(i==0){
                first = currMoney;
            }
            if(i==1){
                second = currMoney;
            }

            int newScore1 = score1 + first;
            int newScore2 = score2 + second;
            int newTurn = turn+1;
            int newDepth = depth+1;

            if(depth == moneyList.size()-1){
                answerList.add(Math.abs(newScore1-newScore2));
            }
            // if(depth ==7){
            //     System.out.println(newScore1 + " " + newScore2);
            //     return;
            // }

            dfs(newScore1, newScore2, newTurn, newDepth);

        }


    }
}

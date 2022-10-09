import java.util.*;

class Node{
    int score;
    List<Integer> path;

    public Node(int score, List<Integer> path){
        this.score = score;
        this.path = path;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public String toString(){
        return "score: " + score + " " + "path: " + path;
    }
}


class Solution {

    static int[][] sReceive;
    static int[][] sSell;
    static List<Node> answerCand = new ArrayList<>();

    public void print2D(int[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[] solution(int[][] receive, int[][] sell) {
        int[] answer = {};

        if(receive.length ==1){
            int [] answer1 = {0,0,0,0,0};
            return answer1;
        }

        sReceive = receive;
        sSell = sell;

        int score =0;
        int day = 0;
        List<Integer> path = new ArrayList<>();
        int [][] rest = new int[sReceive.length][1]; //재고 table

        dfs(score, day, path, rest);

        Collections.sort(answerCand, Comparator.comparing(Node::getScore).reversed());
        //System.out.println(answerCand);

        answer = answerCand.get(0).path.stream().mapToInt(i->i).toArray();

        return answer;
    }

    public void dfs(int score, int day, List<Integer> path, int[][] rest){

        //매장 i<- index
        //그 날의 재고 구해주자
        int[][] newRest = getRest(day, rest);
        //print2D(newRest);

        for(int i=0; i<sReceive.length; i++){

            int[][] newRR = copy(newRest);

            //dfs
            int newDay = day+1;
            int newScore = score + newRR[i][0];

            newRR[i][0] = 0;
            List<Integer> newPath = new ArrayList<>(path);
            newPath.add(i);

            // System.out.println(newPath);
            // System.out.println(newScore);
            //System.out.println();

            if(newDay ==5){
                answerCand.add(new Node(newScore, newPath));
                return;
            }

            dfs(newScore, newDay, newPath, newRR);

        }

    }

    public int[][] getRest(int day, int[][] rest){
        for(int store=0; store<sReceive.length; store++){
            if(sReceive[store][day] - sSell[store][day]>0){
                rest[store][0] += sReceive[store][day] - sSell[store][day];
            }else{
                rest[store][0] +=0;
            }
        }

        return rest;

    }

    public int[][] copy(int[][] source){
        int[][] target = new int[source.length][1];
        for(int i=0; i<target.length; i++){
                target[i][0] = source[i][0];
        }
        return target;
    }
}

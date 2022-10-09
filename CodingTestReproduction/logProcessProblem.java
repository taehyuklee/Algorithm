import java.util.*;

class Solution {

    public String[][] makeTable(String[] logs){

        String[][] newTable = new String[logs.length][3];
        String string = "";
        int count = 0;
        for(int i=0; i<logs.length; i++){
            String element = logs[i];
            count=0;
            for(int j=0; j<element.length();j++){
                char c = element.charAt(j);
                if(c == ' '){
                    newTable[i][count] = string;
                    string = "";
                    count++;
                    continue;
                }
                string +=c;
            }

            //마지막 부분
            newTable[i][count] = string;
            string = "";
        }

        return newTable;

    }

    public void print2D(String[][] table){
        for(int i=0; i<table.length; i++){
            for(int j=0; j<table[0].length; j++){
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }


    public String[] solution(String[] logs, String[] events) {

        String[] process = events;
        Set<String> blackList = new HashSet<>();

        String[][] table = makeTable(logs);
        //print2D(table);

        boolean skip = false;
        int p = 0;
        String oldUser = table[0][1];
        for(int i=0; i<table.length; i++){

            String newUser = table[i][1];

            if(skip){
                if(!newUser.equals(oldUser)){
                    skip = false;
                    i--;
                }
                continue;
            }

            if(!process[p%events.length].equals(table[i][2])){
                blackList.add(oldUser);
                skip = true;
            }

            p++;

            if(!newUser.equals(oldUser)){
                skip = false;
                oldUser = newUser;
            }

        }
        List<String> answerList = new ArrayList<>(blackList);
        Collections.sort(answerList);
        //System.out.println(answerList);
        String[] answer = new String[answerList.size()];
        String[] return0 = new String[1];
        if(answerList.size() ==0){
            return0[0] = "-1";
            return return0;
        }else{
            for(int i=0; i<answerList.size(); i++){
                answer[i] = answerList.get(i);
            }
        }

        return answer;
    }
}

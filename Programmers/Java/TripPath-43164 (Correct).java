import java.util.*;
import java.io.*;

class Ticket{
    
    String start;
    String end;
    
    public String getStart(){
        return this.start;
    }
    
    public String getEnd(){
        return this.end;
    }
    
    public Ticket(String start, String end){
        this.start = start;
        this.end = end;
    }
    
    @Override
    public String toString(){
        return "start: " + start + ", " +  "end " + end + " |";
    }
}

class Solution {
    
    public static List<Ticket> ticketList = new ArrayList<>();
    public static List<String> answerList = new ArrayList<>();
    public static String[] answer ={};
    
    public void print(String str){
        System.out.println(str);
    }
    
    public void print2D(String[][] arr){
        
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        
    }
    
    public String[] solution(String[][] tickets) {
        
        //객체로 관리
        for(int i=0; i<tickets.length; i++){
            ticketList.add(new Ticket(tickets[i][0], tickets[i][1]));
        }
        
        Collections.sort(ticketList, Comparator.comparing(Ticket::getEnd));
        System.out.println(ticketList);
        
        int depth =0;
        boolean[] visit = new boolean[ticketList.size()];
        
        dfs(depth, "ICN", ticketList, visit);
        
        for(int i=0; i<answer.length; i++){
            System.out.print(answer[i] + " ");
        }

        
        return answer;
    }
    
    public void dfs(int depth, String start, List<Ticket> ticketList, boolean[] visit){
        
        //여기서 한 번 정답이 나왔을때를 막아줘야 한다
        if(answer.length>0){
            return;
        }
        
        //return 조건 티켓을 모두 소진했을때
        if(depth == ticketList.size()){
            answerList.add(start);
            answer = new String[answerList.size()];
            for(int i=0; i<answer.length; i++){
                answer[i] = answerList.get(i);
            }
            //print("들어옴");

            return;
        }
        
        
        //dfs 알고리즘
        for(int i=0; i<ticketList.size(); i++){
            if(!visit[i] && ticketList.get(i).start.equals(start)){
                visit[i] = true;
                depth = depth+1;
                answerList.add(start);  
                for(int k=0; k<visit.length; k++){
                    System.out.print(visit[k] + " ");
                }
                System.out.println();
                dfs(depth, ticketList.get(i).end, ticketList, visit);
                visit[i] = false;
                depth = depth-1;
                answerList.remove(answerList.size()-1);

            }
        }
        
        
    }
}

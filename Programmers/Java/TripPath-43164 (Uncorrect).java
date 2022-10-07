import java.util.*;
import java.util.Map;
import java.util.stream.*;

class Ticket{
    String start;
    String end;
    boolean visit;
    
    public Ticket(String start, String end, boolean visit){
        this.start = start;
        this.end = end;
        this.visit = visit;
    }
    
    public String getEnd(){
        return end;
    }
    
    @Override
    public String toString(){
        return "start:" +" "+ this.start  + "  "+ "end:" + " "+ this.end + "  " + "visit:" + " "+ this.visit;
    }
}

class Solution {
    
    static List<Ticket> ticketList = new ArrayList<>();
    static List<String> answerList = new ArrayList<>();
    
    
    public Ticket findByStart(String start){
        
        List<Ticket> candList = new ArrayList<>();
        
        for(int i=0; i<ticketList.size(); i++){
            Ticket candTicket = ticketList.get(i);
            if(candTicket.start.equals(start) && candTicket.visit==false){
                candList.add(candTicket);
            }
        }
        
        Collections.sort(candList, Comparator.comparing(Ticket::getEnd));
        
        if(candList.size()!=0){
            Ticket ticket = candList.get(0);
            
            return ticket;
        }else{
            return null; 
            
        }
    }
    
    public boolean ticketChecks(){
        
        boolean stop = false;
        int count=0;
        for(int i=0; i<ticketList.size(); i++){
            Ticket ticket = ticketList.get(i);
            if(ticket.visit == false){
                count++;
            }
        }
        
        if(count ==0){
            stop = true;
        }
        
        return stop;
    }
    
    public String[] solution(String[][] tickets) {
        String[] answer = {};
        
        for(int i=0; i<tickets.length; i++){
            ticketList.add(new Ticket(tickets[i][0], tickets[i][1], false));     
        }
        
        //System.out.println(ticketList);
        
        //initial condition
        //findByStartPoint
        Ticket firstTrip = findByStart("ICN");
        answerList.add("ICN"); // 처음꺼는 매뉴얼하게 넣어준다

        //첫 여행지를 받아온다
        String dest = firstTrip.end;
        answerList.add(dest); // 처음꺼는 매뉴얼하게 넣어준다
        
        //이 티켓은 사용처리한다
        firstTrip.visit = true;
        
        int size=0;
        while(true){
            
            //전에 목적지가 시작점으로 바뀐다.
            String start = dest;
            //System.out.println(start);
            
            //출발지로 티켓을 찾는다
            Ticket ticket = findByStart(start);
            
            //다음 여행지를 받아온다
            dest = ticket.end;
            
            //답 list에 넣어준다
            answerList.add(dest);
            
            //이 티켓은 사용처리한다
            ticket.visit = true;
            
            //System.out.println(dest);
            
            boolean stop = ticketChecks();
            if(stop){
                break;
            }
            
            // if(size==1){
            //     break;
            // }
            //size++;
            
        }

        //System.out.println(answerList);
        answer = convertString(answerList);
        
        return answer;
    }
    
    public String[] convertString(List<String> stringList){
        String[] answer = new String[stringList.size()];
        for(int i=0; i<stringList.size(); i++){
            String string = stringList.get(i);
            answer[i] = string;
        }
        
        return answer;
        
    }
}

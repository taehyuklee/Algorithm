import java.util.*;
import java.io.*;

class Time{
    int stTime;
    int endTime;
    boolean available = true;

    public Time(int stTime, int endTime){
        this.stTime = stTime;
        this.endTime = endTime;
    }

    @Override
    public String toString(){
        return "stTime: " + stTime + "  " + "endTime: " + endTime +" " + "available: " + available + "||";
    }
}

class Room{
    String roomName;
    ArrayList<Time> timeList = new ArrayList<>();

    public Room(String roomName){
        this.roomName = roomName;
    }

    @Override
    public String toString(){
        return "roomNmae: " + roomName;
    }

    public String getRoomName(){
        return roomName;
    }
}

public class Main
{
    private static int N, M;
    private static Map<String, Room> roomMap = new HashMap<>();
    private static String[] rNameList;

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        //회의실, 시작 시각(시 단위), 종료 시각(시 단위) 정보 포함
        N = sc.nextInt(); //회의실 수
        M = sc.nextInt(); //예약된 회의 수
        rNameList = new String[N];

        String space = sc.nextLine();

        for(int i=0; i<N; i++){
            String roomName = sc.nextLine();
            Room room = new Room(roomName);
            rNameList[i] = roomName;

            for(int j=0; j<9; j++){
                Time time = new Time(9+j, 10+j);
                room.timeList.add(time);
            }

            roomMap.put(roomName, room);
        }

        //회의실 예약한 부분들 
        for(int i=0; i<M; i++){
            String input = sc.nextLine();
            String[] parsedInput = input.split(" ");

            String rName = parsedInput[0];
            int stTime = Integer.parseInt(parsedInput[1]);
            int endTime = Integer.parseInt(parsedInput[2]);

            //System.out.println(rName + " " + stTime + "  " + endTime);

            Room targetRoom = roomMap.get(rName);

            //특정 방의 시간대를 조회하고 available이 true -> false로 false이면 그냥 skip한다.
            findTime(targetRoom, stTime, endTime);

        }

        //회의실 출력하는 부분:
        ArrayList<Room> resultRoom = new ArrayList<Room>();
        for(int i=0; i<N; i++){
            resultRoom.add(roomMap.get(rNameList[i]));    
        }

        //오름차순으로 정렬 roomName으로
        Collections.sort(resultRoom, Comparator.comparing(Room::getRoomName)); 


        for(int i=0; i<resultRoom.size(); i++){
            Room room = resultRoom.get(i);
            //Room 이름 출력하고,
            System.out.println("Room " + room.roomName + ":");

            //timeList보고 출력하기
            ArrayList<Time> timeList = room.timeList;
            ArrayList<String> availTimeSt = new ArrayList<>();

            //가능한 시간대가 있는지 확인.
            ArrayList<Time> availList = checkNoAvail(timeList);

            //시간대 출력하기
            if(availList.size() == 0){
                //availTime시간대가 없을때
                System.out.println("Not available");
                if(i != resultRoom.size()-1){
                    System.out.println("-----");
                }

            }else{
                //availTime시간대가 있을때
                int availCnt = 0;

                int oldStTime = availList.get(0).stTime;
                int oldEndTime = availList.get(0).endTime;
          
               	if(availList.size() == 1) {
                    //하나만 있을때
            		availCnt +=1;

                    //가능한 시간대에 대해서 적어주고 
                    writeAndSave(oldStTime, oldEndTime, availTimeSt);

                    //출력한다.
                    printResult(availCnt, availTimeSt, resultRoom, i);
                    
                    
            	}else {
                    //available time 여러개 존재할때 (차례대로 시간을 확인한다. 이어붙여졌는지)
	                for(int t=1; t<availList.size(); t++){	
	                	
	                    Time availTime = availList.get(t);
            
	                    if(oldEndTime == availTime.stTime){ //시간이 연속일때

                            //마지막일때
	                        if(t == availList.size()-1){
	                            availCnt+=1;

                                //가능한 시간대에 대해서 적어주고 
                                writeAndSave(oldStTime, availTime.endTime, availTimeSt);

	                            oldStTime = availList.get(t).stTime;
	                            oldEndTime = availList.get(t).endTime;
	                                
	                            continue;
	                        }
                            //stTime은 안해줘도 된다. (이어 붙여지니까)
                            oldEndTime = availTime.endTime;
	                    }
	                    else{//시간이 연속이 아닐때
	                        availCnt+=1;
                            
                            //가능한 시간대에 대해서 적어주고 
                            writeAndSave(oldStTime, oldEndTime, availTimeSt);

	                        oldStTime = availList.get(t).stTime;
	                        oldEndTime = availList.get(t).endTime;

                            //마지막일때 조건은 처리해줘야 한다. (여기가 마지막으로 다음 사이클이 없으니까 여기서 update쳐줘야함)
                            if(t == availList.size()-1){
                                availCnt+=1; //마지막 처리할때 1더해줘야 한다.
                                writeAndSave(oldStTime, oldEndTime, availTimeSt);
                            }
	                        continue;

	                    }

	                    //마지막에 전환되는게 없으니까 마무리될때 +1해서 마무리 wrapping해준다
	
	                }

                    printResult(availCnt, availTimeSt, resultRoom, i);
	
	            }
            }


        }

    }

    public static void printResult(int availCnt, ArrayList<String> availTimeSt, ArrayList<Room> resultRoom, int i){
        System.out.println(availCnt + " " + "available:");
        
        for(int f=0; f<availTimeSt.size(); f++){
            System.out.println(availTimeSt.get(f));
        }

        if(i != resultRoom.size()-1){
            System.out.println("-----");
        }
    }

    public static void writeAndSave(int oldStTime, int oldEndTime, ArrayList<String> availTimeSt){
        if(oldStTime<10){
            //10미만일때 0을 붙여줘야 한다.
            String target = "0"+oldStTime+"-"+oldEndTime;
            availTimeSt.add(target);
        }else{
            String target = oldStTime+"-"+oldEndTime;
            availTimeSt.add(target);
        }
    }

    public static ArrayList<Time> checkNoAvail(ArrayList<Time> timeList){

        ArrayList<Time> availList = new ArrayList<>();

        int notAvailCnt = 0;
        //예약 가능한 시간이 있는지 확인하기.
        for(int j=0; j<timeList.size(); j++){
            
            Time eachTime = timeList.get(j);

            if(eachTime.available == false){
                notAvailCnt +=1;
            }else{
                availList.add(eachTime);
            }

        }

        return availList;
    }

    public static void findTime(Room targetRoom, int stTime, int endTime){

        ArrayList<Time> tarTimeList = targetRoom.timeList;

        for(int i=0; i<tarTimeList.size(); i++){
            
            if(tarTimeList.get(i).stTime == stTime){
                int count=0;

                //stTime과 endTime사이에 모두가 avail == true일경우만 예약 가능
                for(int j = 0; j<endTime-stTime; j++){

                    if(tarTimeList.get(i+j).available == true){
                        // targetRoom.get(i).available = false;
                        count +=1;
                    }

                }

                //모든 회의실이 비어있다는 것을 알면, 그 회의실을 모두 false로
                if(count == endTime-stTime){
                    for(int j=0; j<endTime-stTime; j++){
                        tarTimeList.get(i+j).available = false;
                    }
                }

                break;

            }

        }
    }
}

import java.util.*;
import java.io.*;
import java.time.*;


public class Main
{

    private static List<String> inputList = new ArrayList<>();
    private static long answer=0;

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        //식시 사간, 근무외 시간 모두 포함
        for(int i=0; i<5; i++){
            String timeLine = sc.nextLine();
            
            String[] timeComb = timeLine.split(" ");

            String timeB = timeComb[0];
            String timeA = timeComb[1];
            
            LocalTime timesB = LocalTime.parse(timeB);
            LocalTime timesA = LocalTime.parse(timeA);

            Duration duration = Duration.between(timesB, timesA);

            // long totalMin = timesA.minusMinutes(timesA);
            long diffMin = duration.toMinutes();
            answer+= diffMin;

        }

        System.out.println(answer);

    }
}

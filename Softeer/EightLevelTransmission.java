import java.util.*;
import java.io.*;


public class Main
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        String inputLine = sc.nextLine();

        String[] parsedInput = inputLine.split(" ");

        int old = Integer.parseInt(parsedInput[0]);
        int newInt =0;
        int ascendCnt=0, descendCnt=0, mixedCnt=0;

        for(int i=1; i<parsedInput.length; i++){

            newInt = Integer.parseInt(parsedInput[i]);

            if(old < newInt){
                ascendCnt+=1;
            }else if(old > newInt){
                descendCnt+=1;
            }
            old = newInt;
        }
        
        if(ascendCnt == parsedInput.length-1){
            System.out.println("ascending");
        }
        else if(descendCnt == parsedInput.length-1){
            System.out.println("descending");
        }else{
            System.out.println("mixed");
        }
        
    }
}

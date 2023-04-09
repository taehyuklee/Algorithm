import java.util.*;
import java.io.*;
import java.math.*;


public class Main
{
    public static void main(String args[])
    {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();    

        int oldSquare = 1;
        int vertexNum = 4;
        int nextSquare = 1;
        int lineNum = 1, oldLineNum =1;

        for(int i=1; i<N+1; i++){

            nextSquare = (int) Math.pow(4,i);
            lineNum = (int) Math.pow(2,i);

            if(oldSquare ==1){
                vertexNum = vertexNum + oldSquare*4 + oldSquare;
            }else{

                int firstSquare = 4; //첫 번째 네모
                int threeNum = (oldLineNum-1)*3*2; //세개 짜리.
                int twoNums = (oldSquare -(oldLineNum-1)*2 - 1)*2; //두개 짜리 (각 줄을 빼고 첫 번째 Square도 뺀다)
                int midNums = oldSquare; // 가운데 포인트들
    
                vertexNum = vertexNum + firstSquare + threeNum + twoNums + midNums;

            }

            oldSquare = nextSquare;
            oldLineNum = lineNum;
        }

        System.out.println(vertexNum);

    }
}

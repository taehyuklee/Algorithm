import java.util.*;
import java.io.*;


public class Main
{
    private static int N, M, curRef;
    private static ArrayList<int[]> referList = new ArrayList<>();
    private static ArrayList<int[]> realList = new ArrayList<>();
    private static ArrayList<Integer> answerList = new ArrayList<>();
    private static int[] referArray;

    public static void print(ArrayList<int[]> array){
        for(int i=0; i<array.size(); i++){
            System.out.print("(" + array.get(i)[0] + " ");
            System.out.print(array.get(i)[1] + ") ");
        }
        System.out.println();
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        
        N = sc.nextInt();
        M = sc.nextInt();

        for(int i=0; i<N; i++){
            int[] newInt = new int[] {sc.nextInt(), sc.nextInt()};
            referList.add(newInt);
        }

        for(int j=0; j<M; j++){
            int[] realInt = new int[] {sc.nextInt(), sc.nextInt()};
            realList.add(realInt);
        }

        solution();

    }

    public static void solution(){

        //첫번재 referArray구간을 설정해준다.
        curRef = 0;
        referArray = referList.get(curRef);

        for(int i=0; i<M; i++){

            int[] realArray = realList.get(i);

            boolean loop = true;

            while(loop){

                //구간이 넘지 않을때,
                if(realArray[0]<=referArray[0]){
                    inRange(realArray);
                }
                //구간이 넘을때,
                else{
                    outRange(realArray);
                }

                if(realArray[0] ==0){
                    loop =false;
                }
            }

        }

        if(answerList.size() != 0){
            Collections.sort(answerList);
            System.out.println(answerList.get(answerList.size()-1));
        }else{
            //이 부분 문제에서 읽었었는데 까먹고 있었다. 확실히 읽어야 할 부분.
            System.out.println(0);
        }
    }

    public static void inRange(int[] realArray){

        //제한 속도를 넘을때 answerList에 넣어준다.
        if(realArray[1] - referArray[1] >0){
            answerList.add(realArray[1] - referArray[1]);
        }

        referArray[0] = referArray[0] - realArray[0]; //reference 남은 구간을 업데이트해준다.

        if(referArray[0] == 0 && curRef != referList.size()-1){ 
            /*마지막부분 =같을때 0이되면서 한 번 더 돌아간다. 그래서 curRef가 ++되면서 오류*/
            //다음 reference구간으로 넘어간다.
            curRef++;
            nextReference(curRef);
        }

        //현재 구간은 다 지났음을 update해준다.
        realArray[0] = 0;
        
    }

    public static void outRange(int[] realArray){

        //제한 속도를 넘을때 answerList에 넣어준다.
        if(realArray[1] - referArray[1] >0){
            answerList.add(realArray[1] - referArray[1]);
        }

        //현재 구간이 기준구간을 넘어서니까 얼마나 남았는지를 확인해준다.
        realArray[0] -= referArray[0];

        //이후 reference 남은 구간을 업데이트해준다.
        referArray[0] = 0; 

        //여긴 바로 다음 refer로 update해준다.
        curRef++;
        nextReference(curRef);
    }

    public static void nextReference(int curRef){
        referArray = referList.get(curRef);
    }
}

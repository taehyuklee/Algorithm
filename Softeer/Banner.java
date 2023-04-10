import java.util.*;
import java.io.*;


public class Main
{
    private static int T;
    private static ArrayList<char[]> inputList = new ArrayList<>();
    private static int count=0;

    public static void print(char[] array){
        for(int k=0; k<array.length; k++){
            System.out.print(array[k] +" ");           
        }
        System.out.println();
    }

    public static void main(String args[])
    {

        Scanner sc = new Scanner(System.in);

        T = sc.nextInt();

        sc.nextLine(); //한칸 띄고
        
        //총 몇 번의 Iteration
        for(int turn=0; turn<T; turn++){

            String inputLine = sc.nextLine();
            String[] inputArray = inputLine.split(" ");

            //자리수 맞추고 List에 넣기
            for(int i=0; i<inputArray.length; i++){

                String inputSt = "eeeee";
                char[] charArray = inputSt.toCharArray();
                String A = inputArray[i];

                for(int j=0; j<A.length(); j++){
                    char a = A.charAt(A.length()-1-j);
                    charArray[charArray.length-1-j] = a;
                }

                // print(charArray);
                inputList.add(charArray);
            }

            solution();
            inputList = new ArrayList<>(); //초기화
            count = 0; //초기화
        }
    }

    public static void solution(){

        //한글자 한글자 꺼내서 비교하기
        char[] A = inputList.get(0);
        char[] B = inputList.get(1);

        //비교 (5글자로 다 맞춰 놨으니까)
        for(int i=0; i<5; i++){
            //무슨 글자인지부터 인지하기 
            ArrayList<Integer> numA = getCharInxList(A[i]);
            ArrayList<Integer> numB = getCharInxList(B[i]);

            //두개를 비교하는 로직을 짜면 된다.
            compareNums(numA, numB);
            
        }
       System.out.println(count);
        
    }

    public static void compareNums(ArrayList<Integer> numA, ArrayList<Integer> numB){

        //없는거 우선 추가
        for(int i=0; i<numB.size(); i++){
            if(!numA.contains(numB.get(i))){
                numA.add(numB.get(i));
                count+=1;
            }
        }
        
        //필요 없는거 제거 (제거하면서 index바꾸는건 위험.)
        ArrayList<Integer> numACopy = new ArrayList<>(numA);
        for(int i=0; i<numA.size(); i++){
            if(!numB.contains(numA.get(i))){
                numACopy.remove(numA.get(i)); //복사한걸 지워준다.
                count+=1;
            }
        }
        //System.out.println(count);
    }

    public static ArrayList<Integer> getCharInxList(char in){

        if(in == 'e'){
            return new ArrayList<Integer>();
        }
        else if(in == '0'){
            return new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5));
        }
        else if(in == '1'){
            return new ArrayList<Integer>(Arrays.asList(4,5));
        }
        else if(in == '2'){
            return new ArrayList<Integer>(Arrays.asList(0,2,3,5,6));
        }
        else if(in == '3'){
            return new ArrayList<Integer>(Arrays.asList(0,3,4,5,6));
        }
        else if(in == '4'){
            return new ArrayList<Integer>(Arrays.asList(1,4,5,6));
        }
        else if(in == '5'){
            return new ArrayList<Integer>(Arrays.asList(0,1,3,4,6));
        }
        else if(in == '6'){
            return new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,6));
        }
        else if(in == '7'){
            return new ArrayList<Integer>(Arrays.asList(0,1,4,5));
        }
        else if(in == '8'){
            return new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));
        }
        else if(in == '9'){
            return new ArrayList<Integer>(Arrays.asList(0,1,3,4,5,6));
        }
        else{
            return null;
        }
    }
}

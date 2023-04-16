import java.util.*;
import java.io.*;


public class Main
{
    public static String input="", output="";
    public static char[][] encKey = new char[5][5];

    public static void print(char[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public static String makeKey(String inputKey){
        String alpah = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String key = "";
        boolean skip = false;

        for(int i=0; i<inputKey.length(); i++){
            if(!key.contains(String.valueOf(inputKey.charAt(i)))){
                key += String.valueOf(inputKey.charAt(i));
            }

            if(key.length() == 25){
                skip = true;
                break;
            }
        }

        if(!skip){
            for(int i=0; i<alpah.length(); i++){
                if(!key.contains(String.valueOf(alpah.charAt(i)))){
                    key += String.valueOf(alpah.charAt(i));
                }

                if(key.length() == 25){
                    break;
                }
            }
        }

        return key;

    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        input = sc.nextLine();
        String inputKey = sc.nextLine();

        String key = makeKey(inputKey); //"PLAYFIRCHEKBDGMNOQSTUVWXZ";

        int keyIdx = 0;
        for(int i=0; i<encKey.length; i++){
            for(int j=0; j<encKey[0].length; j++){
                encKey[i][j] = key.charAt(keyIdx);
                keyIdx++;
            }
        }

        //print(encKey);

        //System.out.println(input);

        solution();
        
        
    }

    public static void solution(){

        //input X, Q등으로 2분할 프로세싱
        String processed = processData();
        //System.out.println(processed);

        //암호화 작업
        encodeProcess(processed);        

        //Answer
        System.out.println(output);

    }

    public static void encodeProcess(String processed){

        //두개씩 세트로 다시 나눠야 한다. 
        List<List<Character>> collecions = getPair(processed);
        //System.out.println(collecions);

        //같은 행에 존재
        for(int i=0; i<collecions.size(); i++){

            List<Character> pairs = collecions.get(i);
            
            int firstCol=0, firstRow=0;
            int secondCol=0, secondRow=0;
            for(int j=0; j<encKey.length; j++){
                for(int k=0; k<encKey.length; k++){
                    if(pairs.get(0) == encKey[j][k]){
                        firstCol = k;
                        firstRow = j;
                    }

                    if(pairs.get(1) == encKey[j][k]){
                        secondCol = k;
                        secondRow = j;
                    }
                }
            }

            if(firstRow == secondRow){
                output += String.valueOf(encKey[firstRow][(firstCol+1)%5]);
                output += String.valueOf(encKey[secondRow][(secondCol+1)%5]);
            }else if(firstCol == secondCol){
                output += String.valueOf(encKey[(firstRow+1)%5][firstCol]);
                output += String.valueOf(encKey[(secondRow+1)%5][secondCol]);

            }else{
                output += String.valueOf(encKey[firstRow][secondCol]);
                output += String.valueOf(encKey[secondRow][firstCol]);
            }


        }
        // System.out.println(output);    

    }

    public static String processData(){

        String processed = "";

        char oldChar = input.charAt(0);
        //processed += String.valueOf(oldChar);
        for(int i=1; i<input.length(); i++){
        
            String newInput = String.valueOf(input.charAt(i));

            if(input.charAt(i) == oldChar){
                if(input.charAt(i) == 'X'){
                    processed += oldChar + "Q";
                    oldChar = input.charAt(i);

                }else{
                    processed += oldChar + "X";
                    oldChar = input.charAt(i);
   
                }

                //이거 처리 안해주면 YY, XX등과 같은 마지막에 같을때 마지막 글자가 안들어가짐
                if(i == input.length()-1){
                    processed += newInput + "X";
                }

            }else{
                processed += oldChar + newInput;
                //System.out.println(oldChar +" " + newInput);
                i+=1;

                if(i<input.length()-1){
                    oldChar = input.charAt(i);
                }else if(i == input.length()-1){ 
                    oldChar = input.charAt(i);
                    processed += String.valueOf(oldChar) + "X";
                }

                //System.out.println(oldChar);
            }
          
        }

        if(input.length()==1){
            processed += oldChar + "X";
        }

        //System.out.println("1: " + processed);
        return processed;
    }

    public static List<List<Character>> getPair(String input){

        List<Character> pair = new ArrayList<>();
        List<List<Character>> collecions = new ArrayList<>();

        for(int l=0; l<input.length(); l++){

            if(l%2 ==0 && l !=0){
                collecions.add(pair);
                pair = new ArrayList<Character>();

            }

            pair.add(input.charAt(l));

            if(l == input.length()-1){//더하는건 마지막에 있어야지
                collecions.add(pair);
            }
        }

        return collecions;
    }
}

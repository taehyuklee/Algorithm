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

        for(int i=0; i<inputKey.length(); i++){
            if(!key.contains(String.valueOf(inputKey.charAt(i)))){
                key += String.valueOf(inputKey.charAt(i));
            }
        }


        for(int i=0; i<alpah.length(); i++){
            if(!inputKey.contains(String.valueOf(alpah.charAt(i)))){
                key += String.valueOf(alpah.charAt(i));
            }

            if(key.length() == 25){
                break;
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

        print(encKey);

        System.out.println(input);

        solution();
        
        
    }

    public static void solution(){
        

        //input X, Q등으로 2분할 프로세싱
        String processed = processData();
        System.out.println(processed);

        //암호화 작업
        encodeProcess(processed);
        //System.out.println(output);

    }

    public static void encodeProcess(String processed){

        //두개씩 세트로 다시 나눠야 한다. 
        List<List<Character>> collecions = getPair(processed);
        System.out.println(collecions);

        //같은 행에 존재
        for(int i=0; i<collecions.size(); i++){

            List<Character> pairs = collecions.get(i);

            boolean rowTrue = false;
            boolean colTrue = false;
            
            //행에 관련된 이야기
            boolean breakPoint = false;
            for(int j=0; j<encKey.length; j++){ //행
                String cand = "";
                for(int l=0; l<2; l++){ //글자 순서가 있다.
                    for(int k=0; k<encKey[0].length; k++){ //열
                        if(encKey[j][k] == pairs.get(l)){
                            cand += String.valueOf(encKey[j][(k+1)%5]);
                        }
                    }
                    if(cand.length() == 2){
                        output += cand;
                        breakPoint = true;
                        break;
                    }
                }
                if(breakPoint){
                    rowTrue = true;
                    break;
                }
            }
            //열에 관련된 이야기 - 같은 행이 아닌데, 같은 열에 존재
            if(rowTrue == false){
                breakPoint = false;
                for(int k=0; k<encKey[0].length; k++){ //열
                    String cand = "";
                    for(int l=0; l<2; l++){
                        for(int j=0; j<encKey.length; j++){ //행
                            if(encKey[j][k] == pairs.get(l)){                                
                                cand += String.valueOf(encKey[(j+1)%5][k]);
                            }
                        }
                        //System.out.println("k: "  + k + " j: " + j + " " + cand);
                        if(cand.length() == 2){
                            output += cand;
                            breakPoint = true;
                            break; //이거때문에 고생함
                        }
                    }
                    if(breakPoint){
                        colTrue = true;
                        break;
                    }
                }
            }

            //모두가 아닐때
            int firstRow = 0 , firstCol =0;
            int secondRow = 0, secondCol =0;
            if(colTrue ==false && rowTrue == false){
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
                output += String.valueOf(encKey[firstRow][secondCol]);
                output += String.valueOf(encKey[secondRow][firstCol]);
            }
 

        }
        System.out.println(output);    

    }

    public static String processData(){

        String processed = "";
        
        char oldChar = input.charAt(0);
        processed += String.valueOf(oldChar);
        for(int i=1; i<input.length(); i++){
        
            String newInput = String.valueOf(input.charAt(i));

            if(input.charAt(i) == oldChar){
                if(input.charAt(i) == 'X'){
                    processed += String.valueOf(oldChar) + "Q" + newInput;
                    oldChar = input.charAt(i);
                    continue;
                }else{
                    processed += String.valueOf(oldChar) + "X" + newInput;
                    oldChar = input.charAt(i);
                    continue;
                }

            }else{
                processed += newInput;
            }

            if(i+1<=input.length()-1){
                oldChar = input.charAt(i+1);
            }
          
        }
            
        // System.out.println(processed);

        //마무리 문자 처리
        if(processed.length()%2 !=0){
            processed += "X";
        }

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

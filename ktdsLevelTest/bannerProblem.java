import java.util.*;

class Solution {

    public void copyBanner(char[] banner, char[] banner1){
        for(int i=0; i<banner.length-1; i++){
            banner1[i] = banner[i+1];
  
        }
    }

    public String solution(int n, String text, int second) {

        String answer = "";
        String newText = "";

        char[] banner = new char[n];
        char[] banner1 = new char[n];

        for(int i=0; i<banner.length; i++){
            banner[i] = '_';
            banner1[i] = '_';
        }

        char blank = '\0';


        //뒤에 빈 공간 두기
        newText = text;

        for(int i=0; i<text.length(); i++){
            newText += blank;
        }

        int textLen = newText.length();

        int sec=0;
        int b = n-1;
        int tind = 0;
        while(true){

            if(sec !=0){

                if(newText.charAt(tind%textLen) == blank){
                    copyBanner(banner, banner1);
                    banner1[b] = '_';

                }else{
                    copyBanner(banner, banner1);
                    banner1[b] = newText.charAt(tind%textLen);

                }

                banner = banner1;
                tind +=1;

                }

                if(sec == second){
                    break;
                }

            sec +=1;

        } 

        //char array를 문자열로 만들기
        for(int i=0; i<banner.length; i++){
            //System.out.print(banner[i]);
            answer += banner[i];

        }

        return answer;

    }

}

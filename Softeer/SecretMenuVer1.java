import java.util.*;
import java.io.*;

//두 가지 방법이 있다 수열로 푸는 방법과 String으로 푸는 방법 
//수열 version (좀 더 일반적)
public class Main
{
    private static int N,M,K;
    private static int[] secret, user;
    private static boolean secretTrue;

    public static void print(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String args[])
    {

        Scanner sc = new Scanner(System.in);

        M = sc.nextInt(); //사용자가 누른 버튼의 수
        N = sc.nextInt(); //비밀 메뉴 조작 버튼 수
        K = sc.nextInt(); //각 정수 1이상 K이하.

        secret = new int[M];
        user = new int[N];

        for(int i=0; i<M; i++){
            secret[i] = sc.nextInt();
        }

        for(int i=0; i<N; i++){
            user[i] = sc.nextInt();
        }
        // print(secret);
        // print(user);
        
        for(int i=0; i<N-1; i++){

            if(secret[0] == user[i]){
                int count=0;

                //secret 첫 번째 숫자를 찾았으면 그 숫자로부터 Secret만큼 일치하는지 확인.
                for(int j=0; j<M; j++){
                    
                    if(i+j>=N){
                        break; //범위를 넘어서면 break;
                    }

                    if(secret[j] != user[i+j]){
                        //다른게 하나라도 있으면 break걸고 다음걸로,
                        break;
                    }
                    count+=1;

                }

                if(count == M){
                    secretTrue = true;
                    break;
                }

            }

        }

        if(secretTrue == true){
            System.out.println("secret");
        }else{
            System.out.println("normal");
        }

    }
}

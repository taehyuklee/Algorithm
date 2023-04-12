import java.util.*;
import java.io.*;


public class Main
{   
    private static int N;
    private static int[][] arr, result;

    public static void print(int[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String args[]) throws Exception
    {   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        arr = new int[4][N]; 
        result = new int[4][N];

        //array에 저장
        for(int i=0; i<3; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                int score = Integer.parseInt(st.nextToken());
                arr[i][j] = score;
            }
        }

        solution();
        
    }

    public static void solution(){

        //total 구하면서 순위를 구해주자 
        for(int j=0; j<N; j++){
            int sum=0;
            for(int i=0; i<4; i++){

                //각 대회마다 순위를 stream처럼 흘러가면서 해주기 
                int rank=1;
                for(int k=0; k<N; k++){
                    //그 특정대회에서 다른 사람들보다 작으면 rank가 1씩 밀린다.
                    if(arr[i][j]<arr[i][k]){
                        rank+=1;
                    }
                }
                result[i][j] = rank;
                
                //여긴 total sum 구하는 부분
                sum += arr[i][j];
                if(i==3){
                    arr[i][j] = sum;
                }
            }

        }

        //total부분은 따로 순위를 매겨준다.
        for(int i=0; i<N; i++){
            int rank=1;
            for(int j=0; j<N; j++){
                if(arr[3][i]<arr[3][j]){
                    rank+=1;
                }
            }
            result[3][i] = rank;
        }
        

        //print(arr);
        print(result);
 
    }

}

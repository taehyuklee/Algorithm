import java.util.*;
import java.io.*;
import java.math.*;


public class Main
{
    private static int N, K;
    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    private static int[] score;
    
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        //System.out.println(N + " " + K);

        score = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            // map.put(i, Integer.parseInt(st.nextToken()));
            score[i] = Integer.parseInt(st.nextToken());
        }
        //System.out.println(map);
        
        for(int i=0; i<K; i++){
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            
            float sum =0.0F;
            for(int j =A-1; j<B; j++){
                sum += (float) score[j] / (float) (B-A+1);
            }

            //System.out.println(Math.round(sum*100)/100.0);
            System.out.println(String.format("%.2f", sum));
        }

    }
}

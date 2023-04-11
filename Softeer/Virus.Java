import java.util.*;
import java.io.*;
import java.math.*;


public class Main
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        int K = sc.nextInt();
        int P = sc.nextInt();
        int N = sc.nextInt();
        boolean over = false;

        //double virus = K*Math.pow(P,N);
        //double virus =  (double) K;
        long virus = (long) K;

        for(int i=0; i<N; i++){

            virus = (virus*P) % 1000000007;

        }

        System.out.println( (int) virus);

    }
}

import java.util.*;
import java.io.*;


public class Main
{
    static int N, M, K;
    static int[] railWeight;
    static ArrayList<int[]> outArr = new ArrayList<>();
    static ArrayList<Integer> answerList = new ArrayList<>();

    public static void print(int[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void print(int[] arr, int r){
        for(int i=0; i<r; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); //레일의 개수
        M = Integer.parseInt(st.nextToken()); //바구니 담을수 있는 무게
        K = Integer.parseInt(st.nextToken()); //몇 번 옮겨야 하는지

        railWeight = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            railWeight[i] = Integer.parseInt(st.nextToken());
        }

        //print(railWeight);

        solution();
        
    }

    public static void solution(){

        //permutation test
        int[] arr= railWeight; //new int[] {1,2,3};
        int n = arr.length;        
        int r = N;
        int[] output = new int[N];
        boolean[] visit = new boolean[N];
        int depth = 0;

        //순열로 각 조합에 대해서 구해온다. (안에 같이 넣어줘야 시간 초과가 안나나보다.)
        permutation(arr, output, visit, n, r, depth);

        Collections.sort(answerList, Comparator.naturalOrder());

        //정답
        System.out.println(answerList.get(0));


    }

    public static void getInBucket(int[] comb){

        int cnt=0;
        int bucket =0;
        int sum=0;
        int outCnt=1;

        while(true){

            if(outCnt>K){
                answerList.add(sum);
                break;
            }

            int weight = comb[cnt];

            bucket+= weight;

            cnt = (cnt+1)%N;

            if(bucket + comb[cnt]> M){
                sum += bucket;
                bucket = 0;
                outCnt +=1;
            }

        }
    }



    public static void permutation(int[] arr, int[] output, boolean[] visit, int n, int r, int depth){

        if(depth ==r){
            outArr.add(output);
            getInBucket(output);
            //print(output, r);
            return;
        }

        for(int i=0; i<n; i++){
            if(visit[i] != true){
                visit[i] = true;
                output[depth] = arr[i];
                permutation(arr, output, visit, n, r, depth+1);
                //output[depth] = 0;
                visit[i] = false;
            }
        }


    }


}

//Permutation에 대해서 

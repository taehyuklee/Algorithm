import java.util.*;
import java.io.*;

class Person implements Comparable<Person>{

    int idx;
    int score;

    public Person(int idx, int score){
        this.idx = idx;
        this.score = score;

    }

    @Override
    public String toString(){
        return "index: " + idx + " score: " + score + " | ";
       
    }
    @Override
    public int compareTo(Person person){
        return person.score - this.score; //내림차순
    }

}

public class Main
{   
    private static int N;
    private static List<Person> personList = new ArrayList<Person>();
    private static Person[] personArray;
    private static int[] totalScore;

    public static void print(int[] arr){

        for(int k=0; k<arr.length; k++){
            System.out.print(arr[k] + " ");
        }
        System.out.println();

    }

    public static void main(String args[]) throws Exception
    {   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        personArray = new Person[N];
        totalScore = new int[N];
        

        //대회마다 scoring후 출력해주기.
        for(int i=0; i<3; i++){

            st = new StringTokenizer(br.readLine());
            PriorityQueue<Person> pq = new PriorityQueue<>();

            for(int j=0; j<N; j++){

                //우선순위 queue에 넣는다. 우선순위 큐에서는 score순서대로 되어 있을 거
                Person person = new Person(j, Integer.parseInt(st.nextToken()));
                pq.offer(person);
                totalScore[j] += person.score;

            }

            int[] orderArr = getOrder(pq);
            print(orderArr);
        }

        //total에 대해서 위와 같은 작업을 해준다.
        PriorityQueue<Person> pq = new PriorityQueue<>();
        for(int i=0; i<N; i++){
            Person person = new Person(i, totalScore[i]);
            pq.offer(person);

        }

        int[] orderArr= getOrder(pq);
        print(orderArr);

    }

    public static int[] getOrder(PriorityQueue<Person> pq){

        int[] orderArr = new int[N];

        int rank =1;
        int oldRank = 1;
        int oldScore = 0;

        while(!pq.isEmpty()){

            Person person = pq.poll();
            //System.out.println(person.idx + " " + person.score);

            if(rank ==1){ //처음에만 oldScore 설정
                oldScore = person.score;
            }

            if(oldScore == person.score){
                //원래 순서대로에 등수를 넣어준다.
                orderArr[person.idx] =  oldRank;

            }else{
                orderArr[person.idx] = rank;
                oldRank = rank;
            }

            //oldScore로 업데이트
            oldScore = person.score; 
            rank++;

        }

        return orderArr;
    }

}

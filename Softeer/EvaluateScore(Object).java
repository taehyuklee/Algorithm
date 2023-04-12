import java.util.*;
import java.io.*;

class Person{
    int idx;
    int score1, score2, score3, total;
    int num1=0, num2=0, num3=0, num4=0;

    public Person(int idx){
        this.idx = idx;
    }

    @Override
    public String toString(){
        return "index: " + idx + " score1: " + score1 + " score2: " + score2 + " score3: " + score3 + " totScore: " + total + 
        " num1: " + this.num1 + " num2: " + this.num2 + " num3: " + this.num3 + " num4: " +this.num4 + " |" +"\n";
    }

    public int getIdx(){
        return this.idx;
    }

    public int getScore1(){
        return this.score1;
    }

    public int getScore2(){
        return this.score2;
    }

    public int getScore3(){
        return this.score3;
    }

    public int getTotal(){
        return this.total;
    }

}

public class Main
{   
    private static int N;
    private static List<Person> personList = new ArrayList<Person>();

    public static void main(String args[]) throws Exception
    {   
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

       //System.out.println(N);

        //첫번째 대회 10^5
        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=N; i++){
            Person person = new Person(i);
            person.score1 = Integer.parseInt(st.nextToken());
            personList.add(person);
        }
        //System.out.println(personList);

        //두번째 대회 10^5
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            Person person = personList.get(i);
            person.score2 = Integer.parseInt(st.nextToken());
        }

        //세번째 대회 10^5
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            Person person = personList.get(i);
            person.score3 = Integer.parseInt(st.nextToken());
        }

        //총 점수 합에 대한 10^5
        for(int i=0; i<N; i++){
            Person person = personList.get(i);
            person.total = person.score1 + person.score2 + person.score3;
        }

        //System.out.println(personList);

        solution();
        
    }

    public static void solution(){

        //각 대회마다 sorting후 등수 매기기
        sortingEachComp();

        //이후 답으로 출력하기
        printOut();

    }

    public static void sortingEachComp(){

        //1번 대회에 대한 점수로 sorting후 등수 매기기 (10^5 X 2)
        Collections.sort(personList, Comparator.comparing(Person::getScore1).reversed()); //reversed 혹시모르니 docu에서 찾는 연습

        //초기값 setting
        int oldScore =  personList.get(0).score1;
        personList.get(0).num1 = 1;
        int oldNum = 1;

        for(int i=1; i<N; i++){
            if(personList.get(i).score1 == oldScore){
                personList.get(i).num1 = oldNum;
            }else{
                personList.get(i).num1 = i+1; //등수 numbering
                oldScore = personList.get(i).score1;
                oldNum = i+1;
            }
        }


        //2번 대회에 대한 점수로 sorting후 등수 매기기 (10^5 X2)
        Collections.sort(personList, Comparator.comparing(Person::getScore2).reversed());

        //초기값 setting
        oldScore =  personList.get(0).score2;
        personList.get(0).num2 = 1;
        oldNum = 1;

        personList.get(0).num2 = 1;
        for(int i=0; i<N; i++){
            if(personList.get(i).score2 == oldScore){
                personList.get(i).num2 = oldNum;
            }else{
                personList.get(i).num2 = i+1; //등수 numbering
                oldScore = personList.get(i).score2;
                oldNum = i+1;
            }
        }

        //3번 대회에 대한 점수로 sorting후 등수 매기기 (10^5 X2)
        Collections.sort(personList, Comparator.comparing(Person::getScore3).reversed());

        //초기값 setting
        oldScore =  personList.get(0).score3;
        personList.get(0).num3 = 1;
        oldNum = 1;

        personList.get(0).num3 = 1;
        for(int i=0; i<N; i++){
            if(personList.get(i).score3 == oldScore){
                personList.get(i).num3 = oldNum;
            }else{
                personList.get(i).num3 = i+1; //등수 numbering
                oldScore = personList.get(i).score3;
                oldNum = i+1;
            }
        }

        //전체 점수로 sorting후 등수 매기기 (10^5 X2)
        Collections.sort(personList, Comparator.comparing(Person::getTotal).reversed());

        //초기값 setting
        oldScore =  personList.get(0).total;
        personList.get(0).num4 = 1;
        oldNum = 1;

        personList.get(0).num4 = 1;
        for(int i=0; i<N; i++){
            if(personList.get(i).total == oldScore){
                personList.get(i).num4 = oldNum;
            }else{
                personList.get(i).num4 = i+1; //등수 numbering
                oldScore = personList.get(i).total;
                oldNum = i+1;
            }
        }
         
    }

    public static void printOut(){
        
        //10^5
        Collections.sort(personList, Comparator.comparing(Person::getIdx));
        //System.out.println(personList);   

        //첫 대회에 대한 순위 출력 10^5
        for(int i=0; i<N; i++){
            System.out.print(personList.get(i).num1 + " ");
        }
        System.out.println();

        //두 번째 대회에 대한 순위 출력 10^5
        for(int i=0; i<N; i++){
            System.out.print(personList.get(i).num2 + " ");
        }
        System.out.println();

        //세 번째 대회에 대한 순위 출력 10^5
        for(int i=0; i<N; i++){
            System.out.print(personList.get(i).num3 + " ");
        }
        System.out.println();

        //전체 대회에 대한 순위 출력 10^5
        for(int i=0; i<N; i++){
            System.out.print(personList.get(i).num4 + " ");
        }


    }
}

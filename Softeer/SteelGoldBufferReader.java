import java.util.*;
import java.io.*;


public class Main
{

    private static int N, W;
    private static ArrayList<int[]> list = new ArrayList<>();

    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int W = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int[] newInt = new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            list.add(newInt);
        }
        
        //Collections.sort(list,(o1,o2)->o2[1]-o1[1]);

        Collections.sort(list, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2){

                if(o1[1]<o2[1]){
                    return 1;
                }
                else if(o1[1] == o2[1]){ //이거 빼먹어서 틀렸었음
                    return 0;
                }
                else{
                    return -1;
                }
            }
        });

        int curW=0;
        int price=0;

        for(int i=0; i<list.size(); i++){

            int nextW = list.get(i)[0];
            int nextPrice = list.get(i)[1];
            
            if(curW + nextW <W){
                //현재 배낭의 무게에 다음 전체 보석을 다 더했을때 W를 안넘었다면,
                curW += nextW;
                price += nextW*nextPrice;

            }else{
                //현재 배낭의 무게
                int restW = W - curW; //현재 채울수 있는 무게 
                curW += restW;
                price += nextPrice*restW;

            }

            //W가 다 찼으면 break
            if(curW==W){
                break;
            }
        }

        System.out.println(price);

    }
}

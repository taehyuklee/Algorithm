import java.util.*;
import java.io.*;

class Belt{
	int durability, id;
	boolean robot;
	
	public Belt(int durability, int id, boolean robot) {
		super();
		this.durability = durability;
		this.id = id;
		this.robot = robot;
	}

	@Override
	public String toString() {
		return "Belt [durability=" + durability + ", id=" + id + ", robot=" + robot + "]";
	}
	
	
}


public class Main {
	
	static int N,K,count;
	static int[] durability = new int[201];
	
	public static void print1D(int[] arr) {
		for(int i=0; i<2*N; i++) {
			System.out.print(arr[i] + " ");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		//Input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<2*N; i++) {
			durability[i] = Integer.parseInt(st.nextToken());
		}
		
		//System.out.println(N + " " + M);
		//print1D(durability);

		//solution
		solution();
		
	}
	
	public static void solution() {
		
		List<Belt> beltList = new ArrayList<>();
		for(int i=0; i<2*N; i++) {
			beltList.add(new Belt(durability[i], i, false));
		}

		int answer=1;
		while(true) {
			
//			System.out.println(answer +"번째입니다");
//			System.out.println(beltList);
			
			//System.out.println("회전시킨다");
			//ConveyorBelt 회전
			Belt last = beltList.remove(2*N-1);
			beltList.add(0, last);

			//즉시 내린다 내리는 위치에 있다면
			if(beltList.get(N-1).robot == true) {
				beltList.get(N-1).robot = false;
			}
			//System.out.println(beltList);

			//System.out.println("이동시킨다");

			for(int i =N-1; i>=1; i--) {
				if(!beltList.get(i).robot && beltList.get(i-1).robot && beltList.get(i).durability >=1) {
					beltList.get(i-1).robot =false;

					if(i == N-1) { //즉시 내린다.
						beltList.get(i).robot =false;
					}else{//true로 해주는 거 어디감?
						beltList.get(i).robot =true;
					}
					beltList.get(i).durability -=1;
				}
			}
			//System.out.println(beltList);
			
			//System.out.println("싣는다");
			//그다음 올리는 곳에 싣기!
			if(!beltList.get(0).robot && beltList.get(0).durability >=1) {
				beltList.get(0).durability -=1; 
				beltList.get(0).robot = true;
			}
			//System.out.println(beltList);
			
			//K==0인거 체크하기
			for(int i=0; i<2*N; i++) {
				if(beltList.get(i).durability ==0) {
					count+=1;
				}
			}
			
			//System.out.println(count);
			
			
			if(count >=K) {
				//System.out.println(count);
				
				break;
			}
			//System.out.println();
			
			count = 0;
			answer+=1;
		}
		//System.out.println(beltList);
		System.out.println(answer);
		
	}

}

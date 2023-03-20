import java.util.*;
import java.io.*;

class Box{
	int id;
	int weight;
	
	int prev;
	int next;
	
	public Box(int id, int weight, int before, int after) {
		super();
		this.id = id;
		this.weight = weight;
		this.prev = before;
		this.next = after;
	}

	@Override
	public String toString() {
		return "Box [id=" + id + ", weight=" + weight + ", prev=" + prev + ", next=" + next + "]";
	}
	
	public void cutPrev() {
		this.prev = -1;
	}
	
	public void cutNext() {
		this.next = -1;
	}
	
	public int getPrev() {
		return this.prev;
	}
	
	public int getNext() {
		return this.next;
	}
	
	public void connectPrev(int prev) {
		this.prev = prev;
	}
	
	public void connectNext(int next) {
		this.next = next;
	}

}

class Belt{
	int beltNum;
	int firstBox;
	int lastBox;
	HashMap<Integer, Box> inBox;
	
	public Belt(int beltNum, HashMap<Integer, Box> inBox, int firstBox, int lastBox) {
		this.beltNum = beltNum;
		this.inBox =inBox;
		this.firstBox = firstBox;
		this.lastBox = lastBox;
	}

	
	@Override
	public String toString() {
		return "Belt [beltNum=" + beltNum + ", firstBox=" + firstBox + ", lastBox=" + lastBox + ", inBox=" + inBox
				+ "]";
	}


	public void add(Box box) {
		this.inBox.put(box.id, box);
	}
	
	public Box getFistBox() {
		return this.inBox.get(this.firstBox);
	}
	
	public Box getLastBox() {
		return this.inBox.get(this.lastBox);
	}
	
}


public class Main {

	static int Q, N, M;
	
	static ArrayList<Belt> beltList = new ArrayList<Belt>();
	
	public static void print(int[] array) {
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
	
	public static void print(ArrayList<Belt> beltArray) {
		for(int b=0; b<beltArray.size(); b++) {
			System.out.println(beltArray.get(b));
			System.out.println();
		}
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		//5단계로 solution
		solution(sc);

	}
	
	public static void solution(Scanner sc) {
		
		//1. 공장설립
		constFactory(sc);
		
		//sc.nextLine();
		
//		while(true) {
		//sc.nextLine(); // 한줄 넘어갈때마다 뭐지 이게
		
//		for(int i=0; i<10000; i++) {
//			String nextString = sc.nextLine();
//			if(nextString == "") {
//				break;
//			}
//			String[] numsString= nextString.split(" ");
//
//			int opsNum = Integer.parseInt(numsString[0]);
//			int targetNum = Integer.parseInt(numsString[1]);
		
		for(int i=0; i<Q-1; i++) {
			
			int opsNum = sc.nextInt();
			int targetNum = sc.nextInt();
			
			if(opsNum == 200) {
				//2. 물건 하차
				unloadStuff(targetNum);
			
			}else if(opsNum == 300) {
				//3. 물건 제거
				removeStuff(targetNum);
				
			}else if(opsNum == 400) {
				//4. 물건 확인
				
			}else if (opsNum ==500) {
				//5. 벨트 고장
				
			}
	}

		
	}
	
	public static void constFactory(Scanner sc) {
		//1. 공장 설립
		Q = sc.nextInt();
		
		//처음 Index는 띄워버리고
		sc.nextInt();
		N = sc.nextInt(); //박스의 숫자
		M = sc.nextInt(); //belt의 숫자
		
		int div = N/M;
		
		int[] idArray = new int[N];
		int[] wArray = new int[N];
		
		for(int i=0; i <N; i++) {
			idArray[i] = sc.nextInt();
		}
		
		for(int i=0; i <N; i++) {
			wArray[i] = sc.nextInt();
		}
//		System.out.println();
//		print(idArray);
//		print(wArray);
		
		int index=0;
		for(int b=0; b<M; b++) {
			
			HashMap<Integer, Box> boxMap = new HashMap<>();
			int firstBox = 0;
			int lastBox = 0;
			for(int i=0; i<div; i++) {
				
				if(index%div==0) { //index%(M+1)==0
					boxMap.put(idArray[index], new Box(idArray[index], wArray[index], -1, idArray[index+1]));
					firstBox = idArray[index]; //첫 박스에 대한 id 구하기
				}else if(index%div==div-1) { //index%(M+1)==div-1
					boxMap.put(idArray[index], new Box(idArray[index], wArray[index], idArray[index-1], -1));
					lastBox = idArray[index]; //마지막 박스에 대한 id 구하기
				}else {
					boxMap.put(idArray[index], new Box(idArray[index], wArray[index], idArray[index-1], idArray[index+1]));
				}
				index+=1;
			}
			beltList.add(new Belt(b, boxMap, firstBox, lastBox));
		}
		
		//sc.close();

//		print(beltList);
	}
	
	
	public static void unloadStuff(int w_max) {
		
		print(beltList);
		
		int unloadedW =0;
		
		//belt는 한 바퀴 돌릴수밖에 없어 full scan으로
		for(int b=0; b<beltList.size(); b++) {
			Belt belt = beltList.get(b);
			HashMap<Integer, Box> beltBoxMap= belt.inBox;
			Box frontBox = belt.getFistBox();
			
			if(frontBox.weight<=w_max){
				unloadedW += frontBox.weight; //무게를 더해준다.
				belt.inBox.remove(frontBox); //belt에서 하차시켜준다

				
			}else {
				//만약 무게가 더 무겁다면 맨 뒤로 보내준다.

			}

		}
		
		//하차된 무게 출력
		System.out.println(unloadedW);

	}

	
	public static void removeStuff(int r_id) {
		
		
		
	}

}

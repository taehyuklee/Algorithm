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
				checkStuff(targetNum);
				
			}else if (opsNum ==500) {
				//5. 벨트 고장
				malfuncBelt(targetNum);
				
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
		for(int b=1; b<=M; b++) {
			
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
		
//		print(beltList);
		
		int unloadedW =0;
		
		//belt는 한 바퀴 돌릴수밖에 없어 full scan으로
		for(int b=0; b<beltList.size(); b++) {
			Belt belt = beltList.get(b);
			HashMap<Integer, Box> beltBoxMap= belt.inBox;
			Box frontBox = belt.getFistBox();
			
			if(frontBox.weight<=w_max){
				//맨 앞에 박스 하차 준비//
				unloadedW += frontBox.weight; //무게를 더해준다.
				int nextBoxId = frontBox.next; //하차할 박스 다음 박스에 대한 id를 가져온다.
				belt.inBox.remove(frontBox.id); //belt에서 하차시켜준다
				
				//하차 이후에 바로 다음 박스 Linked-list 재정리//
				Box nextBox = belt.inBox.get(nextBoxId); // 맨 처음에 올 박스를 가져온다.
				nextBox.cutPrev(); //새로 맨 앞의 박스가 된 것에서 prev를 끊어준다.
				belt.firstBox = nextBoxId; //처음 박스 Id를 기록해준다.
				
			}else {
				//만약 무게가 더 무겁다면 맨 뒤로 보내준다.
				//마지막 박스가 무엇인지 확인
				int lastBoxId = belt.lastBox;
				Box lastBox = belt.inBox.get(lastBoxId);
				
				//맨 앞으로 올 box에 대해서도 정리
				Box secondBox = belt.inBox.get(frontBox.next);
				secondBox.prev = -1;
				belt.firstBox = secondBox.id;
				
				
				//맨 뒤로 보낼 박스 Link 재정리
				frontBox.next = -1;
				frontBox.prev = lastBox.id;
				
				//마지막에 있던 박스 링크 재정리
				lastBox.next = frontBox.id;
				
				//belt last box update
				belt.lastBox = frontBox.id;

			}

		}
		
		//하차된 무게 출력
		System.out.println(unloadedW);
		//print(beltList);

	}

	
	public static void removeStuff(int r_id) {
		
		boolean isRemove = false;
		
		for(int b=0; b<beltList.size(); b++) {
			Belt belt = beltList.get(b);
			
			boolean isBox = belt.inBox.containsKey(r_id);
			
			if(isBox) {
				Box targetBox = belt.inBox.get(r_id);

				if(targetBox.prev==-1) {
					//box가 맨 앞일때 그 다음 박스 맨 앞으로 땡기기
					int nextBoxId = targetBox.next;
					Box nextBox = belt.inBox.get(nextBoxId);
					nextBox.prev = -1;
					belt.firstBox = nextBox.id;
					
					//맨 앞의 박스 제거
					belt.inBox.remove(r_id);
					
					isRemove = true;
					
				}else if(targetBox.next==-1) {
					//box가 맨 뒤일때
					//맨 뒤의 박스에서 바로 앞 박스 맨 뒤 박스로 바꾸기
					int prevBoxId = targetBox.prev;
					Box prevBox = belt.inBox.get(prevBoxId);
					prevBox.next = -1;
					belt.lastBox = prevBox.id;
					
					//맨 뒤 박스 제거
					belt.inBox.remove(r_id);		
					
					isRemove = true;
					
				}else {	
					//box가 중간일때
					//box의 연결을 모두 끊는다 양쪽의 box를 이어주고
					Box nextBox = belt.inBox.get(targetBox.next);
					Box prevBox = belt.inBox.get(targetBox.prev);
					
					nextBox.prev = prevBox.id;
					prevBox.next = nextBox.id;
					
					//박스 제거
					belt.inBox.remove(r_id);
					
					isRemove = true;
				}
				
			}
			
		}
		
		if(isRemove) {
			System.out.println(r_id);
		}else {
			System.out.println(-1);
		}
		
		//print(beltList);

	}

	
	public static void checkStuff(int f_id) {

		int targetBeltNum = -1;
		
		for(int b=0; b<beltList.size(); b++) {
			Belt belt = beltList.get(b);
			
			boolean isBox = belt.inBox.containsKey(f_id);
			
			if(isBox) {
				//from here
				Box targetBox = belt.inBox.get(f_id);
				int willLastBoxId = targetBox.prev;
				
				
				//to here
				int lastBoxId = belt.lastBox;
				Box toBox = belt.inBox.get(lastBoxId);
				
				//firstBox (위의 박스들을 이 박스 앞에 놓아줘야 한다)
				int firstBoxId = belt.firstBox;
				Box firstBox = belt.inBox.get(firstBoxId);
				
				//연결시켜준다. 맨 뒤에를 맨 앞의 박스 앞으로
				firstBox.prev = toBox.id;
				toBox.next = firstBox.id;
				
				//맨 앞의 박스가 바뀐다.
				targetBox.prev = -1;
				belt.firstBox = targetBox.id;
				
				//맨 뒤의 박스도 바뀐다.
//				belt.lastBox = targetBox.prev;
				Box willLastBox = belt.inBox.get(willLastBoxId);
				willLastBox.next = -1;
				belt.lastBox = willLastBox.id;
				
				targetBeltNum = belt.beltNum;
			}
		}

		System.out.println(targetBeltNum);
		
		//print(beltList);

	}
	
	
	public static void malfuncBelt(int b_num) {
		
		int malPrint = -1;
		
		for(int b=0; b<beltList.size(); b++) {
			
			Belt belt = beltList.get(b);
			
			if(belt.beltNum ==b_num) {
				//해당 벨트가 있다면, 모든 짐들을 옮겨주고 제거해준다.
				
				int nextBeltNum = (b+1)%beltList.size();
				Belt nextBelt = beltList.get(nextBeltNum); //toBelt로 옮겨야한다.

				HashMap<Integer, Box> asBelt = belt.inBox;
				HashMap<Integer, Box> toBelt = nextBelt.inBox;
				
				//연결작업을 해주고 asBelt를 넣어주도록 한다.
				
				//기존 belt의 첫박스와 마지막 박스
				int asFirstBoxId = belt.firstBox;
				int asLastBoxId = belt.lastBox;
				
				Box asFirstBox = asBelt.get(asFirstBoxId);
				Box asLastBox = asBelt.get(asLastBoxId);
				
				//옮길 belt의 첫박스와 마지막 박스
				int toFirstBoxId = nextBelt.firstBox;
				int toLastBoxId = nextBelt.lastBox;
				//System.out.println(toLastBoxId);
				
				Box toFirstBox = toBelt.get(toFirstBoxId);
				Box toLastBox = toBelt.get(toLastBoxId);
				

				//연결작업
				toLastBox.next = asFirstBox.id;
				asFirstBox.prev = toLastBox.id;
				
//				System.out.println(asFirstBox.id + " " + toLastBox.id );

				asLastBox.cutNext(); //마지막 부분은 연결 끊어주고 (원래 되어 있었겠지만) 혹시
				toFirstBox.cutPrev(); //처음 부분도 연결 끊어주고 (원래 되어 있었겠지만) 혹시
				
				//박스들을 옮겨준다.
				nextBelt.inBox.putAll(asBelt);
				
				//망가진 belt를 없애준다.
				beltList.remove(belt);
				
				nextBelt.firstBox = toFirstBox.id;
				nextBelt.lastBox = asLastBox.id;
				
				//break;
				malPrint = b_num;
			}

		}
		//print(beltList);
		System.out.println(malPrint);
		
	}
}

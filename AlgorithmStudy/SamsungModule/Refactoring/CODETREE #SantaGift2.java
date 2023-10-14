package 삼성문제2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
/*
 * 
 * 각 벨트의 정보, 선물 정보 조회할 수 있는 기능 추가 
 * 
 * 1. 공장 설립 
 * - n개의 벨트 설치, 총 m개의 물건을 준비 
 * - m개의 선물의 위치가 공백을 사이에 두고 주어진다.
 * 각 선물의 번호는 오름차순으로 벨트에 쌓인다. 
 * 
 * 2. 물건 모두 옮기기  // 모두 옮기는 것 또한 bet정보와 Box_map을 이용하면O(1)로 끝난다.
 * - m_src번째있는 선물들을 모두 -> m_dst번째 벨트로 옮긴다. 
 * 옮겨진 선물들은 m_dst 기존 물건 앞에 위치된다. 
 * (단, m_src 번째 벨트에 선물이 존재하지 않는다면, 옮기지 않는다) 
 *  옮긴 후 m_dst 번째 벨트에 있는 선물 개수를 출력한다. 
 *  
 * 3. 앞 물건 교체하기 -// belt정보와 Box_map을 이용하면 O(1)로 가능하다.
 * - m_src 벨트 맨 앞의 선물을 m_dst번째 벨틔 선물들 중 가장 앞에 있는 선물과 교체한다. 
 * (둘중 하나라도 선물이 존재하지 않는다면, 교체하지 않고 해당 벨트로 선물을 옮기기만 한다)
 *  이후 m_dst 선물 개수를 출력한다. 
 *  
 * 4. 물건 나누기 -> 이 부분이 과연 belt정보와 Box_map을 이용하면 할 수 있을 것인가? - 100번까지만 주어진다.
 * - m_src번째 벨트에 있는 선물의 개수를 n이라 했을때 
 * -> 맨 앞에서 (n/2)까지의 선물을 m_dst로 옮긴다.
 * (단 src에 선물이 1개인 경우 선물을 옮기지 않는다) 
 * 옮긴 이후 m_dst 벨트에 있는 선들의 개수를 출력한다. 
 * 
 * 5. 선물 정보 얻기 //// <Box> Map으로 금방 O(1)로 끝난다.
 * - 선물번호 p_num이 주어질 때, 해당 선물의 앞 선물 번호 a와 뒤 선물 번호 b라 할때 
 * a+2*b를 출력한다
 * (단 앞 선물이 없으면 a=-1, 뒤 가 없을 경우 b= -1을 대입한다)
 * 
 * 
 * 6. 벨트 정보 얻기 /// O(1)로 끝남 -> belt도 Map으로 관리.
 * - 벨트 번호 b_num이 주어질 때, 해당 벨트 맨 앞의 선물 번호 a, 맨 뒤 선물 번호 b, 해당 벨트의 선물 개수 c
 * a+2*b + 3*c 값을 출력한다. 
 * 선물이 없을 경우 a =-1, b=-1을 대입한다.
 * 
 *  입력 형식 
 *  Q개의 명령어 수가 주어진다. 
 *  
 * */
import java.util.*;

class Box{
	
	int index;
	int prev, next;


	public Box(int index, int prev, int next) {
		this.index = index;
		this.prev = prev;
		this.next = next;
	}
	
	@Override
	public String toString() {
		return "[index: " + index + " prev: " + prev + " next: " + next +" ] ";
	}
	
	public void connectNext(int next) {
		this.next = next;
	}
	
	public void connectPrev(int prev) {
		this.prev = prev;
	}
	
	public void cutNext() {
		this.next = 0;
	}
	
	public void cutPrev() {
		this.prev = 0;
	}
	
}

class Belt{
	int index;
	int first, last;
	int cnt;

	public Belt(int index, int first, int last, int cnt) {
		this.index = index;
		this.first = first;
		this.last = last;
		this.cnt = cnt;
	}
	
	@Override
	public String toString() {
		return "[b_num: " + index + " first: " + first + " last: " + last + " cnt: " + cnt +" ] ";
	}
	
}

public class Solution {
	
	static int Q, N, M;
	static Map<Integer, Box> boxMap;
	static Map<Integer, Belt> beltMap; 
	
	public static void main(String[] args) throws IOException {
		
		System.setIn(new FileInputStream("/Users/thlee/Desktop/sample 2.txt"));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		Q = Integer.parseInt(st.nextToken());
		
		for(int q=0; q<Q; q++) {
			String order =br.readLine();
			String[] order_array = order.split(" ");
			
			if("100".equals(order_array[0])) order100(order_array);
		
			if("200".equals(order_array[0])) order200(order_array);
			
			if("300".equals(order_array[0])) order300(order_array);
			
			if("400".equals(order_array[0])) order400(order_array);
			
			if("500".equals(order_array[0])) order500(order_array);
			
			if("600".equals(order_array[0])) order600(order_array);
		}
		
	}
	
	static void order100(String[] order_array) {
		
		N = Integer.parseInt(order_array[1]);
		M = Integer.parseInt(order_array[2]);
		
		beltMap = new HashMap<>();
		boxMap = new HashMap<>();

		for(int b_index=1; b_index<=N; b_index++) {
			Belt belt = new Belt(b_index, 0, 0, 0);
			beltMap.put(b_index, belt);
		}
		
		//i가 box index임을 명심하자.
		for(int box_index=1; box_index<=M; box_index++) {
			int belt_num = Integer.parseInt(order_array[3 +(box_index-1)]);
			
			Belt belt = beltMap.get(belt_num);
			
			//belt 작업.
			if(belt.first==0) {
				belt.first = box_index;
				belt.last = box_index;
				belt.cnt = 1;	
				boxMap.put(box_index, new Box(box_index, 0 , belt.last));
			}else {
				int last_inBelt = belt.last;
				//box Map에서 이전 Box 빼오기 
				Box prevBox = boxMap.get(last_inBelt);
				prevBox.next = box_index; //이전 박스 입장에서는 지금 박스가 다음 박스이므로.
				
				
				//box 작업.(box작업부터 해야 이전에 있는 박스 정보를 belt로부터 받아올수 있다)
				//현재 박스에게 이전 박스 정보 prevBox.index를 받아와서 넣어준다.
				boxMap.put(box_index, new Box(box_index, prevBox.index , box_index));
				
				//Belt 정보 업데이
				belt.last = box_index;
				belt.cnt += 1;
				/*명심할 것 - lastIndex와 자기 현재 Index가 같으면, 마지막 박스임.*/
			}

			
			
		}
	
	}

	
	static void order200(String[] order_array) {
		//200 2 4
		/*
		 * 
		 *  * 2. 물건 모두 옮기기  // 모두 옮기는 것 또한 bet정보와 Box_map을 이용하면O(1)로 끝난다.
			 * - m_src번째있는 선물들을 모두 -> m_dst번째 벨트로 옮긴다. 
			 * 옮겨진 선물들은 m_dst 기존 물건 앞에 위치된다. 
			 * (단, m_src 번째 벨트에 선물이 존재하지 않는다면, 옮기지 않는다) 
			 *  옮긴 후 m_dst 번째 벨트에 있는 선물 개수를 출력한다. */
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		//처음에 src에 cnt가 >=1 인지 확인 
		if(belt_src.cnt>=1) {
			//그렇다면 옮긴다.
			int firstSrc = belt_src.first; //첫번째 박스 
			int lastSrc = belt_src.last;
			
			Box firstBoxSrc = boxMap.get(firstSrc);
			Box lastBoxSrc = boxMap.get(lastSrc);
			
			
			// [src] - [dst] 이 순서로 간다. 
			int firstDst = belt_dst.first;
			Box firstBoxDst = boxMap.get(firstDst);
			
			
			//box 조정 및 belt 조정.
			//belt 조정.
			belt_dst.first = belt_src.first; //belt_dst last는 그대로 가도 됨.
			belt_dst.cnt += belt_src.cnt;
					
			belt_src.first = 0;
			belt_src.last = 0;
			belt_src.cnt = 0;
			
			//box 조정.
			firstBoxSrc.prev =0;
			lastBoxSrc.next = firstBoxDst.index;
			firstBoxDst.prev = lastBoxSrc.index;
	
		}
		
		System.out.println(belt_dst.cnt);
//		System.out.println(boxMap);
	}

	
	static void order300(String[] order_array) {
		/*
		 * 3. 앞 물건 교체하기 -// belt정보와 Box_map을 이용하면 O(1)로 가능하다.
		 * - m_src 벨트 맨 앞의 선물을 m_dst번째 벨틔 선물들 중 가장 앞에 있는 선물과 교체한다. 
		 * (둘중 하나라도 선물이 존재하지 않는다면, 교체하지 않고 해당 벨트로 선물을 옮기기만 한다)
		 *  이후 m_dst 선물 개수를 출력한다. 
		 * 
		 * */
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		
		if(belt_src.cnt==0 && belt_dst.cnt!=0) {
			int firstDst = belt_dst.first;
			Box firstBoxDst = boxMap.get(firstDst);
			
			//작업을 해줘야 함 맨 처음꺼 끊어줘야 하니까, 그 다음꺼도 prev 끊고 belt cnt -1해야지 
			Box nextBox =  boxMap.get(firstBoxDst.next);
			
			if(nextBox !=null) {
				nextBox.prev =0;
				belt_dst.cnt --;
				belt_dst.first = nextBox.index; //처음으로 내려온다.
			}else {
				belt_dst.cnt --;
				belt_dst.first = 0; //처음으로 내려온다.
				belt_dst.last = 0;
			}
			
			firstBoxDst.prev=0;
			firstBoxDst.next=firstBoxDst.index; //마지막이라는 표시 자기꺼 그대로.
			//벨트 업데이트 
			belt_src.first = 0; 
			belt_src.last = firstBoxDst.index;
			belt_src.cnt++;
			
			
		}else if(belt_src.cnt!=0 && belt_dst.cnt==0) {
			int firstSrc = belt_src.first;
			Box firstBoxSrc = boxMap.get(firstSrc);
			
			//작업을 해줘야 함 맨 처음꺼 끊어줘야 하니까, 그 다음꺼도 prev 끊고 belt cnt -1해야지 
			Box nextBox =  boxMap.get(firstBoxSrc.next);
			
			if(nextBox !=null) {
				nextBox.prev =0;
				belt_src.cnt --;
				belt_src.first = nextBox.index; //처음으로 내려온다.
			}else {
				belt_src.cnt --;
				belt_src.first = 0; //처음으로 내려온다.
				belt_src.last = 0;
			}
			
			firstBoxSrc.prev=0;
			firstBoxSrc.next=firstBoxSrc.index; //마지막이라는 표시 자기꺼 그대로.
			//벨트 업데이트 
			belt_dst.first = 0; 
			belt_dst.last = firstBoxSrc.index;
			belt_dst.cnt++;
			
			
		}else if(belt_src.cnt!=0 && belt_dst.cnt!=0){
			//여기서는 서로 교환. 
			int firstDst = belt_dst.first;
			
			Box firstBoxDst = boxMap.get(firstDst);
			
			Box nextBoxDst =  boxMap.get(firstBoxDst.next);
			
			int firstSrc = belt_src.first;
			Box firstBoxSrc = boxMap.get(firstSrc);
			
			Box nextBoxSrc =  boxMap.get(firstBoxSrc.next);
			
			if(nextBoxDst !=null && nextBoxSrc ==null) {
				//여기서 교환 
				nextBoxDst.prev =firstBoxSrc.index;
				firstBoxSrc.next = nextBoxDst.index;
				
				//뒤 연결 
				firstBoxDst.prev=0;
				firstBoxDst.next = firstBoxDst.index;
			
			}
			
			if(nextBoxDst ==null && nextBoxSrc !=null) {
				//여기서 교환 
				nextBoxSrc.prev =firstBoxDst.index;
				firstBoxDst.next = nextBoxSrc.index;
				
				//뒤 연결 
				firstBoxSrc.prev=0;
				firstBoxSrc.next = firstBoxSrc.index;
			}
			if(nextBoxDst !=null && nextBoxSrc !=null) {
				//여기서 교환 
				nextBoxSrc.prev =firstBoxDst.index;
				firstBoxDst.next = nextBoxSrc.index;
				
				//여기서 교환 
				nextBoxDst.prev =firstBoxSrc.index;
				firstBoxSrc.next = nextBoxDst.index;
			}
		
			
		}
		
		System.out.println(belt_dst.cnt);
		
	}
	
	
	static void order400(String[] order_array) {
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		if(belt_src.cnt>1) {
			
			int target_num = belt_src.cnt;
			int firstSrc = belt_src.first;
			int lastSrc = belt_src.last;
		
			Box init_box = boxMap.get(firstSrc);
			int mid_box_index =init_box.index;
			
			for(int i=2; i<=target_num/2; i++) {
				int next_box_n = init_box.next;
				Box next_box = boxMap.get(next_box_n);
				mid_box_index = next_box.index;
				
			}
			
			//옮길 상자의 처음 인덱스 중간(마지막)인덱스 
			int first_inx = init_box.index; //그리고 mid_box_index
			
			//src belt 작업 
			int willbefirst = boxMap.get(mid_box_index).next;
			belt_src.first = boxMap.get(willbefirst).index;
			
			
			//dst 상자 작업 
			int firstDst = belt_dst.first;
			if(firstDst!=0) {
				Box firstDstBox = boxMap.get(firstDst);
				Box midBoxFrom = boxMap.get(mid_box_index);
				Box firstBoxFrom = boxMap.get(first_inx);
				firstDstBox.prev = midBoxFrom.index;
				firstBoxFrom.prev = 0;
				belt_dst.first = firstBoxFrom.index;
				//last는 기존과 같다.
				//dst 벨트 작업
				belt_src.cnt -= target_num/2;
				belt_dst.cnt += target_num/2;
			}else {
				Box midBoxFrom = boxMap.get(mid_box_index);
				Box firstBoxFrom = boxMap.get(first_inx);
				belt_dst.first = firstBoxFrom.index;
				belt_dst.last = midBoxFrom.index;
				
				belt_src.cnt -= target_num/2;
				belt_dst.cnt += target_num/2;
			}

		}
		
		
		System.out.println(belt_dst.cnt);
		
	}


	static void order500(String[] order_array) {
		int p_num = Integer.parseInt(order_array[1]);
		int a=-1, b=-1;

		Box curBox = boxMap.get(p_num);
		
		if(boxMap.containsKey(curBox.prev)) {
			a = boxMap.get(curBox.prev).index;
		}
		
		if(boxMap.get(curBox.next).index != boxMap.get(curBox.next).next) {
			b = boxMap.get(curBox.next).index;
		}
		
		System.out.println(a+2*b);
		
	}
	
	
	static void order600(String[] order_array) {
		
		int b_num = Integer.parseInt(order_array[1]);
		int a=-1, b=-1, c=0;
		

		Belt belt = beltMap.get(b_num);
		if(belt.first !=0) {
			a = belt.first;
		}
		if(belt.last !=0) {
			b = belt.last;
		}
		c = belt.cnt;
		
		
		System.out.println(a+2*b+3*c);
		
	}
	
}

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
 * 2. 물건 모두 옮기기 
 * - m_src번째있는 선물들을 모두 -> m_dst번째 벨트로 옮긴다. 
 * 옮겨진 선물들은 m_dst 기존 물건 앞에 위치된다. 
 * (단, m_src 번째 벨트에 선물이 존재하지 않는다면, 옮기지 않는다) 
 *  옮긴 후 m_dst 번째 벨트에 있는 선물 개수를 출력한다. 
 *  
 * 3. 앞 물건 교체하기 
 * - m_src 벨트 맨 앞의 선물을 m_dst번째 벨틔 선물들 중 가장 앞에 있는 선물과 교체한다. 
 * (둘중 하나라도 선물이 존재하지 않는다면, 교체하지 않고 해당 벨트로 선물을 옮기기만 한다)
 *  이후 m_dst 선물 개수를 출력한다. 
 *  
 * 4. 물건 나누기 
 * - m_src번째 벨트에 있는 선물의 개수를 n이라 했을때 
 * -> 맨 앞에서 (n/2)까지의 선물을 m_dst로 옮긴다.
 * (단 src에 선물이 1개인 경우 선물을 옮기지 않는다) 
 * 옮긴 이후 m_dst 벨트에 있는 선들의 개수를 출력한다. 
 * 
 * 5. 선물 정보 얻기 
 * - 선물번호 p_num이 주어질 때, 해당 선물의 앞 선물 번호 a와 뒤 선물 번호 b라 할때 
 * a+2*b를 출력한다
 * (단 앞 선물이 없으면 a=-1, 뒤 가 없을 경우 b= -1을 대입한다)
 * 
 * 
 * 6. 벨트 정보 얻기 
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
	
}

class Belt{
	int index;
	int first, last;
//	Map<Integer, Box> innerMap = new LinkedHashMap<>();
	List<Box> innerList = new ArrayList<>();

	public Belt(int index, int first, int last) {
		this.index = index;
		this.first = first;
		this.last = last;
	}
	
	@Override
	public String toString() {
		return "[index: " + index + " first: " + first + " last: " + last +" ] ";
	}
	
}

public class Main {
	
	static int Q, N, M;
	static Map<Integer, Box> boxMap;
	static Map<Integer, Belt> beltMap; 
	
	public static void main(String[] args) throws IOException {
				
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

		for(int b=1; b<=N; b++) {
			Belt belt = new Belt(b, 0, 0);
			beltMap.put(b,belt);
		}
		
		//i가 box index임을 명심하자.
		for(int i=1; i<=M; i++) {
			int belt_num = Integer.parseInt(order_array[3 +(i-1)]);
			
			Belt belt = beltMap.get(belt_num);
			if(belt.innerList.size()==0) {
				Box box = new Box(i,0,0);
				belt.innerList.add(box);
				
			}else {
				List<Box> tempList = belt.innerList;
				
				Box prev_box = tempList.get(tempList.size()-1); //마지막꺼를 뽑는다.

				int prev = prev_box.index;
				Box newBox = new Box(i, prev, 0);
				prev_box.next = i;
				
				tempList.add(newBox);			
			}
		}
			
	}

	
	static void order200(String[] order_array) {
		//200 2 4
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		List<Box> src_belt = belt_src.innerList;
		List<Box> dst_belt = belt_dst.innerList;
		
		dst_belt.addAll(0, src_belt);
		src_belt.clear();
		System.out.println(dst_belt.size());
//		System.out.println(dst_belt);
//		System.out.println(src_belt);
		
	}

	
	static void order300(String[] order_array) {
		
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		List<Box> src_belt = belt_src.innerList;
		List<Box> dst_belt = belt_dst.innerList;
		
		if(src_belt.size() !=0 || dst_belt.size() !=0) {
			
			if(src_belt.size()==0) {
				Box moveBox = dst_belt.remove(0);
				src_belt.add(moveBox);
				
			}else if(dst_belt.size()==0) {
				Box moveBox = src_belt.remove(0);
				dst_belt.add(moveBox);
				
			}else {
				Box fromDstBox = dst_belt.remove(0);
				Box fromSrcBox = src_belt.remove(0);
				
				dst_belt.add(0, fromSrcBox);
				src_belt.add(0, fromDstBox);
			}
			
		}
		System.out.println(dst_belt.size());
		
		
	}
	
	
	static void order400(String[] order_array) {
		int src = Integer.parseInt(order_array[1]);
		int dst = Integer.parseInt(order_array[2]);
		
		Belt belt_src = beltMap.get(src);
		Belt belt_dst = beltMap.get(dst);
		
		List<Box> src_belt = belt_src.innerList;
		List<Box> dst_belt = belt_dst.innerList;
		
		if(src_belt.size()>1) {
			
			
			List<Box> moveList =new ArrayList<>();
			
			int half = (int) src_belt.size()/2;
			
			if(src_belt.size()%2==0) {
				//짝수 
				for(int i=0; i<half; i++) {
					moveList.add(src_belt.remove(i));
					half--; //half를 줄이든 i를 줄이든 이건 좀 더 정리해서 가야겠다.
				}
			}else {
				//홀수
				for(int i=0; i<=half; i++) {
					moveList.add(src_belt.remove(i));
					half--;
				}
			}
			
//			System.out.println(moveList);
		
			dst_belt.addAll(0, moveList);
//			System.out.println(dst_belt);

		}
		
		System.out.println(dst_belt.size());
		
	}

	
	static void order500(String[] order_array) {
		int p_num = Integer.parseInt(order_array[1]);
		int a=-1, b=-1;
		
		for(int belt_n=1; belt_n<=beltMap.size(); belt_n++) {
			Belt belt = beltMap.get(belt_n);
			List<Box> beltList = belt.innerList;
			
			for(int i=0; i<beltList.size(); i++) {
				Box box = beltList.get(i);
				if(box.index == p_num) {
					
					if(i-1==-1 && i+1 != beltList.size()) {
						a=-1;
						b = beltList.get(i+1).index;
					}
					if(i-1 != -1 && i+1 == beltList.size()) {
						a = beltList.get(i-1).index;
						b=-1;
					}
					if(i-1 != -1 && i+1 != beltList.size()) {
						a = beltList.get(i-1).index;
						b = beltList.get(i+1).index;
					}
					
					
				}
			}
			
		}
		System.out.println(a+2*b);
		
	}
	
	
	static void order600(String[] order_array) {
		
		int b_num = Integer.parseInt(order_array[1]);
		int a=-1, b=-1, c=0;
		

		
		Belt belt = beltMap.get(b_num);
		List<Box> beltList = belt.innerList;
		
		if(beltList.size() !=0) {
			Box firstBox = beltList.get(0); //맨 앞 
			Box lastBox = beltList.get(beltList.size()-1); //맨 뒤 
			a = firstBox.index;
			b =  lastBox.index;
			c = beltList.size();
		}
		
		System.out.println(a+2*b+3*c);
		
	}
	
}


//부분 점수를 맡기 위해 빠르게 이렇게 짬.

import java.util.*;

public class Main {

	public static void main(String[] args) {

//		PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) { //양수면 순서대로, 음수면 역순
//				System.out.println("여기서 확인" + o1 +  "  " + o2);
//				System.out.println(o1-o2);
//				System.out.println(o2-o1);
//				return o1-o2;
//			}
//		});
//		
//		
//		pq.add(100);
//		pq.add(10);
//
//		
//		System.out.println(pq);
//		
//		//Poll할때,
//		for(int i=0; i<pq.size(); i++) {
//			int newone = pq.poll();
//			System.out.println(newone);
//			newone+=1000;
//			pq.add(newone);
//			
//		}
		
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(100);
		list.add(99);
		
		System.out.println(list);
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) { //양수면 순서대로, 음수면 역순
				System.out.println("여기서 확인" + o1 +  "  " + o2);
				System.out.println(o1-o2);
				System.out.println(o2-o1);
				
				if(o1<o2) {
					return 1;
				}else if(o1==o2) {
					return 0;
				}else {
					return -1;
				}
			}
		});
		//양수면 바꾸고 음수면 안바꾸고 
		
		System.out.println(list);
	}
	
	
}

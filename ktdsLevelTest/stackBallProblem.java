package JavaL2;

import java.util.*;
import java.util.stream.*;
import java.util.Map.*;
import java.lang.Math;

public class JavaL2 {
	
	public static void main(String[] args) {
		
		int[] ball = {1,4,3,2,5,6};
		//int[] order = {6,1,2,5,4,3};
		int[] order = {4,3,2,5,6,1};
		
		List<Integer> ballList = Arrays.stream(ball).boxed().collect(Collectors.toList());
		List<Integer> orderList = Arrays.stream(order).boxed().collect(Collectors.toList());
		List<Integer> waitList = new ArrayList<Integer>();
		
		List<Integer> answer = new ArrayList<Integer>();

		int size = 0;
		boolean go = true;
		//for문
		while(true) {
			//if //대기 list를 확인한다. 
			if(waitList.size() !=0) {
				
				//대기 list에서 pop한게 ball에서 가장 밖에 있는지를 확인한다.
				//가장 밖에 있으면 뺀다.	//answer 추
				for(int i=0; i<waitList.size(); i++) {
//					System.out.println(ballList.get(0) +  "" +"and" + waitList.get(i));
//					System.out.println(ballList.get(ballList.size()-1));
//					System.out.println();
					
					if(waitList.get(i).equals(ballList.get(0))) {
						Integer ballWait = waitList.remove(i);
						ballList.remove(0);
						answer.add(ballWait);
						go = false;
						break;
					}else if(waitList.get(i).equals(ballList.get(ballList.size()-1))) {
						Integer ballWait = waitList.remove(i);
						ballList.remove(ballList.size()-1);
						answer.add(ballWait);
						go = false;
						break;
					}
				}
			}
			
			if(go ==true){
				//else
				
				//order 순서대로 pop을 한다
				if(orderList.size()!=0) {
					Integer popedOrder = orderList.remove(0);
					
					//order에 따라 ball이 가장 밖에 있는지를 확인한다.
					//밖에 있으면 뺀다
					if(popedOrder.equals(ballList.get(0))) {
						ballList.remove(0);
						answer.add(popedOrder);
					}else if(popedOrder.equals(ballList.get(ballList.size()-1))) {
						ballList.remove(ballList.size()-1);
						answer.add(popedOrder);
					}else {
						//만약 안에 있으면 대기 list에 넣어둔다
						waitList.add(popedOrder);
					}
					
				}


			}
			go=true;
			
			if(orderList.size() ==0 && ballList.size()==0 && waitList.size()==0) {
				break;
			}
			
//			if(size ==30) {
//				break;
//			}
			System.out.println("ballList" + " " +ballList);
			System.out.println("orderList" + " " + orderList);
			System.out.println("waitList" +  " " + waitList);
			System.out.println("answerList" + " " + answer);
			System.out.println();
			
			//size+=1;

		}
		System.out.println(answer);
		
	
	}
}

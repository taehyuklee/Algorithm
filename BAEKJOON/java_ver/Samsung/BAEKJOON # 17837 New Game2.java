import java.util.*;
import java.io.*;

class Horse{
	int num, x, y, dir;
	
	public Horse(int num, int x, int y, int dir){
		this.num = num;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	@Override
	public String toString() {
		return "num: " + this.num +" x: " + this.x + " y "+ this.y +" " + "dir " + this.dir + "   ";
	}
}

public class Main {
	
	static int N, K;
	static int[][] colorBoard;
	static List<Horse>[][] horseBoard;
	static List<Horse> horseList= new ArrayList<Horse>();

	static int[] dx = {0,0,-1,1}, dy = {1,-1,0,0}; //우, 좌, 상, 하  

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		K = sc.nextInt();
		
		colorBoard = new int[N][N];
		horseBoard = new ArrayList [N][N]; //이건 단순히 ArrayList를 받아들일 판을 만들어 놓은것 뿐
		
		for(int i=0; i<N ; i++) {
			for(int j=0; j<N; j++) {
				colorBoard[i][j] = sc.nextInt();
				horseBoard[i][j] = new ArrayList<Horse>();
			}
		}
		
		for(int i=0; i<K; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int dir = sc.nextInt()-1;
			Horse horse = new Horse(i+1,x,y,dir);
			horseBoard[x][y].add(horse);
			horseList.add(horse);
		}
		
		solution();
		
	}
	
	static void solution(){
		
		//말이 한 바퀴 돌아감
		int k=0;
		
		while(true) {
			
			if(k>1000) {
				System.out.println(-1);
				break;
			}
			System.out.println(k);
			k++;
			for(int i=0 ;i<K; i++) {
				Horse horse = horseList.get(i);
				
				//미리 탐험
				int new_x = horse.x +dx[horse.dir];
				int new_y = horse.y +dy[horse.dir];
				
				//움직였을때 빨강이냐, 파랑이냐, 흰색이냐, 아님 벗어난거냐?
				if(new_x<0 || new_y<0 || new_x>=N || new_y>=N) { //파랑 -> >=N 이걸로 했어야 했는데, 착각함.
					reverseDir(horse);
					System.out.println("Out");
					move(horse, "B");
					 print();
					
					continue;
					
				}else if(colorBoard[new_x][new_y] == 2) { //파랑.
					reverseDir(horse);
					System.out.println("Blue");
					move(horse, "B");
					 print();
					
					continue;
				}
				else if(colorBoard[new_x][new_y] ==1) { //빨강
					move(horse, "R");
					System.out.println("Red");
					 print();
					
					continue;
					
				}else {//흰색
					move(horse, "W");
					System.out.println("White");
					 print();
					 
					continue;
					
				}
		
			}
		}
	}
	

	static void move(Horse target_horse, String color) {
		
		//현재 움직일 target_horse가 옴. target horse가 지금 어느 위치에 있는지 그리고 거기에 쌓여 있는 원판중 어디 위치에 있는지 확인 
		int x = target_horse.x;
		int y = target_horse.y;
		
		int new_x = target_horse.x +dx[target_horse.dir];
		int new_y = target_horse.y +dy[target_horse.dir];
		
		List<Horse> list_tartget = horseBoard[x][y];
		int index = findHorseIndex(list_tartget, target_horse);
		List<Horse> new_list = removeAndMakeTemp(index, list_tartget);
		System.out.println(new_list);
		
//		if(color.equals("R")) {
//			moveListRed(new_list, new_x, new_y);
//		}else if(color.equals("W")) {
//			moveList(new_list, new_x, new_y);
//		}else {
//			//파란색이거나 밖일때는 다시 한 번 판단해줘야 한다.
//			if(colorBoard[new_x][new_y] ==1) { //빨강
//				moveListRed(new_list, new_x, new_y);
//				return;
//				
//			}else if(colorBoard[new_x][new_y] ==0) {//흰색
//				moveList(new_list, new_x, new_y);
//				return;
//			}else {
//				//Blue이거나 밖이므로 stay
//				horseBoard[x][y].addAll(new_list);
//			}
//		}
		
		//새로 다시 판단해주는게 낫다.
		if(new_x<0 || new_y<0 || new_x>=N || new_y>=N || colorBoard[new_x][new_y] == 2) { 
			//여기서도 out되는건 막아줘야 하니까 이것부터.
			horseBoard[x][y].addAll(new_list);
		}
		else if (colorBoard[new_x][new_y] == 1) {
			moveListRed(new_list, new_x, new_y);
		}else if(colorBoard[new_x][new_y] == 0) {
			moveList(new_list, new_x, new_y);
		}
		
	}
	
	static int findHorseIndex(List<Horse> t_hosreList, Horse target_horse) {
		for(int i=0; i<t_hosreList.size(); i++) {
			if(t_hosreList.get(i) == target_horse) {
				return i;
			}
		}
		return 0;
	}
	
	static List<Horse> removeAndMakeTemp(int index, List<Horse> t_hosreList) {
		
		List<Horse> list_temp = new ArrayList<>();
		
		for(int i=index; i<t_hosreList.size(); i++) {
			list_temp.add(t_hosreList.remove(i)); //보드 판에서 제거 (제거하니까 index가 앞으로 땡겨진다) 이부분 또 문제네 
			i--; //이걸 같이 해줘야 함
		}

		return list_temp;		
	}
	
	static void moveList(List<Horse> willMoveList, int new_x, int new_y) {
		
		//말을 동기화 해준다 
		for(int i=0; i<willMoveList.size(); i++) {
			willMoveList.get(i).x = new_x;
			willMoveList.get(i).y = new_y;
		}
		
		horseBoard[new_x][new_y].addAll(willMoveList);
		
	}
	
	static void moveListRed(List<Horse> willMoveList, int new_x, int new_y) {
		
		//말을 동기화 해준다 
		for(int i=0; i<willMoveList.size(); i++) {
			willMoveList.get(i).x = new_x;
			willMoveList.get(i).y = new_y;
		}
		
		//역으로 바꾸는걸 어떻게 하지? 일단 빡구현 
		List<Horse> reversedHorse = new ArrayList<>();
		for(int i=willMoveList.size()-1; i>=0; i--) {
			reversedHorse.add(willMoveList.get(i));
		}
		horseBoard[new_x][new_y].addAll(reversedHorse);
		
	}

	
	static void reverseDir(Horse horse) {
		if(horse.dir==0) {
			horse.dir =1;
		}else if(horse.dir==1) {
			horse.dir=0;
		}else if(horse.dir==2) {
			horse.dir=3;
		}else if(horse.dir==3) {
			horse.dir=2;
		}
		//horse.dir = (horse.dir +2)%2;
	}
	
	
	static void print() {
		for(int k=0; k<horseBoard.length; k++) {
			for (int j=0; j<horseBoard[0].length; j++) {
				System.out.print(horseBoard[k][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}

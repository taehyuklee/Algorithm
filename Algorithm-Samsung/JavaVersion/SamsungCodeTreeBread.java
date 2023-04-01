import java.util.*;
import java.io.*;

class Person{
	
	int targetX;
	int targetY;
	int baseX;
	int baseY;
	
	public Person(int targetX, int targetY) {
		super();
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	@Override
	public String toString() {
		return "Person [targetX=" + targetX + ", targetY=" + targetY + "]";
	}
	
}

class Base{
	
	int baseX;
	int baseY;
	
	public Base(int baseX, int baseY) {
		super();
		this.baseX = baseX;
		this.baseY = baseY;
	}

	@Override
	public String toString() {
		return "Base [baseX=" + baseX + ", baseY=" + baseY + "]";
	}
	
}


public class Main {
	
	private static int M, N;
	private static int[] dx = {-1,0,0,1};
	private static int[] dy = {0,-1,1,0};
	private static int[][] board, board2;
	private static ArrayList<Person> personList = new ArrayList<>();
	private static ArrayList<Base> baseList = new ArrayList<>();

	public static void print(int[][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				System.out.print(array[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		board = new int[N][N];
		board2 = new int[N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = sc.nextInt();
				
				if(board[i][j] ==1) {
					baseList.add(new Base(i,j));
				}
			}
		}
		
		for(int i=0; i<M; i++) {
			personList.add(new Person(sc.nextInt()-1, sc.nextInt()-1));
		}
		
		System.out.println(personList);
		System.out.println(baseList);
		
		solution();
		
	}
	
	
	public static void solution() {
		
		//목표 편의점 -> 베이스캠프 setting해줘야함
		
		
		
		
		//if(사람이 있다면)
		
			//1번 본인이 가고싶은 방향으로 1칸 움직임
			
			
			//2번 해당편의점에 도달 그 칸은 막힘
		
		
		//if(대기중인 사람이 있다면)
		
			//3번 t<=m base로 이동 (남은 사람이 있으면)
		
	}
	
	
	
	
}

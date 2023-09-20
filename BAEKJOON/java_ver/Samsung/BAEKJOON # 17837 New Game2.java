import java.util.*;
import java.io.*;

class Horse{
	int x, y, dir;
	
	public Horse(int x, int y, int dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
}

public class Main {
	
	static int N, K;
	static int[][] colorBoard;
	static List<Horse>[][] horseBoard;
	static int[] dx = {0,0,-1,1}, dy = {1,-1,0,0};
	static List<Horse> horseList= new ArrayList<Horse>();

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		K = sc.nextInt();
		
		colorBoard = new int[N][N];
		horseBoard = new ArrayList[N][N];
		
		for(int i=0; i<N ; i++) {
			for(int j=0; j<N; j++) {
				colorBoard[i][j] = sc.nextInt();
			}
		}
		
		for(int i=0; i<K; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int dir = sc.nextInt()-1;
			Horse horse = new Horse(x,y,dir);
			horseBoard[x][y].add(horse);
			horseList.add(horse);
		}
		
		solution();
		
	}
	
	static void solution(){
		
		//말이 한 바퀴 돌아감

			for(int i=0 ;i<K; i++) {
				Horse horse = horseList.get(i);
				
				//미리 탐험
				int new_x = horse.x +dx[horse.dir];
				int new_y = horse.y +dy[horse.dir];
				
				//움직였을때 빨강이냐, 파랑이냐, 흰색이냐, 아님 벗어난거냐?
				if(colorBoard[new_x][new_y] == 2 || new_x<0 || new_y<0 || new_x>N || new_y>N) { //파랑
					reverseDir(horse);
					move(horse);
					continue;
					
				}else if(colorBoard[new_x][new_y] ==1) { //빨강

					
				}else {
			
					
				}
				
				
				horse.x = new_x; // 저장
				horse.y = new_y; // 저장
				
		}
			
		
	}

	static void move(Horse horse) {
		horseBoard[horse.x][horse.y].remove(horse); //기존 horse판에서 지워주고
		horse.x = horse.x +dx[horse.dir];
		horse.y = horse.y +dy[horse.dir];
		horseBoard[horse.x][horse.y].add(horse); //판에 넣어준다.
		
	}
	
	static void reverseDir(Horse horse) {
		horse.dir = (horse.dir +2)%4;
	}
	
}

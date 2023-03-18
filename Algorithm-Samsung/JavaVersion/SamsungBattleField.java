import java.util.*;
import java.io.*;

class Man{
	
	int dir;
	int stat;
	int score;
	int weapon;
	
	public int getDir() {
		return dir;
	}
	public int getAbility() {
		return stat;
	}
	public int getScore() {
		return score;
	}
	public int getWeapon() {
		return weapon;
	}
	
	public Man(int dir, int ability, int score, int weapon) {
		super();
		this.dir = dir;
		this.stat = ability;
		this.score = score;
		this.weapon = weapon;
	}
	
	@Override
	public String toString() {
		return "Man [dir=" + dir + ", stat=" + stat + ", score=" + score + ", weapon=" + weapon + "]";
	}
	
	public Man(Man man) {
		this.dir = man.dir;
		this.stat = man.stat;
		this.score = man.score;
		this.weapon = man.weapon;
	}
}


public class Main {
	
	static int N, M, K;
	static ArrayList<Integer> [][] gunField;
	static ArrayList<Man> [][] manField;
	static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
	
	public static void printGun(ArrayList<Integer> [][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				ArrayList<Integer> values = array[i][j];
				
				System.out.print(values + "   ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void printMan(ArrayList<Man> [][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				ArrayList<Man> values = array[i][j];
				
				System.out.print(values + "   ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		
		gunField = new ArrayList [N][N];
		manField = new ArrayList [N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				gunField[i][j] = new ArrayList<Integer>();
				manField[i][j] = new ArrayList<Man>();
				
				gunField[i][j].add(sc.nextInt());
				
			}
		}
		
//		printGun(gunField);
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int d = sc.nextInt();
			int s = sc.nextInt();
			manField[x][y].add(new Man(d, s, 0, 0));
		}
		
		printMan(manField);
		
		solution();
		
	}
	
	public static void solution() {
		
		
	}
	
	
}

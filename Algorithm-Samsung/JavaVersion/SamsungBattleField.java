import java.util.*;
import java.io.*;

class Player{
	
	int x, y;
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
	
	public Player(int dir, int ability, int score, int weapon, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.stat = ability;
		this.score = score;
		this.weapon = weapon;

	}
	
	
	
	@Override
	public String toString() {
		return "Player [x=" + x + ", y=" + y + ", dir=" + dir + ", stat=" + stat + ", score=" + score + ", weapon="
				+ weapon + "]";
	}
	
	public Player(Player player) {
		this.dir = player.dir;
		this.stat = player.stat;
		this.score = player.score;
		this.weapon = player.weapon;
		this.x = player.x;
		this.y = player.y;
	}
}


public class Main {
	
	static int N, M, K;
	static ArrayList<Integer> [][] gunField;
	static ArrayList<Player> [][] playerField;
	static ArrayList<Player> playerList = new ArrayList<Player>();
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
	
	public static void printMan(ArrayList<Player> [][] array) {
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				ArrayList<Player> values = array[i][j];
				
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
		playerField = new ArrayList [N][N];
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				
				gunField[i][j] = new ArrayList<Integer>();
				playerField[i][j] = new ArrayList<Player>();
				
				gunField[i][j].add(sc.nextInt());
				
			}
		}
		
//		printGun(gunField);
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int d = sc.nextInt();
			int s = sc.nextInt();
			Player player = new Player(d, s, 0, 0, x, y);
			playerField[x][y].add(player);
			playerList.add(player);
		}
		
//		printMan(playerField);
//		System.out.println(playerList);
		
		//test heap memory sharing
//		for(int i =0; i<manList.size(); i++) {
//			manList.get(i).score = 100;
//		}
//		printMan(manField);
		
		
		solution();
		
	}
	
	public static void solution() {
		
		//player 한명이 움직인다.
		
		Player player = playerList.get(0);
		
//		printMan(playerField);
//		System.out.println(playerList);
//		System.out.println();
//		
//		for(int i=0; i<15; i++) {
			boolean isPlayer = movePlayer(player);
			
			if(isPlayer) {
				//player가 있으면 싸움.
				
			}else {
				//player가 없으면 무기 획득.
				getWeapon(player);
				
			}
			
			
//		}
	}
	
	public static boolean movePlayer(Player player) {
		
		int playerX = player.x;
		int playerY = player.y;
		
		int newX = playerX + dx[player.dir];
		int newY = playerY + dy[player.dir];
		
		boolean checkPlayer = false;
		
		if(newX>=0 && newX<N && newY>=0 && newY<N) {
			
			//움직이려는 장소에 player 체크까지의 기능만 같이 해주기
			checkPlayer = checkPlayer(newX, newY);
			
			//list 에 있는 player 위치 업데이트. (객체 업데이
			player.x = newX; player.y = newY;

			//field에서 업데이트 field에 업데이트 하
			playerField[newX][newY].add(player); //움직인 좌표에 추
			playerField[playerX][playerY].remove(player); //기존의 맵에서 제거해주기.
		}else {
			//방향을 반대로 바꾼다. (반대로 바꾼다)
			player.dir = (player.dir +2)%4;
			
			//새로운 방향으로 간다.
			newX = playerX + dx[player.dir];
			newY = playerY + dy[player.dir];
			
			//움직이려는 장소에 player 체크까지의 기능만 같이 해주기
			checkPlayer = checkPlayer(newX, newY);
			
			//list 에 있는 player 위치 업데이트. (객체 업데이
			player.x = newX; player.y = newY;
			
			//field에서 업데이트 field에 업데이트 하
			playerField[newX][newY].add(player); //움직인 좌표에 추
			playerField[playerX][playerY].remove(player); //기존의 맵에서 제거해주기.
			
		}
		
		return checkPlayer;
		
	}
	
	public static boolean checkPlayer(int x, int y) {
		
		boolean isPlayer = false;
		if(playerField[x][y].size() !=0) {
			isPlayer = true;
		}
		
		return isPlayer;
	}
	
	public static void getWeapon(Player player) {
		int playerX = player.x;
		int playerY = player.y;
		
		ArrayList<Integer> weaponsList = gunField[playerX][playerY];
		
		int curWeapon = player.weapon;
		
		if(curWeapon ==0) {
			//무기가 없을때는
			int newWeapon = chooseWeapon(weaponsList);
			player.weapon = newWeapon;
		}else {
			//무기를 가지고 있을때,
			
			
		}
	}
	
	public static int chooseWeapon(ArrayList<Integer> weaponsList) {
		Collections.sort(weaponsList, Comparator.reverseOrder());
		int bestWeapon = weaponsList.get(0);
		weaponsList.remove(0); //뽑은 무기는 제거해준다 땅바닥에서.
		
		return bestWeapon;
	}
	
}

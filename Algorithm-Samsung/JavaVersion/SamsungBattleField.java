import java.util.*;
import java.io.*;

class Player{
	
	int x, y;
	int dir;
	int stat;
	int score;
	int weapon;
	int totStat;
	
	
	public int getDir() {
		return dir;
	}
	public int getStat() {
		return stat;
	}
	public int getScore() {
		return score;
	}
	public int getWeapon() {
		return weapon;
	}
	public int getTotStat() {
		return totStat;
	}
	
	public Player(int dir, int ability, int score, int weapon, int x, int y, int totStat) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.stat = ability;
		this.score = score;
		this.weapon = weapon;
		this.totStat = totStat;

	}
	

	@Override
	public String toString() {
		return "Player [x=" + x + ", y=" + y + ", dir=" + dir + ", stat=" + stat + ", score=" + score + ", weapon="
				+ weapon + ", totStat=" + totStat + "]";
	}
	public Player(Player player) {
		this.dir = player.dir;
		this.stat = player.stat;
		this.score = player.score;
		this.weapon = player.weapon;
		this.x = player.x;
		this.y = player.y;
		this.totStat = player.totStat;
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
				
				int weapons = sc.nextInt();
				if(weapons !=0)
				gunField[i][j].add(weapons);
				
			}
		}
		
		for(int i=0; i<M; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int d = sc.nextInt();
			int s = sc.nextInt();
			Player player = new Player(d, s, 0, 0, x, y, s+0);
			playerField[x][y].add(player);
			playerList.add(player);
		}
		
		solution();
		
	}
	
	public static void solution() {

		for(int i=0; i<K; i++) {
			
			for(int inx=0; inx<playerList.size(); inx++) {
				
				Player player = playerList.get(inx);
				
				//player가 움직인다.
				boolean isPlayer = movePlayer(player);
				
				if(isPlayer) {
					//player가 있으면 싸움.
					battle(player);
					
				}else {
					//player가 없으면 무기 획득.
					getWeapon(player);
					
				}

			}
		}
		
		//solution
		for(int i=0; i<playerList.size(); i++) {
			System.out.print(playerList.get(i).score + " ");
		}
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
		
		//현재 player가 가지고 있는 무
		int curWeapon = player.weapon;
		
		if(curWeapon ==0) {
			//무기가 없을때는
			int newWeapon = chooseWeapon(weaponsList);
			player.weapon = newWeapon;
			player.totStat = newWeapon + player.stat;
		}else {
			//무기를 가지고 있을때, 자기 무기도 넣어서 최고 무기를 뽑아낸다.
			weaponsList.add(curWeapon);
			
			int newWeapon = chooseWeapon(weaponsList);
			player.weapon = newWeapon;
			player.totStat = newWeapon + player.stat;
			
		}
	}
	
	public static int chooseWeapon(ArrayList<Integer> weaponsList) {
		
		int bestWeapon =0;
		
		if(weaponsList.size() !=0) {
			Collections.sort(weaponsList, Comparator.reverseOrder());
			bestWeapon = weaponsList.get(0);
			weaponsList.remove(0); //뽑은 무기는 제거해준다 땅바닥에서.
		}

		return bestWeapon;
	}
	
	public static void battle(Player player) {
		
		//두 플레이어가 싸움.
		int curX = player.x;
		int curY = player.y;
		
		ArrayList<Player> battlePlayer = playerField[curX][curY];
		
		//싸우는 것을 비교로 구현했다.
		Collections.sort(battlePlayer, Comparator.comparing(Player::getTotStat).thenComparing(Player::getStat).reversed());
		
		Player winnerPlayer = battlePlayer.get(0);
		Player loserPlayer = battlePlayer.get(1);
		
		//승자는 승점을 챙긴다.
		winnerPlayer.score += winnerPlayer.totStat - loserPlayer.totStat;
		
		//진 player는 총을 내려놓는다.
		int loserWeapon = loserPlayer.weapon;
		
		if(loserWeapon !=0) { //loser 무기가있을때
			gunField[curX][curY].add(loserWeapon);
			loserPlayer.totStat -= loserPlayer.weapon; //totStat에서 무기 점수 빼주
			loserPlayer.weapon =0; //무기도 제
		}
		
		//진 Player는 움직인다.
		loserMove(loserPlayer);
		
		//움직인 칸에서 무기를 얻는다.
		getWeapon(loserPlayer);
		
		//이긴 Player의 행동.
		winnerMove(winnerPlayer);
		
		
		
		
	}
	
	public static void loserMove(Player player) {

		int playerX = player.x;
		int playerY = player.y;
		
		boolean empty = false;
		
		while(!empty) {

			int newX = playerX + dx[player.dir];
			int newY = playerY + dy[player.dir];
			
			if(newX>=0 && newX<N && newY>=0 && newY<N && playerField[newX][newY].size()==0) {
				
				//list 에 있는 player 위치 업데이트. (객체 업데이
				player.x = newX; player.y = newY;
	
				//field에서 업데이트 field에 업데이트 하
				playerField[newX][newY].add(player); //움직인 좌표에 추
				playerField[playerX][playerY].remove(player); //기존의 맵에서 제거해주기.
				empty = true;
			}else {
				//방향을 반대로 바꾼다. (90도 회전해서 방향을 바꾼다)
				player.dir = (player.dir +1)%4;
				
			}
		}
		
	}
	
	
	public static void winnerMove(Player player) {

		int playerX = player.x;
		int playerY = player.y;
		
		getWeapon(player);
	}
}

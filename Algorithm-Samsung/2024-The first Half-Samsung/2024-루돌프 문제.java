/*
 * 1 ~ P번 - P 명의 산타들 
 * N X N 격자, 각 위치 (r,c) 좌 상단 (1,1) 
 * M개의 Turn에 걸쳐 진행, 루돌프 산타 한 번씩 움직인다. 
 * 
 * 루돌프 1번 무빙
 * 1번 산타 ~ P번 산타 순서대로 움직인다. (기절, 격자 밖으로 나간 산타는 움직이지 않는다) 
 * 
 * 게임판 두 격자 사이의 거리 (r1-r2)^2 + (c1-c2)^2으로 계산한다. 
 * 
 * --------------------------------------------------
 * 게임의 구성 
 * 1. 루돌프의 움직임 
 *   1.1 루돌프는 가장 가까운 산타를 향해 1칸 돌진한다 ** (단, 게임에서 탈락하지 않은 산타중 선택해야 한다)
 *   1.2 만약 가까운 산타가 2명 이상이라면, r좌표가 큰 -> c좌표가 큰  순서대로 sorting
 *   1.3 루돌프는 8방향 중 하나로 돌진할 수 있다.
 * 
 * 2. 산타의 움직임
 *      산타는 1번부터 P번까지 순서대로 움직인다. 
 *      기절해 있거나 이미 게임에서 탈락한 산타는 움직일 수 없다
 *  2.1 산타는 루돌프에게 거리가 가장 가까워지는 방향으로 1칸 이동한다.
 *  2.2 다른 산타가 있는 칸이나 게임판 밖으로는 움직일 수 없다. 
 *  2.3 만약 못움직이면 움직이지 않는다
 *  2.4 움직일 수 있는 칸이 있더라도 만약 루돌프로부터 멀어진다면 움직이지 않는다
 *  2.5 상하좌우 -> 상우하좌 우선순위로 움직인다.
 * 
 * 
 * 3. 충돌
 *      산타와 루돌프가 같은 칸에 있으면 충돌 이벤트 발생
 *  3.1 CASE1) 루돌프가 산타로 향했을때 
 *      -  산타는 C만큼 점수를 얻게 된다. - 동시에 루돌프가 이동해온 방향으로 C만큼 밀려난다 (C 루돌프의 힘) 
 *      CASE2) 산타가 루돌프한테 향했을때
 *      -  산타는 D만큼 점수를 얻게 된다. - 자신이 이동해온 반대 방향으로 D만큼 밀려난다 (D 산타의 힘)
 *  3.2 날아갈때는 충돌이 일어나지 않는다. (정확히 그 위치에 도달한다)
 *  3.3 만약 밀려난 위체가 게임판 밖이라면 산타는 탈락
 *  3.4 밀려난 칸에 다른 산타가 존재시 상호작용이 ㅂ라생한다
 *  
 *  상호작용이란?
 *   산타가 날아간 칸에 다른 산타가 있다면 해당 위치에 있는 다른 산타는 1칸 (날아온 방향)으로 밀려나가게 된다. 
 *   만약, 그 옆에 또 산타가 있다면 연쇄적으로 1칸씩 밀려나는 것을 반복하게 된다. 게임판 밖으로 밀려나가면 마찬가지로 탈락이 된다.
 *   
 *  기절 효과 
 *   산타는 충돌 후 기절을 하게 된다. -> 충돌 시점이 k번째였다면 (k+1)번째 턴까지 기절하고 (k+2)번째 턴부터 다시 정상 상태가 됨
 *   기절한 산타는 움직일 수 없다.
 *   루돌프는 기절한 산타를 돌진 대상으로 선택할 수 있다
 *   
 *  게임 종료
 *   M번의 턴에 걸쳐 루돌프, 산타가 순서대로 움직인다
 *   만약 P명의 산타가 모두 게임에서 탈락하면, 즉시 게임이 종료
 *   매 턴 이후 아직 탈락하지 않은 산타들에게 1점씩 추가로 부여한다.
 *   
 *   게임이 끝났을 때 각 산타가 얻은 최종 점수를 구하는 프로그램을 작성.
 * 
 * */

import java.util.*;

class Deer{
	int x, y, dir;
	
	public Deer(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "x : " + x + " y : " + y + " | \n";
	}
}

class Santa{
	int x, y, dir, num, distance, score, stun;
	boolean out;
	
	public Santa(int num, int x, int y) {
		this.num = num;
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "num : " + num + " x : " + x + " y : " + y + " out: "+ out + " score: "+ score+ " |";
	}
	
	public void clearDist() {
		this.distance = 0;
	}
}

public class Main {
	
	static int N, M, P, C, D;
	static int[] dx = {-1,0,1,0,-1,-1,1,1}, dy= {0,1,0,-1,-1,1,1,-1};
	static Deer deer;
	static List<Santa>[][] board;
	static List<Santa> playerList;
	
	public static void print2D(List<Santa>[][] board) {
		System.out.println("deer " + deer);
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				if(i==deer.x && j==deer.y){
					System.out.print(-1 + " ");
				}else if(board[i][j].size() == 0) {
					System.out.print(0 + " ");
				}
				else {
					System.out.print(board[i][j].get(0).num + " ");
				}
				
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt(); //격자 크기
		M = sc.nextInt(); //게임 턴 수
		P = sc.nextInt(); //산타 수
		C = sc.nextInt(); //루돌프의 힘
		D = sc.nextInt(); //산타의 힘
		
		board = new ArrayList[N][N];
		playerList = new ArrayList<>();
		
		int deerX = sc.nextInt()-1;
		int deerY = sc.nextInt()-1;
		
		deer = new Deer(deerX, deerY);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				board[i][j] = new ArrayList<>();
			}
		}
		
		for(int i=0; i<P; i++) {
			int santaOrder = sc.nextInt();
			int santaX = sc.nextInt()-1;
			int santaY = sc.nextInt()-1;
			Santa santa = new Santa(santaOrder, santaX, santaY);
			board[santaX][santaY].add(santa);
			playerList.add(santa);
		}
		
		solution();
		
	}
	
	public static void solution() {
		
		for(int turn=1; turn<=M; turn++) {
			System.out.println("\n" +turn + "번째 턴입니다");
			System.out.println("루돌프 정보 : " + deer);
			print2D(board);

			//루돌프의 움직임
			moveDeer();
			//루돌프가 산타 충돌 확인
			Santa deerToSanta = checkChungDol(null, deer.x, deer.y);
			if(deerToSanta !=null) {
				boom(deerToSanta, 0);
			}
			System.out.println("루돌프 움직인 이후 ");
			print2D(board);
			
			//산타의 움직임
			moveSanta();
			//산타가 루돌프 충돌 확인 (이건 moveSanta내부로 들어가야 한다 player가 많기 때문에)
			System.out.println("산타 움직인 이후 ");
			print2D(board);
					
			//점수주기 및 스턴 1턴씩 감소시켜주기
			plusOneScoreAndMinusStun();


//			System.out.println("장외 플레이어 확인");
			System.out.println(playerList);
			for(int i=0; i<playerList.size(); i++) {
				System.out.print(playerList.get(i).score + " ");
			}
			System.out.println();
			
			//마지막 종료조건 체크
			if(checkEnd()) break;

		}
		
		//answer
		orderSanta();
		for(int i=0; i<playerList.size(); i++) {
			System.out.print(playerList.get(i).score + " ");
		}
		
	}
	
	/* 메인 로직 */
	public static void boom(Santa target, int bang) {
		if(bang ==0) {
			//루돌프가 산타 박기
//			System.out.println("루돌프 산타박기");
			
			//1. 점수받기
			target.score += C; //루돌프의 힘만큼 점수 주기
			target.stun =2; //기절해 있는 동안 또 박으면 다시 초기화 되는지 확신이 안들어 
			
			//2. 밀려나기
			int newX = target.x + dx[deer.dir]*C;
			int newY = target.y + dy[deer.dir]*C;
			
			board[target.x][target.y].remove(target);
			
			//장외
			if(newX<0 || newX>=N || newY<0 || newY>=N) {
//				System.out.println("new  " + newX + " " + newY + " \n" + target );
				target.x = newX; //여기 장외라고 생각해서 상태 update안해줬다가 개 고생함.
				target.y = newY;
				target.out = true;
				return;
			}else {
				//board상태 업데이트
//				System.out.println("들어오긴 함?");
				target.x = newX;
				target.y = newY;
				board[newX][newY].add(target);
//				System.out.println(target);
			}
			//상호작용 효과 확인 
			Santa target2 = checkChungDol(target, newX, newY); //여기도 자기 자신;;
			if(target2==null) return;
			knockBack(target2, deer.dir);
			
		}else {
			//산타가 루돌프 박기
			//1. 점수받기
//			System.out.println("산타가 루돌프 박기 확인해보기 " + target);
			target.score += D; //산타의 힘만큼 점수 주기
			target.stun = 2;
			
			//2. 밀려나기
			
			int newDir = 0;
			//반대로 방향 정하기 
			if(target.dir==0) {
				newDir =2;
			}else if(target.dir==1) {
				newDir = 3;
			}else if(target.dir==2) {
				newDir = 0;
			}else {
				newDir =1;
			}
			
			int newX = target.x + dx[newDir]*D;
			int newY = target.y + dy[newDir]*D;
			
			board[target.x][target.y].remove(target);
			
			if(newX<0 || newX>=N || newY<0 || newY>=N) {
				target.x = newX;
				target.y = newY;// 여기서도 장외인거 인식할려면;;
				target.out = true;
				return;
			}else {
				//board상태 업데이트
				target.x = newX;
				target.y = newY;
				board[newX][newY].add(target);
			}
		
			//상호작용 효과 확인 
			Santa target2 = checkChungDol(target, newX, newY);
			if(target2==null) return;
			knockBack(target2, newDir);
		}
		
	}
	
	public static void knockBack(Santa backSanta, int dir) {
		//넉백당하는 산타를 옮겨준다 (한칸씩 움직여준다.
		int newX = backSanta.x + dx[dir];
		int newY = backSanta.y + dy[dir];
		board[backSanta.x][backSanta.y].remove(backSanta);

		//장외일 경우 out
		if(newX<0 || newX>=N || newY<0 || newY>=N) {
			backSanta.out = true;
		}else {
			//board상태 업데이트
			backSanta.x = newX;
			backSanta.y = newY;
			board[newX][newY].add(backSanta);
		}
		
		//그 뒤에 상호작용 하는지 recursive하게 확인 
		Santa target2 = checkChungDol(backSanta, newX, newY); //아 앞에서 update시켜서 자기 자신을 인식했다;;
//		System.out.println(target2);
		if(target2==null) return;
		knockBack(target2, dir);
		
	}
	
	public static void moveSanta() {
		
		//산타 한 번 정렬하고
		orderSanta();
		
		for(int i=0; i<playerList.size(); i++) {
			Santa player = playerList.get(i);
			
			//기절 또는 탈락했으면 참여 불가능
			if(player.out == true || player.stun != 0) continue;
//			System.out.println("player 확인 하기 " + player);
			//루돌프 움직일 방향 정해서 움직이기 
			List<int[]> dirList = new ArrayList<>();
			int oldDistnace = (deer.x - player.x)*(deer.x - player.x) + (deer.y - player.y)*(deer.y - player.y);

			for(int dir=0; dir<4; dir++) {
				int newX = player.x + dx[dir];
				int newY = player.y + dy[dir];
				int newDistance = (deer.x - newX)*(deer.x - newX) + (deer.y - newY)*(deer.y - newY);
				
				//예외 로직 처리
				if(newX<0 || newX>=N || newY<0 || newY>=N) continue; //장외 처리
				if(checkSanta(newX, newY)) continue;  //다른 산타가 있는 곳은 불가능
				if(measureDist(newDistance, oldDistnace)) continue;//기존의 거리보다 멀어지는지 확인 로직
				
				int[] newDir = new int[] {newX, newY, newDistance, dir};
				dirList.add(newDir);
			}
		
			if(dirList.size() ==0) continue; //아무것도 없다면 그냥 안움직이고 끝낸다. 여기서 return해버려서 실수했음.
			
			Collections.sort(dirList, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[2] == o2[2]) {
						return o1[3] - o2[3]; //dir낮은 순서대로 //앞에서 이미 섞어서 상우하좌 해논게 의미가 없어짐
					}
					return o1[2]- o2[2];
				}	
			});
			int[] targetDir = dirList.get(0);
			
			//board상에서 기존 palyer 지워주고 상태 update
			player.dir = targetDir[3];
			board[player.x][player.y].remove(player);
			
			//산타의 움직임
			player.x = targetDir[0];
			player.y = targetDir[1];
			
			//새로운 위치에 넣어주기
			board[player.x][player.y].add(player);
			
			//루돌프와 충돌 확인하기
			Santa santaToDeer = checkChungDol(null, deer.x, deer.y);
			if(santaToDeer !=null) {
				boom(santaToDeer, 1);
			}
	
		}
	}
	
	public static void moveDeer() {
		//거리 측정
		for(int i=0; i<playerList.size(); i++) {
			Santa santa = playerList.get(i);
			
			//거리 재는 로직
			int distance = (deer.x - santa.x)*(deer.x - santa.x) + (deer.y - santa.y)*(deer.y - santa.y);
			santa.distance = distance;
		}
		
		Collections.sort(playerList, new Comparator<Santa>() {
			@Override
			public int compare(Santa o1, Santa o2) {
				
				if(o1.distance == o2.distance) {
					if(o1.x == o2.x) {
						return o2.y - o1.y;
					}
					return o2.x - o1.x;
				}	
				return o1.distance - o2.distance;
			}
			
		});
		
		//santa target 정하기
		Santa target = null;
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).out == true) {
				continue;
			}
			target = playerList.get(i);
			break;
		}
		
		System.out.println("target player 확인 : " + target);
		
		//루돌프 움직일 방향 정해서 움직이기 
		List<int[]> dirList = new ArrayList<>();
		for(int dir=0; dir<8; dir++) {
			int newX = deer.x + dx[dir];
			int newY = deer.y + dy[dir];
			
			int distance = (newX - target.x)*(newX - target.x) + (newY - target.y)*(newY - target.y);
			int[] newDir = new int[] {newX, newY, distance, dir};
			dirList.add(newDir);
		}
		
		Collections.sort(dirList, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2]- o2[2];
			}	
		});
		int[] targetDir = dirList.get(0);
		
		//루돌프의 움직임 상태 update
		deer.x = targetDir[0];
		deer.y = targetDir[1];
		deer.dir = targetDir[3];
		
		clearDist();
		
	}
	
	public static void plusOneScoreAndMinusStun() {
		for(int i=0; i<playerList.size(); i++) {
			Santa santa = playerList.get(i);
			if(santa.out != true) {
				santa.score+=1;
			}
			if(santa.stun >0) {
				santa.stun -=1;
			}
		}
	}

	public static boolean checkEnd() {
		int cnt=0;
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).out == true) {
				cnt++;
			}
		}
		if(cnt == P) {
			return true;
		}
		return false;
	}
	
	/* 공통 유틸 */
	public static void orderSanta() {
		Collections.sort(playerList, new Comparator<Santa>() {
			@Override
			public int compare(Santa o1, Santa o2) {
				return o1.num - o2.num;
			}
		});
	}
	
	public static void clearDist() {
		for(int i=0; i<playerList.size(); i++) {
			playerList.get(i).distance = 0;
		}
	}
	
	public static Santa checkChungDol(Santa target, int x, int y) {
		for(int i=0; i<playerList.size(); i++) {
			if(playerList.get(i).x == x && playerList.get(i).y == y && playerList.get(i) != target) {
				return playerList.get(i);
			}
		}
		return null;
	}
	
	/* 예외 처리 */
	public static boolean checkSanta(int newX, int newY) {
		for(int i=0; i<playerList.size(); i++) {
			Santa sanata = playerList.get(i);
			if(sanata.x == newX && sanata.y ==newY) {
				return true;
			}
		}
		return false;
	}

	public static boolean measureDist(int newDistance, int oldDistance) {
		if(oldDistance<newDistance) { //루돌프로부터 가까워질 수 있는 방법이 없다면 (이 부분 다시 체크)
			return true;
		}
		return false;
	}

}

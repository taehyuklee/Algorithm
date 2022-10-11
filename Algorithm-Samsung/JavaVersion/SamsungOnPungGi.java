import java.util.*;
import java.io.*;

class Blower{
	int x, y, dir;
	
	public Blower(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "Blower [x=" + x + ", y=" + y + ", dir=" + dir + "]";
	}

	
}

class Wall{
	int x, y;
	int nx, ny;
	int t;
	
	public Wall(int x, int y, int t) {
		this.x = x;
		this.y = y;
		this.t = t;
	}

	@Override
	public String toString() {
		return "Wall [x=" + x + ", y=" + y + ", nx=" + nx + ", ny=" + ny + "]";
	}
	
	
}

class Node{
	int x, y, dir, temp;

	public Node(int x, int y, int dir, int temp) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.temp = temp;
	}

}

class CheckPoint{
	int x, y;
	
	public CheckPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
	
public class Main {
	
	static int choco;
	
	static int[] dx = {0,0,-1,1};
	static int[] dy = {1,-1,0,0};
	
	static int[][] map;
	static int[][] subMap;
	static List<Blower> blowerList= new ArrayList<>();
	static List<Wall> wallList= new ArrayList<>();
	static List<CheckPoint> checkList = new ArrayList<>();
	static boolean[][][][] wallArr;
	
	static int R,C,K;
	
	//for bfs spread
	static int[][] dxx = {{0,-1,1},{0,1,-1},{-1,-1,-1},{1,1,1}};
	static int[][] dyy = {{1,1,1},{-1,-1,-1},{0,-1,1},{0,1,-1}};
	
	public static void print2D(int[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void mapAdd() {
		
		for(int i=0; i<subMap.length; i++) {
			for(int j=0; j<subMap[0].length; j++) {
				map[i][j] = subMap[i][j] + map[i][j];
			}
		}
		
	}
	
	public static void clearMap() {
		for(int i=0; i<subMap.length; i++) {
			for(int j=0; j<subMap[0].length; j++) {
				subMap[i][j] = 0;
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		//Input
		Scanner sc = new Scanner(System.in);
		R = sc.nextInt();
		C = sc.nextInt();
		K = sc.nextInt();
		
		//System.out.println(R +" " + C + " " + K);
		
		map = new int[R][C];
		subMap = new int[R][C];
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				int check = sc.nextInt();
				if(check !=0 && check !=5) {
					blowerList.add(new Blower(i, j, check-1));
				}
				
				if(check ==5) {
					checkList.add(new CheckPoint(i,j));
				}
			}
			
		}
		
//		print2D(map);
//		System.out.println(blowerList);
		
		int wallNum = sc.nextInt();
		
		for(int i=0; i<wallNum; i++) {
			int x = sc.nextInt()-1;
			int y = sc.nextInt()-1;
			int t = sc.nextInt();
		
			wallList.add(new Wall(x, y, t));

		}
		
		//System.out.println(wallList);
		wallArr = new boolean[R][C][R][C];
		
		for(int i=0; i<wallList.size(); i++) {
			
			Wall wall = wallList.get(i);
			if(wall.t == 0) {
				wallArr[wall.x][wall.y][wall.x-1][wall.y] = true;
				wallArr[wall.x-1][wall.y][wall.x][wall.y] = true;
				wall.nx = wall.x-1;
				wall.ny = wall.y;
			}else {
				wallArr[wall.x][wall.y][wall.x][wall.y+1] = true;
				wallArr[wall.x][wall.y+1][wall.x][wall.y] = true;
				wall.nx = wall.x;
				wall.ny = wall.y+1;
			}
			
		}	
		//System.out.println(wallList);
		
		
		solution();
		
	}
	
	public static void solution() {
		
		//Loop
		while(true) {
			//bfs(모든 온풍기 확산 시키기)
			for(int i=0; i<blowerList.size(); i++) {
				Blower blower = blowerList.get(i);
				bfs(blower);	
				mapAdd();
				clearMap();
			}
			
			//print2D(map);
			
			//온도 조절하기
			controlTemp();
	//		System.out.println();
			//print2D(map);
			
			//바깥쪽 -1도씩 깎기
			minusTemp();
			//System.out.println();
			//print2D(map);
			
			
			//초콜릿 먹기
			choco++;
			if(choco>100) {
				break;
			}
			
			
			//온도 체크하기
			boolean go = true;
			for(int i=0; i<checkList.size(); i++) {
				CheckPoint chekcPoint = checkList.get(i);
				if(map[chekcPoint.x][chekcPoint.y]<K) {
					go = false;
				}
			}
			
			if(go == true) {
				break;
			}
		
		}

		System.out.println(choco);
		//print2D(map);
		
	}
	
	public static void bfs(Blower blower) {
		
		//visit구하기
		boolean[][] visit = new boolean[R][C];

		//시작점 구하기
		int oldX = blower.x + dx[blower.dir];
		int oldY = blower.y + dy[blower.dir];
		
		visit[oldX][oldY] = true;
		
		Queue<Node> q = new LinkedList<>();	
		int temp =5;
		q.add(new Node(oldX, oldY, blower.dir, temp));
		subMap[oldX][oldY] =5;
		
		while(!q.isEmpty()) {
			
			Node node = q.poll();
			visit[node.x][node.y] = true;
			
			for(int i=0; i<3; i++) {
				
				int nx = node.x + dxx[blower.dir][i];
				int ny = node.y + dyy[blower.dir][i];
				int newTemp = node.temp-1;
				
				if(newTemp>=1) {
					if(nx>=0 && nx<R && ny>=0 && ny<C) {
						if(visit[nx][ny] == false) {
							if(isWall(node.x, node.y, nx, ny, blower.dir) == false) {
								subMap[nx][ny] = newTemp;
								q.add(new Node(nx, ny, blower.dir, newTemp));
							}
							
						}
					}
				}
				
				
	
			}
			
		}
	}
	
	
	public static boolean isWall(int x, int y, int nx, int ny, int dir) {
		
		if(wallArr[x][y][nx][ny] == true) {
			
			return true;
		}else{ //위에는 4방향 공통이지만 여기서부터는 방향에 따라 달라진다.
			if(dir ==0) { //오른쪽
				if(wallArr[x][y][nx][ny-1] == true || wallArr[nx][ny-1][nx][ny] == true) {
					return true;
				}
			}else if(dir ==1) { // 왼쪽
				if(wallArr[x][y][nx][ny+1] == true || wallArr[nx][ny][nx][ny+1] == true) {
					return true;
				}
			}else if(dir ==2) { //위
				if(wallArr[x][y][nx+1][ny] == true || wallArr[nx][ny][nx+1][ny] == true) {
					return true;
				}
			}else if(dir ==3) { //아래
				if(wallArr[x][y][nx-1][ny] == true || wallArr[nx][ny][nx-1][ny] == true) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	public static void controlTemp() {
		
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				
				for(int d=0; d<4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(nx>=0 && nx<R && ny>=0 && ny<C) {
						if(map[nx][ny]<map[i][j]) {
							if(wallArr[i][j][nx][ny] == false) { // 벽을 안막았었음 여기 실수
								subMap[i][j] -= (int) (map[i][j]-map[nx][ny])/4;
								subMap[nx][ny] += (int) (map[i][j]-map[nx][ny])/4;	
							}
						}
					}
					
				}
			}
		}
		
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				map[i][j] += subMap[i][j];
			}
		}
		
		clearMap();
	}
	
	
	
	public static void minusTemp() {
		
		for(int i=0; i<R; i++) {
			if(map[i][0]-1 >=0) {
				map[i][0] -=1;
			}
			if(map[i][C-1]-1 >=0) {
				map[i][C-1] -=1;
			}
		}
		
		for(int j=1; j<C-1; j++) {
			if(map[0][j] -1 >=0) {
				map[0][j] -=1;
			}
			if(map[R-1][j] -1 >=0) {
				map[R-1][j] -=1;
			}
		}
		
		
	}
	
	
}	
	

import java.util.*;
import java.io.*;

class Fish implements Cloneable{
	
	int id, dir, x, y;
	boolean alive;

	public Fish(int id, int dir, int x, int y, boolean alive) {
		this.id = id;
		this.dir = dir;
		this.x = x;
		this.y = y;
		this.alive = alive;
	}
	
	//복사를 위한 생성자
	public Fish(Fish targetFish) {
		this.id = targetFish.id;
		this.dir = targetFish.dir;
		this.x = targetFish.x;
		this.y = targetFish.y;
		this.alive = targetFish.alive;
	}

	@Override
	public String toString() {
		return "Fish [id=" + id + ", dir=" + dir + ", x=" + x + ", y=" + y + ", alive=" + alive + "]";
	}

}

class Shark implements Cloneable{
	int dir, eat, x, y;
	
	public Shark(int dir, int eat, int x, int y) {
		this.dir = dir;
		this.eat = eat;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Shark [dir=" + dir + ", eat=" + eat + ", x=" + x + ", y=" + y + "]";
	}
}


public class Main{
	
//	static List<Fish> fishList = new ArrayList<>();
	
	//direction
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,-1,-1,-1,0,1,1,1};
	static int maxSum = 0;
	static int size=0;
	
	static int[][] fishes = new int[4][4];
	
	public static Fish findByCoord(int x, int y, List<Fish> fishList) {
		for(int i=0; i<fishList.size(); i++) {
			if(fishList.get(i).x == x && fishList.get(i).y ==y) {
				return fishList.get(i);
			}
		}
		return null;
	}
	
	public static Fish findById(int id, List<Fish> fishList) {
		for(int i=0; i<fishList.size(); i++) {
			if(fishList.get(i).id == id) {
				return fishList.get(i);
			}
		}
		return null;
	}
	
	public static void print2DArray(List<Fish> fishList) {
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				if(findByCoord(i,j, fishList).alive ==true) {
					fishes[i][j] = findByCoord(i, j, fishList).id;
				}else {
					fishes[i][j] = -1;
				}
			}
		}
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				System.out.print(fishes[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public static List<Fish> deepCopyArrayList(List<Fish> arrayList) {
		
		List<Fish> copyList = new ArrayList<Fish>();
		for(Fish fish: arrayList) {
			Fish copiedFish = new Fish(fish); //이 부분때문에 상당히 오래걸렸다 딥카피가 안돼서.. 복사 생성자 방법이 있네
			copyList.add(copiedFish);
		}
		return copyList;
	}

	
	public static void main(String[] args) throws IOException {
		
		List<Fish> fishList = new ArrayList<>();
		
		//input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int a,b;
		
		for(int i=0; i<4; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<4; j++) {
				a = Integer.parseInt(st.nextToken());
				b = Integer.parseInt(st.nextToken());
				fishList.add(new Fish(a, b-1, i, j, true));
			}
		}
		
		//System.out.println(fishList);
		
		Collections.sort(fishList, new Comparator<Fish>() {
			@Override
			public int compare(Fish o1, Fish o2) {
				return o1.id - o2.id;
			}
		});
		
		//System.out.println(fishList);
		
		//solution
		solution(fishList);
		
		
	}
	
	
	public static void solution(List<Fish> fishList) {
		
		//상어가 먹는다
		//초기 조건 (0,0)
		Fish firstFish = findByCoord(0,0,fishList);
		Shark shark = new Shark(firstFish.dir, firstFish.id, 0, 0);
		firstFish.alive = false;

		List<Fish> copiedFish = deepCopyArrayList(fishList);
		
		//물고기가 이동한다
		for(int i=0; i<fishList.size(); i++) {
			if(fishList.get(i).alive == true) {
				//System.out.println(i+1 + "번째 입니다");
				moveFish(fishList.get(i), shark, fishList);
				
				//System.out.println();
			}
		}

		//상어가 이동한다.
		size = 0;
		dfs(fishList, shark, size);
		System.out.println(maxSum);

		
	}
	
	public static void dfs(List<Fish> originFishList, Shark shark,int size) {

		int nx, ny;
		
        if (maxSum < shark.eat) {
            maxSum = shark.eat;
        }
		
		for(int dist=1; dist<4; dist++) {

			//복제한다 (3개의 경우의수마다 모두 deepCopy한 경우를 기억해야한다)
			//List<Fish> copiedFish = deepCopyArrayList(originFishList);
			//여기서 처음에 copiedFish를 맨 마지막에 놨었는데 위치를 여기에 놔야 한다. 3가지 경우의 수 모두 copy해서 다르게 갈거니까 여기서 copy해서 보내는게 맞다.
			//아래 원래 originFishList로 옮겨다녔었는데 그러면 안됨.

			nx = shark.x + dx[shark.dir]*dist;
			ny = shark.y + dy[shark.dir]*dist;

			//상어가 먹는다
			
			if(nx>=0 && nx<4 && ny>=0 && ny<4) {
				if(findByCoord(nx,ny,originFishList).alive ==true) {

					List<Fish> copiedFish = deepCopyArrayList(originFishList);
					
					Fish fish= findByCoord(nx,ny,copiedFish);
					
					Shark newShark = new Shark(fish.dir, shark.eat + fish.id, nx, ny);
					fish.alive = false;
					//System.out.println(copiedFish);
					
					//print2DArray(copiedFish);
					//System.out.println();
	
					//물고기가 이동한다.
					for(int i=0; i<copiedFish.size(); i++) {
						if(copiedFish.get(i).alive == true) {
							moveFish(copiedFish.get(i), newShark, copiedFish); // 여기 shark로 되어있었음 -> newShark이어야 함
						}
					}
					size+=1;
					//System.out.println("추적" +" " + size);
					//System.out.println(shark.eat + " " + fish.id);
					
					dfs(copiedFish, newShark, size);

				}
			}

		}

	}
	
	public static void moveFish(Fish fish, Shark shark, List<Fish> fishList) {
		
		int nx, ny;
		int newDir;
		
		for(int dir=0; dir<8; dir++) {
			newDir = (fish.dir + dir)%8;
			nx = fish.x + dx[newDir];
			ny = fish.y + dy[newDir];
			
			//이동하지 못할 조건
			if(nx>3 || nx<0 || ny>3 || ny<0) {
				continue;

			}else if(shark.x == nx && shark.y ==ny){
				continue;
				
			}else {
				//이동할수 있는 조건
				int tempX, tempY;
				fish.dir = newDir;
				Fish targetfish = findByCoord(nx, ny, fishList);
				//교환 -> 내가 실수한 부분 -> 새로운 방향으로 집어 넣어야 하는데 
				tempX = fish.x; tempY = fish.y; 
				fish.x = targetfish.x; fish.y = targetfish.y;
				targetfish.x = tempX; targetfish.y = tempY; 

				return;
			}
			
		}

	}
	
}

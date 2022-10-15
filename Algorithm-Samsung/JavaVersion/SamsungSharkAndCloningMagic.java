import java.util.*;
import java.io.*;


class Shark{
	int x, y, eat;
	
	public Shark(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Shark [x=" + x + ", y=" + y + ", eat=" + eat + "]";
	}
	
}

class Fish{
	int x, y, dir;

	public Fish(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "Fish [x=" + x + ", y=" + y + ", dir=" + dir + "]";
	}
	
	public Fish(Fish fish) {
		this.x = fish.x;
		this.y = fish.y;
		this.dir = fish.dir;
	}
}

public class Main {
	
	static int[][] smellGrid = new int[4][4];
	static boolean[][] sharkGrid = new boolean[4][4];
	
	static Queue<Fish> fishQueue = new LinkedList<Fish>();
	static List<Fish>[][] fishGrid = new List [4][4];
	static List<String> pathList = new ArrayList<>();
	
	static int[] dfx = {0,-1,-1,-1,0,1,1,1};
	static int[] dfy = {-1,-1,0,1,1,1,0,-1};
	
	static int[] dsx = {-1,0,1,0};
	static int[] dsy = {0,-1,0,1};
	
	static int M;
	static int S;
	static int max=0;
	
	public static void print2D(int[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print2D(List<Fish>[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print2D(boolean[][] arr) {
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print1D(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		//Input
		Scanner sc = new Scanner(System.in);
		M = sc.nextInt();
		S = sc.nextInt();
		
		//2D Array만들기
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				fishGrid[i][j] = new ArrayList<Fish>();
			}
		}
		
		for(int i=0; i<M; i++) {
			int fx = sc.nextInt();
			int fy = sc.nextInt();
			int fd = sc.nextInt();
			Fish fish = new Fish(fx-1, fy-1, fd-1);
			fishGrid[fx-1][fy-1].add(fish);
			fishQueue.add(fish);
		}
		
		//상어에 대한 정보
		int sx = sc.nextInt();
		int sy = sc.nextInt();
		Shark shark = new Shark(sx-1, sy-1);
		sharkGrid[sx-1][sy-1] = true;
		
		
		//Solution
		solution(shark);
	}

	
	public static void solution(Shark shark) {
		
		//Loop
		for(int s=0; s<S; s++) {
			//물고기 복제
			List<Fish>[][] copied = copyMagic(fishGrid);			
			
			//물고기 이동
			List<Fish>[][] movedMap =moveFish(fishGrid, shark);	
			
			//상어 이동 패스 구하기 (최적 패스)
			boolean[][] visit = new boolean[4][4];
			getOptPath(movedMap, shark, visit, 0, "");
			
			//정렬해주기
			Collections.sort(pathList);
			String optPath = pathList.get(0);
			int[] optPathArr = new int[3];
			//parsing하기
			for(int i=0; i<optPath.length(); i++) {
				char p = optPath.charAt(i);
				int pInt = Integer.parseInt(optPath.valueOf(p));
				optPathArr[i] = pInt;
			}		
		
			//상어 이동하기 
			moveShark(movedMap, optPathArr, shark);

			//냄새 조정하기 
			reduceSmell(smellGrid);
			
			//복제 더하기 (return fishGrid에 합쳐져서 나온다)
			copyAdd(movedMap, copied);
			pathList.clear();
			max = 0;

		}

	
		//답 구하기
		System.out.println(getAnswer(fishGrid));
	}
	
	public static int getAnswer(List<Fish>[][] fishGrid) {
		int sum=0;
		for(int i=0; i<fishGrid.length; i++) {
			for(int j=0; j<fishGrid[0].length; j++) {
				sum += fishGrid[i][j].size();

			}
		}
		return sum;
	}
	
	public static void copyAdd(List<Fish>[][] movedMap, List<Fish>[][] copied) {
		
		//넣을 곳을 초기화 하고
		init(fishGrid);

		for(int i=0; i<smellGrid.length; i++) {
			for(int j=0; j<smellGrid[0].length; j++) {
				movedMap[i][j].addAll(copied[i][j]);
				fishGrid[i][j].addAll(movedMap[i][j]);
			}
		}

		init(copied);
		init(movedMap);

	}
	
	
	
	public static void reduceSmell(int[][] smellGrid) {
		
		for(int i=0; i<smellGrid.length; i++) {
			for(int j=0; j<smellGrid[0].length; j++) {
				if(smellGrid[i][j] -1 >=0) {
					smellGrid[i][j] -=1;
				}
			}
		}

	}
	
	public static void moveShark(List<Fish>[][] movedMap, int[] optPathArr, Shark shark) {
		
		for(int i=0; i<3; i++) {
			sharkGrid[shark.x][shark.y]= false;
			int nx = shark.x + dsx[optPathArr[i]];
			int ny = shark.y + dsy[optPathArr[i]];
			
			if(movedMap[nx][ny].size() !=0) {
				smellGrid[nx][ny] =3;
				movedMap[nx][ny].clear();
			}
			
			sharkGrid[nx][ny] = true;
			shark.x = nx;
			shark.y = ny;
			
			
		}
		
	}
	
	
	public static void getOptPath(List<Fish>[][] movedMap, Shark shark, boolean[][] visit, int depth, String path) {
		
		if(depth ==3) {
			//System.out.println("shark.eat  " + shark.eat);
			if(max<shark.eat) {
				max = shark.eat;
				pathList.clear();
				pathList.add(path);
			}else if(max == shark.eat) {
				pathList.add(path);
			}
			
			return;
		}
		
		
		for(int i=0; i<4; i++) {
			
			int oldX = shark.x;
			int oldY = shark.y;
			
			int nx = oldX + dsx[i];
			int ny = oldY + dsy[i];
			
			if(nx>=0 && nx<4 && ny>=0 && ny<4) {
				
				if(visit[nx][ny] == false) {
					//기존에 있는 거 false로
					visit[nx][ny] = true;
					
					Shark newShark = new Shark(nx, ny);
					newShark.eat = shark.eat;
					
					
					//상어야 밥먹자
					if(movedMap[nx][ny].size() !=0) {
						newShark.eat += movedMap[nx][ny].size();
					}
					
					//depth 넣기
					int newDept = depth + 1;
					String newPath = path + String.valueOf(i);
					getOptPath(movedMap, newShark, visit, newDept, newPath);
					visit[nx][ny] = false;
					
				}else {
										
					//depth 넣기
					int newDept = depth + 1;
					String newPath = path + String.valueOf(i);
					
					getOptPath(movedMap, shark, visit, newDept, newPath);

					
				}
			
			}
		
		}
	}
	
	public static List<Fish>[][] moveFish(List<Fish>[][] fishGrid, Shark shark) {
		
		List<Fish>[][] newMap = makeEmpty();
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				

				for(int f=0; f<fishGrid[i][j].size(); f++) {
					
					Fish fish = fishGrid[i][j].get(f);
					int oldDir = fish.dir;
					
					boolean hold = false;
					
					for(int d=0; d<8; d++) {
						
						int nx = fish.x + dfx[oldDir];
						int ny = fish.y + dfy[oldDir];
						
						if(nx>=0 && nx<4 && ny>=0 && ny<4) {
							if(smellGrid[nx][ny] ==0) {
								if(sharkGrid[nx][ny] != true) {
									newMap[nx][ny].add(new Fish(nx, ny, oldDir));
									break;
								}
								
							}
								
						}
								
						
						oldDir = (8 + (oldDir-1))%8;
						
						if(d==7) {
							hold = true;
						}

					}
					
					if(hold) {
						//원래 있던 자리에 물고기 추가
						newMap[i][j].add(new Fish(fishGrid[i][j].get(f)));
					}	
				}
				
			}
			
		}
		
		return newMap;
	}
	
	
	public static List<Fish>[][] copyMagic(List<Fish>[][] fishGrid){
		
		List<Fish>[][] newGrid = new List [4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				newGrid[i][j] = new ArrayList<Fish>();
			}
		}
		
		for(int i=0; i<fishGrid.length; i++) {
			for(int j=0; j<fishGrid[0].length; j++){
				List<Fish> oldFishGrid= fishGrid[i][j];
				for(int f =0; f<oldFishGrid.size(); f++) {
					Fish oldFish = oldFishGrid.get(f);
					newGrid[i][j].add(new Fish(oldFish));
				}
				
			}
		}
		return newGrid;
	}
	
	
	public static List<Fish>[][] init(List<Fish>[][] fishGrid){
		
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				fishGrid[i][j].clear();
			}
		}
		
		return fishGrid;
	}

	public static List<Fish>[][] makeEmpty(){
		
		List<Fish>[][] newGrid = new List [4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				newGrid[i][j] = new ArrayList<Fish>();
			}
		}
		
		return newGrid;
	}
	
}

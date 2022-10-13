import java.util.*;
import java.io.*;

class Node{
	int smell, x, y;
	boolean shark;
	
	public Node(int smell, int x, int y, boolean shark) {
		this.smell = smell;
		this.x = x;
		this.y = y;
		this.shark = shark;
	}
	
	@Override
	public String toString() {
		return "Node [smell=" + smell + ", x=" + x + ", y=" + y + ", shark=" + shark + ", fishList=" + fishList + "]";
	}

	List<Fish> fishList = new ArrayList<>();
	
	//복사 생성자
	public Node(Node node) {
		this.smell = node.smell;
		this.x = node.x;
		this.y = node.y;
		this.shark = node.shark;
		this.fishList = new ArrayList<>(node.fishList);
	}
	
}

class Shark{
	int x, y, dir;
	String pathNm;
	int eat;
	
	
	public Shark(int x, int y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public String toString() {
		return "Shark [x=" + x + ", y=" + y + ", dir=" + dir + ", pathNm=" + pathNm
				+ ", eat=" + eat + "]";
	}
	
	public Shark(Shark shark) {
		this.x = shark.x;
		this.y = shark.y;
		this.eat = shark.eat;
		this.pathNm = shark.pathNm;
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
}


public class Main{
	
	static int[][] map;
	static int[][] subMap;
	static Node[][] nodeMap = new Node[4][4];
	static Node[][] copiedMap = new Node[4][4];
	static int M;
	static int S;
	
	static int[] dfx = {0,-1,-1,-1,0,1,1,1};
	static int[] dfy = {-1,-1,0,1,1,1,0,-1};
	
	static int[] dsx = {-1,1,0,0};
	static int[] dsy = {0,0,-1,1};
	
	static int max=0;
	static List<String> pathList = new ArrayList<>();
	static List<Fish> fishList = new ArrayList<>();
	
	public static void print2D(Node[][] arr) {
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
	}
	
	
	public static void main(String[] args) {
		
		//Input
		Scanner sc = new Scanner(System.in);
		M = sc.nextInt();
		S = sc.nextInt();
		
		//2D Array만들기
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				nodeMap[i][j] = new Node(0, i ,j, false); 
			}
		}
		
		for(int i=0; i<M; i++) {
			int fx = sc.nextInt();
			int fy = sc.nextInt();
			int fd = sc.nextInt();
			Fish fish = new Fish(fx-1, fy-1, fd-1);
			nodeMap[fx-1][fy-1].fishList.add(fish);
		}
		
		//상어에 대한 정보
		int sx = sc.nextInt();
		int sy = sc.nextInt();
		Shark shark = new Shark(sx-1, sy-1);
		
		nodeMap[sx-1][sy-1].shark = true;
		//Solution
		solution(shark);
		
	}
	
	public static void solution(Shark shark) {
		
		//Loop S번
		
			//복제 마법 사용
			Node[][] copied = copyMagic(nodeMap);
//			print2D(nodeMap);
			
			//물고기 이동
			Node[][] movedMap = moveFish();
//			System.out.println();
//			print2D(subNodeMap);
			
			
			//상어 이동
			int depth =1;
			String path = "";
			boolean[][] visit = new boolean[4][4];
			findShortest(movedMap,visit, shark, depth, path);
			
			Collections.sort(pathList);
			System.out.println(pathList);
			
			//최단 경로 선택
			String shortestPath = pathList.get(0);
			if(shortestPath.length() ==3) {
				System.out.println("ok");
			}else {
				System.out.println("something problem");
			}
			
			int[] shortArr = new int[3];
			
			for(int i=0; i<shortestPath.length(); i++) {
				String element = Character.toString(shortestPath.charAt(i));
				shortArr[i] = Integer.parseInt(element);
			}
			
			//최단경로로 상어 이동
			//print1D(shortArr);
//			System.out.println(shark);
			moveShark(shortArr, movedMap, shark);

			
			//냄새가 1씩 사라짐
			reduceSmell(movedMap);
			
			//print2D(movedMap);
			
			
			//복제 한거 선택된 map에 더함
			//update(movedMap);
			
			//updated movedMap을 nodeMap으로 업데이트한다

		
	}
	
	
	public static void copiedOperation(Node[][] movedMap) {
		
		for (int i=0; i<movedMap.length; i++) {
			for (int j=0; j<movedMap[0].length; j++) {
				
				movedMap[i][j].fishList.addAll(nodeMap[i][j].fishList);
			}
		}
	}
	
	public static void reduceSmell(Node[][] movedMap) {
		
		for (int i=0; i<movedMap.length; i++) {
			for (int j=0; j<movedMap[0].length; j++) {
				
				if(movedMap[i][j].smell-1>=0) {
					movedMap[i][j].smell -=1;
				}
			}
		}
		
		
	}
	
	public static void moveShark(int[] shortArr, Node[][] movedMap, Shark shark) {
		
		for (int i=0; i<shortArr.length; i++) {
			
			int nx = shark.x + dsx[shortArr[i]];
			int ny = shark.y + dsy[shortArr[i]];
			
			//기존에꺼는 false상태로
			movedMap[shark.x][shark.y].shark = false;

			//현재상태 udpate
			movedMap[nx][ny].shark = true;
			
			//상어 객체도 update
			shark.x = nx;
			shark.y = ny;
			shark.dir = shortArr[i];
			
			//냄새를 남겨준다.
			//이동하면서 물고기 존재하면 제외시키고 냄새 남김 (smell =2)로
			if(movedMap[nx][ny].fishList.size() !=0) {
				movedMap[nx][ny].fishList.clear();
				movedMap[nx][ny].smell = 3;
			}

		}
		
	}
	
	public static void findShortest(Node[][] movedMap, boolean[][] visit, Shark shark, int depth, String path) {
		//subNodeMap
		if(depth ==4) {
			//System.out.println(shark.eat);
			if(max < shark.eat) {
				max = shark.eat;
				pathList.clear();
				pathList.add(shark.pathNm);
				System.out.println(shark.eat + " " +shark.pathNm);
			}else if(max == shark.eat) {
				pathList.add(shark.pathNm);
			}
			return;
		}
		
		for(int i=0; i<4; i++) {
			
			Node[][] copiedNode = copyMagic(movedMap);
			Shark newShark = new Shark(shark);
			boolean[][] newVisit = new boolean[4][4];
			
			for(int a=0; a<4; a++) {
				for(int b=0; b<4; b++) {
					newVisit[a][b] = visit[a][b];
				}
			}
			
			
			int nx = newShark.x + dsx[i];
			int ny = newShark.y + dsy[i];
			//System.out.println(nx + " " + ny);
			
			if(isOver(nx, ny)) {
				if(newVisit[nx][ny] == false) {
					copiedNode[newShark.x][newShark.y].shark = false;
					copiedNode[nx][ny].shark = true;
					newVisit[nx][ny] = true;
					//System.out.println(copiedNode[nx][ny].fishList.size());
					
					if(copiedNode[nx][ny].fishList.size() != 0) {
						newShark.eat =+ copiedNode[nx][ny].fishList.size(); // 물고기 먹는다.
						copiedNode[nx][ny].fishList.clear();
					}

					int newDepth = depth+1; //newDept = depth++와 newDept = depth+1 은 다르다
					String newPath = path + String.valueOf(i);
					//new shark에 대한 방향을 새로 안정해줌
					newShark.x = nx;
					newShark.y = ny;
					newShark.pathNm = newPath;
					//System.out.println("dpeth" +  newDepth + " " + "dirPath" + newPath);
					//System.out.println("dir" + shark.dir + " " + "xCoor" + nx + " "+ "yCorr" + ny);

					findShortest(copiedNode, newVisit, newShark, newDepth, newPath);
					newShark.pathNm = "";
					newPath = "";
				}
				
			}else {
				continue;
			}

		}
		
		
	}
	
	public static Node[][] moveFish() {
		
		Node[][] subMap = makeSubNodeMap(nodeMap);
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				//System.out.println(nodeMap[i][j].fishList.size());
				if(nodeMap[i][j].fishList.size() !=0) {
					for(int f=0; f<nodeMap[i][j].fishList.size(); f++) {
						Fish fish = nodeMap[i][j].fishList.get(f);
						
						for(int dirN=0; dirN<8; dirN++) {
							int nx = fish.x + dfx[fish.dir];
							int ny = fish.y + dfy[fish.dir];
							
							if(nx>=0 && nx<4 && ny>=0 && ny<4){
								if(nodeMap[nx][ny].smell ==0) {
									if(nodeMap[nx][ny].shark == false) {
										subMap[nx][ny].fishList.add(new Fish(nx, ny, fish.dir));
										nodeMap[i][j].fishList.remove(f);
	
										break;
										
									}
								}
							}
							
							fish.dir = (8+(fish.dir -1))%8; // 역으로갈때도 + size해주면 periodic이 형성된다.
						}
						
						
					}
				}
			}
			
			
		}
		return subMap;
	}
	
	public static boolean isOver(int nx, int ny) {
		
		boolean go = false;
		if(nx>=0 && nx<4 && ny>=0 && ny<4) {
			go = true;
		}
		
		return go;
	}
	
	
	public static Node[][] copyMagic(Node[][] originMap) {
		Node[][] copied = new Node[4][4];
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				Node newNode = new Node(originMap[i][j]);
				copied[i][j] = newNode;
			}
		}
		return copied;
	}
	
	public static Node[][] makeSubNodeMap(Node[][] originMap) {
		
		Node[][] sub= new Node[4][4];
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				Node newNode = new Node(originMap[i][j]);
				newNode.fishList = new ArrayList<>();
				sub[i][j] = newNode;
			}
		}
		
		return sub;
		
	}
	
	
}

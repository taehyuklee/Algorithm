import java.util.*;
import java.io.*;

class Shark{
	
	int id, x, y, dir;
	int[][] order = new int[4][4];
	
	public Shark(int id, int x, int y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Shark [id=" + id + ", x=" + x + ", y=" + y + ", dir=" + dir + ", order=" + Arrays.deepToString(order) + "]";
	}

	
}

class Field{
	
	int smell, sharkId;
	boolean sharkYn;
	
	public Field(int smell, int sharkId, boolean sharkYn) {
		super();
		this.smell = smell;
		this.sharkId = sharkId;
		this.sharkYn = sharkYn;
	}

	@Override
	public String toString() {
		return "Field [smell=" + smell + ", sharkId=" + sharkId + ", sharkYn=" + sharkYn + "]";
	}

}

public class Main {
	
	static int N,M,K;
	static Field[][] mapField;
	static int[][] smellMap;
	static int[][] oldSmellMap;
	static int[][] sharkMap;
	static Map<Integer, Shark> sharkHash = new HashMap<>();
	static List<Field>[][] listField;
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	public static void print2DArray(int[][] arr) {
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		mapField = new Field[N][N];
		sharkMap = new int[N][N];
		smellMap = new int[N][N];
		oldSmellMap = new int[N][N];
		//System.out.println(N + " " + M +" " + K);
		

		for(int i=0; i<N; i++) {
			
			st = new StringTokenizer(br.readLine());
			int token;
			
			for(int j=0; j<N; j++) {
				token = Integer.parseInt(st.nextToken());
				sharkMap[i][j] = token;
				
				
				if(token>0) {
					sharkHash.put(token, new Shark(token,i,j));
					mapField[i][j] = new Field(K,token,true);
					smellMap[i][j] = K;
				}else {
					mapField[i][j] = new Field(0,0,false);
					smellMap[i][j] = 0;
				}

			}
			
		}
		
		
//		print2DArray(smellMap);
//		System.out.println();
//		print2DArray(sharkMap);
		
		
		
		//좌표랑 상어까지는 다 기입됨 상어 현재 상어 direction 넣어줘야
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<M; i++) {
			Shark shark = sharkHash.get(i+1);
			shark.dir = Integer.parseInt(st.nextToken())-1;
		}
		
		// 각 상어들한테 order array를 기입해주자
		for(int sharkI=1; sharkI<M+1; sharkI++) {
			for(int i=0; i<4; i++) {
				
				st = new StringTokenizer(br.readLine());
				int token;
				
				for(int j=0; j<4; j++) {
					token = Integer.parseInt(st.nextToken());
					Shark shark = sharkHash.get(sharkI);
					shark.order[i][j] = token-1;
	
				}
				
			}
		}
		
		//System.out.println(sharkHash);
		
		solution();

	}
	
	
	public static void solution() {
	
		
		copy2DArray(smellMap, oldSmellMap);
		//1번 상어만 남던지, 아니면 k=1001이 되던지 -> stop
		int k=1;
		while(true) {
			//상어의 움직
			for(int shark=1; shark<M+1; shark++) {

					moveShark(shark);
				
			}
			
			prevFieldUpdate();
			
			//print2DArray(oldSmellMap);
			//System.out.println();
			//print2DArray(smellMap);
			//System.out.println();
			//print2DArray(sharkMap);
			//System.out.println(mapField);
			//조건 1번 상어만 남는다는 조건이 있어야 한다/
			
			
			copy2DArray(smellMap, oldSmellMap);
			
			//break조
			if(sharkHash.size() ==1 && k <=1000) { //k<=1000조건 안넣어서... 6%에서 틀렸었
				System.out.println(k);
				break;
			}else if(k>1000) {
				System.out.println(-1);
				break;
			}
			
			k++;
			
		}
		
	}
	
	public static void moveShark(int sharkNum) {
		
		Shark shark = sharkHash.get(sharkNum);
		if(shark==null) {
			return; // hashMap에서 뺏을때를 대비한 null처리 
		}
		
		int nx, ny, oldx, oldy;
		boolean goBack = false;
		oldx = shark.x;
		oldy = shark.y;
		

		
		int oldDir = shark.dir;
		int[] newDirList = new int[4];
		
		if(oldDir ==0) {
			newDirList = shark.order[oldDir]; //pointer - hashCode가 넘어
			
		}else if(oldDir ==1) {
			newDirList = shark.order[oldDir];
			
		}else if(oldDir ==2) {
			newDirList = shark.order[oldDir];
			
		}else if(oldDir ==3) {
			newDirList = shark.order[oldDir];
			
		}
		
		
		//System.out.println(Arrays.toString(newDirList));
		//System.out.println(shark);
		
		
		for(int preDir=0; preDir<4; preDir++) {


			nx = shark.x + dx[newDirList[preDir]];
			ny = shark.y + dy[newDirList[preDir]];	

			
			if(nx>=0 && ny>=0 && nx<N && ny<N) {//벽 또한 갈수 없는 조건이다. (0이상인데 0초과로 해서 디버깅 시간 보냄)
				
//				if(shark.id ==3) {
//					System.out.println(preDir);
//					System.out.println(newDirList[preDir]);
//					System.out.println();
//					System.out.println(shark.x + " " + dx[newDirList[preDir]] + " " + nx);
//					System.out.println(shark.y + " " + dy[newDirList[preDir]] + " " + ny);
//					System.out.println(smellMap[nx][ny]);
//					System.out.println(sharkMap[nx][ny]);
//					System.out.println();
//					
//				}
				
				if(oldSmellMap[nx][ny] ==0) {
					
					//그 다음칸에 상어가 없을
					if(sharkMap[nx][ny] ==0) {
						//sharkMap update
						sharkMap[nx][ny] = shark.id;
						sharkMap[shark.x][shark.y] = 0;
						
						//smellMap update
						smellMap[nx][ny] = K;

						// 그 전 스멜맵 -1해주는거 맨 뒤에서 해주냐 여기서 해주
						
						//fieldMap update
						mapField[nx][ny].sharkId = shark.id;
						mapField[nx][ny].sharkYn = true;
						mapField[shark.x][shark.y].sharkYn = false; // 기존꺼는 false 처리 
						mapField[nx][ny].smell = K;
						
						//sharkObject update
						shark.x = nx;
						shark.y = ny;
						shark.dir = newDirList[preDir];

						break;
						
						
					//그 다음칸에 상어가 있을때
					}else if(sharkMap[nx][ny] !=0) {
						
						if(shark.id < sharkMap[nx][ny]) {
							//sharkMap update
							sharkMap[nx][ny] = shark.id;
							sharkMap[shark.x][shark.y] = 0;
							
							//smellMap update
							smellMap[nx][ny] = K;
							// 그 전 스멜맵 -1해주는거 맨 뒤에서 해주냐 여기서 해주
							
							//fieldMap update
							mapField[nx][ny].sharkId = shark.id;
							mapField[nx][ny].sharkYn = true;
							mapField[shark.x][shark.y].sharkYn = false; // 기존꺼는 false 처리 
							mapField[nx][ny].smell = K;
							
							//sharkObject update
							shark.x = nx;
							shark.y = ny;
							shark.dir = newDirList[preDir];
							
							break;
						}else {
							
							sharkHash.remove(sharkMap[shark.x][shark.y]);
							sharkMap[shark.x][shark.y]=0;
							mapField[shark.x][shark.y].sharkYn = false; // 기존꺼는 false 처리 
							

							break;
							
						}
						
					}
				}
					
//				}else {
//					//앞에 다른 상어 냄새로 막혀 있을때 
//					
//					
//					
//					
//				}
				
				
				
			}
			
			//4방향 다 봤는데 break가 안걸렸다
			if(preDir ==3) {
				goBack = true;
			}
			
		}
		
		if(goBack) {
			
			//방향을 반대로 원래 오던 방향으로 하는건 어떻게 하면편할까?
			for(int preDir=0; preDir<4; preDir++) {
				
				
				nx = shark.x + dx[newDirList[preDir]];
				ny = shark.y + dy[newDirList[preDir]];
				
				if(nx>=0 && ny>=0 && nx<N && ny<N) {
					if(mapField[nx][ny].sharkId == shark.id) {
						
						//System.out.println(nx +" "+ ny);
						//System.out.println(mapField[nx][ny]);
						
						//sharkMap update
						sharkMap[nx][ny] = shark.id;
						sharkMap[shark.x][shark.y] = 0;
						
						//smellMap update
						smellMap[nx][ny] = K;
						// 그 전 스멜맵 -1해주는거 맨 뒤에서 해주냐 여기서 해주
						
						//fieldMap update
						mapField[nx][ny].sharkId = shark.id;
						mapField[nx][ny].sharkYn = true;
						mapField[shark.x][shark.y].sharkYn = false; // 기존꺼는 false 처리 
						mapField[nx][ny].smell = K;
						
						shark.x = nx;
						shark.y = ny;
						shark.dir = newDirList[preDir];
						break;
					}

				}
			}
			
		}
		
		
		
	}
	
	public static void prevFieldUpdate() {
		//아 이러면 M마리 상어가 지나갈때마다 모두 차감된다. 감소할 좌표만가지고 있어야 겠다.

		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {

				// && i !=nx && j !=ny
				if(smellMap[i][j] >0 && sharkMap[i][j] ==0) {
					
					smellMap[i][j] -=1;
					mapField[i][j].smell -=1;
					
					if(smellMap[i][j] ==0) {
						//field 자체를 다 초기화 해줘야 한
						mapField[i][j].sharkId=0;
						mapField[i][j].sharkYn=false;
						//mapField[oldX][oldY].smell=0;
						}
				}
			}
		}
		
	}
	
	public static void copy2DArray(int[][] sourceArr, int[][] targetArr) {
		
		for(int i=0; i<N; i++) {
			
			for(int j=0; j<N; j++) {
				targetArr[i][j] = sourceArr[i][j];
			}
			
		}
		
	}
	
	
}

import java.util.*;
import java.io.*;

class Horse{
	int pathIndx;
	int[] path;
	int pathNum;
	int currScore;
	int horseNum;
	
	public Horse(int pathIndx, int[] path, int horseNum) {
		this.pathIndx = pathIndx;
		this.path = path;
		this.horseNum = horseNum;
	}
	
	@Override
	public String toString() {
		return "Horse [pathIndx=" + pathIndx + ", currScore=" + currScore
				+ ", horseNum=" + horseNum + ", pathNum=" + pathNum +"]";
	}

	//복사 생성
	public Horse(Horse horse) {
		this.pathIndx = horse.pathIndx;
		this.path = horse.path;
		this.horseNum = horse.horseNum;
		this.currScore = horse.currScore;
		this.pathNum = horse.pathNum;
		
	}
}


public class Main {
	
	static int maxScore;
	static int[] path1 = {0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,-1};
	static int[] path2 = {10,13,16,19};
	static int[] path3 = {20,22,24};
	static int[] path4 = {30,28,27,26};
	static int[] path5 = {25,30,35,40,-1};
	static int[] order = new int[10];
	
	public static void print1DArray(int[] arr){
		
		for(int i=0; i<arr.length; i++ ) {
			System.out.print(arr[i] + " ");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		//input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<10; i++) {
			order[i] = Integer.parseInt(st.nextToken());
		}

		solution();
		
	}
	
	public static void solution() {
		
		//첫 시작은 path1에서 0좌표에 있다고 생각하면 된
		Map<Integer, Horse> horseMap =  new HashMap<>();
		
		int turn = 0;
		
		for(int i=1; i<=4; i++) {
			Horse horse = new Horse(0, path1, i);
			horse.pathNum = 1;
			horseMap.put(i, horse);
			
		}
		
		int score = 0;
		
		dfs(turn, horseMap, score);
		System.out.print(maxScore);
		
	}
	
	public static void dfs(int turnIndx, Map<Integer, Horse> horseMap, int score) {
		
		if(maxScore<score) {
			maxScore = score;
		}

		if(turnIndx==10) {
			return;
		}


		//말 4개를 모두 이동시키면 된다. (horseMap size로 하는 이유 -> 중간에 줄어들수 있으므로)
		for(int horseN=1; horseN<=4; horseN++) {
			
			//일단 그 전 상태를 남겨놔야 하므로 복사
			Map<Integer, Horse> newHorseMap = deepCopyHorseMap(horseMap);

			//말을 꺼내본다.
			Horse newHorse = newHorseMap.get(horseN);

			if(newHorse.currScore != -1) {
				
				int	newIndx = newHorse.pathIndx + order[turnIndx]; //그 말의 새로운 위치 (주사위에 의한)
				
				//현재의 path보다 더 넘었을때
				if(newHorse.path.length <= newIndx) {
					
					if(newHorse.pathNum ==2 || newHorse.pathNum ==3 || newHorse.pathNum ==4) {
						
						if(path5.length -1 <= newIndx-newHorse.path.length) {
							newHorse.currScore = -1;
							
							//위의 말은 제외하고 다음 턴으로 넘어가게 된다.
							int newTurnIndx = turnIndx +1;
							
							dfs(newTurnIndx, newHorseMap, score);
							return;
							
						}else {
							
								boolean goContinue = false;
								
								Horse tempHorse = new Horse(newHorse);
								tempHorse.pathIndx = newIndx-tempHorse.path.length;
								tempHorse.path = path5;
								tempHorse.currScore =tempHorse.path[tempHorse.pathIndx];
								tempHorse.pathNum = 5;
								
								goContinue = duplicatedCheck(newHorseMap, tempHorse, tempHorse.pathIndx);
								
								if(goContinue) {
									goContinue = false;
									continue;
								}
	
								newHorse.pathIndx = newIndx-newHorse.path.length;
								newHorse.path = path5;
								newHorse.currScore = path5[newHorse.pathIndx];
								newHorse.pathNum = 5;
								newIndx = newHorse.pathIndx;
								
	
								int newTurnIndx = turnIndx +1;
								int newScore = score + newHorse.currScore;
								
								dfs(newTurnIndx, newHorseMap, newScore);	
								return;
							
						}
		
						
						
					}else if(newHorse.pathNum ==1 || newHorse.pathNum ==5) {

						newHorse.currScore = -1;
						
						//위의 말은 제외하고 다음 턴으로 넘어가게 된다.
						int newTurnIndx = turnIndx +1;
						
						dfs(newTurnIndx, newHorseMap, score);
						return;
					}

				}
				
				boolean goContinue = false;
				goContinue = duplicatedCheck(newHorseMap, newHorse, newIndx);
				
				if(goContinue) {
					goContinue = false;
					continue;
				}
				
				
				//위의 모든것을 통과한다면 말의 상태를 모두 바꿔준다.
				//10일때 path를 바꾼다 (그 말의 새로운 위치)
				if(newHorse.path[newIndx] == 10 && newHorse.pathNum==1) {
					newHorse.path = path2;
					newHorse.currScore = 10;
					newHorse.pathNum = 2;
					newHorse.pathIndx = 0; //새로운 길에 들어왔으므
	
				}
				
				//20일때 path를 바꾼다 (그 말의 새로운 위치)
				else if(newHorse.path[newIndx] == 20 && newHorse.pathNum==1) {
					newHorse.path = path3;
					newHorse.currScore = 20;
					newHorse.pathNum = 3;
					newHorse.pathIndx = 0; //새로운 길에 들어왔으므
	
				}
				
				//30일때 path를 바꾼다 (그 말의 새로운 위치)
				else if(newHorse.path[newIndx] == 30 && newHorse.pathNum==1) {
					newHorse.path = path4;
					newHorse.currScore = 30;
					newHorse.pathNum = 4;
					newHorse.pathIndx = 0; //새로운 길에 들어왔으므
	
				}else {
					newHorse.currScore = newHorse.path[newIndx];
					newHorse.pathIndx = newIndx;
				}
				
			
			
			int newTurnIndx = turnIndx +1;
			int newScore = score + newHorse.currScore;
			
			dfs(newTurnIndx, newHorseMap, newScore);	
			
				
			}
		}
		return;
	}
	
	public static boolean duplicatedCheck(Map<Integer, Horse> newHorseMap, Horse newHorse, int newIndx) {
		//말 4마리 돌리면서 하나라도 있으면 넘긴다 백트랙
		boolean goContinue = false;
		for(int i=1; i<=4; i++) {
			//말을 돌리다가 혹시 이동한 곳에 말이 있을 경우 이 말은 백트래킹한다.
			if(newHorseMap.get(i).currScore != -1) {
				if(newHorse.horseNum != newHorseMap.get(i).horseNum) { //자기 자신을 제외
						if(newHorse.path[newIndx] == newHorseMap.get(i).currScore) { //path가 같을때
								if(newHorse.pathNum == newHorseMap.get(i).pathNum) {
									goContinue = true;
									break;
								}else {
									if(newHorse.path[newIndx] == 10 && newHorseMap.get(i).currScore==10 && newHorse.pathNum ==1 && newHorseMap.get(i).pathNum==2) {
										goContinue = true;
										break;
									}
									if(newHorse.path[newIndx] == 20 && newHorseMap.get(i).currScore==20 && newHorse.pathNum ==1 && newHorseMap.get(i).pathNum==3) {
										goContinue = true;
										break;
									}
									else if(newHorse.path[newIndx] == 30 && newHorseMap.get(i).currScore==30 &&  newHorse.pathNum ==1 && newHorseMap.get(i).pathNum==4) {
										goContinue = true;
										break;
									}
//									else if(newHorse.path[newIndx] == 40 && newHorseMap.get(i).currScore==40 && newHorse.pathNum ==1 && newHorseMap.get(i).pathNum==5) {
//										goContinue = true;
//										break;
//									}
//									else if(newHorse.path[newIndx] == 40 && newHorseMap.get(i).currScore==40 && newHorse.pathNum ==5 && newHorseMap.get(i).pathNum==1) {
//										goContinue = true;
//										break;
//									}
								}
							}	
						}
				}
			
		}
		return goContinue;

	}
		
	
	
	public static Map<Integer, Horse> deepCopyHorseMap(Map<Integer, Horse> originalMap){
		
		Map<Integer, Horse> newHorseMap = new HashMap<>();
		for(int i=1; i<=4; i++) {
				newHorseMap.put(i, new Horse(originalMap.get(i)));
			}
		
		return newHorseMap;
		
	}

}

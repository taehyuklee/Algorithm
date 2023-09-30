import java.util.*;

class Potap{
    int x, y, attack, recent;
    boolean involve = false;

    public Potap(int x, int y, int attack, int recent){
        this.x = x;
        this.y = y;
        this.attack = attack;
        this.recent  = recent;
    }

    @Override
    public String toString(){
        return "x: " + this.x + " y: " + this.y + " attack: " +this.attack + " recent " +this.recent + "   ";
    }

}

public class Main {

    private static int N, M, K;
    private static List<Potap> listPotap = new ArrayList<>();
    private static Potap[][] boardMap;
    private static boolean[][] visit;
    private static int[] dx = {0,1,0,-1,-1,-1,1,1}, dy = {1,0,-1,0,-1,1,-1,1};


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        boardMap = new Potap[N][M];
        visit = new boolean[N][M];

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                int attack = sc.nextInt();
                Potap potap = new Potap(i, j, attack,0);
                listPotap.add(potap);
                boardMap[i][j] = potap;
            }
        }
        
        solution();
        System.out.println(pickStrong());
        

    }

    static void solution(){

    	for(int turn=1; turn<=K; turn++) {
    		System.out.println("turn" + turn);

	        //공격자 및 피 공격자 선정
    		Potap potap_receive = pickStrong();
	        Potap potap_attack = pickWeak();
	        
	        //둘 다 뽑고나서 반영했어야 함..
	        potap_attack.attack += N+M;
	        potap_attack.involve = true;
            potap_attack.recent = potap_attack.recent+1; //공격 횟수 한 번 늘려주고 
            System.out.println("약한자" + potap_attack);
            System.out.println("강한자" + potap_receive);
            System.out.println();
	
	        //공격자의 공격 및 부서짐
            System.out.println("공격받기 이전");
	        int[][] before = print2D(boardMap);
	        LetsAttack(potap_attack, potap_receive);
	        if(checkRest()) return;

	        //포탑 정비
	        arrangement(potap_attack, potap_receive);
	        System.out.println("공격 받고 회복한 이후");
	        int[][] after = print2D(boardMap);
	        printDiffere(before, after);
	        System.out.println(pickStrong().attack);
    	}
    	
    	//getAnswer
    	System.out.println(pickStrong().attack);



    }

    static Potap pickWeak(){
    	
        Collections.sort(listPotap, new Comparator<Potap>(){
            @Override
            public int compare(Potap o1, Potap o2){
                
                if(o1.attack == o2.attack){
                    
                    if(o1.recent == o2.recent){
                        if((o1.x+o1.y)==(o2.x+o2.y)){
                            return o2.y - o1.y;
                        }

                        return (o2.x+o2.y) - (o1.x+o1.y); 
                    }

                    return o2.recent - o1.recent; //가장 최근이니까 역순이 맞다.
                }

                return o1.attack - o2.attack;
            }

        });


        for(int i=0; i<listPotap.size(); i++){
            if(listPotap.get(i).attack !=0){
                Potap potap = listPotap.get(i);
                return potap;
            }
        }

        return null;
    }

    static Potap pickStrong(){

        Collections.sort(listPotap, new Comparator<Potap>(){
            @Override
            public int compare(Potap o1, Potap o2){
                
                if(o1.attack == o2.attack){
                    
                    if(o1.recent == o2.recent){
                        if((o1.x+o1.y)==(o2.x+o2.y)){
                            return o1.y - o2.y; //열 값이 가장 작은 
                        }

                        return (o1.x+o1.y)-(o2.x+o2.y); //행과 열의 합이 가장 작은 (순서 또 바뀜)
                   
                    }

                    return o1.recent - o2.recent; //가장 오래된 포탑
                }

                return o2.attack - o1.attack; // 가장 강한 포탑
            }

        });
        
        return listPotap.get(0);
    }

    static void LetsAttack(Potap source, Potap target){
    	
    	//Laser Attack
    	 List<int[]> attackedTrack = bfs(source, target);
    	 
    	 printTracks(attackedTrack);
    	 
    	 if(attackedTrack!=null) {
    		 System.out.println("레이저");
	    	 for(int i=0; i<attackedTrack.size(); i++) {
	    		 int[] attackedNode = attackedTrack.get(i);
//	    		 System.out.println(attackedTrack.get(i)[0] + " " +  attackedTrack.get(i)[1]);
	    		 
	    		 if(attackedNode[0] == target.x && attackedNode[1] == target.y) {
	    			 target.attack -= source.attack;
	    			 if(target.attack<0) target.attack = 0;
	    			 target.involve = true; //전쟁에 연루됨 
	    		 }else {
	    			 boardMap[attackedNode[0]][attackedNode[1]].attack -= (int) source.attack/2;
	    			 if(boardMap[attackedNode[0]][attackedNode[1]].attack<0) boardMap[attackedNode[0]][attackedNode[1]].attack=0;
	    			 boardMap[attackedNode[0]][attackedNode[1]].involve = true; //전쟁에 연루됨.
	    		 }
	    	 }
	    	 
    	 }else {
        	 //PotanAttack
    		 System.out.println("포탄");
    		 target.attack -= source.attack;
    		 if(target.attack<0) target.attack = 0;
    		 //involve한거 까먹음 (1일 걸림 이것때문에_)
    		 target.involve = true; //전쟁에 연루됨 -> 이거 어찌해야할까..
    		 
    		 for(int i=0; i<8; i++) {
    			 //자기한테도 반여오디게 해놔버렸다.. (이런 섬세함 필요) 레이저는 반영안된다고 여기서도 간과해버림.
    			 int attacked_x = (target.x + dx[i]+N)%N;
    			 int attacked_y = (target.y + dy[i]+M)%M;
                 
    			 if(attacked_x == source.x && attacked_y == source.y) continue; //자기 자신은 반영 안되게 해야함 (디버깅때 알았음)
                 
    			 boardMap[attacked_x][attacked_y].attack -= (int) source.attack/2;
    			 if(boardMap[attacked_x][attacked_y].attack<0) boardMap[attacked_x][attacked_y].attack=0;
    			 //여기 involve안해놨다 
    			 boardMap[attacked_x][attacked_y].involve = true;
    			 
    		 }
    		 
    	 }
    	 
    }
  
    static void arrangement(Potap source, Potap target) {
    	for(int i=0; i<listPotap.size(); i++) {
    		Potap potap = listPotap.get(i);

    		if(potap.attack ==0) continue;
    		else if(!potap.involve) {
    			potap.attack+=1;
    		}else {
    			potap.involve = false;
    		}
    	}

    	visit = new boolean[N][M]; //아 이거 빼먹었다;; 아오!! 이거
    	for(int i=0; i<listPotap.size(); i++) {
    		Potap potap = listPotap.get(i);
    		potap.involve = false;
    	}
    }
    
    
    
    /****************** common Utility (시간이 오래 걸림 파악되지 않은 문제로) ******************/
    static List<int[]> bfs(Potap source, Potap target){

        Queue<BfsNode> Q = new LinkedList<>();

        //board랑 동기화 하는거 꼭!!!
        Q.add(new BfsNode(source.x, source.y));
        visit[source.x][source.y] = true;
        
        while(!Q.isEmpty()){

            BfsNode node = Q.poll();

            if(node.x == target.x && node.y == target.y){
                return node.tracks;
            }
            
            for(int i=0; i<4; i++){

                int new_x = (node.x + dx[i] +N)%N;
                int new_y = (node.y + dy[i] + M)%M;
                
                if(visit[new_x][new_y] == true) continue;
                if(boardMap[new_x][new_y].attack == 0) continue;

                //방문 기록
                visit[new_x][new_y] = true;
                
                //방문한 새로운 노드와 track만들기
                BfsNode newNode = new BfsNode(new_x, new_y);
                int[] newArray = new int[] {new_x, new_y};
                newNode.copyAndAddTracks(node.tracks, newArray);
                
                //Q에 추가하기
                Q.add(newNode);

            }
        }
		return null;

        }
    
	static boolean checkRest() {
		List<Potap> semiAnswer = new ArrayList<>();
		Potap lastPotap = null;
		for(int i=0; i<listPotap.size(); i++) {
			if(listPotap.get(i).attack!=0) {
				lastPotap = listPotap.get(i);
				semiAnswer.add(listPotap.get(i));
			}
		}
		if(semiAnswer.size()==1) {
			System.out.println(lastPotap.attack);
			return true;
		}
		return false;
	}
	
	
	static void printTracks(List<int[]> tracks) {
		if(tracks == null) return;
		
		int[][] testBoard = new int[N][M];
			
		 for(int i=0; i<tracks.size(); i++) {
    		 int[] attackedNode = tracks.get(i);
//    		 System.out.print("[" + tracks.get(i)[0] + " " +  tracks.get(i)[1]+ "], ");
    		 testBoard[attackedNode[0]][attackedNode[1]] = 1;
    	
    	 }
		 
		 
		 for(int i=0; i<boardMap.length; i++) {
			 for(int j=0; j<boardMap[0].length; j++) {
				System.out.print(testBoard[i][j] + " ");
			 }
			 System.out.println();
		 }
		 System.out.println();


	}
    
    static int[][] print2D(Potap[][] boardMap) {
    	int[][] newTemp = new int[N][M];
    	for(int i=0; i<boardMap.length; i++) {
    		for(int j=0; j<boardMap[0].length; j++) {
    			System.out.printf("%6d", boardMap[i][j].attack);
    			newTemp[i][j] = boardMap[i][j].attack;
    		}
    		System.out.println();
    	}
    	System.out.println();
    	return newTemp;
    }
    
    static void printDiffere(int[][] before, int[][] after) {
    	for(int i=0; i<boardMap.length; i++) {
    		for(int j=0; j<boardMap[0].length; j++) {
    			System.out.printf("%3d", before[i][j] - after[i][j]);
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
   }



    class BfsNode{
        int x, y;
        List<int[]> tracks = new ArrayList<>();

        public BfsNode(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        public void copyAndAddTracks(List<int[]> oldTracks, int[] newArray){
        	for(int i=0; i<oldTracks.size(); i++) {
        		tracks.add(oldTracks.get(i));
        	}
        	tracks.add(newArray);
        }
    }

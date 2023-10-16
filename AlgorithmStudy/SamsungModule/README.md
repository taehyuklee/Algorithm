## 삼성이 좋아하는 여러 구현 모듈들 및 참고사항 정리 (완벽 암기)

### [구현모듈]
1. 회전시키기 90도 방향으로 좌표 또는 일부 회전하기, 반시계방향, 시계방향 모두
   - 메이즈러너 문제 등 다양한 곳에서 일부 좌표를 90도 회전하는 문제가 나온다. ```RotationImpl.java```
   - '예술성' 문제
     ```java    		
		int N=5;
		int[][] map = new int[N][N];
		for(int i=0; i<5; i++) {
			map[i][0]=1;
		}
		
        //기존에 map이 있다고 가정.
		int start_x = 0, end_x = N;
		int start_y = 0, end_y = N;
		
		//90도 시계방향 회전
		int[][] newMap = new int[N][N];
		  
		for(int i=start_x; i<end_x; i++){
			for(int j=start_y; j<end_y; j++){
				newMap[start_x+(j-start_y)][(end_y-1)-(i-start_x)] = map[i][j];
			}
		}
		  
      ```
     
   <br>
   
2. Periodic boundary Condition - 주기적 경계문제. 경계를 넘어가는 순간 그 다음 경계에 영향을 준다.
   - 기존에 경계를 넘어 다음 경계로 넘어가는 문제 ('포탑 부수기' 문제)
   - 여기서 중요한 것은 index를 내가 '0'부터 잡느냐 아니면 '1'부터 잡느냐이다 0 ~ N-1 or 1 ~ N 이렇게 되면 달라진다.
   - ```PeriodicBoundary Distance & Unit step.java```
     ```java
     //이 경우는 1칸씩 step by step으로 움직일때의 케이스
     //0 ~ N-1 indexing잡았을 때
     int new_x = (N + (x + dx[dir]))%N;
     int new_y = (M + (y + dy[dir]))%M;

     //1 ~ N indexing 잡았을 때 (밖에 1을 더해주고 내부적으로 1을 빼준다)
     int new_x = (N+((x-1)+dx[dir]))%N + 1;
     int new_y = (M+((y-1)+dy[dir]))%M + 1;
     ```

      ```java
     //이 경우는 한 번에 크게 움직일때의 케이스
     //0 ~ N-1 indexing잡았을 때
     int distance = 150000000;
     int new_x = Math.abs((N + (x + distance)))%N;
     int new_y = Math.abs((M + (y + distance)))%M;

     //1 ~ N indexing 잡았을 때 (밖에 1을 더해주고 내부적으로 1을 빼준다)
     int new_x = Math.abs((N+((x-1)+distance)))%N + 1;
     int new_y = Math.abs((M+((y-1)+distance)))%M + 1;
     ``` 
     
   <br>
3. 거꾸로 방향 바꾸는 문제
   - 대표적으로 '주사위 문제3' 와 '토끼와 경주' 문제가 있다. 이 부분에 대해서 이해후 암기
   <br>

4. Sort - 조건에 따라 정렬하는 조건
    - 최근에 빈번하게 4~5개 조건씩 해서 나오는 문제가 많이 나온다.
     
		 ```java
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) { //양수면 순서대로, 음수면 역순
				
				if(o1<o2) {
					return 1; //이전거가 다음거보다 작을때 안바꾸면 오름차순, 바꾸면 내림차순 (양수 바꿔, 음수 안바꿔)
	  				//이걸 어떻게 생각하면 되냐면, o1기준으로 o2를 비교했을때 o1<o2이면 return 양수 -> 바꿔! o1과 o2가 바뀜.
				}else if(o1==o2) {
					return 0;
				}else {
					return -1;
				}
			}
		});
	
		//원래는 위와 같이 되어 있지만, 편하게 아래와 같이 생각하면 된다. (위의 case는 long type때문에)
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) { 
			    return o1-o2; //o1-o2 (오름차순) , o2-o1 (내림차순) o1 o2 순서대로 (오름), o2 o1 역으로 (내림)
			}
		});
		```
	  	<br>
    
	   	 - 예를 들어  List에 0부터 100까지 숫자가 임의대로 있다고 하자. 그리고 0은 나열의 숫자가 아니라고 한다.<br>
     	  	Q. 이럴때 어떤 조건을 달아서 sort를 해야 할까?<br>
	    
	  	```java
		List<Integer> list = new ArrayList<>(Arrays.asList(1,30,0,0,1,3,2,1,0,5,2,0,3));
		Collections.sort(list, new Comparator<Integer>() {
		    @Override
		    public int compare(Integer o1, Integer o2) {
		        if (o1 == 0) {
		            if (o2 == 0) {
		                return 0; // o1과 o2가 둘 다 0이면 같다.
		            } else {
		                return 1; // o1만 0이므로 o1을 뒤로 보낸다.
		            }
		        } else if (o2 == 0) {
		            return -1; // o2만 0이므로 o2를 뒤로 보낸다.
		        } else {
		            return o1.compareTo(o2); // o1과 o2가 둘 다 0이 아니면 숫자의 크기로 정렬한다.
		        }
		    }
		});

		```

     		
      	
	  	- 그리고 Comparator는 기본적으로 int type을 반환하게 되어 있는데 만약 Long type이라면? 그냥 compareTo그대로 쓰면 된다. <br>
               [o1-o2 -> o1.compareTo(o2), o2-o1 -> o2.compareTo(o1)]  <br>
      
 		```java
		List<Long> list2 = new ArrayList<>(Arrays.asList(1L,30L,0L,0L,1L,3L,2L,1L,0L,5L,2L,0L,3L));
		Collections.sort(list2, new Comparator<Long>() {
		    @Override
		    public int compare(Long o1, Long o2) {
		        if (o1 == 0) {
		            if (o2 == 0) {
		                return 0; // o1과 o2가 둘 다 0이면 같다.
		            } else {
		                return 1; // o1만 0이므로 o1을 뒤로 보낸다.
		            }
		        } else if (o2 == 0) {
		            return -1; // o2만 0이므로 o2를 뒤로 보낸다.
		        } else {
		            return o1.compareTo(o2); // o1과 o2가 둘 다 0이 아니면 숫자의 크기로 정렬한다.
		        }
		    }
		});
		System.out.println(list2);

		```
      	
 
   <br>
6. BFS - 최단거리 찾는 문제
    - 대부분의 문제가 BFS를 요구하지만, Trace를 모두 가져와야 하는지 등에 대해서 조건이 달라질수 있고 구현이 달라질수 있다. ('포탑 부수기'문제)
   <br>
7. Spiral - 좌표 ```SpiralCoordinate.java```
    - Spiral 좌표문제가 한때 자주 나왔었다. 혹시 모르니 익혀가야 한다 ('블리자드' 문제 및 '스톰' 문제)
    - 추가적으로 숨바꼭질 문제에서는 좌표만이 아니라 방향 또한 저장했어야 했다 <- 이거 착각한거때문에 고생함.
      
   <br>
8. 방사형 ('온풍기 안녕' 문제, '마법사 상어와 토네이도' 문제) 
    - 방사형으로 공기, 모래 등이 퍼지는 문제, 이 부분에 대해서는 두 두 가지 접근 방법이 있다. (Recursive하게 탐험하면서 하던가, 아니면 미리 좌표를 만들어 놓던가)
    - 10-12일에 온풍기 안녕 문제 3시간 30분 걸림 Recursive하게 5개 층을 탐험하면서 방사시킴, 이전에 다른 솔루션 참고한 것은 bfs로 함 (깔끔함)
    	- 미리 좌표를 만들어 놓고 하는게 가장 편하긴 할듯 -> 방사시킬때 Wall check만 하면 된다. (Wall check도 4차원 visit을 만들어서 함)

   <br>
9. Indexing - 이동 거리와 길이 사이의 관계를 확실히 하자. ('산타의 선물공장' 여기서 선물을 벨트에 균등하게 잘라 넣을때 이때가 문제였다)
   <br><br>

### 내가 했던 실수들 
* <b>전역 조건, 국부 조건</b><br>
  설명: 문제 읽을때 조심해야 하는 부분 - 어떤 조건이 있을때, 그 조건이 시뮬레이션이 돌아가는 전체동안에 해당되는 것인지 아니면, 국부적으로만 해당되는것인지 확실히 알아야 한다
  * '포탑 부수기 문제' - 시계열에 따라 최근 포탑을 포탑이 공겨한 횟수로 착각함
  * '토끼와 경주' - K번 돌아가는 조건내에서 한 번이라도 jump한 토끼에 해당되는데 전체 시뮬레이션에서 한 번이라도 jump한 토끼로 착각함.
<br>

* <b>동시성 문제</b><br>
  <b>Case1)</b> List에서 remove했을때 for loop를 그냥 돌려버리면, 하나를 그냥 건더 띄어 버린다. 이럴 경우 remove가 될 경우 i--를 함으로써 동시성 문제를 해결할수 있다.
   * 동시에 remove시키면서 전진시키니까 [1,2,3,4,5] -> 만약 index 2일때 3을 remove했을때 그 다음 index는 3이 된다. 하지만 list안에는 [1,2,4,5] 4가 아닌 5를 가리키게 된다. (조심해야 할 부분)
   * '숨바꼭질'문제에서도 --안해서 동시성 문제 발견함.
<br> 


* <b>Loop 빠질때 break 여부 확인</b><br>
  <b>Case1)</b> '꼬리잡기' 문제에서 1번 머리가 그 다음 갈 곳을 찾을때 4방향에 대해서 탐색하게 되는데, 이때 '4'를 찾고 break하지 않으면, 이상한걸로 update가 되게 된다.

  ```java
  /*(예를 들어 아래와 같은 과정을 거쳐야 하는데, 두 번째 케이스에서 보면 1이 4를 찾아서 옮겨가고 여기서 break를 하지 않으면,
   4를 찾고 나서도 이후 이상한 값으로 덮어 씌워질수 있다.)*/
  
  4 3 2 1         4 3 2 1        4 3 2 2         4 3 3 2        4 4 3 2
  4 0 0 4    ->   4 0 0 1   ->   4 0 0 1   ->    4 0 0 1   ->   4 0 0 1
  4 4 4 4         4 4 4 4        4 4 4 4         4 4 4 4        4 4 4 4
  ```
  
<br>
* <b>Test case 작성</b><br>
  설명: 문제 읽고 모듈단위로 구현할때 꼭 Unit test해야 한다. 안하면 거의 실수가 있기 마련이다.
  * 이동의 경우 각 케이스마다 이동이 잘 되는지 확인하고, 아무리 바빠도 Unit test는 하고 간다.
<br>

<br>

### [자료구조 사용]
* Java에서 Collections는 공통적인 Interface가 존재하고, 거기에는 size(), isEmpty(), contains(object)가 존재한다.

1. Queue [익숙함] - LinkedList 구현체 : add, poll, peek, remove {항상 개발할때 많이 사용}
   * PriorityQueue는 넣은 것을 poll을 할때 내가 정한 순서대로 나오게 된다. 그냥 출력하면 그 순서가 보장되는지 알수 없다.

3. List [익숙함] - ArrayList 구현체 : add, get, remove, clear {항상 개발할때 많이 사용}

4. Map [익숙함 半] - HashMap 구현체 : put, get(key) {이정도만 많이 사용} => 하지만, Entry가져오는 것 자체는 크게 익숙하지 않다.
   * LinkedHashMap<>() : 이걸로 Map 인터페이스를 구현하면, 순서가 보장된다. 다만, 성능이 많이 떨어진다.

5. Set [익숙하지 않음] - HashSet 구현체: add.. ?
   - Set의 경우 add한 이후 출력하기에 Iterator를 사용해야 한다. 또는 List형으로 변경해서 출력해도 가능하다.
   ```java
   Set<String> set = new HashSet<>(Arrays.asList("a","b","c"));
   Iterator<String> iter = set.iterator();
   while(iter.hasNext()){
     Syste.out.println(iter.next());
   }

   //또는 List로 변경
   List<String> list = new ArrayList<>(set);
   //이렇게 해서 간편하게 사용 가능하다.
   ```
   

<br>
* 필요시 array자체를 많이 사용하기도 함 구현할때


-------------------
### 2023-10-15(일요일) 오후 15:00 ~ 17:00 (삼성전자 DS 시험 후기)
1. 소와 풍선을 두른 사람
   - 우선 문제에 대한 설명을 하자면 조건이 상당히 많고 구현량이 많았다.
  
   Input 조건
   1. N : 격자 크기 , P : 사람 수, M: 시간, C : 소의 힘, D : 사람의 힘
   2. P줄동안 - 사람의 번호 및 위치가 적혀 있다 (1, 2, 3)
   3. 소의 위치
  
Process
1. 소가 움직임
2. 사람이 순서대로 움직임
   
   1초 소 -> 1 사람 -> 2 사람 -> 3 사람 ... -> P 사람 <br>
   2초 소 -> 1 사람 -> 2 사람 -> 3 사람 ... -> P 사람 <br>
   ... <br>
   M초 소 -> 1 사람 -> 2 사람 -> 3 사람 ... -> P 사람 <br>
<br>

- 거리에 대한 정의 <b> distance = (r1 -r2)*(r1 -r2) + (c1-c2)*(c1-c2)</b>

* 소의 이동
	소는 자신과 가장 가까운 사람한테 돌진한다 (8방으로 움직일수 있다) 
	(매 초 1칸 움직인다)
<br>

* 사람의 이동
	* 사람은 소에 부딪히면 희열을 느껴 소에게 가장 가까운 거리로 움직인다 4개 방향으로 (위 오 아 왼) 순서대로
	* 매 초 사람도 한 칸씩 움직인다.
        * 사람은 다른 사람이 있는 장소로 움직일수 없다. 만약 움직일수 없다면 움직이지 않는다.
  <br>
  
* 박치기 (두 가지 케이스) <br>

   1. case 1) 소가 사람한테 박을때 (소가 박은 방향으로 사람이 C거리만큼 날아간다) <br>
   2. case 2) 사람이 소한테 박을때 (사람이 소한테 돌진한 반대 방향으로 D거리만큼 날아간다)

  사람이 포물선으로 날아가기때문에 날아가는 동안 그 경로에 있는 사람들은 무시하고 날아갈수 있다. <br>
  하지만, 도착지에 사람이 있으면 <b> 상호작용 </b>을 하게 된다.

  * 상호작용이란?
    

## 삼성이 좋아하는 여러 구현 모듈들 및 참고사항 정리 (완벽 암기)

### [구현모듈]
1. 회전시키기 90도 방향으로 좌표 또는 일부 회전하기, 반시계방향, 시계방향 모두
   - 메이즈러너 문제 등 다양한 곳에서 일부 좌표를 90도 회전하는 문제가 나온다. ```RotationImpl.java```
   
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
    - 최근에 빈번하게 4~5개 조건씩 해서 나오는 문제
   <br>
5. BFS - 최단거리 찾는 문제
    - 대부분의 문제가 BFS를 요구하지만, Trace를 모두 가져와야 하는지 등에 대해서 조건이 달라질수 있고 구현이 달라질수 있다. ('포탑 부수기'문제)
   <br>
6. Spiral - 좌표 ```SpiralCoordinate.java```
    - Spiral 좌표문제가 한때 자주 나왔었다. 혹시 모르니 익혀가야 한다 ('블리자드' 문제 및 '스톰' 문제)
    - 추가적으로 숨바꼭질 문제에서는 좌표만이 아니라 방향 또한 저장했어야 했다 <- 이거 착각한거때문에 고생함.
      
     <br>
7. Indexing - 이동 거리와 길이 사이의 관계를 확실히 하자. ('산타의 선물공장' 여기서 선물을 벨트에 균등하게 잘라 넣을때 이때가 문제였다)

  
<br></br>
* <b>전역 조건, 국부 조건</b><br>
  설명: 문제 읽을때 조심해야 하는 부분 - 어떤 조건이 있을때, 그 조건이 시뮬레이션이 돌아가는 전체동안에 해당되는 것인지 아니면, 국부적으로만 해당되는것인지 확실히 알아야 한다
  * '포탑 부수기 문제' - 시계열에 따라 최근 포탑을 포탑이 공겨한 횟수로 착각함
  * '토끼와 경주' - K번 돌아가는 조건내에서 한 번이라도 jump한 토끼에 해당되는데 전체 시뮬레이션에서 한 번이라도 jump한 토끼로 착각함.
<br>

* <b>동시성 문제</b><br>
  <b>Case1)</b> List에서 remove했을때 for loop를 그냥 돌려버리면, 하나를 그냥 건더 띄어 버린다. 이럴 경우 remove가 될 경우 i--를 함으로써 동시성 문제를 해결할수 있다.
   * 동시에 remove시키면서 전진시키니까 [1,2,3,4,5] -> 만약 index 2일때 3을 remove했을때 그 다음 index는 3이 된다. 하지만 list안에는 [1,2,4,5] 4가 아닌 5를 가리키게 된다. (조심해야 할 부분)
   * '숨바꼭질'문제에서도 --안해서 동시성 문제 발견함.
 
<br></br>
* <b>Test case 작성</b><br>
  설명: 문제 읽고 모듈단위로 구현할때 꼭 Unit test해야 한다. 안하면 거의 실수가 있기 마련이다.
  * 이동의 경우 각 케이스마다 이동이 잘 되는지 확인하고, 아무리 바빠도 Unit test는 하고 간다.
<br>

<br>

### [자료구조 사용]
* Java에서 Collections는 공통적인 Interface가 존재하고, 거기에는 size(), isEmpty(), contains(object)가 존재한다.

1. Queue [익숙함] : add, poll, peek, remove {항상 개발할때 많이 사용}

2. List [익숙함] : add, get, remove, clear {항상 개발할때 많이 사용}

3. HashMap [익숙함 半] : put, get(key) {이정도만 많이 사용} => 하지만, Entry가져오는 것 자체는 크게 익숙하지 않다.

4. Set [익숙하지 않음] : add.. ?

5. PriorityQueue [익숙하지 않음]: add, poll, peek, remove {Queue와 똑같지만, 내부적으로 순서를 정해줘야 한다}
   * PriorityQueue는 넣은 것을 poll을 할때 내가 정한 순서대로 나오게 된다. 그냥 출력하면 그 순서가 보장되는지 알수 없다.

<br>
* 필요시 array자체를 많이 사용하기도 함 구현할때

Softeer Platform (Hyundai)

<b>1. 금고털이</b> - SteelGold 문제 (https://softeer.ai/practice/info.do?idx=1&eid=395&sw_prbl_sbms_sn=173097) <br>
&nbsp; &nbsp; <b>특징:</b>  BufferedReader를 사용하지 않으면 풀리지 않는 문제 (TimeOut이 남)<br><br>

<b>2. 근무시간</b> - WorkingTime 문제 (https://softeer.ai/practice/info.do?idx=1&eid=990&sw_prbl_sbms_sn=173116) <br>
&nbsp; &nbsp; <b>특징:</b> LocalDateTime이나 LocalTime에 대한 시간차이 사용법 Duration객체 이용 등<br>
&nbsp; &nbsp; &nbsp; -> Period, Duration 참고 사이트 (https://www.daleseo.com/java8-duration-period/)<br><br>

<b>3. 지도 자동 구축</b> - MapAutoCreation 문제 (https://softeer.ai/practice/info.do?idx=1&eid=413&sw_prbl_sbms_sn=173142) <br>
&nbsp; &nbsp; <b>특징:</b> 수학적 규칙을 찾는 문제.<br><br>

<b>4. 8단 변속기</b> - EightLevelTransmission 문제 (https://softeer.ai/practice/info.do?idx=1&eid=408&sw_prbl_sbms_sn=173144) <br>
&nbsp; &nbsp; <b>특징:</b> 그냥 순차적으로 구현되는지 보는 문제 (count를 이용해 중간에 올림, 내림을 판단).<br><br>

<b>5. 비밀메뉴</b> - SecretMenu 문제 (https://softeer.ai/practice/info.do?idx=1&eid=623&sw_prbl_sbms_sn=173194) <br>
&nbsp; &nbsp; <b>특징:</b> 두 가지 버전으로 풀수 있다. 일반적인 수열(Ver1), String contains로 엄청 간편하게 1분안에 풀수 있다 (Ver2)<br><br>

<b>6. 회의실예약</b> - ReserveRoom 문제 (https://softeer.ai/practice/info.do?idx=1&eid=626&sw_prbl_sbms_sn=173494) <br>
&nbsp; &nbsp; <b>특징:</b> 나한테 나름 빡구현이었다. 다른 사람 풀이 참조해서 배워보자 <br>
&nbsp; &nbsp; &nbsp; 내가 만든 test case - https://jealous-watch-86a.notion.site/ReserveRoom-ece2d37ec26e4accb403a74e8a208f10.<br><br>

<b>7. 전광판</b> - Banner 문제 (https://softeer.ai/practice/info.do?idx=1&eid=624&sw_prbl_sbms_sn=173510) <br>
&nbsp; &nbsp; <b>특징:</b> char를 잘 사용할수 있도록 하는 문제, ArrayList<Integer>(Arrays.asList());에 대해서도 잘 사용해보자. <br><br>

<b>8. 바이러스</b> - Virus 문제 (https://softeer.ai/practice/info.do?idx=1&eid=407) <br>
&nbsp; &nbsp; <b>특징:</b> long형 (19자리) 과, double형 (정수만 표현했을때 15자리 정도)에 대한 정수자리수 표현 길이를 정확히 알아야 한다. <br><br>

<b>9. GBC건물</b> - GBC 문제 (https://softeer.ai/practice/info.do?idx=1&eid=584) <br>
&nbsp; &nbsp; <b>특징:</b> 문제 잘 읽고 풀어야 하고 예외 케이스들 좀 잘 생각해봐야 한다. (다른 사람 풀이 참고해볼것) <br><br>

<b>10. 성적평균</b> - AverageScore 문제 (https://softeer.ai/practice/info.do?idx=1&eid=389) <br>
&nbsp; &nbsp; <b>특징:</b> 1. 얼핏 보면, map으로 풀수도 있는데, put하는데 map이 Array보다 2배정도 시간이 걸린다 10^6기준. <br>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 2. 또한 array는 index로 접근해서 조회하면 O(1)로 똑같기때문에 array로 쓰는게 timeOut에 안걸림. map은 걸림. <br><br>

<b>11. 성적평가</b> - EvaluateScore 문제 (https://softeer.ai/practice/info.do?idx=1&eid=1309) <br>
&nbsp; &nbsp; <b>특징:</b> 1. array에 index를 잘 사용해서 Complexity를 줄일수 있다. PriorityQueue를 이용하거나, Collecions Sort를 하고 index를 맞춰야 함. <br><br>

<b>12. 암호화페어</b> - Encoding 문제 (https://softeer.ai/practice/info.do?idx=1&eid=804) <br>
&nbsp; &nbsp; <b>특징:</b> 1. 암호길이가 1일때 예외를 못잡아서 고생했다, 데이터 프로세싱 마지막 처리도 (edge조건들) - 깔끔하게 Queue같은걸로 접근하자 <br><br>

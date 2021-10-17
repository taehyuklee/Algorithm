#상어 초등학교 문제
'''
교실 NXN격자로 되어 있다
학생수는 N^2명이다

자리정하는 날
1번부터 N^2번 학생들

선생님은 학생 순서를 정하고 각 학생이 좋아하는 4명 조사

3
4 2 5 1 7
3 1 9 4 5
9 8 1 2 3
8 1 9 3 4
7 2 3 4 8
1 9 2 5 7
6 5 2 3 4
5 1 9 2 8
2 9 3 1 4
'''

from sys import stdin
from collections import deque
from itertools import combinations
import copy

#Make input part
input = stdin.readline

N = int(input())

S_List =[]
visit = []

for _ in range(N**2):
    #print(input().strip().split())
    S_List.append(list(map(int, input().strip().split())))

for _ in range(N**2):
    visit.append([False]*N)

#Direction setting - 상하좌우
dy = [-1, 1, 0, 0]
dx = [0, 0, -1, 1]


Class =[[0]*N for _ in range(N)]
#print(Class)

dic_stu={}
for S, p1, p2, p3, p4 in (S_List):
    dic_stu[S] = [p1, p2, p3, p4]

def Cal_sat(Class):

    manjok = 0; count_favor = 0;

    for j in range(N):
        for i in range (N):

            #4방향에 p1, p2, p3, p4가 몇명 앉아있는지 Count
            for d in range(len(dy)):
                side_y = j + dy[d]
                side_x = i + dx[d]

                if side_y<0 or side_x<0 or side_y>=N or side_x>=N:
                    continue
                if Class[side_y][side_x] in dic_stu[Class[j][i]]:
                    count_favor+=1
                # favor 0 = 0, 1 ==> 1, 2==> 10, 3==> 100, 4==> 1000

            if count_favor ==0: manjok +=0
            elif count_favor ==1: manjok +=1
            elif count_favor ==2: manjok +=10
            elif count_favor ==3: manjok +=100
            elif count_favor ==4: manjok +=1000

            #print("sat", count_favor)
            count_favor =0

    return manjok



for S, p1, p2, p3, p4 in (S_List):

    favor = {}; vac = {}; row = {}; col = {}
    #Class를 전반적으로 돌아가면서
    for j in range(N): #j행, i 열
        for i in range (N):

            if visit[j][i] == False:
                love_count = 0
                vaccancy = 0

                #4방향에 p1, p2, p3, p4가 몇명 앉아있는지 Count
                for d in range(len(dy)):
                    side_y = j + dy[d]
                    side_x = i + dx[d]

                    if side_y<0 or side_x<0 or side_y>=N or side_x>=N:
                        continue

                    if Class[side_y][side_x] ==p1: love_count +=1
                    if Class[side_y][side_x] ==p2: love_count +=1
                    if Class[side_y][side_x] ==p3: love_count +=1
                    if Class[side_y][side_x] ==p4: love_count +=1
                    if Class[side_y][side_x] ==0: vaccancy += 1

                #Lover.append([(j,i), love_count, vaccancy, i,j])
                favor[(j,i)] = love_count
                vac[(j,i)] = vaccancy
                row[(j,i)] = j
                col[(j,i)] = i
            #Lover 자리, 좋아하는 사람, 빈 자리, 행, 렬
            #print("좋아하는 사람 List", Lover)
            #이 문제 풀고나서 좀 더 효과적으로 정리할수 있는 방법 없을까 찾아보기

    #print("\nStudent",S)
    #print("favor",favor)
    #print("vac", vac)
    #print("row", row)
    #print("col", col)
    #print("\n")
    #앞에서 애시당초 위에서 append를 빼줘야한다 Case남은 자리에 대해서만 할 수 있게


    #Favor student
    favor2={}; vac2 ={}; row2={}; col2={}; temp={}
    for k,v in favor.items():
        if v == max(favor.values()):favor2[k] = v #append할때 자리 비어있는지 확인하고 후보를 골라야한다
    #print("Favor dic check", favor2)

    #Favor student pick
    if len(favor2) == 1:
        for k in favor2.keys():
            y = k[0]; x = k[1]
        Class[y][x] =S; visit[y][x] = True;
        continue
    #print("Favor check", Class)

    #Vacancy part 위에서 선택된 인자와 일치하는 vacancy value짝 찾기
    if len(favor2)>1:
        for k,v in favor2.items():
            for kv, vv in vac.items():
                if k == kv: temp[kv] = vv #위에서 내려온 key에 대해서 일치하는 vac value를 받는다
    #위에서 선택된 인자의 vacancy중 가장 큰 값을 dictionary에 넣기
    for k,v in temp.items():
        if v == max(temp.values()):vac2[k] = v #Vacancy
    #print("Vac dic check",vac2)
    #Vacancy 기준으로 앉히기
    if len(vac2) == 1:
        for k in vac2.keys():
            y = k[0]; x = k[1]
        Class[y][x] =S; visit[y][x] = True;
        continue
    #print("Vacancy check", Class)

    # Row
    temp = {}
    if len(vac2) > 1:
        for k, v in vac2.items():
            for kv, vv in row.items():
                if k == kv: temp[kv] = vv  # 위에서 내려온 key에 대해서 일치하는 vac value를 받는다
    # 위에서 선택된 인자의 vacancy중 가장 큰 값을 dictionary에 넣기
    for k, v in temp.items():
        if v == min(temp.values()): row2[k] = v  # Vacancy
    #print("Row dic check", row2)
    # Row 기준으로 앉히기
    if len(row2) == 1:
        for k in row2.keys():
            y = k[0]; x = k[1]
        Class[y][x] =S; visit[y][x] = True;
        continue
    #print("Row check", Class)

    # Col
    temp = {}
    if len(row2) > 1:
        for k, v in row2.items():
            for kv, vv in col.items():
                if k == kv: temp[kv] = vv  # 위에서 내려온 key에 대해서 일치하는 vac value를 받는다
    # 위에서 선택된 인자의 vacancy중 가장 큰 값을 dictionary에 넣기
    for k, v in temp.items():
        if v == min(temp.values()):col2[k] = v  # Vacancy
    #print("Col dic check", col2)

    # col 기준으로 앉히기
    if len(col2) == 1:
        for k in col2.keys():
            y = k[0]; x = k[1]
        Class[y][x] =S; visit[y][x] = True;
        continue
    #print("Col check", Class)

#만족도 구하기
print(Cal_sat(Class))


#favor 0 = 0, 1 ==> 1, 2==> 10, 3==> 100, 4==> 1000.
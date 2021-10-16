import copy
from itertools import combinations
from collections import deque
from sys import stdin

input = stdin.readline

#input
N,M = list(map(int, input().split()))

#make board
board = [list(map(int, input().split())) for _ in range(N)]

#make visit graph for exception
visit = [[False]*N for _ in range(N)]

#이거 갯수대로 어떻게 받아올지가 고민이네
dir =[]
for _ in range(M): #명령 몇 번 내릴건지
    d,s = list(map(int, input().split()))
    dir.append([d,s])



Cloud_old = [[N-1,0],[N-1,1],[N-2,0],[N-2,1]]
next_cloud =[]
#왼, 왼위, 위, 오위, 오, 아오, 아, 아왼
dy = [0,-1,-1,-1,0,1,1,1]
dx = [-1,-1,0,1,1,1,0,-1]

for d, s in (dir):#8가지 명령을 하나씩 수행해본다

    visit1 = copy.deepcopy(visit)
    next_cloud = [] #next_ cloud 초기화 안해줫었네

    # 구름의 이동
    for y, x in (Cloud_old):  # d-1하는 이유는 index상의 차이때문이다
        next_x = (x + dx[d - 1] * s) % N
        next_y = (y + dy[d - 1] * s) % N  # N으로 나눈 나머지 Periodic boundary형성을 시킨다

        if next_x < 0:
            next_x = N + next_x

        if next_y < 0:
            next_y = N + next_y

        visit1[next_y][next_x] = True
        next_cloud.append([next_y, next_x])

    # 비바라기
    for y, x in (next_cloud):
        board[y][x] += 1

    # 대각선 위치 1,3,5,7
    for y, x in (next_cloud):
        for _ in ([1, 3, 5, 7]):
            cross_y = y + dy[_]
            cross_x = x + dx[_]

            if cross_y < 0 or cross_y >= N or cross_x < 0 or cross_x >= N:
                continue
            if board[cross_y][cross_x] != 0: #문자열인지 숫자인지 정확히 판단했어야 함 '0'라고 되어 있었음

                board[y][x] += 1

    # 비구름 위치 제외하고 2 이상의 board에서는 2씩 감소시킨다
    # 문제파악 잘못함 구름이 생성되고 그 곳에서 2가 사라지는거임
    Cloud_old = []
    for j in range(N):
        for i in range(N):
            if visit1[j][i] == False:
                if board[j][i] >= 2:
                    Cloud_old.append([j,i])
                    board[j][i] -= 2


sum1 = 0
for j in range(N):
    for i in range(N):
        sum1 += board[j][i]

print(sum1)
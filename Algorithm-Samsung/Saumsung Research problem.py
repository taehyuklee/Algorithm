from sys import stdin
import copy
from collections import deque
from itertools import combinations

input = stdin.readline

'''
연구소 바이러스 퍼짐 --> 연구소 벽을 세울려고 함 

연구소 크기 NXM 직사각형 연구소는 빈칸, 벽으로 이루어져 있다 

일부 칸은 바이러스가 존재 --> 이 바이러스는 상하좌우로 인접한 빈 칸 모두 퍼져나갈수 있다 

새로 세울수 있는 벽의 개수는 3개, 꼭 3개를 세워야 함 

0은 빈칸 1은 벽 2는 바이러스
'''

#Input part#
N,M = list(map(int, input().split()))
board = [list((input().replace(' ','').strip())) for _ in range(N)]

visit =[[False]*M for _ in range(N)]

#print("board check", board)

def count_zero(board):

    global N, M
    count_z = 0

    for j in range(M):
        for i in range(N):
            if board[i][j] == '0':
                count_z += 1
    return count_z


def BFS(Vy, Vx, visit, board):

    global N, M

    #Visit 및 움직일수 있는 설정 만들기

    #Virus 위치확인
    Queue = deque()
    cur_count = 0
    #상하좌우
    dy = [-1,1,0,0] #행
    dx = [0,0,-1,1] #열

    Queue.append([Vy, Vx, cur_count])


    #While문으로 진행하기
    while Queue:

        #Queue pop here
        cur_Vy, cur_Vx, cur_count = Queue.popleft()

        #End condition

        #시뮬레이션 (내부에서 종료조건)

        for _ in range(len(dx)):

            next_Vy = cur_Vy + dy[_]
            next_Vx = cur_Vx + dx[_]

            #print("Check current virus position y, x", cur_Vy, cur_Vx)
            #print("Check next virus position y, x", next_Vy, next_Vx)

            #예외조건
            if next_Vy <0 or next_Vx <0 or next_Vy >=N or next_Vx >=M:
                continue #벽 밖으로 넘어가면 continue Queue

            if board[next_Vy][next_Vx] == '1':
                continue


            if board[next_Vy][next_Vx] != '1' and  board[next_Vy][next_Vx] == '0': #바이러스 확산 구현
                board[next_Vy][next_Vx] = '2'


            #Queue append or not

            if visit[next_Vy][next_Vx] == False:
                visit[next_Vy][next_Vx] = True
                Queue.append([next_Vy, next_Vx, cur_count+1])

    return board


#0의 Position 파악하기
zero_list =[]; zero_com = []
two_list =[]
for j in range(M):
    for i in range(N):
        if board[i][j] == '0':
            zero_list.append([i,j])
        if board[i][j] == '2':
            two_list.append([i,j])

#print(zero_list)
#print(two_list)

#말뚝을 박을수 있는 모든 Combination을 가지고 옴

for i in combinations(zero_list, 3): #Combination 이거 암기
    #print("Combination check")
    #print(i)
    zero_com.append(i)


#3개의 조합씩 나온 List를 받아온다
re_list=[] # return list
for w in (zero_com):

    board2 = copy.deepcopy(board)
    visit2 = copy.deepcopy(visit)

    for i,j in w:
        #print("check _ and i, j", _, i,j)
        board2[i][j] = '1'

    for Vy, Vx in two_list: #각 바이러스마다 퍼질수 있는거 다 퍼뜨리기
        #print(Vy, Vx)
        board2[:] = BFS(Vy, Vx, visit2[:], board2[:])


    #print("Case transition")
    #print("board check")
    #print(board2)

    ret = count_zero(board2[:])
    re_list.append(ret)

#print(re_list)
print(max(re_list))

#print(len(zero_com))


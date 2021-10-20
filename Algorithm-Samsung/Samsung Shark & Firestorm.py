from sys import stdin
import copy
from collections import deque
#stdin = open("file name", 'r')

#Input part
input = stdin.readline

N, Q = list(map(int, input().split()))
board = [list(map(int, input().strip().split())) for _ in range(2**N)]
L_list = []

#L = int(input())
L_list =list(map(int,input().split()))


#양 옆 위 아래 확인 (상하좌우)
dy = [-1,1,0,0]
dx = [0,0,-1,1]

def rotate_90(local_board, L):

    local_sub = copy.deepcopy(local_board)

    for j in range(len(local_board)):
        for i in range(len(local_board)):
            local_board[j][i] = local_sub[(len(local_board)-1)-i][j]

    return local_board

def BFS(board,j ,i):
    
    #기본적인 방향 세팅
    Q = deque()
    visit = [[False]*2**N for _ in range(2**N)]
    count = 0;
    if board[j][i] != 0 and visit[j][i] ==False:
        #첫번째꺼가 Count가 안되네
        Q.append([j,i])
        count +=1; visit[j][i] = True

    while len(Q) !=0:
        #Pop from Q
        y, x = Q.popleft()

        #시뮬레이션
        for _ in range(4):
            ny = y + dy[_]
            nx = x + dx[_]

            if -1< ny <2**N and -1<nx<2**N and visit[ny][nx] ==False:
                #예외조건
                if board[ny][nx] != 0:
                    #Push Q
                    count +=1
                    Q.append([ny,nx])
                    visit[ny][nx] = True
    return count


for L in (L_list):
    #Divide grids
    Small_l = 2**L

    local_board = [[0]*Small_l for _ in range(Small_l)]
    for j in range(2**N):
        for i in range(2**N):

            if -1<j*Small_l and  j*Small_l<2**N and -1<i*Small_l and  i*Small_l<2**N:

                #Small grids 범위 잡기
                for s_j in range(Small_l):
                    for s_i in range(Small_l):
                        local_board[s_j][s_i] = board[j*Small_l+s_j][i*Small_l+s_i]

                #Local잡았고 이제 회전시키는 부분을 해주면 된다.
                #회전시켜서 다시 Original board에 넣어주기
                #print("Local_board", local_board)
                local_board = rotate_90(local_board, L)
                #print("After rotate board", local_board)

                #다시 원래대로 넣어놓기
                for s_j in range(Small_l):
                    for s_i in range(Small_l):
                        board[j*Small_l+s_j][i*Small_l+s_i] = local_board[s_j][s_i]
    #얼음 녹이기
    board_1 = [[0] * (2 ** N) for _ in range(2 ** N)]
    for j in range(2**N):
        for i in range(2**N):
            ice_count = 0
            for _ in range(4):
                y = j + dy[_]
                x = i + dx[_]
                if -1< y < 2**N and -1<x < 2**N and board[y][x] != 0:
                    ice_count +=1
            if ice_count <3 and board[j][i] !=0: #중간에 녹여버리면 안된다. (그래서 deep copy써서 해줘야한다)
                board_1[j][i] = board[j][i] - 1
            else:
                board_1[j][i] = board[j][i]
    board = copy.deepcopy(board_1)
#BFS 로 연결시키자
ice_amount = []
for j in range(2**N):
    for i in range(2**N):
        ice_lump = BFS(board, j, i)
        ice_amount.append(ice_lump)


max_list = []
sum = 0; Max_val = max(board); count = 0
for j in range(2**N):
    for i in range(2**N):
        sum += board[j][i]
        max_list.append(board[j][i])

print(sum)
print(max(ice_amount))

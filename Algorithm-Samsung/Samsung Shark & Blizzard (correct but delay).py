import copy
from sys import stdin

input = stdin.readline
N, M = list(map(int, input().split()))
board =[list(map(int, input().strip().split())) for _ in range(N)]
order_list = []
for order in range(M):
    d, s = list(map(int, input().split()))
    order_list.append([d,s])
broken_marble = [0,0,0,0] #0,1,2,3

#상하좌우
dy = [-1,1,0,0]
dx = [0,0,-1,1]
#indexing
spiral_inx = {}
def inx():
    i =1; ref_depth = 1; turn=0; cur_depth =0
    dy_spiral = [0,1,0,-1]
    dx_spiral = [-1,0,1,0]
    y0 = N//2; x0 = N//2
    while i<N**2:
        ny = y0 + dy_spiral[turn]
        nx = x0 + dx_spiral[turn]

        spiral_inx[i] = (ny,nx)
        y0 = ny; x0 =nx

        cur_depth +=1; i+=1

        if cur_depth == ref_depth:
            if turn in [1,3]:
                ref_depth +=1
            turn = (turn+1)%4
            cur_depth = 0

def blizzard(board):
    x0 = N//2; y0 = N//2
    for dis in range(1,s+1):
        ny = y0 + dy[d-1]*dis
        nx = x0 + dx[d-1]*dis
        board[ny][nx] = -100

def pulling(board):
    #구슬 땡기기
    while True:
        for k, v in spiral_inx.items():
            if board[v[0]][v[1]] == -100:
                y_o = v[0]; x_o = v[1]; i=0
                while (k+i) <N**2:
                    y_n, x_n = spiral_inx[k+i]
                    board[y_o][x_o] = board[y_n][x_n]
                    y_o = y_n; x_o=x_n; i+=1
        if all(-100 not in l for l in board):
            return

def bomb_marble(board, broken_marble):
    count_series=1; series=[]
    for k, v in spiral_inx.items():
        if k==1:y=v[0]; x=v[1]; value_o = board[y][x]; series.append([y,x]) #Initial condition에서만
        y_n, x_n = spiral_inx[k+1]
        next_value = board[y_n][x_n]
        if (y_n,x_n) == (0,0): break

        if len(series) >=4 and next_value != value_o:
            for y, x in series:
                if board[y][x] == 1: broken_marble[1] +=1
                if board[y][x] == 2: broken_marble[2] +=1
                if board[y][x] == 3: broken_marble[3] +=1
                board[y][x] = -100
            series =[]

        if next_value != value_o:
            value_o = next_value
            count_series=1
            series =[]
            series.append([y_n,x_n])
            pass

        elif next_value == value_o:
            count_series+=1
            series.append([y_n,x_n])

def change_marble(board):
    One_D_list =[]; One_I_list =[]
    for k, v in spiral_inx.items(): #뱀 또아리를 풀듯
        One_D_list.append(board[v[0]][v[1]])

    num = 0
    while (num+1)<N**2-1:
        if num ==0: value_o = One_D_list[num]; One_I_list.append(num)
        next_value = One_D_list[num+1]

        if next_value == value_o: One_I_list.append(num+1)

        elif next_value != value_o:
            change_count = len(One_I_list)
            change_num = value_o

            for _ in (sorted(One_I_list, reverse=True)): #일단 싹다 삭제하고
                One_D_list.pop(_)#제거는 거꾸로 한다 처음부터하면 밀리는걸 생각해야함

            #기준 Index로부터 +1 index까지 change_count, num 순서대로 추가한다
            length_same = len(One_I_list)
            index = One_I_list[0] #대신 채워넣는건 순서대로

            One_D_list.insert(index, change_count)
            One_D_list.insert(index+1, change_num)
            num = index+2
            One_I_list=[]
            value_o = next_value; One_I_list.append(num)

            #넘어가면 자르는 부분
            Sub_list = One_D_list[0:N**2-1]
            One_D_list = [0]*(N**2-1)
            if len(Sub_list)< N**2-1:
                One_D_list[0:len(Sub_list)] = (Sub_list[0:len(Sub_list)])
            else:
                One_D_list[:] = Sub_list[0:N**2-1]
            continue
        num+=1

    i=0
    for k, v in spiral_inx.items(): #뱀 또아리를 말듯
        board[v[0]][v[1]] = One_D_list[i]; i+=1

#main
inx() #indexing spiral
for d,s in order_list:
    blizzard(board) #blizzard skill
    while True: #끌고 터뜨리고 끌고 터뜨리고
        pulling(board)
        bomb_marble(board, broken_marble)
        if all(-100 not in l for l in board):
            break
    #구슬 변환
    change_marble(board)

ans = broken_marble[1]*1 + broken_marble[2]*2 + broken_marble[3]*3
print(ans)
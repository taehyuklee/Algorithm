import copy
from sys import stdin
from collections import deque

#stdin = open("file name", 'r')
input = stdin.readline

N, M = list(map(int, input().split()))
board = [list(map(int, input().strip().split())) for _ in range(N)]

#direction for Search (상하좌우)
dy = [-1,1,0,0]
dx = [0,0,-1,1]

def BFS (j,i, value, visit, board):
    Q = deque(); count=0
    group =[]; rain_bow = []
    if visit[j][i] == False:
        Q.append([j,i])
        visit[j][i] = True
        count+=1; group.append([j,i])

    #시뮬레이션
    while len(Q) !=0:
        y,x = Q.popleft()

        for _ in range(4):
            ny = y + dy[_]
            nx = x + dx[_]
            #예외조건
            if -1<nx<N and -1<ny<N:
                if board[ny][nx] == value or board[ny][nx] ==0:
                    if visit[ny][nx] == False:
                        Q.append([ny, nx])
                        count +=1; group.append([ny, nx])
                        visit[ny][nx] = True

                    if board[ny][nx] ==0:
                        rain_bow.append([ny,nx])

    for y, x in rain_bow:
        visit[y][x] = False

    if len(group)>=2:
        return group
    else: return

def gravity(board):
    for _ in range(N):
        for j in range(N-2,-1,-1):
            for i in range(N):
                if board[j][i] != -1:
                    if board[j+1][i] == -100:
                        board[j+1][i] = board[j][i]
                        board[j][i] = -100
    return board

def rotate(board):
    board_copy = copy.deepcopy(board)
    for i in range(N):
        for j in range(N):
            board[i][N-1-j] = board_copy[N-1-j][N-1-i]
    return board

def Max_group(total_group):
    max_group = []
    max_length = []
    for group in total_group:
        if group != None:#None값 처리
            max_group.append(group)
            max_length.append(len(group))

    #가장 최대 길이의 그룹들을 모아둔다.
    max_group2 = []
    for group in max_group:
        if len(group) == max(max_length):
            max_group2.append(group)
    return max_group2

def Block_info(max_group2):
    #무지개 그룹이 가장 많은 블록 그룹을 선택
    group ={}; rain ={}; ref_row_d={}; ref_col_d={};i=0
    for g in max_group2:
        rain_bow_c=0
        row = {}; column={};
        for y, x in g:
            if board[y][x] == 0: #무지개 블록
                rain_bow_c +=1;
            if board[y][x] >0: #무지개 블록 아닌거
                row[(y,x)] = y; column[(y,x)] = x
        #Reference point
        row2 ={}; col2 ={}; temp = {}
        #Row condition
        for k, v in row.items():
            if v == min(row.values()): row2[k] = v
        if len(row2) == 1:
            for k, v in row2.items():
                ref_row = k[0]; ref_col = k[1]

        else: #Col condition
            for k, v in row2.items(): #위에서 받아온 인자를 Column 인자와 일치하는걸 일단 따온다.
                for ki, vi in column.items():
                    if ki == k: temp[ki] = vi
            for k, v in temp.items():
                if v == min(temp.values()): col2[k] = v #col2에 마지막 Point 넣기
            for k, v in col2.items(): ref_row = k[0]; ref_col = k[1]

        group[i]=g; rain[i]=rain_bow_c; ref_row_d[i]=ref_row; ref_col_d[i]= ref_col
        i +=1
    return group, rain, ref_row_d, ref_col_d

score = 0
while True:
    visit = [[False]*N for _ in range(N)]
    #Grouping한다
    total_group = []
    for j in range(N):
        for i in range(N):
            if board[j][i] != 0 and board[j][i] !=-1 and board[j][i] !=-100 and visit[j][i] ==False: #board[j][i] != 0?
                normal_value = board[j][i]
                group_indx = BFS(j,i, normal_value, visit, board)
                total_group.append(group_indx)

    #중복되는 최대 그룹 List를 받아온다
    max_group2=Max_group(total_group)

    if len(max_group2) ==0:
        break #break조건

    if len(max_group2)==1:
        max_group_f = copy.deepcopy(max_group2[0]) #List하나 벗겨준다.

    if len(max_group2)>=2:
        group, rain, ref_row_d, ref_col_d = Block_info(max_group2)
        temp ={}; rain2 ={}; ref_row_d2={}; ref_col_d2={}
        #무지개 블록이 많은거 선택
        for k, v in rain.items():
            if v == max(rain.values()): rain2[k] = v
        if len(rain2) == 1:
            for k, v in rain2.items(): max_group_f = copy.deepcopy(group[k]); pass
        #행이 제일 큰거
        temp={}
        for k, v in rain2.items(): #위에서 뽑힌 인자들을 ref_row_d group으로 가져오기
            for ki, vi in ref_row_d.items():
                if ki == k: temp[ki] = vi
        for k, v in temp.items(): #위에서 받아온 인자중 가장 작은 row를 갖는걸 뽑아내기
            if v == max(temp.values()): ref_row_d2[k] = v
        if len(ref_row_d2) == 1:
            for k, v in ref_row_d2.items():  max_group_f = copy.deepcopy(group[k]); pass
        #열이 제일 큰거
        temp ={}
        for k, v in ref_row_d2.items(): #위에서 뽑힌 인자들을 ref_row_d group으로 가져오기
            for ki, vi in ref_col_d.items():
                if ki == k: temp[ki] = vi
        for k, v in temp.items(): #위에서 받아온 인자중 가장 작은 col를 갖는걸 뽑아내기
            if v == max(temp.values()): ref_col_d2[k] = v
        if len(ref_col_d2) == 1:
            for k, v in ref_col_d2.items():  max_group_f = copy.deepcopy(group[k])

    #제거하고 점수 얻기
    B = 0;
    for y, x in max_group_f: board[y][x] = -100; B+=1
    score += B**2

    #중력 작용하기
    board = copy.deepcopy(gravity(board)) #gravity
    board = copy.deepcopy(rotate(board)) # roatation
    board = copy.deepcopy(gravity(board)) #gravity

print(score)
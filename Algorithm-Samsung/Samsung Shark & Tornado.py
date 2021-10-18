#상어와 토네이도
import copy
from collections import deque
from itertools import combinations
from sys import stdin
import copy
import math

input = stdin.readline

N = int(input())

board = [list(map(int, input().strip().split())) for _ in range(N)]

#좌하우상 (상하좌우)
dy = [0,1,0,-1] #행
dx = [-1,0,1,0] #렬

cur_depth = 0
ref_depth = 1
turn=0; ans =0; temp=0
y = (N + 1) // 2 -1  # 상어 y위치 index때문
x = (N + 1) // 2 -1  # 상어 x위치 index때문

sand_left_rate = [[0,0,2,0,0],[0,10,7,1,0],[5,'a',0,0,0],[0,10,7,1,0],[0,0,2,0,0]]
sand_down_rate = [[0,0,0,0,0],[0,1,0,1,0],[2,7,0,7,2],[0,10,'a',10,0],[0,0,5,0,0]]
sand_right_rate = [[0,0,2,0,0],[0,1,7,10,0],[0,0,0,'a',5],[0,1,7,10,0],[0,0,2,0,0]]
sand_up_rate = [[0,0,5,0,0],[0,10,'a',10,0],[2,7,0,7,2],[0,1,0,1,0],[0,0,0,0,0]]
#sand_board = [[0]*5 for _ in range(5)]

def spread(tor_j,tor_i, dir, origin):

    global ans, temp, N, board

    sand_board = [[0]*5 for _ in range(5)]

    if dir ==0: #"Left"
        sand_board = copy.deepcopy(sand_left_rate)

    elif dir ==1: #"down"
        sand_board = copy.deepcopy(sand_down_rate)

    elif dir ==2: #"right"
        sand_board = copy.deepcopy(sand_right_rate)

    elif dir ==3: #"up"
        sand_board = copy.deepcopy(sand_up_rate)

    temp = 0 #이거 초기화 안한게 너무 Critical했다
    #rem_a =[]
    for j in range(5):
        for i in range(5):
            rel_y = (tor_j-2)+j
            rel_x = (tor_i-2)+i

            if sand_board[j][i] != 'a' and sand_board[j][i]!=0: #a가 아닐때
                #print(sand_board[j][i])
                if -1<rel_x<N and -1<rel_y <N:
                    board[rel_y][rel_x] += board[tor_j][tor_i]*sand_board[j][i]//100
                else:
                    ans += board[tor_j][tor_i]*sand_board[j][i]//100

                temp += board[tor_j][tor_i]*sand_board[j][i]//100 #밖에 나가든 안에 있든 총량을 계싼해야하니까
                #print("temp accu check", board[tor_j][tor_i])
            elif sand_board[j][i] == 'a': #a일때
                rem_a = (j,i)

    #예외처리 'a'에 대한 것
    a_y = rem_a[0]; a_x = rem_a[1]


    #print("origin & temp", board[tor_j][tor_i], temp)
    #print("check minus", board[tor_j][tor_i] - temp)
    if -1 < a_x + (tor_i-2)< N and -1 < a_y + (tor_j-2) < N:
        board[a_y + (tor_j-2)][a_x + (tor_i-2)] += board[tor_j][tor_i] - temp
        #print("A coordinate & value", a_y + (tor_j-2),a_x + (tor_i-2), board[a_y + (tor_j-2)][a_x + (tor_i-2)])

    else:
        ans += board[tor_j][tor_i] - temp

    board[tor_j][tor_i] = 0 #0으로




#토네이도 도는 거 구현
while x>-1 and y>-1:

    #나아가는 방향
    #print("turn", turn)
    #print("dy, dx", dy[turn], dx[turn])
    y = y +dy[turn] #행
    x = x +dx[turn] #열


    cur_depth +=1
    #print("cur_depth", cur_depth)
    #print("before ref_depth", ref_depth)

    #옮겨간 토네이도 위치와 그때의 방향을 넘겨준다.
    spread(y, x, turn, board[y][x])
    #print("y,x", y,x)
    #print(board)


    if cur_depth == ref_depth:
        if turn in [1,3]:
            ref_depth +=1

        turn  = (turn+1)%4
        cur_depth = 0

    #print("after ref_depth", ref_depth)
    #print("\n")

print(ans)

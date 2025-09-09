from collections import deque
from copy import deepcopy

N, M = map(int, input().split(" "))
board = [list(map(int, input().split(" "))) for _ in  range(M)]
visit_board = [[False]*N for _ in range(M)]
dx = [0,0,1,-1]
dy = [1,-1,0,0]

print(visit_board)

#O(N^2)
start_list = []

for i in range(M):
    for j in range(N):
        if board[i][j] == 1:
            start_list.append([i, j])

print(start_list)

def bfs(start_list):
    Q = deque()
    max_cnt = 0

    for start_p in start_list:
        x, y = start_p
        cnt = 0
        Q.append([x, y, cnt])
        visit_board[x][y] = True

    while Q:
        x, y, cnt = Q.popleft()

        max_cnt = max(max_cnt, cnt)

        for direct in range(4):
            new_x = x + dx[direct]
            new_y = y + dy[direct]

            if new_x >= M or new_x < 0 or new_y >= N or new_y < 0: continue

            if board[new_x][new_y] ==0 and visit_board[new_x][new_y] == False:
                board[new_x][new_y] = 1
                visit_board[new_x][new_y] = True
                new_cnt = cnt + 1

                Q.append([new_x, new_y, new_cnt])
    return max_cnt

day = bfs(start_list)

print(day)

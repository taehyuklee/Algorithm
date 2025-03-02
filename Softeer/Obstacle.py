
from collections import deque
from sys import stdin

input = stdin.readline

def BFS(x: int, y: int, board, visit_board, block_idx: int):
    Q = deque()

    dx = [-1,1,0,0]
    dy = [0,0,-1,1]

    coord_init = [x,y]
    visit_board[x][y] = True
    count_block = 1

    Q.append(coord_init)

    while Q:

        x, y = Q.popleft()

        for dir in range(4):
            new_x = x + dx[dir]
            new_y = y + dy[dir]

            if new_x<0 or new_x>=N or new_y<0 or new_y>=N:
                continue

            if board[new_x][new_y] !=0 and visit_board[new_x][new_y] == False:
                Q.append([new_x, new_y])
                visit_board[new_x][new_y] = True
                count_block+=1

    block_idx+=1

    return block_idx, count_block



N = int(input().strip())
# board = [[0] *N for _ in range(N)]
# print(input())
board =  [list(map(int, input().strip())) for _ in range(N)]
visit_board = [[False] * N for _ in range(N)]

block_idx = 0
answer_list = []

for i in range(len(board)):
    for j in range(len(board[i])):
        # print(board[i][j])
        # print("현재 좌표는 : " + str(i) + "  " + str(j))

        if board[i][j] != 0 and visit_board[i][j] == False:
            block_idx, count = BFS(i,j, board, visit_board, block_idx)

            answer_list.append((block_idx, count))

sorted_list = sorted(answer_list, key=lambda x: x[1])
# 총 블록의 개수
print(block_idx)
for i in range(len(sorted_list)):
    print(sorted_list[i][1])

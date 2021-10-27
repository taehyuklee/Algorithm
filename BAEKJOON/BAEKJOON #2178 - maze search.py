from collections import deque

N, M = list(map(int, input().split()))
board = [list(map(int, input())) for _ in range(N)]

#상하좌우
dy = [-1,1,0,0]
dx = [0,0,-1,1]


def BFS(board, y, x):

    Q = deque()
    count = 0
    visit = [[False] * M for _ in range(N)]
    num_board = [[0] * M for _ in range(N)]

    if visit[y][x] == False:
        visit[y][x] = True
        count = 1
        Q.append([y,x, count]) #자꾸 Q에서 append할때 tuple로하거나 list로해야하는데 따로 넣네.

    while len(Q) !=0:

        y, x, count = Q.popleft()
        num_board[y][x] = count

        for _ in range(4):
            ny = y +dy[_]
            nx = x +dx[_]

            if -1<nx<M and -1<ny<N: #x를 가로축으로 잡았으니 열 M이되는거구 y를 세로축으로 잡았으니 행 N이되는거
                if board[ny][nx] !=0 and visit[ny][nx] == False:
                    count_new = count + 1
                    Q.append([ny, nx, count_new])
                    visit[ny][nx]=True

    return num_board

#main
y=0; x=0
num_board=BFS(board,y,x)
print(num_board[N-1][M-1])
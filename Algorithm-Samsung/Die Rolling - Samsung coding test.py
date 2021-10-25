from collections import deque

N, M, K = list(map(int, input().split())) #행 렬
board = [list(map(int, input().strip().split())) for _ in range(N)]

#상하좌우
dy = [-1,1,0,0]
dx = [0,0,-1,1]

def BFS(board,y,x):

    visit = [[False]*M for _ in range(N)]

    Q = deque()
    ref_value = board[y][x]
    count=0
    if visit[y][x] ==False:
        Q.append([y,x])
        visit[y][x] = True
        count=1

    while len(Q) != 0:

        y,x = Q.popleft()

        for _ in range(4):
            ny = y + dy[_]
            nx = x + dx[_]

            #예외조건
            if -1<nx<=M-1 and -1<ny<=N-1 and board[ny][nx] == ref_value and visit[ny][nx] == False:
                Q.append([ny,nx])
                count+=1
                visit[ny][nx] = True

    return ref_value, count

def die_move(cur_die, dir, un_fold):

    if dir==0: die_num = un_fold[0]; un_fold[1]=cur_die; un_fold[0]=7-cur_die #위로 굴러가면 현재 있는게 아래로 기준이 됨#up
    if dir==1: die_num = un_fold[1]; un_fold[0]=cur_die; un_fold[1]=7-cur_die #down
    if dir==2: die_num = un_fold[2]; un_fold[3]=cur_die; un_fold[2]=7-cur_die #left
    if dir==3: die_num = un_fold[3]; un_fold[2]=cur_die; un_fold[3]=7-cur_die #right

    return die_num, un_fold

cur_die = 6
dir =3
un_fold = [2,5,4,3] #상하좌우 #initial 6기준
yo=0; xo=0
score = 0

for order in range(K):
    #print("check point",dir,dy[dir],dx[dir])
    ny = yo + dy[dir]
    nx = xo + dx[dir]
    #벽에 튕기는거 예외 케이스
    if nx<0: nx=1; dir=3
    if nx>M-1: nx=M-2;dir=2
    if ny>N-1: ny=N-2; dir=0
    if ny<0: ny=1; dir=1

    #print("dire", dir)
    cur_die, un_fold = die_move(cur_die, dir, un_fold) #주사위 이동
    #print(un_fold)
    board_num = board[ny][nx]
    #print("CUR_DIE""board""Coord",cur_die,board_num,(ny,nx))
    ref_value, count = BFS(board,ny, nx) #BFS
    #print(ref_value, count)
    score += ref_value*count

    if cur_die < board_num: #좌
        if dir==0: dir = 2; pass
        elif dir==1: dir = 3; pass
        elif dir==2: dir = 1; pass
        elif dir==3: dir = 0;

    if cur_die > board_num: #우
        if dir==0: dir = 3; pass
        elif dir==1: dir = 2; pass
        elif dir==2: dir = 0; pass
        elif dir==3: dir = 1;

    if cur_die == board_num:
        dir = dir
    yo =ny; xo=nx

print(score)
import collections
from sys import stdin

input = stdin.readline

def BFS(strat_ry, start_rx, start_by, start_bx, board, visit):

    cur_count = 0
    next_count = 0
    ret = -1 #실패한 건 초기화할때부터 그렇게 두도록 하자
    # 상 하 좌 우 map[y][x] #이건 암기해놓자
    dy = [-1, 1, 0, 0]
    dx = [0, 0, -1, 1]

    Queue = collections.deque() #큐 생성

    cur_ry = start_ry
    cur_rx = start_rx
    cur_by = start_by
    cur_bx = start_bx

    #처음부분 Queue에 넣는다다
    visit[cur_ry][cur_rx][cur_by][cur_bx] = True # 이부분 넣었고 답지 참조
    Queue.append([cur_ry ,cur_rx ,cur_by ,cur_bx ,cur_count]) #처음 상태 Queue에 Push해준다.

    #그 안에 있는 내용들
    while len(Queue) !=0:

        #Queue pop해줘야 한다
        cur_ry ,cur_rx ,cur_by ,cur_bx ,cur_count = Queue.popleft()

        #--------------- end 조건 ---------------#
        if cur_count >10:
            return ret

        #--------------- 진행 문 ---------------#
        for i in range(4): #모든 방향으로 뻗어가는 Case를 (4)

            next_ry = cur_ry; next_rx = cur_rx
            next_by = cur_by; next_bx = cur_bx

            #빨간공
            while True: #다음 node로 넘어가는 부분

                if board[next_ry][next_rx] !='O' and board[next_ry][next_rx] !='#': #뒤에 #조건이 없으면 망한다 #에 부딪히고 (솔루션 봄)
                    next_ry += dy[i]
                    next_rx += dx[i]

                elif board[next_ry][next_rx] == '#':
                    next_ry -= dy[i]
                    next_rx -= dx[i]
                    break

                elif board[next_ry][next_rx] == 'O':
                    break


            #파란공
            while True: #다음 node로 넘어가는 부분

                if board[next_by][next_bx] != 'O' and board[next_by][next_bx] !='#':
                    next_by += dy[i]
                    next_bx += dx[i]

                elif board[next_by][next_bx] == '#':
                    next_by -= dy[i]
                    next_bx -= dx[i] # '#'벽에 걸리니까 한 발자국 되돌리는거
                    break

                elif board[next_by][next_bx] == 'O': #이건 O에 떨어졌을때를 대비하는거
                    break

            #이게 여기에 왜 들어가는거지 맨 앞에 들어가는거랑 무슨 차이인가?
            # 문제의 조건에서 Red가 'O'로 빠지면 성공 return, Blue red가 같이 빠지면 return ret=-1, count >10 return ret=-1
            if board[next_by][next_bx] == 'O':  # Solution에서는 이 조건이 없는데?
                continue
            if board[next_ry][next_rx] == 'O' and board[next_by][next_bx] != 'O': #만약 11번째 하다가 되면 +1때문에 11이 튀어나오겠네 사실상 10이라서 나가리일텐데
                return cur_count+1

            #--------------- 예외 조건 ---------------#
            if board[next_ry][next_rx] != 'O' and board[next_by][next_bx] != 'O': #둘다 구멍에 빠진게 아닐때
                if next_ry == next_by and next_rx == next_bx: #더 많이 움직인걸 원상태로 해준다
                    red_distance = abs(next_ry - cur_ry) + abs(next_rx - cur_rx)
                    blue_distance = abs(next_by - cur_by) + abs(next_bx - cur_bx)

                    if red_distance > blue_distance:
                        next_ry -= dy[i]
                        next_rx -= dx[i]

                    elif blue_distance >red_distance: #여기서 조건을 blue_distance <red_distance 이렇게 써놨었다.
                        next_by -= dy[i]
                        next_bx -= dx[i]

            if visit[next_ry][next_rx][next_by][next_bx] == False:
                visit[next_ry][next_rx][next_by][next_bx] = True
                next_count = 1 + cur_count
                Queue.append([next_ry,next_rx, next_by,next_bx, next_count])

    return ret

N, M = list(map(int, input().split())) #N 행, M 렬

board = []
#문제 Setting
for i in range(N):
    board.append(list(input().strip()))

#visit board
visit0=[]; visit1=[]; visit3 = []

#일단 List comprehension올 쓰자
visit = [[[[False] * M for _ in range(N)] for _ in range(M)] for _ in range(N)]


#우선 R과 B의 위치부터 찾자 그냥 Brute force로 찾고 (위치 찾는건)
for r in range(N):
    for c in range(M):
        if board[r][c] == "R":
            start_ry = r
            start_rx = c

        elif board[r][c] == "B":
            start_by = r
            start_bx = c


ret = BFS(start_ry, start_rx, start_by,start_bx, board, visit)
if ret >10:
    print(-1)

else:
    print(ret)

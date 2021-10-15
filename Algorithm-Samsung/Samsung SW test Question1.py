#구슬탈출문제
'''
10 10
##########
#R#...##B#
#...#.##.#
#####.##.#
#......#.#
#.######.#
#.#....#.#
#.#.##...#
#O..#....#
##########

5 5
#####
#..B#
#.#.#
#RO.#
#####
'''
# cur_ry ,cur_rx ,cur_by ,cur_bx ,count
import collections
from sys import stdin

input = stdin.readline

def BFS(strat_ry, start_rx, start_by, start_bx, board, visit):

    # 참고로 BFS는 Recursive하게 짤수 없으므로 Function문 앞에 그냥 방향하고 count 갖다 놔도 된다.
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
    print("Check True", visit[1][1][1][3])
    visit[cur_ry][cur_rx][cur_by][cur_bx] = True # 이부분 넣었고 답지 참조
    print("Check check", cur_ry ,cur_rx ,cur_by ,cur_bx)
    print("Check True2", visit[1][1][1][3])
    Queue.append([cur_ry ,cur_rx ,cur_by ,cur_bx ,cur_count]) #처음 상태 Queue에 Push해준다.

    #그 안에 있는 내용들
    while len(Queue) !=0:

        #Queue pop해줘야 한다
        cur_ry ,cur_rx ,cur_by ,cur_bx ,cur_count = Queue.popleft()
        print("Pop from dequeue")
        print(cur_ry ,cur_rx ,cur_by ,cur_bx ,cur_count)


        #print("RED BLUE")
        #print(board[cur_ry][cur_rx], board[cur_by][cur_bx])


        #--------------- end 조건 ---------------#
        #문제의 조건에서 Red가 'O'로 빠지면 성공 return, Blue red가 같이 빠지면 return ret=-1, count >10 return ret=-1
        if board[cur_ry][cur_rx] =='O' and board[cur_by][cur_bx] !='O':
            return cur_count
        #if board[cur_by][cur_bx] == 'O': #Solution에서는 이 조건이 없는데?
        #    return ret
        if board[cur_by][cur_bx] == 'O' and board[cur_ry][cur_rx] =='O':  # 파란 구슬이 구멍에 떨어지지 않으면(실패 X)
            return ret
        if cur_count >10:
            return ret
        #return이 local function을 깨고 그냥 return해주는지 while문을 break해주는건지 잘 모름 아직


        #--------------- 진행 문 ---------------#
        for i in range(4): #모든 방향으로 뻗어가는 Case를 (4)

            next_ry = cur_ry
            next_rx = cur_rx
            next_by = cur_by
            next_bx = cur_bx


            #여기 빨간공하고 파란공 나눠야 하는이유 빨간공이 #에 부딪히고서도 파란공이 #에 닿을때까지 계속 나가게 된다.
            #빨간공
            while True: #다음 node로 넘어가는 부분

                #여기서 'O'일때 Stop조건이 없네 (Solution 봄)

                if board[next_ry][next_rx] !='O' and board[next_ry][next_rx] !='#': #뒤에 #조건이 없으면 망한다 #에 부딪히고 (솔루션 봄)
                    next_ry += dy[i]
                    next_rx += dx[i]

                    print("red move", next_ry, next_rx)

                elif board[next_ry][next_rx] == '#':
                    next_ry -= dy[i]
                    next_rx -= dx[i]

                    print("red back", next_ry, next_rx)
                    break

                elif board[next_ry][next_rx] == 'O':
                    break


            #파란공
            #여기 빨간공하고 파란공 나눠야 하는이유 빨간공이 #에 부딪히고서도 파란공이 #에 닿을때까지 계속 나가게 된다.
            while True: #다음 node로 넘어가는 부분

                if board[next_by][next_bx] != 'O' and board[next_by][next_bx] !='#':
                    next_by += dy[i]
                    next_bx += dx[i]

                    print("blue move", next_by, next_bx)

                elif board[next_by][next_bx] == '#':
                    next_by -= dy[i]
                    next_bx -= dx[i] # '#'벽에 걸리니까 한 발자국 되돌리는거

                    print("blue back", next_by, next_bx)
                    break

                elif board[next_by][next_bx] == 'O': #이건 O에 떨어졌을때를 대비하는거
                    break

            #--------------- 예외 조건 ---------------#

            print("exception condition or not?")
            if board[next_ry][next_rx] != 'O' and board[next_by][next_bx] != 'O': #둘다 구멍에 빠진게 아닐때
                if next_ry == next_by and next_rx == next_bx: #더 많이 움직인걸 원상태로 해준다
                    print("\n exception condition")
                    red_distance = abs(next_ry - cur_ry) + abs(next_rx - cur_rx)
                    blue_distance = abs(next_by - cur_by) + abs(next_bx - cur_bx)

                    print("From ry, rx, by, bx", next_ry, next_rx, next_by, next_bx)

                    print("operate red blue", red_distance, blue_distance)
                    if red_distance > blue_distance:
                        next_ry -= dy[i]
                        next_rx -= dx[i]

                    elif blue_distance >red_distance: #여기서 조건을 blue_distance <red_distance 이렇게 써놨었다.
                        next_by -= dy[i]
                        next_bx -= dx[i]

                    print("To ry, rx, by, bx", next_ry, next_rx, next_by, next_bx)


            #Solution 봄 for문 안에서 Queue에 넣어줘야함. 왜? 모든 방향으로 간 것에 대해 Queue에 넣어줘야 하기때문에
            #그리고 Visit한 부분을 안빼주면 Back track현상이 일어나기때문에 해줘야한다.


            #print(visit[next_ry][next_rx][next_by][next_bx] == False)
            #print("visit",visit[next_ry][next_rx][next_by][next_bx])
            #print(next_ry,next_rx, next_by,next_bx)

            if visit[next_ry][next_rx][next_by][next_bx] == False:
                visit[next_ry][next_rx][next_by][next_bx] = True

                next_count = 1 + cur_count
                print(cur_count)

                Queue.append([next_ry,next_rx, next_by,next_bx, next_count])
                print("Let's push queue")
                print("Q append complete", next_ry,next_rx, next_by,next_bx, next_count)



N, M = list(map(int, input().split())) #N 행, M 렬
#n, m = map(int, input().split())

board = []
#문제 Setting
for i in range(N):
    board.append(list(input().strip()))

#visit board
visit0=[]; visit1=[]; visit3 = []
'''
for _ in range(N):
    visit0.append([False]*M)
for _ in range(M):
    visit1.append(visit0)
for _ in range(N):
    visit3.append(visit1)
visit0=[]; visit1=[]
'''

#일단 List comprehension올 쓰자
visit = [[[[False] * M for _ in range(N)] for _ in range(M)] for _ in range(N)]
print(board)
print(visit)


#우선 R과 B의 위치부터 찾자 그냥 Brute force로 찾고 (위치 찾는건)
for r in range(N):
    for c in range(M):
        if board[r][c] == "R":
            print("R index:", (r,c))
            start_ry = r
            start_rx = c

        elif board[r][c] == "B":
            print("B index:", (r,c))
            start_by = r
            start_bx = c

print(start_ry, start_rx, start_by, start_bx)


ret = BFS(start_ry, start_rx, start_by,start_bx, board, visit)

print(ret)
from sys import stdin

input = stdin.readline

N,M,K = list(map(int, input().split()))

Info = []
for _ in range(M):
    r,c,m,s,d = list(map(int, input().strip().split()))
    Info.append([r-1,c-1,m,s,d])

board_temp = [[[] for i in range(N)] for _ in range(N)]
#board = [[]*N for 와 차이점 알아보기 확실하게!

#상 오위 오 오아 아 왼아 왼 왼위
dy = [-1,-1,0,1,1,1,0,-1]
dx = [0,1,1,1,0,-1,-1,-1]

even = [0,2,4,6]
odd = [1,3,5,7]

for order in range(K):

    board_temp = [[[] for i in range(N)] for _ in range(N)]

    #파이어볼 이동
    for y,x,m,s,d in Info:
        ny = (N + y + dy[d] * s)%N #periodic
        nx = (N + x + dx[d] * s)%N #periodic
        board_temp[ny][nx].append([m,s,d])

    Info = []


    #4개로 쪼개져서 이동하는 것
    for j in range(N):
        for i in range(N):
            if len(board_temp[j][i])==0:
                continue

            if len(board_temp[j][i])==1: #아 홀수 빼먹어서 뭔 개고생이냐... overlap되어있는것만 잘못 처리함 처음에
                m_s=0; s_s=0;d_s=0
                m_s = (board_temp[j][i][0][0]); s_s += (board_temp[j][i][0][1]); d_s += (board_temp[j][i][0][2])
                #print(board_temp[j][i])
                Info.append([j, i, m_s, s_s, d_s])

            if len(board_temp[j][i])>1:
                #[m,s,d]로 저장되어 있다.
                m_n=0; s_n=0; d_sum=0; even_c = 0; odd_c = 0
                for In in board_temp[j][i]:
                    m_n += (In[0]); s_n += (In[1]); d_sum = (In[2])
                    if d_sum %2 ==0: #이부분 문제에서 헷갈려서 계속 틀림.
                        even_c +=1
                    else:
                        odd_c +=1

                m_n = m_n//5
                s_n = s_n//len(board_temp[j][i])


                if m_n != 0:
                    if even_c == len(board_temp[j][i]) or odd_c == len(board_temp[j][i]): #Even
                        for _ in range(4):
                            d_n = even[_]
                            Info.append([j,i,m_n,s_n,d_n])

                    else:
                        for _ in range(4):
                            d_n = odd[_]
                            Info.append([j,i,m_n,s_n,d_n])

board_ans = [[[] for i in range(N)] for _ in range(N)]
for y, x, m, s, d in Info:
    board_ans[y][x].append([m,s,d])

ans = 0
for j in range(N):
    for i in range(N):
        if board_ans [j][i] != []:
            for _ in range(len(board_ans[j][i])):#한 list안에 n개의 list 겹쳐 있을경우 파이어볼이 겹쳐있을경우
                ans += (board_ans[j][i][_][0])
print(ans)
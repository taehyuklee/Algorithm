from collections import deque

def BFS(x_o, y_o, maps, answer, n, m):
    # 동서 남북
    dx = [0,0,1,-1]
    dy = [1,-1,0,0]
    
    visit = [[False]*m for _ in range(n)]

    Q = deque()
    Q.appendleft([x_o, y_o, 1])
    visit[x_o][y_o] = True
    
    while Q:
        
        x_old, y_old, trace = Q.pop()
        
        if x_old == n-1 and y_old == m-1:
            answer[0] = trace
            return
        
        for dir in range(4):
            x_new = x_old + dx[dir]
            y_new = y_old + dy[dir]
            new_trace = trace + 1

            if x_new > n-1 or x_new < 0 or y_new > m-1 or y_new < 0: 
                continue

            if maps[x_new][y_new] == 1 and not visit[x_new][y_new]:
                visit[x_new][y_new] = True
                Q.appendleft([x_new, y_new, new_trace])


def solution(maps):
    answer = [0]
    
    n = len(maps)
    m = len(maps[0])
    
    BFS(0,0, maps, answer, n, m)
    
    return answer[0] if answer[0] !=0 else -1


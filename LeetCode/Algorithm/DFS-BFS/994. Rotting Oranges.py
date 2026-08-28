from collections import deque

class Solution:
    def orangesRotting(self, grid: List[List[int]]) -> int:
        
        # rotten orange 위치를 찾아서 해당 지점으로부터 4방으로 1 step씩 뻗어나가는 시나리오 
        # BFS로 접근할 것 같습니다. 

        # 우선 Rotten Orange 위치부터 찾도록 합니다. (m,n 이 크지 않기에 O(n^2)한 번 돕니다)
        rotten_list = []
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == 2:
                    rotten_list.append([i, j])
        
        #visite board를 만듭니다
        visited_board = [[False]* len(grid[0]) for _ in range(len(grid))]

        # BFS 구현 해당 rotten orange 위치로부터 1step씩 4방에 fresh orange가 있다면 썪게 한다.
        def bfs(grid, rotten_list, visited_board) -> int:

            Q = deque()

            # 4방에 대한 정의 (동, 서, 남, 북)
            dx = [0,0,1,-1]
            dy = [1,-1,0,0]

            final_time = 0

            for i in range(len(rotten_list)):
                rotten_list[i].append(0)
                Q.append(rotten_list[i])
                visited_board[rotten_list[i][0]][rotten_list[i][1]] = True
            
            while Q:
                
                x_o, y_o, time_o = Q.popleft()
                final_time= time_o # 계속 갱신

                for k in range(4):

                    x_new = x_o + dx[k]
                    y_new = y_o + dy[k]

                    if x_new < 0 or x_new >=len(grid) or y_new <0 or y_new >=len(grid[0]):
                        continue

                    if visited_board[x_new][y_new] == False and grid[x_new][y_new] == 1:
                        Q.append([x_new, y_new, time_o+1])
                        grid[x_new][y_new] = 2
                        visited_board[x_new][y_new] = True


            return final_time

        time: int = bfs(grid, rotten_list, visited_board)

        # 마지막으로 -1이 되는지 여부를 판단 하기 위해서 검색
        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == 1:
                    return -1

        return time





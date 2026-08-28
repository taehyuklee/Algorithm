from collections import deque

class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:

        # BFS로 풀 수 있습니다.  1을 따라 사방으로 (동서남북)탐색했을때 BFS상 더 이상 못움직일때를 하나의 섬으로 취급하면 됩니다. 주변 0 즉 물일때는 못움직이게 합니다. 
        
        answer = 0

        # 방문 여부 보드
        visited_board = [ [False]* len(grid[0]) for _ in range(len(grid))]

        # BFS 시험
        def bfs(grid, x0, y0, visited_board):

            queue = deque()
            init_con = [x0, y0]
            queue.append(init_con)

            # 동서남북 움직임
            dx = [0,0,1,-1]
            dy = [1,-1,0,0]

            visited_board[x0][y0] = True
            
            while queue:

                x_o, y_o = queue.popleft()

                for i in range(4):

                    x_new = x_o + dx[i]
                    y_new = y_o + dy[i]

                    if x_new < 0 or x_new >= len(grid) or y_new < 0 or y_new >= len(grid[0]): 
                        continue
                    if visited_board[x_new][y_new] == False and grid[x_new][y_new] == "1": 
                        visited_board[x_new][y_new] = True
                        queue.append([x_new, y_new])
                        

        for i in range(len(grid)):
            for j in range(len(grid[0])):
                if grid[i][j] == "1" and visited_board[i][j] != True:
                    bfs(grid, i, j, visited_board)
                    answer += 1

        return answer

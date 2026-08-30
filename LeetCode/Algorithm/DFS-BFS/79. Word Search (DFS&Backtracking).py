from collections import deque
from copy import deepcopy

class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        #DFS로 풀수도, BFS로 풀수도 있습니다. Grid는 BFS로 한 번에 뻗어나가는 케이스로 풉니다.
        # 우선 첫 word 첫 글자를 따와서 시작하도록 합니다. 
        visit = [[False]*len(board[0]) for _ in range(len(board))]
        # 동서남북
        dx = [0,0,1,-1]
        dy = [1,-1,0,0]

        def dfs(x_old, y_old, board, word, cur_idx, visit):

            # 종료 조건
            if len(word)-1 == cur_idx:
                return True

            for d in range(4):
                x_new = x_old + dx[d]
                y_new = y_old + dy[d]
                next_idx = cur_idx+1

                if x_new < 0 or x_new >= len(board) or y_new <0 or y_new >=len(board[0]):
                    continue

                if next_idx > len(word)-1:
                    continue
                    
                if visit[x_new][y_new] == False and word[next_idx] == board[x_new][y_new]:

                    visit[x_new][y_new] = True
                    if dfs(x_new, y_new, board, word, next_idx, visit):
                        return True
                    visit[x_new][y_new] = False

            return False

        for i in range(len(board)):
            for j in range(len(board[0])):
                if board[i][j] == word[0]:
                    visit[i][j] = True
                    if dfs(i,j, board, word, 0, visit):
                        return True
                    visit[i][j] = False
                    
        return False
        

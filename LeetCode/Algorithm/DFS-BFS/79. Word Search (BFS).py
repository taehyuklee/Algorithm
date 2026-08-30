from collections import deque
from copy import deepcopy

class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        #DFS로 풀수도, BFS로 풀수도 있습니다. Grid는 BFS로 한 번에 뻗어나가는 케이스로 풉니다.
        # 우선 첫 word 첫 글자를 따와서 시작하도록 합니다. 

        def bfs(x0, y0, board, word):

            Q = deque()
            visit = [[False]*len(board[0]) for _ in range(len(board))]

            # 위치와 현재 word가 어디있는지 그리고 다음 타겟을 잡도록 합니다. (index로)
            cur_word_path = word[0]
            visit[x0][y0] = True
            Q.append([x0, y0, word[0], 1, cur_word_path, visit])


            # 동서남북
            dx = [0,0,1,-1]
            dy = [1,-1,0,0]

            while Q:
                
                x_old, y_old, cur_word, next_idx, cur_word_path, visit_old = Q.popleft()

                if next_idx == len(word):
                    return True

                for d in range(4):
                    x_new = x_old + dx[d]
                    y_new = y_old + dy[d]

                    if x_new <0 or x_new>=len(board) or y_new <0 or y_new>=len(board[0]): 
                        continue
                    if next_idx >= len(word):
                        continue
                    if visit_old[x_new][y_new] == False and board[x_new][y_new] == word[next_idx]:
                        cur_path =  cur_word_path + word[next_idx]
                        new_visit = deepcopy(visit_old)
                        new_visit[x_new][y_new] = True
                        Q.append([x_new, y_new, word[next_idx], next_idx+1, cur_path, new_visit])
                        print(cur_path)
        ''''
        [["A","B","C","E"],
         ["S","F","E","S"],
         ["A","D","E","E"]]
        '''


        for i in range(len(board)):
            for j in range(len(board[0])):
                if board[i][j] == word[0]:
                    if bfs(i,j, board, word):
                        return True
                    
        return False
        

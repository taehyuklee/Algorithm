from copy import deepcopy

class Solution:
    def generateParenthesis(self, n: int) -> List[str]:
        
        final = []

        def dfs(path, open_count, close_count):

            # 종료 조건
            if len(path) == 2*n:
                final.append(path)
                return

            # '(' 추가 가능
            if open_count <n:
                dfs(path+'(', open_count+1, close_count)

            # ')' 추가 가능
            if close_count <open_count:
                dfs(path+')', open_count, close_count+1)

        dfs("", 0, 0)
        return final

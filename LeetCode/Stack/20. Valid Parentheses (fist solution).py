class Solution:
    def isValid(self, s: str) -> bool:

        if len(s) == 1 or len(s) == 0:
            return False

        stack = []

        open_bracket = {'(':0, '{':1, '[':2}
        idx_open = {0: '(', 1: '{', 2:'['}
        close_bracket = {')':0, '}':1, ']':2}
        idx_close = {0: ')', 1: '}', 2:']'}

        input_list = list(s)
        
        for i in range(len(input_list)):

            if i ==0 and input_list[i] in close_bracket:
                return False

            if input_list[i] in open_bracket:
                stack.append(input_list[i])
            else:
                idx_close = close_bracket[input_list[i]]
                corr_close = idx_open[idx_close]

                if stack:
                    peak_top = stack[-1]
                else:
                    return False
                
                stack.append(input_list[i])

                if peak_top == corr_close:
                    stack.pop()
                    stack.pop()
                
        if stack:
            return False
        else:
            return True

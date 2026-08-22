class Solution:
    def dailyTemperatures(self, temperatures: List[int]) -> List[int]:
        answer_list = [0] * len(temperatures)
        stack = []
        old_temp = 0


        for i in range(len(temperatures)):
            cur_temp = temperatures[i]

            if cur_temp > old_temp and i != 0:
                
                while stack:
                    check_idx = stack[-1]

                    # recursive하게 계속 앞까지 숫자가 큰게 나올때까지 pop을 해야하는걸 이렇게 while문으로 구현한다. (단조로 작아야하니까www 같은 경우도 예외 처리해야함.)
                    if temperatures[i] <= temperatures[check_idx]:
                        break

                    poped_idx = stack.pop()
                    answer_list[poped_idx] = i - poped_idx

            stack.append(i)

            old_temp = cur_temp

        return answer_list

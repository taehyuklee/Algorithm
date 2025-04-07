from collections import deque

def solution(numbers, target):
    answer = 0
    Q = deque()

    summation = 0
    cur_depth = 0
    idx = 0
    tot_depth = len(numbers)
    Q.appendleft([summation, cur_depth, idx])

    # cur_depth 또는 index를 가지고 number에 접근해야 한다.
    while Q:
        summation, cur_depth, idx = Q.pop()

        if cur_depth == tot_depth:
            if summation == target:
                answer += 1

        for sign in [1, -1]:
            if idx < tot_depth:
                new_sum = summation + sign * numbers[idx]
                Q.appendleft([new_sum, cur_depth + 1, idx+1])

    return answer

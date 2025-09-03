# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(N, A):

    answer = [0]*N
    cur_max = 0

    for num in A:
        if num <= N:
            # Increase Num
            answer[num-1] += 1
            cur_max = max(answer[num-1], cur_max)
        else:
            # Set Max Value
            answer = [cur_max]*N

    return answer
    


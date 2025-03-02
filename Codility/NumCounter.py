# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(N, A):
    # Implement your solution here
    answer_list = [0] * N

    for i in range(len(A)):
        # print(A[i])
        counter_num = A[i]
        if counter_num < N+1:
            answer_list[counter_num-1] += 1
        else:
            max_value = max(answer_list)
            answer_list = [max_value] * N
        # print("counter_num: " + str(counter_num))
        # print(answer_list)
    # print(answer_list)
    return answer_list

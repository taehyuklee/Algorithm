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


############################## 위의 경우 max가 들어가서 O(M*N)이 되어버림 ###############################
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(N, A):
    # Implement your solution here
    answer_list = [0] * N
    old_value = 0
    max_value=0

    for i in range(len(A)):
        # print(A[i])
        counter_num = A[i]
        if counter_num < N+1:
            answer_list[counter_num-1] += 1
            if old_value < answer_list[counter_num-1]:
                max_value = answer_list[counter_num-1]
        else:
            answer_list = [max_value] * N
        
        if counter_num < N+1:
            old_value = answer_list[counter_num-1]

    return answer_list

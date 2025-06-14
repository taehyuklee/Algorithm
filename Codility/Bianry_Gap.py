# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(N):
    # Implement your solution here

    binary_list = []
    
    iter_N = N

    while True:
        binary_list.append(iter_N%2)
        iter_N = iter_N//2
        if iter_N == 1:
            binary_list.append(1)
            break


    len_id = len(binary_list)

    answer = 0
    start_i = len_id-1
    end_i = 0

    for i in range(len_id-2, -1, -1):
        
        if binary_list[i] == 1:
            end_i = i

            answer = max((start_i - end_i - 1), answer)
            start_i = end_i

    return answer

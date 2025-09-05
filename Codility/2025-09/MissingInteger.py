# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here

    a_set = set(A) # O(N)
    a_list = list(a_set) # O(N)
    sorted_list = sorted(a_list) #O(N)

    cnt = 1
    for num in sorted_list: #O(N)
        if num <=0:
            continue
        else:
            if cnt != num:
                return cnt
            cnt+=1

    return cnt


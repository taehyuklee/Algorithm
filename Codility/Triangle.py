# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    A.sort(key = lambda x: x)
    
    for i in range(len(A)-2):
        condition_a = A[i] + A[i+1]
        condition_b = A[i+1] + A[i+2]
        condition_c = A[i] + A[i+2]

        if condition_a > A[i+2] and condition_b > A[i] and condition_c > A[i+1]:
            return 1

    return 0

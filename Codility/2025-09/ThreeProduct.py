# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here

    A.sort()
    last_idx = len(A)-1

    max_val = max(A[last_idx]*A[last_idx-1]*A[last_idx-2], A[last_idx]*A[0]*A[1])
    
    return max_val

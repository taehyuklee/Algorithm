# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")
import copy

def rotation(A):
    #Python 기본 shallow copy로 되어 있어서 deepcopy해줘야 함
    B = copy.deepcopy(A)
    for i in range(len(A)):
        B[(i+1)%len(A)] = A[i]
    return B

def solution(A, K):
    # Implement your solution here
    for i in range(K):
        A = rotation(A)
    return A

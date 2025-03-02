# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    A.sort()
    for i in range(1,len(A)+1):
        if i != A[i-1]:
            return i

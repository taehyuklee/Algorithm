# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):

    # Implement your solution here
    A.sort(key=lambda x: x)

    one = A[0]*A[1]*A[len(A)-1]
    two = A[len(A)-1]*A[len(A)-2]*A[len(A)-3]

    return max(one, two)

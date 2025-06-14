# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A, K):
    if not A:
        return A
        
    # Implement your solution here
    for i in range(K):
        last_num = A.pop()
        A.insert(0, last_num)

    return A

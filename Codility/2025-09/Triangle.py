
def solution(A):
    # Implement your solution here

    A.sort()

    for i in range(1, len(A)-1):
        if A[i-1] + A[i] > A[i+1] and A[i] + A[i+1] > A[i-1] and A[i+1] + A[i-1] > A[i]:
            return 1
    
    return 0
        

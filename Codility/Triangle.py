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

'''
정렬된 배열에서는 값이 가까운 세 수일수록 삼각형 부등식 𝐴+𝐵>𝐶 A+B>C를 만족할 가능성이 높고, 만약 인접한 세 수 𝐴[𝑖],𝐴[𝑖+1],𝐴[𝑖+2] A[i],A[i+1],A[i+2]에서도 이 조건이 성립하지 않는다면, 이보다 더 멀리 떨어진 수들 사이에서는 값의 차이가 더 커져 조건을 만족할 수 없기 때문에, 삼각형을 만들 수 있는 조합은 반드시 인접한 세 수 중에 존재해야 합니다.
'''

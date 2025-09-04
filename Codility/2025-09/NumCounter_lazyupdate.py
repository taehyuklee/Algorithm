def solution(N, A):
    # Implement your solution here

    answer = [0]*N
    max_val = 0
    base = 0

    for num in A:
        if num <= N:
            if answer[num-1] < base:
                answer[num-1] = base
            answer[num-1] +=1
            max_val = max(max_val, answer[num-1])
            
        else:
            base = max_val

    for i in range(len(answer)):
        if answer[i] < base:
            answer[i] = base
    
    return answer

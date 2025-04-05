from collections import deque

def solution(queue1, queue2):
    queue1 = deque(queue1)
    queue2 = deque(queue2)

    time = 0
    sum1 = sum(queue1)
    sum2 = sum(queue2)
    
    while True:
        
        if sum1 == sum2:
            return time
            
        if sum1 > sum2:
            poped = queue1.popleft()
            queue2.append(poped)
            sum1 -= poped
            sum2 += poped
        else:
            poped = queue2.popleft()
            queue1.append(poped)
            sum2 -= poped
            sum1 += poped
        
        # 시각을 앞으로 두느냐 뒤로 두느냐를 확실히 해야 한다.
        time+=1 
        
        if time == 2 * len(queue1):
            return -1

    return time

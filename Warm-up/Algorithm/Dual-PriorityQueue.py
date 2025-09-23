import heapq

def solution(operations):
    answer = []
    
    max_heap = []
    min_heap = []
    state_map = {}
    
    for operation in operations:
        cmd, num = operation.split(" ")
        
        if cmd == 'I':
            # print("insert")
            num_int = int(num)
            heapq.heappush(max_heap, -num_int)
            heapq.heappush(min_heap, num_int)
            
            if num_int in state_map.keys():
                state_map[num_int] += 1
            else:
                state_map[num_int] = 1
            
        elif cmd == 'D' and num == '-1':
            # print("extract min")
            while min_heap:
                min_v = heapq.heappop(min_heap)
                if state_map[min_v] > 0:
                    state_map[min_v] -= 1
                    break
                                
        else:
            # print("extract max")
            while max_heap:
                max_v = -heapq.heappop(max_heap)
                if state_map[max_v] > 0:
                    state_map[max_v] -= 1
                    break

    '''
    min_heap[0] 자체가 최소값이 맞아요, 왜냐하면 heapq가 **파이썬에서 최소 힙을 구현할 때 항상 리스트    의 0번 인덱스가 최소값(top)**으로 유지되기 때문입니다.
    '''
                    
    while min_heap:
        if state_map.get(min_heap[0], 0) ==0:
            heapq.heappop(min_heap)
        else:
            break

    while max_heap:
        if state_map.get(-max_heap[0], 0) ==0:
            heapq.heappop(max_heap)
        else:
            break
            
    if not min_heap:
        return [0, 0]
    else:
        return [-max_heap[0], min_heap[0]]  # [최대값, 최소값]

def solution(priorities, location):
    
    list_num = len(priorities)
    answer = 1
    
    #print(priorities)
    list_collect=[]
    for i in range(len(priorities)):
        list_collect.append([i,priorities[i]])
    
    #print(list_collect)
    
    #인쇄 목록
    while True:
        for k in range(len(list_collect)):
            docPri = list_collect[0][1]

            for i in range(len(list_collect)):
                if(list_collect[i][1]>docPri):
                    list_collect.append(list_collect[0])
                    del list_collect[0]
                    break
        
        if(list_collect[0][0] == location):
            break
        else:
            del list_collect[0]
            answer+=1

    #결국 정렬문제임 push, pop들어간 정렬문제 (첫번째꺼 정렬하고 그 다음부터 또 정렬하고)
    
    # for i in range(list_num):
    #     if(location == list_collect[i][0]):
    #         answer = i+1

    return answer
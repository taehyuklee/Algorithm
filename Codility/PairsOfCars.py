# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

#0을 기준으로 1을 count해보자 
#A[0] - 기준으로 앞에 1을 카운트 시작
#A[2] - 기준으로 또 그 뒤에서부터 1을 카운트 시작. 동적으로 계속 카운팅해보자.
def solution(A):
    python_list = []
    answer=0

    for i in range(len(A)):
        if A[i] == 0:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        else:
            python_list[len(python_list)-1][0] += 1

    for i in range(len(python_list)-1, -1, -1):
        answer = python_list[i][0] + 2*answer


    return answer

#######################
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

#0을 기준으로 1을 count해보자 
#A[0] - 기준으로 앞에 1을 카운트 시작
#A[2] - 기준으로 또 그 뒤에서부터 1을 카운트 시작. 동적으로 계속 카운팅해보자.
def solution(A):
    python_list = []
    answer=0
    sum_answer = 0

    for i in range(len(A)):
        if A[i] == 0:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        else:
            python_list[len(python_list)-1][0] += 1

    # python_list = [[3], [5], [1], [2],[3]]
    # print(python_list)
    for i in range(len(python_list)-1, -1, -1):
       
        answer = python_list[i][0] + answer
        sum_answer+=answer

    return sum_answer



########## 예외 조건 ############
def solution(A):
    python_list = []
    answer=0
    sum_answer = 0

    for i in range(len(A)):
        if A[i] == 0:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        else:
            python_list[len(python_list)-1][0] += 1

    # print(python_list)
    for i in range(len(python_list)-1, -1, -1):
        answer = python_list[i][0] + answer
        sum_answer+=answer

    if sum_answer > 1000000000:
        return -1
        
    return sum_answer



#### 시작이 1일때 #### 이건 필요 없는 조건 P<Q 임을 기억하자
def solution(A):
    python_list = []
    answer=0
    sum_answer = 0

    for i in range(len(A)):
        if A[0]==0 and A[i] == 0:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        elif A[0] ==1 and A[i] == 1:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        else:
            python_list[len(python_list)-1][0] += 1

    for i in range(len(python_list)-1, -1, -1):
        answer = python_list[i][0] + answer
        sum_answer+=answer


##### 2025-03-02 20:29 #####
    def solution(A):
    python_list = []
    answer=0
    sum_answer = 0

    for i in range(len(A)):
        if A[i] == 0:
            python_list.append([])
            python_list[len(python_list)-1].append(0)
        else:
            if python_list:
                python_list[len(python_list)-1][0] += 1

    for i in range(len(python_list)-1, -1, -1):
        answer = python_list[i][0] + answer
        sum_answer+=answer

    if sum_answer > 1000000000:
        return -1
        
    return sum_answer

    if sum_answer > 1000000000:
        return -1
        
    return sum_answer

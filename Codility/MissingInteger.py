# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    a_set = set(A)
    last_element = 0

    # O(N)
    for i,element in enumerate(a_set):
        if (i+1) != element:
            return (i+1)

        last_element = element

    return last_element+1

###### 문제 조건 다시 한 번 본 이 후 #########
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
 
    a_list = list(set(A))
    sorted_list = sorted(a_list)
    last_element = 0
    idx_element = sorted_list[0]

    # O(N)
    for element in sorted_list:

        if idx_element != element and idx_element>0:
            return idx_element

        idx_element+=1

        last_element = idx_element

    if last_element<=0:
        return 1
    else:
        return last_element

###########
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    if not A:
        return 1
    a_list = list(set(A))
    sorted_list = sorted(a_list)
    last_element = 0
    idx_element = sorted_list[0]

    # O(N)
    for i in range(len(sorted_list)):
        element = sorted_list[i]


        if idx_element != element and idx_element>0:
            return idx_element
        elif idx_element != element and idx_element<=0:
            idx_element = element

        idx_element+=1

        last_element = idx_element


    if last_element<=0:
        return 1
    else:
        return last_element

# 엄청 간단하네 (아래꺼가 다 맞은 케이스)
def solution(N, A):
    A.sort()
    A = list(set(A))
    missingdata = 1
    for i in A:
        if i == missingdata :
            missingdata +=1
    return missingdata

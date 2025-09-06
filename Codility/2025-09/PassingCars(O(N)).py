# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    multi=0
    cnt=0
    
    for idx, num in enumerate(A):
        if num == 0:
            multi +=1
        else:
            cnt += multi*1

        if cnt > 1000000000:
            return -1  

    return cnt

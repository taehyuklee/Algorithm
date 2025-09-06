# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    cnt = 0

    for i, num in enumerate(A):
        if num == 0:
            for j in range(i, len(A)):
                if A[j] == 1:
                    cnt += 1
        else:
            continue

    print(cnt)
    if cnt >= 1000000000:
        return -1

    return cnt

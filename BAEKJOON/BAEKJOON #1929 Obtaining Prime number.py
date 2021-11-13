import sys

input = sys.stdin.readline

M,N = map(int, input().split())

stack=[]

for i in range(M, N+1):
    if i!=1:stack.append(i)
    for _ in range(2, int(i**0.5)+1): #소수는 자기 자신과 1밖에 약수를 가지고 있지 않다
        if i%_ ==0: #다른 숫자로 하나라도 나눠지면 소수가 아니다
            stack.pop()
            break

for i in stack:
    print(i)
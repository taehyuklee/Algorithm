import sys

input = sys.stdin.readline
N = int(input())

Num_list = list(map(int, input().split()))
count = 0

#소수: 1과 자기 자신을 약수로 가지고 있는 수 --> 그 외의 수로 나눠지면 약수가 아니다.
for i in Num_list:
    if i !=1: #숫자1은 소수가 아니므로 제외
        divisor = 0
        for _ in range(2, i):#1과 자기자신 빼고 쭉 돌려서 나눠서 나눠지면 안됨 (range빼먹었었음)
            if i % _ ==0:
                divisor +=1
        if divisor == 0:
            count +=1
print(count)

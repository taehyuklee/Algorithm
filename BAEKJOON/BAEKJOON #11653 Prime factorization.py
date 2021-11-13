import sys

input = sys.stdin.readline
N = int(input())
# N을 2부터 쭉 안나눠질때까지 나눈다.
#산술의 기본 정리(fundamental theorem of arithmetic) 에 의해 양의정수는 소수로 구성되어 있다는 것을 알 수 있다.

for i in range(2, N+1):
    if N%i ==0:
        while N%i==0:
            N = N/i
            print(i)
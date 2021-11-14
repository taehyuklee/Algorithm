import sys

input = sys.stdin.readline
N,M = map(int, input().split())

def GCD(a,b):
    while a%b !=0: #나머지가 0이 아닐때까지 계속 돌린다
        a, b = b, a%b #GCD --> a,b 최대공약수는 b와 a%b=r과의 최대공약수랑 같다 --> 쭉 타고 내려감
    return b #GCD 출력

def LCM(a,b, gcd):
    c = a*b/gcd #이건 생각해보면 당연함 각자 공통으로 가지고 있는 최대공약수 *a*b (a', b'서로서)
                # a = a'GCD , b = b'*GCD
    return c

gcd = GCD(N,M)
lcm = LCM(N,M,gcd)
print(int(gcd)); print(int(lcm))
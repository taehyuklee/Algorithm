class Solution:
    def climbStairs(self, n: int) -> int:
        
        # 전형적인 Dnymaic Programming 으로 이에 대한 점화식을 일반화해보면 다음과 같습니다
        # n번째 계단까지 올라올 수 있는 경우의수를 f(n) 이라고 한다면
        # 이전 계단에서 n번째 (n-1)에서 올라가는 케이스 + n-2에서올라가는 케이스가있을겁니다
        # 따라서 점화식은 f(n) = f(n-1) + f(n-2) 가 될 것이며
        # Initial Conditino으로 f(0) = 0, f(1)=1, f(2)=2 를 사용하면 됩니다
        # for loop로 bottom up으로 풀어보도록 합니다

        if n == 1:
            return 1

        f_table = [0]*(n+1)
        f_table[1] = 1
        f_table[2] = 2
        
        for i in range(3, n+1):
            f_table[i] = f_table[i-1] + f_table[i-2]

        return f_table[-1]

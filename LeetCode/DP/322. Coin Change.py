class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:
        # f(n)을 해당 n(amount)를 채우는데 최소한의 코인 개수라고 정의 하자
        # 그렇게 될 경우 첫 번째 예시로 예를 하나 들어보도록 하자
        # f(11)을 하기 위해서 11기준으로 f(10) + 1, f(9) + 2, f(6)+5 중에 가장 작은 것을 선택해야 한다
        # 즉 위를 일반화 하면 f(n) = min(f(n-coin) + 1) 관계가 된다 



        f = [float('inf')]* (amount+1) #f(1) 부터 시작

        f[0]=0 # 초기 0을 빼먹음

        for i in range(1, amount+1):
            for coin in coins: # 어차피 돌면서 최솟값이 계속 갱신됨

                if i - coin >=0: # 불가능한건 skip하게 만들어줌 
                    f[i] = min(f[i], f[i - coin] +1) # f[i] 계속 바뀌면서 본인 스스로 최소한으로

        if f[amount] == float('inf'):
            return -1

        return f[amount]
        

class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        # 우선 10^5이므로 O(N) 정도로 끝내야 할 듯합니다.
        # Brute Force로 풀 수 있습니다. 
        # anchor index하나씩 올라가면서 그 다음 j index로 쭉 돌면서 prices[j] -prices[i]에 대한 max를 갱신하면 됩니다. O(N^2)
        
        # Brute Force (Time Limit이 걸립니다.)
        # max_profit = 0
        # for i in range(len(prices)):
        #     for j in range(i+1, len(prices)):
        #         max_profit = max(max_profit, prices[j] - prices[i])

        # return max_profit

        # 그렇다면, 해당 Brufe Force대신, 다른 방식을 생각해봐야 합니다. 
        # O(N) 으로 갱신하면서 풀 수 있을듯합니다. min을 갱신하고, 
        min_price = 10000
        max_profit = 0
        for i in range(len(prices)):
            min_price = min(min_price, prices[i])
            max_profit = max(max_profit, prices[i] - min_price)
               
        return max_profit

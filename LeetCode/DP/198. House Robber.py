class Solution:
    def rob(self, nums: List[int]) -> int:
        # 해당 문제도 DP문제로 풀수 있다. 일반화하는 것이 가장 중요한데,
        # [1,2,3,1] 왼쪽부터 오른쪽까지 강도를 하면서 n번재 포인트에서 최대로 많이 훔쳤을때 금액에 대해 일반화해보자
        #  1 2 4 
        # f(n) = max(f(n-2) + num[n] , f(n-1)) 이렇게 된다
        # Initial Condition f(0) = num[0] 이되고, f(1) = max(num[0], num[1]) 이된다 
        
        if len(nums) == 1:
            return nums[0]

        # 공간 확보
        f = [0]*len(nums)
        f[0] = nums[0]
        f[1] = max(nums[0], nums[1])

        for i in range(2, len(nums)):
            f[i] = max(f[i-2] + nums[i] , f[i-1])

        print(f)

        return f[-1]

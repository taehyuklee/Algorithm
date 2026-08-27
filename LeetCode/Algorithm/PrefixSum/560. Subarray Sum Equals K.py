class Solution:
    def subarraySum(self, nums: List[int], k: int) -> int:

        if len(nums) == 1:
            if nums[0] == k:
                return 1
            else:
                return 0
        
        culcum_hash = {0:1}
        culcum_sum = 0
        answer = 0

        # [ 1, 2, 3 ] <- 누적합을 점진적으로 계산해 갑니다. 
        #  1 3 6 
        # 3 - 3 = 0 0인 지점 하나 추가 
        # 6 - 3 = 3 누적합이 3인지점을 빼면 나머지 [3] 이 나옵니다.
        for i in range(len(nums)):

            culcum_sum += nums[i]

            prefix_p = culcum_sum - k

            if prefix_p in culcum_hash:
                answer+= culcum_hash[prefix_p]

            # 본인까지 누적합 포함해서 해야할듯 아니면 맨 마지막꺼가 빠짐
            # 이 순서면 특히 k = 0일 때 자기 자신을 과거 prefix처럼 세어버릴 수 있어. 예를 들어:
            if culcum_sum not in culcum_hash:
                culcum_hash[culcum_sum] = 1
            else:
                culcum_hash[culcum_sum] += 1
            

        #print(answer)
        return answer

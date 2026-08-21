class Solution:
    def longestConsecutive(self, nums: List[int]) -> int:

        if len(nums) ==0:
            return 0

        hash_set = set(nums)
        nums_list = list(hash_set)
        nums_list.sort()

        cnt = 1
        max_cnt = 1

        for i in range(len(nums_list)-1):
            print(nums_list[i], nums_list[i+1])
            if nums_list[i+1] == nums_list[i] + 1:
                cnt += 1
            else:
                max_cnt = max(cnt, max_cnt)
                cnt = 1
        
        return max(cnt, max_cnt)

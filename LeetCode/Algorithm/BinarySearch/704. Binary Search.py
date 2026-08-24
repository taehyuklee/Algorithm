class Solution:
    def search(self, nums: List[int], target: int) -> int:

        def binary_search(nums: List[int], low, high):

            # 종료 조건
            if low > high:
                return -1

            mid = low + (high - low)//2

            mid_val = nums[mid]

            if mid_val == target:
                return mid
            elif mid_val < target:
                return binary_search(nums, mid+1, high)
            else:
                return binary_search(nums, low, mid-1)
        
        low_init = 0
        high_init = len(nums)-1
        return binary_search(nums, low_init, high_init)

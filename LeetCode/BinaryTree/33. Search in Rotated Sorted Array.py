class Solution:
    def search(self, nums: List[int], target: int) -> int:
        # 깨져 있는 곳은 어차피 한 곳이다. 이에 따라 반씩 나눠서 찾으면 됨.
        # 중앙을 잘라 왼쪽 오른쪽 배열중 정렬된 곳을 찾아간다. 
        # 해당 배열에서 만약 target이 안에 존재할 경우 우리가 아는 binary search처럼 답을 찾아내야함.
        # 해당 배열에서 target이 존재하지 않을 경우 반대쪽으로 가야 함. (배열이 정렬되어 있지 않은 경우로)

        # 오른쪽에 있는가를 물어보는 것 
 
        
        def binarySearch(nums, low, high, target):

            # recursive (종료 조건)
            if low > high:
                return -1

            # 중앙값
            mid = low + (high-low)//2

            if nums[mid] == target:
                return mid

            # 왼쪽 정렬
            if nums[low] <= nums[mid]: #앞에서 이미 mid는 판단했지만 깨진 부분을 판단하기 위해 <=로한다
                # target이 왼쪽 정렬에 존재하는가?
                if nums[low] <= target < nums[mid]: # mid는 이미 확인했으니까 low만
                    return binarySearch(nums, low, mid-1, target)
                else:
                    return binarySearch(nums, mid+1, high, target)

            # 오른쪽 정렬
            else:
                # target이 오른쪽 정렬에 존재하는가?
                if nums[mid+1] <= target <= nums[high]:
                    return binarySearch(nums, mid+1, high, target)
                else:
                    return binarySearch(nums, low, mid-1, target)

        init_low = 0
        init_high = len(nums)-1

        return binarySearch(nums, init_low, init_high, target)

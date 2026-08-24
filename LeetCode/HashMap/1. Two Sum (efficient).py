from collections import defaultdict

class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        # 우선 Brute Force로 두 번 loop를 돌면서 O(n^2) Time Complexity로 풀 수 있습니다만.. 
        # 더 효율적인 방법이 존재합니다. 모두 HashMap으로 만들어서 한 번 순회하면서 target-순회 num = rest num 
        # 이렇게 해서 rest num이 hashMap에 존재하면 그 숫자의 index를 순회하면 될 듯합니다. 그렇게 할 경우 time compleixty O(2*n)으로 끝날 듯 합니다.

        # 이것 보다 더 좋은 방식으로 worst case로 O(n)으로 끝낼수 있는 방법이 있습니다. 
        # 바로 한 싸이클 돌면서 어차피 정답은 딱 2개만 있다는 특징이 있기때문에 하나가 나오면 그에 대한 답은 정해진다
        
        hash_map = {}

        for i in range(len(nums)):
            
            # hash_map[nums[i]] = i 먼저 사전에 넣게 되면 중복될수 있다. 
            rest_num = target - nums[i]

            if rest_num in hash_map:
                return [hash_map[rest_num], i]
            else:
                hash_map[nums[i]] = i #확인하고 나서야 없으면 추가한다.

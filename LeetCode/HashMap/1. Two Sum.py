from collections import defaultdict

class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        # 우선 Brute Force로 두 번 loop를 돌면서 O(n^2) Time Complexity로 풀 수 있습니다만.. 
        # 더 효율적인 방법이 존재합니다. 모두 HashMap으로 만들어서 한 번 순회하면서 target-순회 num = rest num 
        # 이렇게 해서 rest num이 hashMap에 존재하면 그 숫자의 index를 순회하면 될 듯합니다. 그렇게 할 경우 time compleixty O(2*n)으로 끝날 듯 합니다.

        hash_map = defaultdict(List[int])

        for i in range(len(nums)): # O(n)
            if nums[i] in hash_map: # 중복된 숫자를 처리하기 위해 Index는 중복을 피해서 해줍니다.
                hash_map[nums[i]].append(i)
            else:
                hash_map[nums[i]] = [i]

        # print(hash_map)
        
        for key, value in hash_map.items(): # O(n)
            rest_num = target - key
            if rest_num in hash_map:
                # 중복되는 케이스
                if len(hash_map[rest_num]) >=2:
                    return hash_map[rest_num]
                elif len(hash_map[rest_num]) ==1 and hash_map[rest_num][0] == value[0]:
                    continue

                # 중복되지 않는 케이스
                return [value[0], hash_map[rest_num][0]]


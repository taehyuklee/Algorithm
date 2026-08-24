class Solution:
    def threeSum(self, nums: list[int]) -> list[list[int]]:

        target = 0
        candidate = set()
        nums = sorted(nums)
        
        # Two Sum을 한다고 생각하면
        for i in range(len(nums)):
            
            # 이것만 Skip해도 엄청 빨라지네 
            if i>0 and nums[i] == nums[i-1]:
                continue

            anchor = nums[i]

            # 정렬을 했기때문에 가능한 것 맨 처음 0초과하면 어차피 0이될수 없음 
            if anchor>0:
                break

            target_2 = target - anchor

            hash_map = {}
            hash_map[anchor] = i

            for j in range(i+1, len(nums)):
                
                sec_anchor = nums[j]
                rest_num = target_2 - sec_anchor

                if rest_num in hash_map and hash_map[rest_num] != i:
                    # 있으면 같이 반환
                    # print([anchor, rest_num, sec_anchor])
                    # list는 set의 원소가 안됨. tuple은 가능함
                    candidate.add(tuple(sorted([anchor, rest_num, sec_anchor])))
                else:   
                    hash_map[sec_anchor] = j 
    
        list_answer = list(candidate)
        for i in range(len(list_answer)):
            list_answer[i] = list(list_answer[i])

        # print(list_answer)
        return list_answer

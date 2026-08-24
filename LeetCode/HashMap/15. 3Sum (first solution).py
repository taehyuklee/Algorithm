class Solution:
    def threeSum(self, nums: list[int]) -> list[list[int]]:

        target = 0
        candidate = set()
        
        # Two Sum을 한다고 생각하면
        for i in range(len(nums)):
            anchor = nums[i]

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

'''
처음에 정렬하지 않을 경우 Time Limit Exceeded
315 / 316 testcases passed 가나옴 처음 정렬하면 굳이 같은 숫자 i를 매번 계산할 필요도 sort할 필요도 없다.
'''

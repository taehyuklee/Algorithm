from collections import defaultdict

class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        default_dict = defaultdict(int)
        
        for num in nums:
            default_dict[num] += 1

        list_item = list(default_dict.items())
        
        # 결국 정렬 
        list_item.sort(key=lambda x: x[1], reverse=True)
        answer_list = []

        for i in range(k):
            tuple_1 = list_item[i][0]
            answer_list.append(tuple_1)

        return answer_list

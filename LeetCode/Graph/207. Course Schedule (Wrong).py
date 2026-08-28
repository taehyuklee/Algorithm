class Solution:
    def canFinish(self, numCourses: int, prerequisites: List[List[int]]) -> bool:
        # must에들어가는 class를 따로 관리해야 할 듯 합니다. 
        # case가 [1, 0] [0, 1] 이 케이스들이 false 케이스인 전부인지를 확인해봐야 합니다. 
        # cyclic하게 이어붙어지면

        hash_map = {}

        for i in range(len(prerequisites)):
            # hash map 중복
            a, b = prerequisites[i]
            if b not in hash_map:
                hash_map[b] = [a]
            else:
                hash_map[b].append(a)

        print(hash_map)
        for key, values in hash_map.items(): #O(N)
            for value in values:
                if value in hash_map:
                    for val in hash_map[value]:
                        if  val == key:
                            return False
        
        return True

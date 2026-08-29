class Solution:
    def merge(self, intervals: List[List[int]]) -> List[List[int]]:
        print(intervals)
        # 일단 긴 한줄을 생각하고 그 사이를 채워가는 형식으로 갈 것 같습니다.

        long_range = [0]*(10**4)
        # print(long_range)

        for interval in intervals: #O(N)
            
            for idx in range(interval[1]-interval[0]+1): # 독립된 다시 반복하지 않게 O(N)
                if long_range[interval[0]+idx] ==1:
                    continue
                long_range[interval[0]+idx] = 1

        print(long_range)

        answser_list = []
        start_bool = False
        for el in range(len(long_range)):
            if long_range[el] == 1 and start_bool == False:
                start_p = el
                start_bool = True

            if start_bool == True:
                if long_range[el] == None or long_range[el] == 0:
                    end_p = el-1
                    start_bool = False
                    answser_list.append([start_p, end_p])

        return answser_list

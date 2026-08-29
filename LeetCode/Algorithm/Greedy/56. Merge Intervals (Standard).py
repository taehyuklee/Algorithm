class Solution:
    def merge(self, intervals: List[List[int]]) -> List[List[int]]:
        # 우선 시작점을 정렬하고 끝점과 다음 시작점을 비교해가면서 겹치는 부분을 봅니다.
        
        # O(nlong)
        intervals.sort(key=lambda x: x[0])

        # Initial
        start_p = intervals[0][0]
        end_p = intervals[0][1]

        answer_list = [[start_p, end_p]]
        
        for i in range(1, len(intervals)):
            old_s, old_e = answer_list[-1]
            next_s = intervals[i][0]
            next_e = intervals[i][1]

            # [1, 3] 옛날 end가 그 다음 start보다 뒤에 있으면 end 두개 비교해서 큰걸로 마지막 start는 처음껄 가져가면 됨
            if old_e >= next_s:
                new_s = old_s
                new_e = max(old_e, next_e)
                answer_list.pop() # 기존꺼 제거하고 
                answer_list.append([new_s, new_e])
            
            else:
                answer_list.append([next_s, next_e])

        print(answer_list)
        return answer_list

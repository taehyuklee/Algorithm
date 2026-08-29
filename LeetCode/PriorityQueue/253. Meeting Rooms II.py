

class Solution:
    def minMeetingRooms(self, intervals: List[List[int]]) -> int:
        # 예외처리 1개 일때

        intervals.sort(key=lambda x: x[0])

        
        # Simulation처럼 
        # 순서대로 가면서 room을 
        rooms = []

        old_start = 0
        old_end = 0
        cnt_room = 1
        append_bool = False

        for i in range(len(intervals)):
            new_start, new_end = intervals[i]

            rooms.sort(key=lambda x: x[1])

            if rooms:
                old_start, old_end = rooms[0]

            if new_start<old_end:
                rooms.append([new_start, new_end])
                cnt_room += 1
            else:
                if rooms:
                    #교체 해야합니다.
                    # 지웁니다.
                    rooms.pop(0) 
                rooms.append([new_start, new_end])

        return cnt_room

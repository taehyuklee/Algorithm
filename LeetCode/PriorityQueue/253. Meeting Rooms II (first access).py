

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

            for room in rooms:
                old_start, old_end = room

                if new_start<old_end:
                    rooms.append([new_start, new_end])
                    append_bool = True
                    cnt_room += 1

            if append_bool != True:
                rooms.append([new_start, new_end])
            print(rooms)
            append_bool = False



        print(cnt_room)

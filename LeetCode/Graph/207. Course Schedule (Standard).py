from collections import deque

class Solution:
    def canFinish(self, numCourses: int, prerequisites: List[List[int]]) -> bool:

        # --------------------------------------------------
        # 1. Graph 생성
        # --------------------------------------------------

        graph = [[] for _ in range(numCourses)]

        # numCourses = 4
        #
        # 처음:
        # graph =
        # [
        #   [],   # course 0 이후에 들을 수 있는 과목들
        #   [],   # course 1 이후에 들을 수 있는 과목들
        #   [],   # course 2 이후에 들을 수 있는 과목들
        #   []    # course 3 이후에 들을 수 있는 과목들
        # ]


        # --------------------------------------------------
        # 2. Indegree 생성
        # --------------------------------------------------

        indegree = [0] * numCourses

        # 처음:
        #
        # indegree = [0, 0, 0, 0]
        #
        # 의미:
        # course 0 선수과목 0개
        # course 1 선수과목 0개
        # course 2 선수과목 0개
        # course 3 선수과목 0개
        #
        # 아직 prerequisites를 읽기 전이라 전부 0


        # --------------------------------------------------
        # 3. prerequisites를 읽으면서
        #    graph + indegree 구성
        # --------------------------------------------------

        for course, prerequisite in prerequisites:

            graph[prerequisite].append(course)

            # prerequisite → course
            #
            # 예:
            # [1, 0]
            #
            # 0을 먼저 들어야 1을 들을 수 있음
            #
            # 0 → 1

            indegree[course] += 1


        # prerequisites를 하나씩 따라가보면:
        #
        #
        # -------------------------------
        # 첫 번째 [1, 0]
        # -------------------------------
        #
        # 0 → 1
        #
        # graph[0].append(1)
        #
        # graph =
        # [
        #   [1],
        #   [],
        #   [],
        #   []
        # ]
        #
        # course 1은 선수과목 0이 필요함
        #
        # indegree[1] += 1
        #
        # indegree =
        # [0, 1, 0, 0]
        #
        #
        # -------------------------------
        # 두 번째 [2, 0]
        # -------------------------------
        #
        # 0 → 2
        #
        # graph[0].append(2)
        #
        # graph =
        # [
        #   [1, 2],
        #   [],
        #   [],
        #   []
        # ]
        #
        # indegree[2] += 1
        #
        # indegree =
        # [0, 1, 1, 0]
        #
        #
        # -------------------------------
        # 세 번째 [3, 1]
        # -------------------------------
        #
        # 1 → 3
        #
        # graph[1].append(3)
        #
        # graph =
        # [
        #   [1, 2],
        #   [3],
        #   [],
        #   []
        # ]
        #
        # indegree[3] += 1
        #
        # indegree =
        # [0, 1, 1, 1]
        #
        #
        # -------------------------------
        # 네 번째 [3, 2]
        # -------------------------------
        #
        # 2 → 3
        #
        # graph[2].append(3)
        #
        # 최종 graph:
        #
        # graph =
        # [
        #   [1, 2],
        #   [3],
        #   [3],
        #   []
        # ]
        #
        # indegree[3] += 1
        #
        # 최종 indegree:
        #
        # [0, 1, 1, 2]
        #
        # 의미:
        #
        # course 0: 선수과목 0개
        # course 1: 선수과목 1개 (0)
        # course 2: 선수과목 1개 (0)
        # course 3: 선수과목 2개 (1, 2)


        # --------------------------------------------------
        # 4. 처음부터 들을 수 있는 과목 찾기
        # --------------------------------------------------

        queue = deque()

        for course in range(numCourses):

            if indegree[course] == 0:
                queue.append(course)


        # indegree:
        #
        # [0, 1, 1, 2]
        #
        # indegree == 0인 course는 0뿐
        #
        # queue =
        #
        # deque([0])
        #
        # 의미:
        #
        # "현재 바로 들을 수 있는 과목 = 0"


        # --------------------------------------------------
        # 5. 실제 처리 시작
        # --------------------------------------------------

        completed = 0

        # 아직 아무 과목도 듣지 않았으므로
        #
        # completed = 0


        while queue:

            current = queue.popleft()

            completed += 1


            # ==============================================
            # 첫 번째 while
            # ==============================================
            #
            # queue = [0]
            #
            # current = 0
            #
            # queue = []
            #
            # completed = 1
            #
            # 이제 course 0을 들었다고 생각하면 됨.


            for next_course in graph[current]:

                indegree[next_course] -= 1


                # current = 0
                #
                # graph[0] = [1, 2]
                #
                # 즉 0을 선수과목으로 가지고 있는:
                #
                # 1
                # 2
                #
                # 를 확인함.
                #
                #
                # next_course = 1
                #
                # indegree[1]:
                #
                # 1 → 0
                #
                # indegree =
                #
                # [0, 0, 1, 2]
                #
                # 이제 1은 필요한 선수과목이 없으므로
                #
                # queue.append(1)
                #
                #
                # queue = [1]
                #
                #
                # 다음:
                #
                # next_course = 2
                #
                # indegree[2]:
                #
                # 1 → 0
                #
                # indegree =
                #
                # [0, 0, 0, 2]
                #
                # queue.append(2)
                #
                # queue =
                #
                # [1, 2]


                if indegree[next_course] == 0:
                    queue.append(next_course)


            # 첫 번째 while 종료 후:
            #
            # completed = 1
            #
            # queue = [1, 2]
            #
            # indegree =
            #
            # [0, 0, 0, 2]


            # ==============================================
            # 두 번째 while
            # ==============================================
            #
            # current = 1
            #
            # queue:
            #
            # [1, 2]
            #   ↓ popleft()
            #
            # [2]
            #
            # completed = 2
            #
            # graph[1] = [3]
            #
            # 즉 1을 완료하면 3의 선수조건 하나가 해결됨.
            #
            # indegree[3]:
            #
            # 2 → 1
            #
            # indegree =
            #
            # [0, 0, 0, 1]
            #
            # 하지만 indegree[3] != 0
            #
            # 즉 아직 course 3은 못 들음.
            #
            # 왜?
            #
            # course 3은
            #
            # 1 AND 2
            #
            # 둘 다 완료되어야 하기 때문.
            #
            # queue는 여전히:
            #
            # [2]


            # ==============================================
            # 세 번째 while
            # ==============================================
            #
            # current = 2
            #
            # queue = []
            #
            # completed = 3
            #
            # graph[2] = [3]
            #
            # indegree[3]:
            #
            # 1 → 0
            #
            # indegree =
            #
            # [0, 0, 0, 0]
            #
            # 이제 course 3의 선수조건이 모두 해결됨.
            #
            # 따라서:
            #
            # queue.append(3)
            #
            # queue =
            #
            # [3]


            # ==============================================
            # 네 번째 while
            # ==============================================
            #
            # current = 3
            #
            # queue = []
            #
            # completed = 4
            #
            # graph[3] = []
            #
            # 3 다음에 이어지는 과목이 없으므로
            # 아무 작업도 하지 않음.
            #
            # while 종료.


        # --------------------------------------------------
        # 최종 결과
        # --------------------------------------------------

        # completed = 4
        # numCourses = 4
        #
        # 모든 과목을 정상적으로 처리함.
        #
        # 따라서:
        #
        # 4 == 4
        #
        # True

        return completed == numCourses

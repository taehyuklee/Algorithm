class Solution:
    def canFinish(self, numCourses: int, prerequisites: List[List[int]]) -> bool:
        # 필수과목이 Cyclic해질 경우 문제가 생길수 있습니다.

        # Graph를 만들고 선수과목들을 하나씩 제거하면서 그래프 끝까지 완주 할 수 있는지를 볼 수 있으면 됩니다.
        # 각 node마다 indegree (들어오는 화살표 개수)를 측정하고 indegree가 0인 노드로부터 출발합니다. (선수과목 필요 없음)
        # 탐색하면 할수록 (과목을 들을 수록) 그 다음 node의 indegree 줄어드는 포인트를 잡아 구현합니다.

        # Graph를 만듭니다. (Graph의 자료구조는 Hashmap으로 만들겠습니다)
        graph = {}

        # 필수과목 기준으로 b -> a node로 되어 있기때문에, b 기준으로 잡도록 하겠습니다
        for i in range(len(prerequisites)): #O(N)
            a, b = prerequisites[i]
            
            if b in graph:
                graph[b].append(a)

            else:
                graph[b] = [a]
        
        # { b: [a,c], a: [d]}
        # b -> a -> d
        #    |-> c

        # 그리고 필수과목이 몇 개있는지 각각 과목마다 숫자를 count한 array를 만들게 됩니다
        indegrees = [0] * numCourses
        # [0,0,0,0]

        # indegrees 채우기 (필수과목들이 얼마만큼 있는지 확인) #O(N)
        for course, prerequsite in prerequisites:
            indegrees[course] += 1

        # a -> b -> d
        #    |- c - | BFS를 사용합니다.
        visit_table = [False] * numCourses
        from collections import deque

        Q = deque()

        # indegree가 0인 과목을 우선 queue에 넣습니다 (시작점)
        for i in range(len(indegrees)):
            if indegrees[i] == 0:
                Q.append(i)

        completed = 0

        # BFS 
        while Q:
            
            init_list = []
            for i in range(len(Q)):
                q = Q.popleft()
                # visit_table[q] = True 완벽하지 않은 부분도 완벽하다라고 하는 케이스가 있습니다
                # a -> c 
                # b  - |

                completed+=1

                if q in graph:
                    next_nodes :List[int] = graph[q] 
                else:
                    next_nodes =[]

                # 그 다음 단계로 갔기때문에 indegrees 화살표 개수를 줄입니다
                for j in range(len(next_nodes)):
                    indegrees[next_nodes[j]] -= 1

                    if indegrees[next_nodes[j]] == 0:
                        Q.append(next_nodes[j])

        if completed == numCourses:
            return True
        else:
            return False
            



from collections import deque

# 가장 짧은 거리를 보장해야 하므로 bfs로 할 수 있다. 그렇게 최단거리로 간 노드들중 가장 멀리간 것을 선택하면 된다.
# 이전에 dfs로 구현했을때는 시간초과가 남
def bfs(start, graph_dict, memory):
    Q = deque()

    cnt = 0
    Q.appendleft([start, cnt])
    memory[start] = cnt

    while Q:
        cur_node, cnt = Q.pop()
        for each_node in graph_dict[cur_node]:
            if memory[each_node] == -1:
                new_cnt = cnt + 1
                Q.appendleft([each_node, new_cnt])
                memory[each_node] = new_cnt


def solution(n, edge):
    answer = 0
    graph_dict = {}

    memory = [-1] * (n + 1)

    for i in range(1, n + 1):
        graph_dict[i] = []

    for vertex in edge:
        graph_dict[vertex[0]].append(vertex[1])
        graph_dict[vertex[1]].append(vertex[0])

    bfs(1, graph_dict, memory)

    memory.sort(reverse=True)
    old_value = memory[0]
    for i in range(0, len(memory) + 1):
        if old_value == memory[i]:
            answer += 1
        else:
            break
            
    return answer

import heapq


def dijkstra(start, destination):
    global memory, graph_dict

    heap = []
    heapq.heappush(heap, [0, start])
    memory[start] = 0

    # heap이 알아서 visit할거는 선택해줄 것임.
    while heap:
        # 방문
        w, n = heapq.heappop(heap)

        node_lists = graph_dict[n]

        # 실제 방문할려고 보니까 이전에 탐험했을때 이미 더 짧은 거리가 있었다.
        if w > memory[n]:
            continue

        # 탐색 (해당 Node로부터)
        for node in node_lists:
            new_weight = w + node[0]
            new_node = node[1]

            # 탐색했을때 기존에 있는 것보다 크면 의미가 없다.
            if new_weight < memory[new_node]:
                # memory update
                memory[new_node] = new_weight
                heapq.heappush(heap, [new_weight, new_node])


N = int(input())
M = int(input())

graph_dict = {}

# (중요) 기존에 있었던 것 처럼 upsert하면 안되고 아래처럼 모든 Node에 대해서 만들어놓긴 해야 한다.
for node in range(1, N+1):
    graph_dict[node] = []

for i in range(M):
    src, dest, weight = map(int, input().split(' '))
    graph_dict[src].append([weight, dest])


INF = float('inf')
memory = [INF] * (N+1)

src, dest = map(int, input().split(' '))

dijkstra(src, dest)

print(memory[dest])

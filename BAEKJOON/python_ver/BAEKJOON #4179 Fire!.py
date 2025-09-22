from collections import deque

def print2D(array):
    for i in range(len(array)):
        print(array[i])

def burned_fire(board_map, dist_f, visit_f, fire_start):
    # 동서남북
    dx = [0, 0, 1, -1]
    dy = [1, -1, 0, 0]

    Q = deque()

    for fire_coord in fire_start:
        f_x, f_y = fire_coord
        Q.append([f_x, f_y, 0])
        visit_f[f_x][f_y] = True

    while Q:
        x_o, y_o, time = Q.popleft()

        for d in range(4):
            x_n = x_o + dx[d]
            y_n = y_o + dy[d]

            if x_n >= len(board_map) or y_n >= len(board_map[0]) or x_n < 0 or y_n < 0:
                continue
            if board_map[x_n][y_n] == '#' or board_map[x_n][y_n] == 'F' or visit_f[x_n][y_n] == True:
                continue
            if board_map[x_n][y_n] == '.' or board_map[x_n][y_n] == 'J':
                dist_f[x_n][y_n] = time + 1
                visit_f[x_n][y_n] = True
                Q.append([x_n, y_n, time + 1])


def escape(board_map, dist_j, visit_j, dist_f, j_start):
    # 동서남북
    dx = [0, 0, 1, -1]
    dy = [1, -1, 0, 0]

    Q = deque()

    j_x, j_y = j_start
    Q.append([j_x, j_y, 0])
    visit_j[j_x][j_y] = True

    while Q:
        x_o, y_o, time = Q.popleft()

        for d in range(4):
            x_n = x_o + dx[d]
            y_n = y_o + dy[d]
            new_time = time + 1

            if x_n >= len(board_map) or y_n >= len(board_map[0]) or x_n < 0 or y_n < 0:
                return new_time
            if board_map[x_n][y_n] == '#' or board_map[x_n][y_n] == 'F' or visit_j[x_n][y_n] == True:
                continue

            # 이 부분에 대한 사고가 부족했다. (처음에 dist_j[x_n][y_n] >= dist_f[x_n][y_n] 으로 했다.
            # if new_time >= dist_f[x_n][y_n]: # 지훈이가 더 늦게 도착하면 이미 불은 있는거니까.
            #     continue

            if board_map[x_n][y_n] == '.':
                # 이 조건이 생각하기가 조금 까다로웠다. dist_f[x_n][y_n] 해당 부분
                if dist_f[x_n][y_n] != 0 and new_time >= dist_f[x_n][y_n]:  # 지훈이가 더 늦게 도착하면 이미 불은 있는거니까.
                    continue
                dist_j[x_n][y_n] = new_time
                visit_j[x_n][y_n] = True
                Q.append([x_n, y_n, new_time])

    return -1
def solution():
    # 동시에 따로 가는건 사실상 상태를 따로 관리할수밖에 없다. 그렇게 하는게 맞음. (판을 두 개가 가지고 가자)
    # 행, 렬
    N, M = list(map(int, input().strip().split(" ")))

    board_map = []
    fire_start = []
    x_j, y_j = 0, 0
    for i in range(N):
        row_str = input().strip()
        for j in range(M):
            element = row_str[j]
            if element == 'J':
                x_j, y_j = i, j
            elif element == 'F':
                fire_start.append([i, j])
        board_map.append(list(row_str))

    #지훈 및 불 상태 관리
    dist_j = [[0] * M for _ in range(N)]
    dist_f = [[0] * M for _ in range(N)]

    visit_j = [[False] * M for _ in range(N)]
    visit_f = [[False] * M for _ in range(N)]

    #불 상태 관리 (불에 대한 상태는 끝)
    burned_fire(board_map, dist_f, visit_f, fire_start)

    ans = escape(board_map, dist_j, visit_j, dist_f, [x_j, y_j])

    if ans == -1:
        print("IMPOSSIBLE")
    else:
        print(ans)


solution()

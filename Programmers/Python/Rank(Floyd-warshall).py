def solution(n, results):
    answer = 0
    board = [[float('inf')] * (n+1) for _ in range(n+1)]

    # 우선 있는것 부터 mapping시켜 놓자
    for a, b in results:
        board[a][b] = 1
        board[b][a] = -1

    for k in range(1, n+1):
        for i in range(1, n+1):
            for j in range(1, n+1):
                if i == j or board[i][j] in [1,-1]:
                    continue
                # 중간에 연결되어 있다면, 해당 node를 1로 바꿔줘야 한다.
                if board[i][k] == board[k][j] == 1:
                    board[i][j] = 1
                    board[j][i] = -1

    for row in board:
        if row.count(float('inf')) ==2:
            answer += 1

    return answer

def connection(com_idx, computers, computers_visit):
    for j in range(len(computers[com_idx])):
        if computers[com_idx][j] == 1 and computers_visit[j] != True:
            print(computers_visit)
            computers_visit[j] = True
            connection(j, computers, computers_visit)


def solution(n, computers):
    answer = 0

    computers_visit = [False] * n

    for visit_idx in range(len(computers_visit)):
        if not computers_visit[visit_idx]:
            connection(visit_idx, computers, computers_visit)
            answer +=1

    return answer

answer = []

def change_check(begin, change):
    cnt = 0
    for i in range(len(begin)):
        if begin[i] != change[i]:
            cnt += 1

    if cnt == 1:
        return True
    else:
        return False


def recursive(begin, target, visit, words, cnt):
    if begin == target:
        answer.append(cnt)
        return

    for idx in range(len(words)):

        if change_check(begin, words[idx]) and not visit[idx]:
            visit[idx] = True
            cnt += 1
            recursive(words[idx], target, visit, words, cnt)

            visit[idx] = False
            cnt -= 1

def solution(begin, target, words):
    visit = [False] * len(words)
    cnt = 0

    recursive(begin, target, visit, words, cnt)

    answer.sort()

    if answer:
        return answer[0]
    else:
        return 0

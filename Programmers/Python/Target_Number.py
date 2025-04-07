def recursive(summation, numbers, target, answer, cur_depth, tot_depth):
    print("cur_depth: " + str(cur_depth) + " / " + str(tot_depth))
    print("length: " + str(len(numbers)))
    print("summation: " + str(summation))
    print("\n")

    # 아래 조건으로 하게 되면 5일때 return안하고 바로 넘어가게 된다. summation == target안맞을 경우 그래서 if문을 두 번써야 한다.
    # if cur_depth == tot_depth and summation == target:
    #     print("summation in depth " + str())
    #     answer[0] += 1
    #     print("answer: " + str(answer))
    #     return

    if cur_depth == tot_depth:
        if summation == target:
            print("summation in depth " + str())
            answer[0] += 1
            print("answer: " + str(answer))
        return

    next_one = numbers.pop(0)
    summation += next_one
    cur_depth += 1

    recursive(summation, numbers, target, answer, cur_depth, tot_depth)

    # 원상 복귀
    summation -= next_one
    cur_depth -= 1
    numbers.insert(0, next_one)  # 이거 복구 안했었다.
    print("복구")
    print("cur_depth: " + str(cur_depth) + " / " + str(tot_depth))
    print("length: " + str(len(numbers)))
    print("summation: " + str(summation))
    print("\n")

    next_one = numbers.pop(0)
    summation += -next_one
    cur_depth += 1
    recursive(summation, numbers, target, answer, cur_depth, tot_depth)

    # 원상 복귀
    summation -= -next_one
    cur_depth -= 1
    print("복구")
    print("cur_depth: " + str(cur_depth) + " / " + str(tot_depth))
    print("length: " + str(len(numbers)))
    print("summation: " + str(summation))
    print("\n")
    numbers.insert(0, next_one)


def solution(numbers, target):
    answer = [0]
    summation = 0
    cur_depth = 0
    tot_depth = len(numbers)
    print(tot_depth)

    recursive(summation, numbers, target, answer, cur_depth, tot_depth)

    return answer


numbers = [4, 1, 2, 1]
target = 4

print(solution(numbers, target))


################ 규칙 찾아서 좀 더 간단하게 일반화 한것 ########################
def recursive(summation, numbers, target, answer, cur_depth, tot_depth):
    
    if cur_depth == tot_depth:
        if summation == target:
            answer[0] += 1
        return

    for sign in [1, -1]:
    
        next_one = sign * numbers.pop(0)
        summation += next_one
        cur_depth += 1

        recursive(summation, numbers, target, answer, cur_depth, tot_depth)

        # 원상 복귀
        summation -= next_one
        cur_depth -= 1
        numbers.insert(0, next_one)  # 이거 복구 안했었다.


def solution(numbers, target):
    answer = [0]
    summation = 0
    cur_depth = 0
    tot_depth = len(numbers)

    recursive(summation, numbers, target, answer, cur_depth, tot_depth)

    return answer[0]


# 처음에 잘못된 버전 - 부분합을 for loop를 돌려버려서 worst case에 (O^2)이 되어버림.

N, M = map(int, input().split())
input_list = list(map(int, input().split()))

left_point = 0
right_point = 0
answer_count = 0

while left_point != N and right_point != N:
    sub_sum = 0
    for i in range(left_point, right_point+1):
        sub_sum += input_list[i]

    if sub_sum < M:
        right_point += 1
    elif sub_sum > M:
        left_point +=1
    else:
        answer_count +=1
        left_point += 1

print(answer_count)

##################################################################
#아래같이 풀어야함
N, M = map(int, input().split())
input_list = list(map(int, input().split()))

left_point = 0
right_point = 0
answer_count = 0
sub_sum = 0

while left_point <= N and right_point <= N:
    #right이 이미 벗어났다는것은 더 이상 전진의 의미가 없다 사실 ㄱ

    if sub_sum < M:
        if right_point < N:
            sub_sum += input_list[right_point]
        right_point += 1

    elif sub_sum > M:
        sub_sum -= input_list[left_point]
        left_point +=1

    else:
        answer_count +=1
        sub_sum -= input_list[left_point]
        left_point += 1
    # print(str(left_point) + "   " + str(right_point))
    # print(sub_sum)
print(answer_count)

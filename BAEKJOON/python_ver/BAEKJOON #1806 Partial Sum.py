from typing import List

N, target = map(int, input().split(' '))

input_array = list(map(int, input().split(' ')))

start_point: int = 0
end_point: int = 0
answer_array: List[int] = []

partial_sum = input_array[start_point]
min_length = float('inf')

while True:

    if partial_sum < target:
        end_point += 1
        if end_point >= N:
            break
        partial_sum += input_array[end_point]

    elif partial_sum >= target:
        min_length = min(min_length, end_point - start_point+1)
        partial_sum -= input_array[start_point]
        start_point += 1

if min_length != float('inf'):
    print(min_length)
else:
    print(0)

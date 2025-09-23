import sys

''' (10 이상인지 확인)
5 1 3 5 10 7 4 9 2 8  => sum 5
s
e

5 1 3 5 10 7 4 9 2 8  => sum 6
s
  e

5 1 3 5 10 7 4 9 2 8  => sum 9
s
    e

5 1 3 5 10 7 4 9 2 8  => sum 14
s
      e

5 1 3 5 10 7 4 9 2 8  => sum 9
  s
      e


5 1 3 5 10 7 4 9 2 8  => sum 8
                   s
                   e
'''

import sys

def solution():
    N, S = map(int, input().split(" "))
    input_list = list(map(int, input().split(" ")))

    start_p = 0
    end_p = 0
    # min_len = int(float('inf'))
    min_len = sys.maxsize
    summation = input_list[0]

    while start_p < N and end_p < N:

        if summation >= S:
            # 이거에 대한 위치가 문제인데 (이미 summation이 S를 넘은 시점에 들어온거니까)
            min_len = min(min_len, end_p - start_p + 1)
            summation -= input_list[start_p]
            start_p += 1
            # 이거에 대한 위치가 문제인데
            # min_len = min(min_len, end_p - start_p + 1)

        else:
            end_p += 1
            if end_p != N:
                summation += input_list[end_p]

    if min_len == sys.maxsize:
        print(0)
    else:
        print(min_len)


solution()

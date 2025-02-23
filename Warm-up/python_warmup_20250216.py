# Python 문법 다시 한 번 각인 시키기
# from typing import List, Any

# arr : List[Any] = [(1, 5), (2, 3), (1, 2), (3, 1)]

# # 두 번째 요소(인덱스 1)를 기준으로 정렬
# sorted_arr = sorted(arr, key=lambda x: x[1])
# # print(sorted_arr)

# job = lambda x: x[1]

# # print(job(arr))


# # Python에서의 sort와 sorted 차이점

# arr = [1,3,1,23,7,4,2,67,78]

# # 원본을 정렬시켜버림 Instance (Object)내의 method
# arr.sort()

# # print(arr)

# # sorted() 원본이 아닌 새로운 list를 만들어서 반환함 
# sortedArr = sorted(arr) # Python의 Global 내장 함수

# # print(sortedArr)

# # Key로 정렬
# arr = [("orange", 5), ("banana", 2), ("apple", 3)]

# def get_first_element(x):
#     return x[0]  # 첫 번째 요소를 반환

# arr.sort(key=get_first_element)

# # print(arr)


# arr = [("apple", 5), ("banana", 2), ("orange", 3)]

# # key 함수 정의
# def get_first_element(x):
#     print(f"get_first_element({x}) = {x[0]}")  # 호출된 함수와 그 결과 출력
#     return x[0]

# # sort 호출 전에 원본 리스트 출력
# print("Before sort:")
# print(arr)

# # sort 실행 (key는 get_first_element)
# arr.sort(key=get_first_element)

# arr.sort(key=lambda x: x[1])

# # sort 후 결과 출력
# print("\nAfter sort:")
# print(arr)


############### Python Basic Grammar  #################
# Input


# List push, pop 
python_list = [1,2,3,4,5,6,7]

# List에 Push
python_list.append(999)

print(python_list)

# 어떠한 값을 객체를 제거할때 사용 

# Python에서 특정 객체를 제거할때 사용. (객체의 내용을 보고 즉, 내용을 확인해야하므로 O(N))
# removed_data = python_list.remove(3)

# Python에서 특정 index에 있는 data를 pop한다. (index 접근, 맨 처음, 마지막 O(1), 중간 index O(N))
pop_data = python_list.pop()
print(pop_data)

# 일반적으로index로 접근하게 되면 O(1) 걸리겠지만, 그냥 객체를 찾아서 제거하기 위해서는 Worst (O(N)) 이 걸린다. 

# 특정 index에 특정 값을 넣기 위함. (O(N))
python_list.insert(3, "hi")

print(python_list)



# Map push, remove
'''
Python에서는 Map구조를 Dictionary라는 자료구조로 명명해서 사용하고 있다. 
'''
python_map = {"100": "good"}

# Dictionary 새로운 key value 추가 하는 방법
python_map["1"] = "shot"
python_map["2"] = "1"
python_map["100"] = "1" # 해당 value로 덮어 씌어진다.
print(python_map)

# print(python_map.get("1"))
# print(python_map["1"])
# Dictionary 기존 key 삭제 (del은 반환이 아니라 내부의 변수를 삭제해버림)
# del python_map["1"]

pop_data = python_map.pop("1")
print(pop_data)
print(python_map)


#dictionary 결합
dict1 = {"100": "good"}
dict2 = {"200": "good2"}
dict1.update(dict2)

print(dict1)



# Dequeue (Queue로 양방향 Queue를 많이 사용한다)
from collections import deque # (FIFO 구조)

Q = deque()

Q.append([1,2])
Q.append([2,3])
Q.append([3,4])

x, y = Q.popleft()

print(Q)

print(x)
print(y)

# ---------- 위와같이 Queue구조로 사용할 수 있다 ------------- #
Q1 = deque()
Q1.appendleft([1,2])
Q1.appendleft([2,3])

x,y = Q1.pop() # 마지막 index꺼를 pop한다.

print(x)
print(y)




# sort 정렬 

sort_before_list = [(1, "apple"), (5, "cocktail"), (2, "shoot"),(7, "apply"), (3, "goodbye")]

def sorting (element_list):
    return element_list[1]

# sort_before_list.sort(key=sorting)

sorted(sort_before_list, key=lambda x: x[0])
# lambda 기법에 익숙하다면 할 수 있다. 

print(sort_before_list)




# Python 문자열 출력 방식 (입력)
'''
7 4
0 0 0 2 3 2 3
1 2 3 1 2 3 1
2 3 1 2 3 1 2
1 2 3 0 2 3 1
2 3 1 2 3 1 2
3 1 2 3 1 2 3
1 2 3 1 2 3 1
1 3
2 2
3 1
4 3
'''

# 공백 기준 입력 받기
# Python의 input은 readline 한줄씩 읽어 온다.
# Java에서 처럼 Scanner sc = new Scanner(); sc.nextInt(); 이런게 없다. 무조건 br.readline(); 또는 sc.nextLine() 개행문자까지 가져옴 처럼 된다.
'''
list_str = input().split() # 한줄씩 읽으므로 .split()을 써서 공백으로 split해줘야 한다. 본래는 string형태의 list가 반환된다. 
N, M = map(int, list_str)
'''
# 위에 있는 두 줄이 합쳐져서 아래와 같이 된다.
N, M = map(int, input().split())
# print(N)
# print(M)

# print([0] * N) # 파이썬 만의 문법이다. 


# Python에는 사실 배열 개념이 존재하지는 않는다. 이중 List형태일뿐. 다만 index접근은 가능하게 해놓음.
# board = [[] for _ in range(N)]
# for i in range(N):
#     line = list(map(int, input().split()))
#     for j in range(N):
#         board[i].append(line[j])

# print(board)

# comprehension으로 한 번에 
board_final = [list(map(int, input().split())) for _ in range(N)]

print(board_final)

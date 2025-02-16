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
python_map["1"] = "1"
python_map["2"] = "1"
python_map["100"] = "1" # 해당 value로 덮어 씌어진다.

# Dictionary 기존 key 삭제 
del python_map["1"]

str_python : str = "hello world"



print(python_map)
print(type(python_map))





# Dequeue




# Python 문자열 출력 방식 
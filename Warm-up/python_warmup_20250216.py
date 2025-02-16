# Python 문법 다시 한 번
from typing import List, Any

arr : List[Any] = [(1, 5), (2, 3), (1, 2), (3, 1)]

# 두 번째 요소(인덱스 1)를 기준으로 정렬
sorted_arr = sorted(arr, key=lambda x: x[1])
print(sorted_arr)

job = lambda x: x[1]

print(job(arr))


# Python에서의 sort와 sorted 차이점

arr = [1,3,1,23,7,4,2,67,78]

# 원본을 정렬시켜버림 Instance (Object)내의 method
arr.sort()

print(arr)

# sorted() 원본이 아닌 새로운 list를 만들어서 반환함 
sortedArr = sorted(arr) # Python의 Global 내장 함수

print(sortedArr)

# Key로 정렬
arr = [("orange", 5), ("banana", 2), ("apple", 3)]

def get_first_element(x):
    return x[0]  # 첫 번째 요소를 반환

arr.sort(key=get_first_element)

print(arr)
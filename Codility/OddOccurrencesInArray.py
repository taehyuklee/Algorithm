# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here

    python_map = {}

    for num in A:
        if num in python_map.keys():
            python_map[num] += 1
        else:
            python_map[num] = 1

    for key, value in python_map.items():
        if value%2 != 0:
            return key

    

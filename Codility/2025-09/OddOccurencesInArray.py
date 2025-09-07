# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here

    python_map = {}

    for num in A:
        if num not in python_map.keys():
            python_map[num] = 1

        else:
            python_map[num] += 1

    for keys, values in python_map.items():
        if values %2 != 0:
            return keys


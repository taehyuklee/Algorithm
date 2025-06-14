# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    python_set = set()

    for num in A:
        python_set.add(num)

    return len(python_set)

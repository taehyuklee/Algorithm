# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(A):
    # Implement your solution here
    num_dict = {}

    for i in range(len(A)):
        if A[i] in num_dict.keys():
            num_dict[A[i]] += 1
        else:
            num_dict[A[i]] = 1

    for entry in num_dict.items():
        if entry[1] % 2 != 0:
            return entry[0]

# easy
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(X, Y, D):
    # Implement your solution here
    sol = (Y-X) // D

    if (Y-X)%D !=0:
        sol+=1
    return sol

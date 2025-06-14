# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(S):
    # Implement your solution here
    python_list = []
    left_bracket = ['(', '{', '[']
    right_bracket = [')', '}', ']']
    map_bracket = {'(': ')', '{': '}', '[':']'}

    for s in S:
        if s in left_bracket:
            python_list.append(s)

        elif s in right_bracket:
            poped_str = python_list.pop()
            if s == map_bracket[poped_str]:
                continue
            else:
                return 0

    return 1


############################ 수저본 ############################
# you can write to stdout for debugging purposes, e.g.
# print("this is a debug message")

def solution(S):
    # Implement your solution here
    python_list = []
    left_bracket = ['(', '{', '[']
    right_bracket = [')', '}', ']']
    map_bracket = {'(': ')', '{': '}', '[':']'}

    for s in S:
        if s in left_bracket:
            python_list.append(s)

        elif s in right_bracket:
            if not python_list:
                return 0
                
            poped_str = python_list.pop()
            if s == map_bracket[poped_str]:
                continue
            else:
                return 0

    return 1 if not python_list else 0

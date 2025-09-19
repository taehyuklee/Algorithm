'''
# N Strings
S = []

returns the maximum nums of strings from S can be built

given test case in problem
S = ['abc', 'abb', 'cb', 'a', 'bbb'] K = 2
Answer: 3 (abb, a, bbb)

두글자로 만들 수 있는 것 => 3개

Other Test cases
S = ['adf', 'jjbb', 'jcgj', 'eiji', 'adf'] K = 3
Answer: 1 (adf)

S = ['abcd', 'efgh'] K = 3
Answer: 0

S = ['bc', 'edf', 'fde', 'dge', 'abcd'] K = 4
Answer: 3 (edf, fde, dge)

My Solution: BackTracking
'''

from typing import List, Set
from copy import deepcopy

answer_list: List[str] = []
answer: int = 0


# DFS 탐색기법 + BackTracking
def recursive(S: List[str], K: int, str_set_old: Set[str]):
    global answer_list, answer

    for i in range(len(S)):
        str_1 = S.pop(i)  # 실제 시험볼때 pop(0)을 하고 있었어서 계속 안맞었었음
        new_str_set = set(str_1)  # 중복을 제거하기 위해 set으로 감싼다
        str_set_copy = deepcopy(new_str_set)

        str_set_old.update(str_set_copy)
        
        # Backtracking 조건
        if len(str_set_copy) > K:
            S.insert(i, str_1) # BackTracking조건이기때문에 i에서 pop한걸 다시 넣어준다.
            continue 

        answer_list.append(str_1)

        # 만약 K라면 Update해준다.
        if len(str_set_copy) == K:
            new_answer = len(answer_list)
            answer = max(answer, new_answer)
            
        # 재귀 호출
        recursive(S, K, str_set_copy)
        
        # 돌아갈때는 원상 복귀 해줘야 함. 이 부분에서 앞에 pop을 한 i로 넣어줘야 한다
        S.insert(i, str_1)
        answer_list.pop(0)


def solution(S: List[str], K: int):
    answer = 0
    return answer

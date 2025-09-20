# https://school.programmers.co.kr/learn/courses/30/lessons/12930
def solution(s):
    strings = s.split(" ")
    
    answer = ''
    for string in strings:
        new_string = ''
        for idx, s in enumerate(string):
            
            if (idx)%2 ==0:
                # 짝수일 경우
                new_string +=  s.upper()
            else:
                # 홀수일 경우
                new_string +=  s.lower()
        
        answer+= new_string + " "
    return answer[:-1]

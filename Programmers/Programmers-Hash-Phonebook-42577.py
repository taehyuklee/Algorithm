def solution(phone_book):
    answer = True

    for i in range(len(phone_book)):
    #refNum = phone_book[i] 
        for j in range (i+1, len(phone_book)):
            if (phone_book[j].startswith(phone_book[i])):
                return False 
            if(phone_book[i].startswith(phone_book[j])):
                return False

    return answer
#원래 answer=False 넣어서 return answer로 해줬는데, 이게 timeout난다
#if 분기처리해서 return해줄때는 바로 return 해주는게 좋다
'''
원래는 이렇게 했었음
if (phone_book[j].startswith(phone_book[i])):
    answer = False 
if(phone_book[i].startswith(phone_book[j])):
    answer = False 
'''

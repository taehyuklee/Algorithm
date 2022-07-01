def solution(clothes):
    answer = 0
    #clothes의 각 type별로 갯수가 중요 
    dic={}
    for item in clothes:
        if item[1] in dic:
            dic[item[1]] += 1
        else:
            dic[item[1]] = 1
    
    #각 type의 갯수에 +1 (입지 않았을때를 포함) -> 전체 경우의수 곱하기
    #ex) (type1 num +1) X (type2 num + 1) X (type3 num+1) = totalnum+1
    count = 1
    for num_type in dic:
        count *= (dic[num_type]+1)
    
    #마지막에 모두 안입었을때를 -1
    #totalnum+1 -1 = totalnum
    answer = count-1
    print(answer)
    
    return answer

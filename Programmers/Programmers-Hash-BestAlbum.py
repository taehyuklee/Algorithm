  def solution(genres, plays):  
    answer = []
    dic = {}
    for i in range(len(genres)):
        if genres[i] in dic:
            dic[genres[i]].append([plays[i],i])
        else:
            dic[genres[i]] = [[plays[i],i]]
    
    #첫 번째 속한 노래가 많이 재싱된 장르를 먼저 수록한다 (기준 계산하기)
    genre_rank = {}
    for i in range(len(genres)):
        if genres[i] in genre_rank:
            genre_rank[genres[i]] += plays[i]
        else:
            genre_rank[genres[i]] = plays[i]
    
    sorted_rank = sorted(genre_rank.items(), key = lambda x: x[1], reverse =True)
    
    for sor_genre in sorted_rank:
        key = sor_genre[0]
        
        sel_List = dic[key]
        #sel_List 출력 -> [[600, 1], [2500, 4]]
        #sel_List중 (같은 장르) -> 속에서 재생수가 많은거 먼저 
        last_List = sorted(sel_List, key=lambda x: (-x[0], x[1]))
        
        j = 0
        for list1 in last_List:
            answer.append(list1[1])
            j +=1
            
            if j ==2:
                break
    return answer

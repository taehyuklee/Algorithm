def solution(str1, str2):
    alpabet_list ='abcdefghijklmnopqrstuvwxyz'
    str1_multi_set = []
    str2_multi_set = []

    for i in range(len(str1)-1):
        if str1[i].lower() in alpabet_list and str1[i+1].lower() in alpabet_list:
            str_element = str1[i] + str1[i+1]
            str1_multi_set.append(str_element.lower())
    
    for i in range(len(str2)-1):
        if str2[i].lower() in alpabet_list and str2[i+1].lower() in alpabet_list:
            str_element = str2[i] + str2[i+1]
            str2_multi_set.append(str_element.lower())
    
    # print(str1_multi_set)
    # print(str2_multi_set)
    
    # 공통으로 가지고 있는 원소부터 구해보자 (해당 집합을 구할필요 없이 숫자로만 계산 하자)
    str1_set = set(str1_multi_set)
    str2_set = set(str2_multi_set)
    
    if len(str1_set) ==0 and len(str2_set) ==0:
        return 65536
    
    return_set = str1_set.intersection(str2_set)
    # print(return_set)
    
    # 교집합 원소가 각 multi-set에 몇 개씩 있는지 확인하기 
    
    cnt_1 = {}
    # print(return_set)
    for idx, set_element in enumerate(return_set):
        cnt_element = 0
        cnt_1[set_element] = 0
        for str1_set in str1_multi_set:
            if str1_set == set_element:
                cnt_element += 1
                cnt_1[set_element] = cnt_element
    
    # print(cnt_1)
    
    cnt_2 = {}
    # print(return_set)
    for idx, set_element in enumerate(return_set):
        cnt_element = 0
        cnt_2[set_element] = 0
        for str2_set in str2_multi_set:
            if str2_set == set_element:
                cnt_element += 1
                cnt_2[set_element] = cnt_element
    
    # print(cnt_2)
    
    #get final_cnt
    final_cnt = {}
    for key, value in cnt_1.items():
        value2 = cnt_2[key]
        final_cnt[key] = min(value, value2)
    
    # print(final_cnt)
    
    #get 교집합 갯수 합
    inter_cnt = 0
    for value in final_cnt.values():
        inter_cnt += value
    
    # print(inter_cnt)
    # for str2_set in str2_multi_set:
    #     if str2_set in return_set:
    #         cnt2 +=1
    
    
    # 교집합 수 (잘못 구함 모든 원소에 대해서 이렇게 구해버림 원소 각각에 대해 필요함)
    # inter_cnt = min(cnt1, cnt2)
    
    tot_cnt = len(str1_multi_set) + len(str2_multi_set)
    inter_tot_cnt = tot_cnt - inter_cnt
    
    jarcard_similarity = int(inter_cnt/inter_tot_cnt*65536)
    return jarcard_similarity

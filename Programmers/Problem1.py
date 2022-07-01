from collections import deque
import copy

#board =[[100,90,98,88,65],[50,45,99,85,77],[47,88,95,80,67],[61,57,100,80,65],[24,90,94,75,65]] #test case 1
#board = [[50,90],[50,87]] #test case 2
board = [[70,49,90],[68,50,38],[73,31,100]] #test case 3

row_num = len(board); col_num = len(board[0])
min_index = 0; min_value = 0; max_value=0; max_index=0
temp_col = []; temp_collect=[]

def getScore(score):
    if score >=90: return "A"
    elif score>=80: return "B"
    elif score>=70: return "C"
    elif score>=50: return "D"
    else: return "F"

for j in range(col_num):
    for i in range(row_num):
        temp_col.append(board[i][j])
    temp_collect.append(temp_col)
    temp_col = []

#최대, 최소의 index를 받아오기
max_index = []; min_index = []
for j in range(row_num): #len(temp_collect)
    min_value = min(temp_collect[j])
    max_value = max(temp_collect[j])
    #최소, 최대 index 구하기
    for i in range(col_num): #len(temp_collect[i])
        if(min_value == temp_collect[j][i]):
            min_index.append(i)
        #print("check", i, max_value, temp_collect[i]) Debugging part
        if(max_value == temp_collect[j][i]):
            max_index.append(i)

    #제거하자
    if(len(min_index) ==1):
        for i in min_index:
            if(i == j): del temp_collect[i][j]
    if(len(max_index) ==1):
        for i in max_index:
            if(i ==j): del temp_collect[i][j]
    #초기화
    max_index = []; min_index = []

avg_list =[]
for elem_list in temp_collect:
    avg_list.append(sum(elem_list)/len(elem_list))

answer = ""
for score in avg_list:
    StringGrade = getScore(score)
    answer = answer + StringGrade

print(answer)

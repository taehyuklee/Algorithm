def solution(prices):
  profit = 0; depth_p = 0; depth_l = 0
  
  for i in range(1, len(prices)):
    price_old = prices[i-1]; price_next = prices[i]
  
    #다음 step의 price가 더 클때
    if price_next > price_old:
      depth_p +=1; depth_l = 0 #이득 depth를 하나 더 증가 #손실 depth는 초기화
  
    #다음 step의 price가 더 작을때
    elif price_next < price_old:            
      depth_l +=1; depth_p =0
  
    #가격이 같을때는 depth초기화
    elif price_next == price_old:
      depth_l, depth_p = 0, 0
  
    #3번연속 상승 또는 내려갈때
    if depth_p == 3:
      profit -= price_next #3번 연속 올라가면 매수 (내 통장에서 돈 나감 그래서 -)
      depth_l, depth_p = 0, 0 #depth는 초기화
  
    elif depth_l ==3 or i == len(prices)-1: #or 다음 조건 - 마지막에는 무조건 매도해야함
      profit += price_next #3번 연속 내려가면 매도 (파니까 내 통장에 돈 들어옴 +)
      depth_l, depth_p = 0, 0

    answer = profit
    return answer

#### 예시도 같이 넣어놓았습니다

testCase1 = [5, 3, 4, 6, 7, 9, 19, 18, 17, 16, 12, 14, 15, 20, 13]
testCase2 = [1, 2, 3, 3, 4, 5, 5, 6, 7, 7, 8, 9, 10, 1]
print(solution(testCase1))

#당시에 다 못풀었습니다.
import copy

def checktable(temp_board):
  TF = False
  
  return TF
  
def solution(word1, word2):
  answer = 0
  
  board = [[['A']*3 for _ in range(3)] for _ in range(3)]
  temp_board = [['A']*3 for _ in range(3)]
  spell = ['A','B','C']    print(board)
  
  #모든 경우의수를 Cube형태 3x3x3으로 넣는다
  for i in range(3):
    for j in range(3):
      for k in range(3):
        board[j][i][k] = spell[k]
        
      print(temp_board)
      Check = checktable(temp_board)
      if Check == True:
        answer +=1
        
      return answer
      
word1 = 'ABC'
word2 = 'CBC'

print(solution(word1, word2))

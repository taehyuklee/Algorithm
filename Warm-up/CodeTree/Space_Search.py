class Golem:

    def __init__(self, ci: int, di: int):
        self.c_i: int = ci
        self.d_i: int = di

    def __repr__(self):
        return "c_i: " + str(self.c_i) + " ,d_i: " + str(self.d_i) + " ||"

R, C, K = map(int, input().split(' '))

# List Comprehension은 '생성한다'는 개념이 디폴트로 포함되어 있는 문법이다.
board = [[0]*C for _ in range(R)]

print(board)

golem_list = []

for i in range(K):
    c_i, d_i = map(int, input().split(' '))
    golem = Golem(c_i, d_i)
    golem_list.append(golem)

print(golem_list)

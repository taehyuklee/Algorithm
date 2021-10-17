from sys import stdin

#Make input part
input = stdin.readline

N = int(input())

S_List =[]
visit = []

for _ in range(N**2):
    S_List.append(list(map(int, input().strip().split())))

for _ in range(N**2):
    visit.append([False]*N)

#Direction setting - 상하좌우
dy = [-1, 1, 0, 0]
dx = [0, 0, -1, 1]

Class =[[0]*N for _ in range(N)]

dic_stu={}
for S, p1, p2, p3, p4 in (S_List):
    dic_stu[S] = [p1, p2, p3, p4]

def Cal_sat(Class):

    manjok = 0; count_favor = 0;

    for j in range(N):
        for i in range (N):

            for d in range(len(dy)):
                side_y = j + dy[d]
                side_x = i + dx[d]

                if side_y<0 or side_x<0 or side_y>=N or side_x>=N:
                    continue
                if Class[side_y][side_x] in dic_stu[Class[j][i]]:
                    count_favor+=1

            if count_favor ==0: manjok +=0
            elif count_favor ==1: manjok +=1
            elif count_favor ==2: manjok +=10
            elif count_favor ==3: manjok +=100
            elif count_favor ==4: manjok +=1000

            count_favor =0

    return manjok


for S, p1, p2, p3, p4 in (S_List):

    max_love = -1
    max_vaccancy = -1
    max_i = 0
    max_j = 0

    for j in range(N):
        for i in range (N): #어차피 낮은곳부터 update되니까

            if visit[j][i] == False:
                love_count = 0
                vaccancy = 0

                #4방향에 p1, p2, p3, p4가 몇명 앉아있는지 Count
                for d in range(len(dy)):
                    side_y = j + dy[d]
                    side_x = i + dx[d]

                    if side_y<0 or side_x<0 or side_y>=N or side_x>=N:
                        continue

                    if Class[side_y][side_x] ==p1: love_count +=1
                    if Class[side_y][side_x] ==p2: love_count +=1
                    if Class[side_y][side_x] ==p3: love_count +=1
                    if Class[side_y][side_x] ==p4: love_count +=1
                    if Class[side_y][side_x] ==0: vaccancy += 1

                if max_love < love_count:
                    max_j = j #행
                    max_i = i #열
                    max_love = love_count
                    max_vaccancy  =vaccancy

                if max_love == love_count and max_vaccancy <vaccancy:
                    max_j = j  # 행
                    max_i = i  # 열
                    max_love = love_count
                    max_vaccancy  = vaccancy

    Class[max_j][max_i] = S
    visit[max_j][max_i] = True

print(Cal_sat(Class))
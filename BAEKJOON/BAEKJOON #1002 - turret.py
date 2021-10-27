T = int(input())

for _ in range(T):
    x1, y1, r1, x2, y2, r2 = list(map(int, input().split()))
    distance = ((x1-x2)**2 + (y1-y2)**2)**(0.5)
    if abs(r1 - r2) < distance < (r1 + r2):  print(2)
    elif (x1, y1) != (x2,y2) and abs(r1 - r2) == distance or r1 + r2 == distance:  print(1)
    elif distance == 0 and r1 == r2: print(-1)
    else: print(0)
'''
if (1)
if (2)
if (3)
로 되어 있는 것과

if (1)
elif (2)
elif (3)
elif (4)
로 되어있는 것의 차이점
if는 독립적으로 (1)--> (2) --> (3)을 다 훓어보고 지나간다
if elif는 중간에  (1) --> (2) 만약 (2)조건에서 만족한다면 끊긴다.

예시 
print("just if")
if A ==1:
    print(1)
if A ==2:
    print(2)
if A ==1:
    print(3)
else:
    print(4)

print("elif loop")

if A ==1:
    print(1)
elif A ==2:
    print(2)
elif A ==1:
    print(3)
else:
    print(4)
'''

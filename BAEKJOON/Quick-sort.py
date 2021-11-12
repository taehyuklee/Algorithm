#퀵정렬 (Divde & Conquer)
import sys

sys.setrecursionlimit(10000)
input = sys.stdin.readline

N = int(input())
array = [int(input()) for i in range(N)]

def quick_sort(array, start, end):
    #종료조건 (이 조건을 안해주면 계속 Recursive하게 들어간다)
    if start>=end:
        return

    pivot_index = start
    pivot = array[pivot_index]
    left = start+1
    right = end

    #Pivot을 기준으로 분할하는 작업
    while left<=right:
        #while array[left]<=pivot and left<=end: #left<end
        while left<=end and array[left]<=pivot: #and조건이라서 한번에 처리되는게 아니라 순서대로 처리됨
            #왼쪽은 Pivot보다 작으면 계속 진행 크면 멈춰 따라서 작으면 while을 돌려야함
            left+=1
        while right>start and array[right]>=pivot:
            #으론쪽 Pivot보다 크면 계속 진행 작으면 멈춰 따라서 크면 while을 돌려야함
            right-=1
        #엇갈린 상황 - 왼쪽, 오른쪽으로 분할이 다 끝난상황이다 Pivot을 중앙으로!
        if left>right: #array[left]<array[right]:
            array[start], array[right] = array[right], array[start]
            median = right #Pivot의 위치를 median에 저장
        else: #엇갈리지 않은 상황에서는 위치를 계속 왼쪽에는 pivot보다 작은수 오른쪽은 pivot보다 큰 수로 바꿔준다.
            array[right], array[left] = array[left], array[right]
    #왼쪽을 정복
    quick_sort(array, start, right-1)
    #오른쪽을 정복
    quick_sort(array, right+1, end)

quick_sort(array, 0, len(array)-1)

for _ in range(len(array)):
    print(array[_])
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        max_len = 0
        l = 0
        hash_set = set()
        
        for r in range(len(s)):
            
            while s[r] in hash_set:
                hash_set.remove(s[l])
                l+=1
            
            # 현재 문자 추가
            hash_set.add(s[r])

            # 현재 widnow 길이
            max_len = max(max_len, r-l+1)
        
        return max_len
      
'''
Sliding Window는 이전 탐색 결과를 버리지 않고 l의 위치를 계속 이어가니까 O(n)이야.

중첩문이라고 무조건 O(n²)이 아니라, 내부 while이 프로그램 전체에서 총 몇 번 실행되는지를 봐야 한다.

r은 처음부터 끝까지 한 번 쭉 가고, l도 중복이 생길 때만 앞으로 가는데 절대 뒤로 안 돌아가.

그래서 전체 실행 동안:

r 최대 n번 이동
l 최대 n번 이동

'''

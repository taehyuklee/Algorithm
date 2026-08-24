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
      

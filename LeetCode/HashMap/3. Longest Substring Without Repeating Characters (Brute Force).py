class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        # 우선 생각나는 것은 중복 관련된게 없어야한다 -> HashSet, HashMap 자료구조 사용 
        # Brute Force로 풀 수 있나를 봤을때는 가능할듯 합니다. O(n^2)으로
        # for i in range 기준으로 한 번 잡고 그 다음 a,b,c,d,e 내용들을 Hash에 넣고 순회하면서 중복이 생기면 거기까지만의 길이를 측정하면 될듯합니다. 그렇게 해서 가장 긴걸 max로 해서 풀 수 있습니다. 10^5인걸로 봤을때 최대 10^10이 되므로 이는 올바르지 않은 방식이라 생각합니다. input S 한글자
        
        if len(s) == 1:
            return 1

        elif len(s) == 0:
            return 0

        elif len(s) == len(set(s)):
            return len(s)
        
        max_len = 0

        for i in range(len(s)):
            char_hash = {}
            anchor_char = s[i]
            char_hash[s[i]] = True

            # 한 글자 자체도 valid substring (첫 글자) <- 이거 빼먹음
            max_len = max(max_len, 1)

            for j in range(i+1, len(s)):
                sliding_char = s[j]

                if sliding_char in char_hash:
                    # max_len = max(max_len, j-i) 처음에 중복이 나와야 앞까지 계산 했는데 "eea" case같은게 안됨
                    break
                else:
                    char_hash[s[j]] = True
                    max_len = max(max_len, j-i+1) # 여기서 길이를 계속 갱신해가야함. 
        
        return max_len

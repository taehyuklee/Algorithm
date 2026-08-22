class Solution:
    def longestPalindrome(self, s: str) -> str:
        # 중심 확장 (expand around center)

        # abac abba <- 홀수, 짝수 케이스 둘 다 생각합니다.
        # i를 중심으로, 또는 i, i+1을 중심으로 확장해 나가는 형식으로 갈 예정

        res_len = 0
        res = ""

        for i in range(len(s)):
            
            l = i
            r = i         

            # Odd Case
            while l>=0 and r<len(s):
                
                # 가장 긴 palindromic substring
                if s[l] == s[r]: # 정답 조건
                    if r-l+1 > res_len:
                        res = s[l:r+1]
                        res_len = len(res)
                else: # pandlomic이 아니면 바로 빼야함.
                    break

                l -= 1
                r += 1
            
            # Even Case (l, r의 시작 포인트만 다름)
            l = i
            r = i+1

            while l>=0 and r<len(s):
                
                # 마찬가지 정답 조건
                if s[l] == s[r]:
                    if r-l+1 > res_len: # 만약 나중에 찾은 답으로 하고 싶다면 =을 넣어야함.
                        res = s[l:r+1]
                        res_len = len(res) # s로 잘못함. res로 했어야하는데;;
                else:
                    # 예를 들어 "abbcccba" -> cc에서 -> bccc -> 그 다음 if문에 bbcccb가 됨. 근데 else break없으면 bccc에서 멈추지 않아버림.
                    break
                l -= 1
                r += 1

        return res

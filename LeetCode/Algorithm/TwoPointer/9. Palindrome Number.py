class Solution:
    def isPalindrome(self, x: int) -> bool:

        if x<0:
            return False

        x_str = list(str(x))
        
        for i in range(len(x_str)):

            # odd case
            l = i
            r = i

            res_str = ""
            res_len = 0

            while l>=0 and r<len(x_str):
                
                if x_str[l] == x_str[r]:
                    if r-l+1 == len(x_str):
                        return True
                else:
                    break


                l -= 1
                r += 1


            # even case
            l = i
            r = i+1

            while l>=0 and r<len(x_str):

                if x_str[l] == x_str[r]:
                    if r-l+1 == len(x_str):
                        return True
                else:
                    break

                l -= 1
                r += 1
            

        return False

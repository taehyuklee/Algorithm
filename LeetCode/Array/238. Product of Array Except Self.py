class Solution:
    def productExceptSelf(self, nums: List[int]) -> List[int]:

        left_product = [1] # identity elements
        right_product = [1]*len(nums) # identity elements
        answer = [1]*len(nums)
        
        # I don't know why time out occrued, when I combined left operation and right operation at once
        for i in range(len(nums)-1): 
            # exclude it's own index num so at the end of the num we should skip.
            left_num = nums[i]
            left_product.append(left_product[i]*left_num)
        
        # for i in range(len(nums)):
        #     right_num = nums[len(nums)-1-i]
        #     right_product.append(right_product[i]*right_num)

        # right hand side should be filled with any nums. since it will be filled with
        # [1,1,1,1] <- right product
        '''
        when i is equal 3 -> nothing is at right side of 4 so identity element will be there 1
        i = 2 -> only number 4 is there.
        i = 1 -> 3, 4 nums are there. product 12
        i = 0 -> 2, 3, 4 nums . proudct 24
        
        index   0 , 1, 2, 3
              [ 24, 12, 4, 1 ]
        '''
        
        # exclude identity element, starting from n-2
        for i in range(len(nums)-2, -1, -1):
            right_product[i] = nums[i+1] * right_product[i+1]
        # in intuitive sight, index i just right hand side num (바로 오른쪽 숫자를 우리가 처리해야 하므로 num[i+1]을 처리한다. i 기준으로 바로 오른쪽까지 어떻게 되는지를 보는거기때문에, right_product[i+1]도 직관적인 확인 )


        print(left_product, right_product)

        for i in range(len(right_product)):
            answer[i] = left_product[i]*right_product[i]

        return answer

            

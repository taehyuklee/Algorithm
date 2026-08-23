# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution:
    def countNodes(self, root: Optional[TreeNode]) -> int:

        if root == None:
            return 0

        if root.left == None:
            return 1

        # count tree height
        left_node = root.left
        height_l = 1

        # O(logn)
        while True:
            if not left_node.left:
                break

            left_node = left_node.left
            height_l += 1

        # 마지막 right-side에 값이 있는지 확인
        height_r = 0

        if root.right:
            right_node = root.right
            height_r = 1

            # O(logn)
            while True:
                if not right_node.right:
                    break

                right_node = right_node.right
                height_r += 1

        # cumulative
        # 1*2^0 + 2^1 + 2^2 ...
        # 등비수열 합 기억이 안나면 1/1-alpha ?
        # a(r^(n-1)) / r-1

        sum_answer = 0  # O(logn)

        for i in range(height_l + 1):
            sum_answer += 1 * (2 ** i)


        if height_r == height_l:
            return sum_answer

        else:
            return (
                1
                + self.countNodes(root.left)
                + self.countNodes(root.right)
            )

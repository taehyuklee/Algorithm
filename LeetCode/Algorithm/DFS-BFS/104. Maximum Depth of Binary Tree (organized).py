# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution:
    def maxDepth(self, root: Optional[TreeNode]) -> int:
        
        def recursiveDepth(root, depth):

            if root is None:
                return 0

            # 왼쪽 탐색
            if root.left is not None:
                left_depth = recursiveDepth(root.left, depth+1)
            else:
                left_depth = depth

            # 오른쪽 탐색
            if root.right is not None:
                right_depth = recursiveDepth(root.right, depth+1)
            else:
                right_depth = depth

            return max(left_depth, right_depth)

        return recursiveDepth(root, 1)

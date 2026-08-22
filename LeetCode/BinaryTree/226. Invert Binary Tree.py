# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:
        
        def recursive_search(node: TreeNode):

            if not node:
                return

            temp_node = node.left

            node.left = node.right

            recursive_search(node.left)

            node.right = temp_node

            recursive_search(node.right)
        
        # inverse 시작
        recursive_search(root)

        return root

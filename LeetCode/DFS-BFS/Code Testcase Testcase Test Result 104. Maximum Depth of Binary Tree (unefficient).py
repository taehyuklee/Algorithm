# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right

class Solution:
    def maxDepth(self, root: Optional[TreeNode]) -> int:
        # DFS로 풀어야 할 듯 합니다. 바로 답이 나옵니다
        max_depth = 1

        # 빈 트리 처리
        if root is None:
            return 0
        
        def recursiveDept(root, depth):
            nonlocal max_depth

            # 현재 root 3이라고 한다면
            max_depth = max(max_depth, depth)
            
            if root.left == None:
                pass 
            else:
                left_node = root.left
                depth += 1
                recursiveDept(left_node, depth)
                # 만약 없을 경우 backtracking을 해야하므로 depth를 -1을 해야하긴 하는데 그 위치를 찾아야 합니다.
                depth -= 1

            if root.right == None:
                pass
            else:
                right_node = root.right
                depth += 1
                recursiveDept(right_node, depth)
                depth -= 1

        recursiveDept(root, 1)
        return max_depth
        

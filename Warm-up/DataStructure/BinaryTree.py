# 노드 정의
class Node:
    def __init__(self, key):
        self.key = key
        self.left = None   # 왼쪽 자식
        self.right = None  # 오른쪽 자식

# 이진 탐색 트리(Binary Search Tree) 클래스
class BinarySearchTree:
    def __init__(self):
        self.root = None

    # 삽입
    def insert(self, key):
        if self.root is None:
            self.root = Node(key)
        else:
            self._insert(self.root, key)

    def _insert(self, node, key):
        if key < node.key:
            if node.left is None:
                node.left = Node(key)
            else:
                self._insert(node.left, key)
        else:  # key >= node.key
            if node.right is None:
                node.right = Node(key)
            else:
                self._insert(node.right, key)

    # 탐색
    def search(self, key):
        return self._search(self.root, key)

    def _search(self, node, key):
        if node is None or node.key == key:
            return node
        if key < node.key:
            return self._search(node.left, key)
        else:
            return self._search(node.right, key)

    # 중위 순회 (왼쪽 - 루트 - 오른쪽)
    def inorder(self, node):
        if node:
            self.inorder(node.left)
            print(node.key, end=" ")
            self.inorder(node.right)


# ----------------------------------
# 실행 예시
bst = BinarySearchTree()
bst.insert(50)
bst.insert(30)
bst.insert(70)
bst.insert(20)
bst.insert(40)
bst.insert(60)
bst.insert(80)

print("중위 순회 결과 (오름차순):")
bst.inorder(bst.root)   # 20 30 40 50 60 70 80

print("\n검색 테스트:")
print(bst.search(40))   # Node 객체 반환
print(bst.search(100))  # None

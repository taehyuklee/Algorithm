# Invert Binary Tree — 실수 포인트 핵심 정리

## 핵심 한 줄

> **Tree 재귀에서는 `왼쪽 자식이 없으면 return`이 아니라, `현재 node 자체가 None이면 return` 해야 한다.**

왜냐하면 **왼쪽 자식은 없지만 오른쪽 자식은 있을 수 있기 때문**이다.

---

## 1. 처음 작성했던 코드

```python
def recursive_search(node: TreeNode):

    if not node.left:
        return
```

처음에는 이렇게 생각했다.

```text
왼쪽 자식이 없네?
→ 더 내려갈 곳이 없네?
→ return 하면 되겠네
```

그런데 여기서 중요한 착각이 있다.

`return`은

```text
"왼쪽으로 더 안 간다"
```

가 아니라

```text
"현재 함수 실행 전체를 끝낸다"
```

는 뜻이다.

---

## 2. 가장 직관적인 반례

Tree가 이렇게 생겼다고 하자.

```text
1
 \
  2
```

현재 `node`는 `1`이다.

```python
node.left = None
node.right = TreeNode(2)
```

여기서:

```python
if not node.left:
    return
```

을 실행하면:

```text
node.left == None
        ↓
      return
        ↓
함수 전체 종료
```

가 된다.

그런데 실제로는 오른쪽에 `2`가 있다.

```text
1
 \
  2
```

즉,

> **위의 node 입장에서 왼쪽을 봤을 때 없다고 바로 return 해버리면, 오른쪽 node가 실제로 존재하더라도 아예 처리하지 못한다.**

---

## 3. `if not node.left`의 진짜 의미

```python
if not node.left:
    return
```

이 코드는:

> "왼쪽 자식이 없으면 왼쪽만 건너뛰자."

가 아니다.

실제로는:

> **"왼쪽 자식이 없으면 현재 `recursive_search()` 실행 자체를 끝내자."**

라는 의미다.

따라서:

```text
        1
         \
          2
```

에서:

```text
1.left 없음
    ↓
return
    ↓
1.right의 2는 존재하지만 탐색하지 못함
```

이 된다.

---

## 4. Invert Binary Tree에서는 더 큰 문제가 된다

이번에는:

```text
    1
   /
  2
```

가 있다고 하자.

Invert 결과는:

```text
    1
     \
      2
```

가 되어야 한다.

현재 상태는:

```python
node.left = TreeNode(2)
node.right = None
```

그런데:

```python
if not node.right:
    return
```

이라고 하면:

```text
right가 None
    ↓
return
    ↓
swap 자체를 안 함
```

그래서 Tree가 그대로:

```text
    1
   /
  2
```

남아버린다.

---

## 5. 중요한 포인트: `None`도 swap 대상이다

현재:

```python
node.left = TreeNode(2)
node.right = None
```

이어도 그냥:

```python
node.left, node.right = node.right, node.left
```

하면 된다.

그러면:

```python
node.left = None
node.right = TreeNode(2)
```

가 된다.

즉:

```text
Before

    1
   /
  2


After

    1
     \
      2
```

`None`이라고 해서 swap을 못 하는 것이 아니다.

---

## 6. 그래서 언제 `return` 해야 하는가?

자식이 없는지를 검사하는 게 아니라:

```python
if not node:
    return
```

현재 **node 자체가 존재하는지** 검사해야 한다.

차이를 보면:

```python
# ❌ 잘못된 생각
if not node.left:
    return
```

의미:

```text
현재 node는 존재하지만
왼쪽 자식이 없으면 함수 전체 종료
```

반면:

```python
# ✅ 올바른 Base Case
if not node:
    return
```

의미:

```text
현재 위치에 node 자체가 없으면 종료
```

이다.

---

## 7. `recursive_search(None)`은 정상이다

Tree 재귀에서는 그냥:

```python
recursive_search(node.left)
recursive_search(node.right)
```

를 호출한다.

예를 들어:

```text
    1
     \
      2
```

에서 `1.left`는 `None`이다.

따라서:

```python
recursive_search(node.left)
```

은 실제로:

```python
recursive_search(None)
```

이 된다.

이건 문제가 아니다.

함수에 들어가서:

```python
def recursive_search(node):

    if not node:
        return
```

여기서 바로 끝나면 된다.

즉:

```text
recursive_search(None)
        ↓
if not node
        ↓
return
```

이게 정상적인 Tree DFS 흐름이다.

---

## 8. Base Case가 없으면 왜 에러가 나는가?

만약:

```python
def recursive_search(node):

    temp_node = node.left
```

처럼 바로 접근한다고 하자.

재귀 과정에서:

```python
recursive_search(None)
```

이 호출되면:

```python
temp_node = node.left
```

은 사실상:

```python
temp_node = None.left
```

이 된다.

그래서:

```text
AttributeError:
'NoneType' object has no attribute 'left'
```

가 발생한다.

따라서:

```python
if not node:
    return
```

이 필요하다.

---

# 9. `[1,2]`와 `[1,null,2]`

이 부분도 처음에 헷갈렸다.

## `[1,2]`

```text
    1
   /
  2
```

즉:

```python
root.left = TreeNode(2)
root.right = None
```

---

## `[1,null,2]`

```text
    1
     \
      2
```

즉:

```python
root.left = None
root.right = TreeNode(2)
```

따라서:

```text
[1,2]
```

를 invert하면:

```text
[1,null,2]
```

가 된다.

여기서 `null`은 새로운 값이 생긴 것이 아니다.

> **왼쪽 자리가 비어 있다는 것을 LeetCode가 표현하기 위해 넣은 placeholder다.**

---

# 10. 최종 올바른 코드

```python
class Solution:
    def invertTree(self, root: Optional[TreeNode]) -> Optional[TreeNode]:

        if not root:
            return None

        def recursive_search(node):

            if not node:
                return

            node.left, node.right = node.right, node.left

            recursive_search(node.left)
            recursive_search(node.right)

        recursive_search(root)

        return root
```

---

# 11. 재귀 흐름

```text
현재 node가 None?
        |
       YES
        |
      return

       NO
        |
        v
left / right swap
        |
        v
recursive_search(node.left)
        |
        v
recursive_search(node.right)
```

---

# 내가 했던 실수 정리

### 실수 1

```python
if not node.left:
    return
```

이걸:

```text
왼쪽 자식이 없으니까
왼쪽 탐색만 종료
```

라고 생각했다.

하지만 실제로는:

```text
왼쪽 자식이 없으니까
현재 함수 전체 종료
```

다.

그래서 **오른쪽 자식이 있어도 놓칠 수 있다.**

---

### 실수 2

```text
자식이 None이면 swap할 수 없다.
```

라고 생각했다.

하지만:

```python
node.left, node.right = node.right, node.left
```

에서 `None`도 그냥 정상적으로 swap된다.

---

### 실수 3

```text
recursive_search(None)
```

이 호출되면 안 된다고 생각했다.

하지만 Tree DFS에서는 **None까지 내려가는 것이 정상**이다.

```python
if not node:
    return
```

이 Base Case가 `None`을 처리한다.

---

### 실수 4

```text
[1,2]
[1,null,2]
```

의 차이가 처음에는 직관적이지 않았다.

하지만:

```text
[1,2]

    1
   /
  2
```

이고:

```text
[1,null,2]

    1
     \
      2
```

이다.

Tree에서는 **값뿐 아니라 left/right 위치가 구조 그 자체**다.

---

# 최종 암기

```text
자식이 없으면 return ❌

현재 node가 없으면 return ✅
```

그리고 특히:

> **`if not node.left: return`은 "왼쪽만 안 본다"가 아니다.  
> 현재 함수 전체를 종료하기 때문에 오른쪽 자식이 있어도 놓칠 수 있다.**

Tree DFS 기본형:

```python
def dfs(node):

    if not node:
        return

    # 현재 node 처리

    dfs(node.left)
    dfs(node.right)
```

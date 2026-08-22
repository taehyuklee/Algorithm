# Monotonic Stack (단조 스택)

## 1. 개념

**Monotonic Stack**은 일반 Stack을 사용하되, **Stack 내부의 값들이 한 방향의 순서를 유지하도록 관리하는 알고리즘 패턴**이다.

### 종류

- **Monotonic Increasing Stack**
  - 단조 증가 스택
  - 작은 값 → 큰 값 순서를 유지

- **Monotonic Decreasing Stack**
  - 단조 감소 스택
  - 큰 값 → 작은 값 순서를 유지

> 새로운 값이 들어왔을 때 Stack의 단조성을 깨뜨린다면, 조건을 만족할 때까지 기존 원소를 `pop()`한다.

---

## 2. 언제 사용하는가?

다음과 같은 조건이 보이면 **Monotonic Stack**을 의심한다.

- Next Greater Element
- Next Smaller Element
- Previous Greater Element
- Previous Smaller Element
- 오른쪽에서 처음으로 나보다 큰 값
- 오른쪽에서 처음으로 나보다 작은 값
- 다음으로 더 따뜻한 날
- 이전/다음 조건을 만족하는 가장 가까운 원소

대표 문제:

- LeetCode 739 - Daily Temperatures
- LeetCode 496 - Next Greater Element I
- LeetCode 84 - Largest Rectangle in Histogram

---

# Daily Temperatures

## 3. 문제

```text
temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
```

각 날짜에서 **며칠 뒤에 더 높은 온도가 등장하는지** 구한다.

결과:

```text
[1, 1, 4, 2, 1, 1, 0, 0]
```

Brute Force로 각 날짜마다 오른쪽을 탐색하면:

```text
Time Complexity: O(n²)
```

Monotonic Stack을 사용하면:

```text
Time Complexity: O(n)
```

---

## 4. 핵심 아이디어

Stack에는 다음 정보를 저장한다.

> **아직 자신보다 높은 온도를 만나지 못한 날짜의 index**

온도 자체가 아니라 **index를 저장**하는 것이 중요하다.

정답으로 필요한 것은 온도 차이가 아니라:

```text
며칠 뒤인가?
```

이기 때문이다.

따라서 다음 계산이 필요하다.

```python
answer[previous_index] = current_index - previous_index
```

---

## 5. Stack 동작 예시

현재 Stack에 해당하는 온도가 다음과 같다고 하자.

```text
[75, 71, 69]
         ↑
        TOP
```

현재 온도:

```text
72
```

### Step 1

```text
72 > 69
```

`69`는 자신보다 높은 온도를 찾았다.

```text
69 → POP
```

---

### Step 2

새로운 TOP은 `71`.

```text
72 > 71
```

`71`도 자신보다 높은 온도를 찾았다.

```text
71 → POP
```

---

### Step 3

새로운 TOP은 `75`.

```text
72 < 75
```

`72`는 `75`보다 높지 않으므로 여기서 중단한다.

현재 `72`를 Stack에 넣는다.

```text
Before

[75, 71, 69]

Current = 72


72 > 69
→ POP 69

[75, 71]


72 > 71
→ POP 71

[75]


72 < 75
→ STOP


PUSH 72

[75, 72]
```

Stack은 다시 **단조 감소 상태**를 유지한다.

---

## 6. 왜 TOP부터 비교하는가?

Stack이 단조 감소 상태라고 하자.

```text
[75, 72, 69]
```

현재 온도가 `76`이라면:

```text
76 > 69
→ POP

76 > 72
→ POP

76 > 75
→ POP
```

현재 값 하나가 이전의 여러 원소에 대한 정답을 한 번에 확정할 수 있다.

반대로 현재 온도가 `73`이라면:

```text
73 > 69
→ POP

73 > 72
→ POP

73 < 75
→ STOP
```

단조 감소 구조이기 때문에 `75`에서 막히면 더 이상 진행할 필요가 없다.

---

## 7. 핵심 패턴

```text
현재 값 > Stack TOP
        ↓
TOP의 정답 확정
        ↓
      POP
        ↓
새로운 TOP과 다시 비교
```

조건을 만족하지 않으면:

```text
현재 값 <= Stack TOP
        ↓
       STOP
        ↓
현재 index를 PUSH
```

따라서 하나의 원소만 검사하는 `if`가 아니라 여러 원소를 해결할 수 있도록 **`while`을 사용한다.**

---

## 8. 구현

```python
class Solution:
    def dailyTemperatures(self, temperatures):
        answer = [0] * len(temperatures)
        stack = []

        for i, temp in enumerate(temperatures):

            while stack and temperatures[stack[-1]] < temp:
                previous_index = stack.pop()

                answer[previous_index] = i - previous_index

            stack.append(i)

        return answer
```

---

## 9. 코드 흐름

### 현재 날짜와 온도

```python
for i, temp in enumerate(temperatures):
```

### Stack의 TOP index

```python
stack[-1]
```

### TOP에 해당하는 온도

```python
temperatures[stack[-1]]
```

### 현재 온도가 더 높은지 확인

```python
temperatures[stack[-1]] < temp
```

현재 온도가 더 높다면 이전 날짜는 정답을 찾은 것이다.

### 이전 index 제거

```python
previous_index = stack.pop()
```

### 기다린 날짜 수 계산

```python
answer[previous_index] = i - previous_index
```

### 현재 날짜 저장

```python
stack.append(i)
```

현재 날짜 역시 아직 미래의 더 높은 온도를 모르기 때문에 Stack에 저장한다.

---

## 10. 왜 O(n)인가?

코드를 보면:

```python
for ...
    while ...
```

구조라서 처음에는 `O(n²)`처럼 보일 수 있다.

하지만 각 index는 Stack에:

```text
최대 1번 PUSH
최대 1번 POP
```

된다.

따라서 전체적으로 최대:

```text
n번 PUSH + n번 POP
```

수준의 연산만 발생한다.

### Complexity

```text
Time  : O(n)
Space : O(n)
```

---

## 11. 핵심 직관

Monotonic Stack의 목적은 단순히 Stack을 정렬하는 것이 아니다.

진짜 목적은:

> **아직 정답을 찾지 못한 원소들을 Stack에 보관하고, 새로운 값이 등장했을 때 이전 원소들의 정답을 효율적으로 확정하는 것**

이다.

---

## 12. 문제를 보고 떠올리는 방법

문제에서 다음 표현이 등장하면:

```text
Next Greater
Next Smaller
Previous Greater
Previous Smaller
다음으로 더 큰 값
다음으로 더 작은 값
가장 가까운 더 큰/작은 값
```

먼저 생각한다.

```text
Monotonic Stack?
```

특히:

```text
Next Greater Element
        ↓
Monotonic Decreasing Stack
```

패턴을 기억해두면 좋다.

---

## 한 줄 암기

> **현재 값이 이전 원소의 정답을 확정할 수 있으면 POP하고, 아직 정답을 못 찾은 원소들만 Stack에 단조롭게 남긴다.**

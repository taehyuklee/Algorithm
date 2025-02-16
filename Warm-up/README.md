# Python Coding Test Warming-up
<p>2022년부터 2024년까지는 Java로 코딩 테스트를 준비했다. 물론 개발에서는 Java, Python 둘다 사용해왔다. 2025년에는 Python으로 다시 코딩 테스트를 준비하고자 한다. 코딩테스트로 Java를 사용할 때 느꼈던 가장 큰 이유 중 하나는 타이핑해야 할 코드가 너무 많았다는 점이다. (물론 Java는 타입을 명시할 수 있어 명확하다는 장점이 있다)
: 현 시점 2025-02-16</p>

<br>

## Python Grammar (자료구조 관련 필수 문법)
<h3> <b>[ Input ]</b> </h3>
Python에서 Input정보를 가져오기 위해 input() 내장함수를 사용하거나 sys.readline()을 사용해야 한다.

<br><br>



<h3> <b>[ Python List append (Push) & insert ]</b> </h3>
Python의 List에 어떤 값을 마지막 index 다음에 추가할때 사용 

### 1. append
우리가 일반적으로 Stack구조에서 사용하는 Push랑 같다 마지막 index다음에 특정 데이터를 삽입하게 된다.

``` python
# List자료구조는 push할때 마지막에 push된다. : 복잡도 O(N)
python_list = [1,2,3,4,5,6,7]
python_list.append(100)

print(python_list)

# Result
[1,2,3,4,5,6,7,100]
```
<br>

### 2. insert
 특정 위치에 Insert를 하고 싶을때, insert method를 사용한다. 구조는 ```list.insert(index, value)``` 형태로 되어 있으며, 원하는 value를 원하는 index에 넣을 수 있다. <br>
해당 방법은 특정 index까지 찾는 것은 O(1)이 될 수 있으나, 뒤쪽 요소들을 이동해야 하므로 최악의 경우 O(N)이 발생한다.

``` python
# List자료구조는 insert는 뒤의 요소들 이동. : 복잡도 O(N)
python_list = [1,2,3,4,5,6,7]
python_list.insert(3, 100)

print(python_list)

# Result
[1,2,3,100,4,5,6,7]
```
<br><br>


<h3> Python List pop & remove (pop)</h3>

Python의 List에서 어떠한 값을 객체를 제거할때 사용한다. index로 접근해서 제거하는 것과, 객체 내용을 보고 제거하는 것 두 가지 방법이 있다. 각각 pop, remove method에 해당된다.

### 1. pop
Python의 List에서 특정 위치의 값을 제거하고 싶을때 pop을 이용한다. (<b>index로 접근하여 해당 index 값을 빼낸다. 반환값은 해당 데이터가 반환 된다.</b>) ``` list.pop(index) ``` 형태를 가지고 있다.
<b>index 접근, 맨 처음, 마지막 O(1), 중간 index O(N) 특징을 갖는다.</b>

``` python
python_list = [1,2,3,4,5,6,7,999]
pop_data1 = python_list.pop() # 마지막 index가 pop됨. 
pop_data2 = python_list.pop(3) # 3번째 index내용이 pop됨.


# Result (LIFO)
999 # pop_data1
4 # pop_data2

```
<br>


### 2. remove
특정 내용을 기준으로 제거하게 된다. 아래의 예시에서 999를 제거하고 싶어 그러면 ```list.remove(999)```를 하게 되면 999가 제거된다.

``` python
# List자료구조에서 즉정 내용을 확인해야하므로 : 복잡도 (O(N))
python_list = [1,2,3,999,4,5,6,7,]
pop_data = python_list.remove(999) 

print(pop_data)

# Result (LIFO)
999
```
<br><br>



<h3> Python Map (Dictionary) </h3>

Python에서는 HashMap구조를 Dictionary라는 자료구조로 명명해서 사용하고 있다. 사용법을 알아보자.

<br>

### 1. put (push)
어차피 중복되지 않기때문에 중복된 key가 들어가면 해당 value로 엎어쳐진다.
```python
python_map = {}

python_map["key"] = "value"

```
<br>

### 2. del & pop (remove)
```python
python_map = {"key1":"value1", "key2":"value2"}

# del method
# del은 아예 참조 변수를 없애기때문에 Value를 반환하지 않는다.
del python_map["key1"]

# Result del
{"key2":"value2"}

# - pop method
# pop method는 해당 객체의 key에 해당되는 value를 반환하고 삭제된다.
return_data = python_map.pop("key2")
print(return_data)

# Result pop
value2

```
<br>


### 3. update (dictionary 결합)
두개의 dictionary를 결합한다.
```python
dict1 = {"100": "good"}
dict2 = {"200": "good2"}
dict1.update(dict2)

# Result 
{'100': 'good', '200': 'good2'}
```


<br><br>


<h3> Python Queue (Dequeue) </h3>

<br>


<h3> Python Output </h3>
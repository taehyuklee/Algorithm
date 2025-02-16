class HashMap:
    def __init__(self, size=10):
        """ 해시 테이블 크기(size)만큼 버킷(bucket) 리스트를 생성 """
        self.size = size
        self.buckets = [[] for _ in range(size)]  # 각 인덱스마다 빈 리스트(체이닝 방식)

    def _hash(self, key):
        """ 해시 함수: 키를 받아서 해시 인덱스를 반환 """
        return hash(key) % self.size

    def put(self, key, value):
        """ 키-값을 해시 테이블에 저장 """
        index = self._hash(key)  # 해시 함수로 인덱스 계산
        bucket = self.buckets[index]

        print(index)

        # 같은 키가 존재하면 값을 업데이트
        for i, (k, v) in enumerate(bucket):
            if k == key:
                bucket[i] = (key, value)
                return
        
        # 키가 없으면 새롭게 추가
        bucket.append((key, value))

        # 위의 로직에서 의문, 만약 key가 완전히 다른데 해시코드가 같다고해서 같은 index의 bucket List안에 같이 넣으면 새로 메모리 할당도 안되고
        # 하나의 Bucket List만 엄청 길어지잖아 어떻게 되는거지?

    def get(self, key):
        """ 키를 사용하여 값 조회 """
        index = self._hash(key)
        bucket = self.buckets[index]

        # 해당 버킷에서 키를 찾음
        for k, v in bucket:
            if k == key:
                return v
        return None  # 키가 없으면 None 반환

    def remove(self, key):
        """ 키를 사용하여 요소 삭제 """
        index = self._hash(key)
        bucket = self.buckets[index]

        # 해당 버킷에서 키를 찾아 제거
        for i, (k, v) in enumerate(bucket):
            if k == key:
                del bucket[i]
                return True
        return False  # 삭제 실패(키 없음)

    def display(self):
        """ 해시 테이블 전체 출력 (디버깅용) """
        for i, bucket in enumerate(self.buckets):
            print(f"Index {i}: {bucket}")


# 사용 예제
# 해시 맵 생성
hash_map = HashMap(size=5)  # 크기를 5로 설정 (충돌을 쉽게 유도)

# 충돌이 발생할 수 있는 키 삽입
hash_map.put("apple", 10)
hash_map.put("banana", 20)
hash_map.put("cherry", 30)

# 일부러 같은 해시 값이 나오도록 키를 설정
hash_map.put("elppa", 40)  # "apple"과 충돌 가능 (해시 값이 같을 수도 있음)
hash_map.put("ananab", 50)  # "banana"와 충돌 가능

# 해시맵 출력
hash_map.display()
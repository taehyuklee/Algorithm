class HashMap:
    def __init__(self, size=5):
        self.size = size
        self.count = 0  # 현재 저장된 key-value 개수
        self.buckets = [[] for _ in range(size)]

    def _hash(self, key):
        """ 키를 해싱하여 버킷 인덱스 반환 """
        return hash(key) % self.size

    def insert(self, key, value):
        """ 해시 맵에 (key, value) 삽입 """
        if self.count / self.size > 0.7:  # 사용률이 70% 넘으면 크기 증가
            self._resize()

        index = self._hash(key)
        bucket = self.buckets[index]

        # 이미 존재하는 키면 업데이트
        for i, (k, v) in enumerate(bucket):
            if k == key:
                bucket[i] = (key, value)
                return

        # 새로운 키 추가
        bucket.append((key, value))
        self.count += 1

    def _resize(self):
        """ 테이블 크기 2배로 증가 & 모든 데이터를 새롭게 해싱 """
        new_size = self.size * 2
        new_buckets = [[] for _ in range(new_size)]

        for bucket in self.buckets:
            for key, value in bucket:
                new_index = hash(key) % new_size
                new_buckets[new_index].append((key, value))

        self.buckets = new_buckets
        self.size = new_size
        print(f"🔄 해시 테이블 크기 증가: {new_size}")

    def get(self, key):
        """ 키로부터 값을 검색 """
        index = self._hash(key)
        bucket = self.buckets[index]

        for k, v in bucket:
            if k == key:
                return v
        return None

    def display(self):
        """ 현재 해시 맵 출력 """
        for i, bucket in enumerate(self.buckets):
            print(f"버킷 {i}: {bucket}")


# 해시맵 생성 & 데이터 삽입
hash_map = HashMap(size=5)

# 여러 개의 키 삽입 (충돌 유도)
hash_map.insert("apple", 10)
hash_map.insert("banana", 20)
hash_map.insert("cherry", 30)
hash_map.insert("date", 40)
hash_map.insert("elderberry", 50)
hash_map.insert("fig", 60)  # 70% 초과 시 리사이징 발생!

# 해시맵 출력
hash_map.display()

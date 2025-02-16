class HybridHashMap:
    def __init__(self, size=5, load_factor=0.7):
        self.size = size
        self.count = 0  # 저장된 요소 개수
        self.load_factor = load_factor  # 리사이징 기준 (70% 이상 채우면 확장)
        self.table = [None] * size  # 해시 테이블 초기화
    
    def _hash1(self, key):
        """첫 번째 해시 함수"""
        return hash(key) % self.size
    
    def _hash2(self, key):
        """두 번째 해시 함수 (이중 해싱 시 사용)"""
        return 1 + (hash(key) % (self.size - 1))
    
    def _linear_probing(self, index):
        """선형 탐사: 다음 위치 찾기"""
        return (index + 1) % self.size

    def _double_hashing(self, index, step):
        """이중 해싱: 두 번째 해시 함수 이용"""
        return (index + step) % self.size
    
    def _resize(self):
        """리사이징: 크기 2배 확장 후 데이터 재해싱"""
        new_size = self.size * 2
        old_table = self.table
        self.table = [None] * new_size
        self.size = new_size
        self.count = 0  # 다시 삽입할 것이므로 초기화
        
        for item in old_table:
            if item:
                self.put(item[0], item[1])  # 기존 데이터 재삽입

    def put(self, key, value, use_double_hashing=True):
        """키-값을 삽입 (선형 탐사 + 이중 해싱 선택 가능)"""
        if self.count / self.size >= self.load_factor:
            self._resize()  # 크기 확장
        
        index = self._hash1(key)
        step = self._hash2(key)  # 두 번째 해시 값
        
        for _ in range(self.size):  # 최대 테이블 크기만큼 탐색
            if self.table[index] is None:
                self.table[index] = (key, value)
                self.count += 1
                return
            elif self.table[index][0] == key:
                self.table[index] = (key, value)  # 기존 키 업데이트
                return
            # 충돌 발생 → 선형 탐사 또는 이중 해싱 적용
            index = self._double_hashing(index, step) if use_double_hashing else self._linear_probing(index)
        
        raise Exception("해시 테이블이 가득 찼습니다.")  # 원래 발생하지 않아야 함

    def get(self, key, use_double_hashing=True):
        """키를 검색하여 값 반환"""
        index = self._hash1(key)
        step = self._hash2(key)
        
        for _ in range(self.size):
            if self.table[index] is None:
                return None  # 키가 없음
            if self.table[index][0] == key:
                return self.table[index][1]  # 값 반환
            index = self._double_hashing(index, step) if use_double_hashing else self._linear_probing(index)
        
        return None  # 찾지 못함
    
    def remove(self, key, use_double_hashing=True):
        """키를 삭제"""
        index = self._hash1(key)
        step = self._hash2(key)
        
        for _ in range(self.size):
            if self.table[index] is None:
                return False  # 키가 없음
            if self.table[index][0] == key:
                self.table[index] = None  # 삭제 (None으로 마킹)
                self.count -= 1
                return True
            index = self._double_hashing(index, step) if use_double_hashing else self._linear_probing(index)
        
        return False
    
    def display(self):
        """해시 테이블 출력"""
        for i, item in enumerate(self.table):
            print(f"Index {i}: {item}")

# 테스트
hash_map = HybridHashMap(size=5)

# 삽입
hash_map.put("apple", 10)  
hash_map.put("banana", 20)  
hash_map.put("orange", 30)  
hash_map.put("grape", 40)  
hash_map.put("peach", 50)  # 리사이징 발생 가능

# 검색
print(hash_map.get("banana"))  # 20 출력
print(hash_map.get("grape"))   # 40 출력

# 삭제 후 검색
hash_map.remove("banana")
print(hash_map.get("banana"))  # None 출력

# 해시 테이블 상태 확인
hash_map.display()

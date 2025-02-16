class OpenAddressingHashMap:
    def __init__(self, size=10):
        self.size = size
        self.table = [None] * size  # 해시 테이블 초기화
    
    def _hash(self, key):
        """해시 함수: key의 해시 값을 테이블 크기로 나눈 나머지"""
        return hash(key) % self.size
    
    def _probe(self, index):
        """선형 탐사: 충돌이 발생하면 다음 빈 슬롯을 찾음"""
        original_index = index
        while self.table[index] is not None:
            index = (index + 1) % self.size  # 다음 슬롯으로 이동 (원형 구조)
            if index == original_index:
                raise Exception("해시 테이블이 가득 찼습니다!")
        return index
    
    def put(self, key, value):
        """키-값을 해시 테이블에 삽입"""
        index = self._hash(key)
        
        if self.table[index] is None:
            self.table[index] = (key, value)
        else:
            # 충돌 발생 → 선형 탐사(다음 빈 슬롯 탐색)
            new_index = self._probe(index)
            self.table[new_index] = (key, value)
    
    def get(self, key):
        """키에 해당하는 값을 반환"""
        index = self._hash(key)
        
        for _ in range(self.size):  # 최대 테이블 크기만큼 탐색
            if self.table[index] is None:
                return None  # 키가 존재하지 않음
            if self.table[index][0] == key:
                return self.table[index][1]  # 값 반환
            index = (index + 1) % self.size  # 다음 슬롯 탐색
        
        return None  # 찾지 못함
    
    def remove(self, key):
        """키에 해당하는 값을 삭제"""
        index = self._hash(key)
        
        for _ in range(self.size):
            if self.table[index] is None:
                return False  # 키가 존재하지 않음
            if self.table[index][0] == key:
                self.table[index] = None  # 삭제 (None으로 마킹)
                return True
            index = (index + 1) % self.size
        
        return False
    
    def display(self):
        """현재 해시 테이블 출력"""
        for i, item in enumerate(self.table):
            print(f"Index {i}: {item}")

# 테스트
hash_map = OpenAddressingHashMap(size=10)
hash_map.put("apple", 10)
hash_map.put("banana", 20)
hash_map.put("orange", 30)
hash_map.put("grape", 40)  # 충돌 가능

print(hash_map.get("banana"))  # 20 출력
hash_map.display()
hash_map.remove("banana")
print(hash_map.get("banana"))  # None 출력

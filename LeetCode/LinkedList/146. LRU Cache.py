
class Node:
    def __init__(self, key, value):
        # key도 중요하다고 함 추후 삭제할때 
        self.key = key
        self.value = value
        self.next_p = None
        self.prev_p = None

class LRUCache:

    def __init__(self, capacity: int):
        self.capacity = capacity
        self.space = {}

        # 가장 오래 안 쓴 Node인 Head와 가장 최근에 쓴 Node인 Tail을 정의한다
        self.head = Node(0,0) #head 오래 안쓴 것
        self.tail = Node(0,0) #tail 가장 최근에쓴 것

        self.head.next_p = self.tail
        self.tail.prev_p = self.head


    def get(self, key: int) -> int:
        target_node = self.space.get(key)

        if not target_node:
            return -1

        # 기존 target_node 빼고 양쪽 이어붙이기
        target_next = target_node.next_p
        target_prev = target_node.prev_p

        target_next.prev_p = target_prev
        target_prev.next_p = target_next

        # tail 가장 최근 쓴 거 업데이트
        old_prev = self.tail.prev_p
        self.tail.prev_p = target_node

        # tail.prev와 old_prev 사이에 끼워넣기 (최신화)
        target_node.next_p = self.tail
        target_node.prev_p = old_prev

        old_prev.next_p = target_node

        return target_node.value
        

    def put(self, key: int, value: int) -> None:

        if key in self.space:
            # 전체 갯수에 대한 변화는 없음
            target_node = self.space[key]

            target_node.value = value


            # 기존 노드 양쪽 이어붙여주기 
            target_next = target_node.next_p
            target_prev = target_node.prev_p

            target_next.prev_p = target_prev
            target_prev.next_p = target_next

            # 새로 썼으니까 update
            old_prev = self.tail.prev_p

            # tail 입장            
            self.tail.prev_p = target_node

            # target_node 입장
            target_node.next_p = self.tail
            target_node.prev_p = old_prev

            # old_prev 입장
            old_prev.next_p = target_node



        else:
            # 기존에 있지 않고 새로 생성했을때
            new_node = Node(key, value)

            # 가장 최근에 쓴 메모리 update
            old_latest = self.tail.prev_p
            self.tail.prev_p = new_node

            # new_node 양쪽에 연결
            new_node.next_p = self.tail
            new_node.prev_p = old_latest

            # old_latest 다음꺼 연결
            old_latest.next_p = new_node

            # 가장 오래된 메모리 self.head는 어떻게 이어붙이지? (일반화를 위해서는 나눠도 되는데)
            self.space[key] = new_node

        if len(self.space) > self.capacity:
            # 어떻게 하면 가장 오래 사용하지 않은 key-value를 없앨수 있을까?
            # head 앞에 있는거 지우고 head를 그 다음꺼랑 연결 
            old_next = self.head.next_p

            # head 입장
            # old_next랑 head 이어붙이기
            new_next = old_next.next_p
            self.head.next_p = new_next

            # new_next node 입장에서도 head 연결해줘야함   
            new_next.prev_p = self.head
            

            # 미아된 old_next 처리
            del self.space[old_next.key]
        


    # Hash Table Update if key exists -> or put the new one.
    # capacity 한계를 초과했을때 가장 오래된 key-value부터 지워버림.



# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(2)
# # param_1 = obj.get(1)
# obj.put(1,1)
# obj.put(2,2)
# obj.get(1)
# obj.put(3,3)
# obj.get(2)
# obj.put(4,4)
# obj.get(1)
# obj.get(3)
# obj.get(4)

# print(obj.space)

class struct_files:
    
    def __init__(self, head, nums, tail, order, origin):
        self.head: str = head
        self.nums: int = nums
        self.tail: str = tail
        self.order: int = order
        self.origin: str = origin
    
    def __repr__(self):
        return f"head: {self.head}, nums: {self.nums}, tail: {self.tail}, order: {self.order}"

    
def find_num_idx(names):
    nums_list = "0123456789"
    nums_idx_list = []
    start_bool = False
    
    for idx in range(len(names)):
        s = names[idx]
        if s in nums_list:
            nums_idx_list.append(idx)
            if idx != len(names)-1 and names[idx+1] not in nums_list:
                return nums_idx_list
    return nums_idx_list

def solution(files):
    answer = []
    process_list = []
        
    for order, file in enumerate(files):
        #여기서 아이디어를 낼 것. 어떻게 head랑 number를 쪼갤것이냐

        # Parsing
        nums_idx = find_num_idx(file)
        # print(nums_idx)
        
        num_start_idx = nums_idx[0]
        num_end_idx = num_start_idx+1
        
        if len(nums_idx) != 1:
            num_end_idx = nums_idx[-1]+1

        # head, number, tail 분해 완료
        #HEAD부분을 기준으로 사전 순으로 정렬 대소문자 구분하지 않는다. lowercase로 바꾸던 해야함.
        head = file[0:num_start_idx].lower()
        number = int(file[num_start_idx: num_end_idx])
        tail = file[num_end_idx:]
        
        # 객체화
        file_instance = struct_files(head, number, tail, order, file)
        
        # 자료구조는 다 만들었음
        process_list.append(file_instance)
        
    #HEAD가 동일할 경우 NUMBER를 기준으로 정렬한다. (정렬)
    #문자에서 01 012 1 12 다르게 정렬된다. 맨 앞에 0이 있으면 그것부터 접두어로 처리됨.
    #두 파일의 HEAD, Number가 같을 경우 그냥 그대로 유지한다.
    process_list.sort(key=lambda x: (x.head, x.nums, x.order))
    
    for task in process_list:
        # print(task)
        # print(task.origin)
        answer.append(task.origin)

    return answer

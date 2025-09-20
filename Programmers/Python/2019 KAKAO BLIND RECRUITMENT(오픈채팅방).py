# 순서를 지키면서 어떻게 닉네임을 한 번에 바꾸는거지. (상태 관리 map)
def solution(record):
    
    user_map = {}
    msg_list = []
    final_answer = []
    
    '''
    자료구조는 이렇게 가져가자.
    user_map = {
        {id} : {user_nick}
    }
    
    msg_list = [[id, msg]]
    '''
    
    for idx, msg in enumerate(record):
        
        # 명령 쪼개기
        split_msg = msg.split(" ")

        action = split_msg[0]
        user_id = split_msg[1]
        
        # leave action은 길이가 2이므로 
        if len(split_msg) == 3:
            user_nick = split_msg[2]
        
        if len(split_msg) == 3:
            user_map[user_id] = user_nick
        msg_list.append([user_id, action])
            
    
    for msg in msg_list:
        
        user_id, action = msg
        user_nick = user_map[user_id]
        
        if action == "Enter":
            final_answer.append(user_nick + "님이 들어왔습니다.")

        elif action == "Leave": # Leave
            final_answer.append(user_nick + "님이 나갔습니다.")
        
    return final_answer

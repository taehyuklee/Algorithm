def solution(bridge_length, weight, truck_weights):
    
    print(bridge_length, weight, truck_weights)
    
    bridge_list = [0 for i in range(bridge_length)]
    
    
    time = 0
    
    while(bridge_list):

        #truck이 더 추가될게 없다면 계속 빼게되고 bridge_list는 없어질 것.
        bridge_list.pop(0)

        #pop된 truck이 현재 bridge와 합쳐져서 무게가 reference weight보다 높 or 낮
        if truck_weights: #list에 truck이 남아있을때만 더 추가하던 0을 추가하던 함
            total_weight = sum(bridge_list) + truck_weights[0]
            
            if total_weight <= weight:
                truck = truck_weights.pop(0)
                bridge_list.append(truck)

            else:
                bridge_list.append(0)

        time +=1

    answer = time
    return answer

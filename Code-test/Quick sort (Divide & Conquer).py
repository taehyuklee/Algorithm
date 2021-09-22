#Quick sort (Divide & Conquer Average Complexity (NlogN))

def quick_sort(array, start_index, end_index):
    
    forward_i = start_index+1
    
    backward_i = end_index #len(array)-1   
    
    
    if start_index >= end_index: #or (backward_i-forward_i)==1 or (backward_i-forward_i)==0
        return    

    pivot = array[start_index] #참고로 잘라서 쓸거면 0과 len(array)로 start end쓰는게 맞겠지만 그게 아니라면 정해줘야함.

    while True:
        while True:    
            
            if forward_i >= end_index: #len(array)-1: #Stop조건이 위로 올라가야함
                break           
                
            if pivot < array[forward_i]:
                index_1 = forward_i  
                break
     
            forward_i += 1 
                
            #forward_i += 1 #여기에 있으면 end_index가 되어도 증가하니까 여기 있으면 안됨
 
    
        while True:

            if backward_i < start_index+1: #앞에서 stop criteria
                break                
            
            if pivot > array[backward_i]:
                index_2 = backward_i
                
                break
  
            backward_i -= 1

        #AH index이용해서 역으로 되는걸 막으면 되는구나
        if forward_i >= backward_i: #이미 위에서 순서가 바뀜 그래서 backward가 forward자리로 오게 됨 
            array[start_index], array[backward_i] = array[backward_i], array[start_index]
            mid_index = backward_i
            break

        #swap
        else: array[index_1], array[index_2] = array[index_2], array[index_1] 
        
  
    left_f = start_index; left_b = mid_index-1
    quick_sort(array, left_f,left_b)
    right_f = mid_index+1; right_b = end_index #len(array) #아 이거 계속 10으로 박히는구나 array를 그대로 보내니까.. (잘라서 보내는게 아님)
    quick_sort(array, right_f, right_b)
    
    return array    

    
#array = [5,7,9,0,3,1,6,2,4,8]

array = list(map(int, input().split()))

start_index = 0
end_index = len(array)-1


print("Input")
print(array)

array = quick_sort(array, start_index, end_index)

print("Output")
print(array)
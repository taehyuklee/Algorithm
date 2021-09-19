#quicksort
import numpy as np 
import random
import matplotlib.pyplot as plt

swap = np.zeros([1], dtype = np.int32)
count = 0

def quickSort(array, s, e):
    
    global count
    
    count +=1  
    if(s<e):
        
        p = partition(array[:], s, e)
        
        quickSort(array[:], s, p-1) #Pivot 기준으로 leftside  
        quickSort(array[:], p+1, e) #Pivot 기준으로 rightside


    return count

        
def partition(array, s, e):
    
    pivot = array[e] #가장 오른쪽에 있는걸 pivot으로
    i = (s-1)
    
    for j in range (s, e):
        
        if(array[j] <= pivot):
            i += 1
            
            swap[:] = array[j]
            array[j] = array[i]
            array[i] = swap[:]
            
            #print(array[i]); print(array[j])
    
    swap[:] = array[e]
    array[e] = array[i+1]
    array[i+1] = swap[:]   

    return (i+1)
    

count_array = np.zeros([5], dtype = np.float64)
x = np.zeros([5], dtype = np.float64)
func1 = np.zeros([5], dtype = np.float64)
func2 = np.zeros([5], dtype = np.float64)

#for graph
x = np.array([10**2,3*10**2 ,5*10**2, 7*10**2, 9*10**2])   
x_square = x*np.log(x)
func_1 = 0.1*x_square
func_2 = 1*x_square

array = np.arange(10**2); random.shuffle(array)
s = 0; e = array.shape[0]-1; count = quickSort(array, s, e)
count_array[0] = count

array = np.arange(3*10**2); random.shuffle(array)
s = 0; e = array.shape[0]-1; count = quickSort(array, s, e)
count_array[1] = count

array = np.arange(5*10**2); random.shuffle(array)
s = 0; e = array.shape[0]-1; count = quickSort(array, s, e)
count_array[2] = count

array = np.arange(7*10**2); random.shuffle(array)
s = 0; e = array.shape[0]-1; count = quickSort(array, s, e)
count_array[3] = count

array = np.arange(9*10**2); random.shuffle(array)
s = 0; e = array.shape[0]-1; count = quickSort(array, s, e)
count_array[4] = count


#print(array)

#Plot graph to check Big theta#
plt.title("count complexity")
plt.plot(x, count_array)
plt.plot(x,func_1);plt.plot(x,func_2)
plt.grid()
plt.show()

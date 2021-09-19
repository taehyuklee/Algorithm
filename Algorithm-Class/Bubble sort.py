#Bubblesort
import numpy as np 
import random
import matplotlib.pyplot as plt


def BubbleSort(array, count):
    for i in range(len(array) - 1, 0, -1):
        for j in range(i):
            if array[j] > array[j + 1]:
                array[j], array[j + 1] = array[j + 1], array[j]
                
            count +=1
            
    return count
    
    
count_array = np.zeros([5], dtype = np.float64)
x = np.zeros([5], dtype = np.float64)
func1 = np.zeros([5], dtype = np.float64)
func2 = np.zeros([5], dtype = np.float64)

#for graph
x = np.array([10**2,3*10**2 ,5*10**2, 7*10**2, 9*10**2])   
x_square = x**2
func_1 = 0.1*x_square
func_2 = 1*x_square
    
    
#first array    
array = np.arange(10**2); random.shuffle(array) #shuffle array

count = 0; count = BubbleSort(array, count); count_array[0] = count

#second array
array = np.arange(3*10**2); random.shuffle(array) #shuffle array

count = 0; count = BubbleSort(array, count); count_array[1] = count


#third array
array = np.arange(5*10**2); random.shuffle(array) #shuffle array

count = 0; count = BubbleSort(array, count); count_array[2] = count

#fourth array
array = np.arange(7*10**2); random.shuffle(array) #shuffle array

count = 0; count = BubbleSort(array, count); count_array[3] = count

#fifth array
array = np.arange(9*10**2); random.shuffle(array) #shuffle array

count = 0; count = BubbleSort(array, count); count_array[4] = count

random.shuffle(array)

#print(array)    
count = BubbleSort(array, count)


#Plot graph to check Big theta#
plt.title("count complexity")
plt.plot(x,count_array)
plt.plot(x,func_1);plt.plot(x,func_2)
plt.grid()
plt.show()
#plt.savefig('count complexity.png')
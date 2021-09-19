#Closest pair of points (Divde and conquer)
import numpy as np 
import random
import matplotlib.pyplot as plt

count = 0


def Brute_force(points,n):
    
    median = np.empty(0)
    median = np.reshape(median, (0,1))
    
    for i in range(n):
        for j in range(i+1,n):
            median = np.append(median, get_distance(points[i,:], points[j,:]))
    
    small_distance = np.min(median[:])
    
    
    return small_distance
    

def get_distance(p1,p2):
    
    distance =  np.sqrt((p2[0] - p1[0])**2 +(p2[1] - p1[1])**2)
    
    return distance


def Closest_Algorithm(points, n):
    
    global count
    
    count +=1
    
    Pl = np.empty(0); Pr = np.empty(0)
    Pl = np.reshape(Pl, (0,2)); Pr = np.reshape(Pr, (0,2))
    
    if n<=3:
        distance_c = Brute_force(points, n)
        _=0
        return distance_c, _

    mid_index = n//2 #index상으로 mid point
    mid_point = points[mid_index,0] #mid_points
    
    #mid point를 기준으로 왼쪽 오른쪽으로 나눠서 Pl, Pr을 만들어낸다
    for i in range(n): 
        if points[i,0]<mid_point:
            Pl = np.append(Pl, points[i:i+1,:], axis=0)
            count+=1
        else:
            Pr = np.append(Pr, points[i:i+1,:], axis=0)
            count+=1

    
    
    #x축에 관한거 (axis=0인 부분)
    Pl_point = np.sort(Pl, axis=0)
    Pr_point = np.sort(Pr, axis=0)

    
    #n개의 Point가 들어옴
    d_l, _ = Closest_Algorithm(Pl_point, len(Pl_point[:,0]))
    d_r, _ = Closest_Algorithm(Pr_point, n - len(Pl_point[:,0]))  


    #d_l, d_r중 작은 거리를 d로 정의한다.
    d = min(d_l, d_r)  
    
    #strip Part
    strip = np.empty(0); strip = np.reshape(strip, (0,2))
    strip_s =np.empty(0); strip_s = np.reshape(strip_s, (0,1))

    #d보다 작은 것들을 모두 strip array안에 넣는다.
    for i in range(len(Pl)):
        if abs(Pl_point[i,0] - mid_point) < d:
            strip = np.append(strip, Pl_point[i:i+1,:], axis=0)
            
    for i in range(len(Pr)):
        if abs(Pr_point[i,0] - mid_point) < d:
            strip = np.append(strip, Pr_point[i:i+1,:], axis=0)


    
    for i in range(len(strip[:,0])):
        for j in range(i+1,len(strip[:,0])):
            if get_distance(strip[i,:], strip[j,:])<d:
                strip_s = np.append(strip_s, get_distance(strip[i,:], strip[j,:]))
                
                count +=1
    
    if len(strip_s) != 0: 
        strip_small = min(strip_s[:])
        smallest = min(d, strip_small)
        
    else:
        smallest = d

    
    return smallest, count
    

    
#---------------------- Random points -------------------------#
#nums of input
num_input = 1*10**3

np.random.seed(seed=1000) #seed 결정해놓기
random.seed(1000) #seed 결정해놓기

#x,y domain from 0 to 10 (x, y random_uniform from Gaussian distribution)
x = np.random.uniform(0,10,[num_input])
y = np.random.uniform(0,10,num_input)

#print(x)

#define points memory
point = np.zeros([num_input,2], dtype = np.float32)

#x,y values to points
for i in range(num_input):
    point[i,0] = x[i]
    point[i,1] = y[i]

    
#Check the point
#print(point[3,:])

#for random choice (from all points)
rand_array = np.arange(num_input)
random.shuffle(rand_array)

median = np.empty(0)
median = np.reshape(median, (0,1))


smallest_distance, count = Closest_Algorithm(point, num_input)
#---------------------------------------------------------------#
print("Input size is {}".format(num_input))
print("smallest_distance is {}".format(smallest_distance))
print("count_number is {}".format(count))

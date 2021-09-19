#Quick-hull algorithm
import numpy as np
import random
import matplotlib.pyplot as plt

#distance line & point
'''
d = abs(ax1 + by1 + c)/sqrt(a**2+b**2)
line: ax+by+c = 0, point= (x1, y1)
line: if (x2, y2), (x3, y3) y - y2 = (y3-y2)/(x3-x2)*(x-x2)
according above: y = (y3-y2)/(x3-x2)*x -(y3-y2)/(x3-x2)*x2 + y2
(x3-x2)*y = (y3-y2)*x - (y3-y2)*x2 + (x3-x2)*y2
as result (y3-y2)*x - (x3-x2)*y - (y3-y2)*x2 + (x3-x2)*y2 == ax + by + c = 0

a = (y3-y2), b = - (x3-x2), c = - (y3-y2)*x2 + (x3-x2)*y2

if point is located left of line <0
   located right of line >0

'''

stack_hull = np.empty(0)
stack_hull = np.reshape(stack_hull, (0,2)) 
count = 0


def point_sort(point, num_input, direction):
    #print("point sorting")
    
    point_sorted = np.zeros([num_input,2], dtype = np.float32)

    if direction == "x":
    
        #print(point[:,:])
        #print(np.sort(point[:,0:1], axis=0))

        p_x_s = np.sort(point[:,0:1], axis=0)

        for i in range(num_input):
            for j in range(num_input):
                if p_x_s[i] == point[j,0]:
                    point_sorted[i,1] = point[j,1]

        point_sorted[:,0:1] = np.sort(point[:,0:1], axis=0)
        #print(point_sorted)
    
    elif direction == "y":
    
        print(point[:,:])
        #print(np.sort(point[:,0:1], axis=0))

        p_x_s = np.sort(point[:,1:2], axis=0)

        for i in range(num_input):
            for j in range(num_input):
                if p_x_s[i] == point[j,1]:
                    point_sorted[i,0] = point[j,0]

        point_sorted[:,1:2] = np.sort(point[:,1:2], axis=0)
        #print(point_sorted)
        
    #print("sorting complete")
        
    return point_sorted


def divide(P_left, P_right, point, num_input):
    
    global count
    
    upper = np.empty(0); lower = np.empty(0)
    upper = np.reshape(upper, (0,2)); lower = np.reshape(lower, (0,2))     

    #if (x2,y2) & (x3,y3)
    x2 = P_left[0,0]; y2 = P_left[0,1]
    x3 = P_right[0,0]; y3 = P_right[0,1]
    
    a = y3 - y2; b = -(x3-x2)
    c = -(y3-y2)*x2 + (x3-x2)*y2
    
    #ax + by + c = 0
    #a = (y3-y2), b = - (x3-x2), c = - (y3-y2)*x2 + (x3-x2)*y2
    for i in range(num_input): 
        if a*point[i,0]+b*point[i,1]+c <0:
            upper = np.append(upper, point[i:i+1,:], axis=0)
        elif a*point[i,0]+b*point[i,1]+c >0:
            lower = np.append(lower, point[i:i+1,:], axis=0)
            
        count = count+1
            
    upper = np.append(upper, P_left, axis=0); upper = np.append(upper, P_right, axis=0)
    lower = np.append(lower, P_left, axis=0); lower = np.append(lower, P_right, axis=0)
    
    return upper, lower


def find_max(P_left, P_right, d_point):
    
    global stack_hull, count
    
    d = np.zeros([len(d_point[:,0])], dtype = np.float32)
    
    x2 = P_left[0,0]; y2 = P_left[0,1]
    x3 = P_right[0,0]; y3 = P_right[0,1]
    
    a = y3 - y2; b = -(x3-x2)
    c = -(y3-y2)*x2 + (x3-x2)*y2 
    
    #print(d_point[0,1]) #check of divided_point coordinate
    
    for i in range(len(d_point[:,0])):
        d[i] = abs(a*d_point[i,0] + b*d_point[i,1] + c)/np.sqrt(a**2+b**2)
    
    #print(d)
    #print(np.max(d[:]))
    
    for i in range(len(d_point[:,0])):
        if np.max(d[:]) == d[i]:
            P_max = d_point[i:i+1,:]
            
        count = count+1
            
    stack_hull = np.append(stack_hull, P_max, axis=0)
    
    
    return P_max


    
def quick_hull_L(upper, num_input):
    
    global stack_hull, count
    
    count = count+1
    
    #만약 두개만 남으면 그 두개를 선택하면 됨
    #print(num_input)
    if num_input <=2:
        return None
    
    point_sorted_x = point_sort(upper, num_input, direction="x")
    
    #get left_most, Right_most
    P_left = point_sorted_x[0:1,:]
    #print(P_left)
    P_right = point_sorted_x[num_input-1:num_input,:]

    #Check of divide
    #print("upper"); print(upper.shape)
    #print("lower"); print(lower.shape)    
    
    if len(upper[:,0])==0:
        return None
    
    P_max = find_max(P_left, P_right, upper)
    #print("P_max")
    #print(P_max)
    

    left_u, _ = divide(P_left, P_max, upper, len(upper[:,0]))
    #print(left_u)
    #print(len(left_u[:,0]))
    quick_hull_L(left_u,len(left_u[:,0]))    

    _, right_u = divide(P_right,P_max, upper, len(upper[:,0]))  

    quick_hull_L(right_u,len(right_u[:,0]))
    


    return None


def quick_hull_R(lower, num_input):
    
    global stack_hull, count
    
    count = count+1
    
    #만약 두개만 남으면 그 두개를 선택하면 됨
    if num_input <=2:
        #print("pass")
        #print(stack_hull)
        return None
    
    
    #point sort - x-direction
    point_sorted_x = point_sort(lower, num_input, direction="x")
    
    #get left_most, Right_most
    P_left = point_sorted_x[0:1,:]
    #print(P_left)
    P_right = point_sorted_x[num_input-1:num_input,:]
    #print(P_right)
    

    #stack_hull = np.append(stack_hull, P_left, axis=0)
    #stack_hull = np.append(stack_hull, P_right, axis=0)
    
    #upper, lower = divide(P_left, P_right, point, num_input)
    #이게 계속 나눠지면서 문제가 
     
    
    if len(lower[:,0])==0:
        return None
    
    P_max = find_max(P_right, P_left,  lower)
    #print("P_max")
    #print(P_max)
    

    left_l, _ = divide(P_max,P_left,  lower, len(lower[:,0]))
    #print(left_u)
    #print(len(left_u[:,0]))
    quick_hull_R(left_l,len(left_l[:,0]))    

    
    _, right_l = divide(P_max, P_right,lower, len(lower[:,0]))  

    quick_hull_R(right_l,len(right_l[:,0]))


    return None



    
#---------------------- Random points -------------------------#
#nums of input
num_input = 1*10**3

np.random.seed(seed=500) #seed 결정해놓기
random.seed(500) #seed 결정해놓기

#x,y domain from 0 to 10 (x, y random_uniform from Gaussian distribution)
x = np.random.uniform(0,10,[num_input])
y = np.random.uniform(0,10,num_input)

#print(x)
#for random choice (from all points)
index = np.arange(num_input)
#random.shuffle(rand_array)

#define points memory
point = np.zeros([num_input,2], dtype = np.float32)

#x,y values to points
for i in range(num_input):
    point[i,0] = x[i]
    point[i,1] = y[i]
    
plt.title("2-D Cartesian full")
plt.plot(point[:,0],point[:,1],'.')
plt.grid()
plt.show()

    
point_m = point[:]
#sorted_1 = point_sort(point, num_input, direction="x") 
#print(sorted_1)

#point sort - x-direction
point_sorted_x = point_sort(point, num_input, direction="x")
    
#get left_most, Right_most
P_left = point_sorted_x[0:1,:]
#print(P_left)
P_right = point_sorted_x[num_input-1:num_input,:]
#print(P_right)
    
stack_hull = np.append(stack_hull, P_left, axis=0)
stack_hull = np.append(stack_hull, P_right, axis=0)
    
upper, lower = divide(P_left, P_right, point, num_input)


#quickhull
quick_hull_L(upper, len(upper[:,0]))
quick_hull_R(lower, len(lower[:,0]))
    
#sort point (reamin coupling)




#---------------------------------------------------------------#


plt.title("2-D Cartesian convex_hull")
plt.plot(stack_hull[:,0],stack_hull[:,1],'.')
plt.grid()
plt.show()


print(count)

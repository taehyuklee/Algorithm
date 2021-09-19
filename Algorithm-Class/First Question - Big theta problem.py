#First Question - Big theta problem
import numpy as np 
import matplotlib.pyplot as plt

n = 3000
x = np.zeros([n], dtype = np.float64)
func1 = np.zeros([n], dtype = np.float64)
func1_c1 = np.zeros([n], dtype = np.float64); func1_c2 = np.zeros([n], dtype = np.float64)
func2 = np.zeros([n], dtype = np.float64)
func2_c1 = np.zeros([n], dtype = np.float64); func2_c2 = np.zeros([n], dtype = np.float64)
func3 = np.zeros([n], dtype = np.float64)
func3_c1 = np.zeros([n], dtype = np.float64); func3_c2 = np.zeros([n], dtype = np.float64)
func4 = np.zeros([n], dtype = np.float64)
func4_c1 = np.zeros([n], dtype = np.float64); func4_c2 = np.zeros([n], dtype = np.float64)

#Initial condtion - correction
func1[0] = 1.0; func2[0] = 0.0; func4[0] = 1.0

for i in range(1,n):
    
    x[i] = i    
    func1[i] = (i**3+1)**6
    func1_c1[i] = 0.1*i**18; func1_c2[i] = 3.0*i**18
    
    func2[i] = (10*i**2 + 7*i + 3)**(0.5)
    func2_c1[i] = 0.1*10**(0.5)*i; func2_c2[i] = 3.0*10**(0.5)*i
    
    func3[i] = 2*i*np.log((i+2)**2) +(i+2)**2*np.log(i)
    func3_c1[i] = 0.1*(i**2 + 6*i + 4); func3_c2[i] = 10.0*(i**2 + 6*i + 4)
    
    func4[i] = func4[i-1] + 1/i #~ log function
    func4_c1[i] = 0.1*np.log(i); func4_c2[i] = 3.0*np.log(i)


#Plot graph to check Big theta#
plt.title("function - 1")
plt.plot(x,func1)
plt.plot(x,func1_c1);plt.plot(x,func1_c2)
plt.grid()
plt.show()
plt.savefig('function-1.png')

plt.title("function - 2")
plt.plot(x,func2)
plt.plot(x,func2_c1);plt.plot(x,func2_c2)
plt.grid()
plt.show()
plt.savefig('function-2.png')

plt.title("function - 3")
plt.plot(x,func3)
plt.plot(x,func3_c1);plt.plot(x,func3_c2)
plt.grid()
plt.show()
plt.savefig('function-3.png')

plt.title("function - 4")
plt.plot(x,func4)
plt.plot(x,func4_c1);plt.plot(x,func4_c2)

plt.show()
plt.savefig('function-4.png')
#------------------------------#
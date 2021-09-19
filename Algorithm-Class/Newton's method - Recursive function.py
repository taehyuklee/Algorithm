#Fourth Question - Newton's method (제곱근 구하기)
import numpy as np 

def iteration_newton(num, sol, epsilon):
    
    while(1):
        
        if abs(sol**(2) - num)<= epsilon:
            break
        else:
            sol[:] = (sol**2+num)/(2*sol)
            iteration_newton(num, sol, epsilon)
            
    return sol


epsilon = 10**(-12)
num = 5
sol = np.ones([1], dtype = np.float64) #initial condition  = 1

sol[:] = iteration_newton(num, sol, epsilon)

print(sol[:])
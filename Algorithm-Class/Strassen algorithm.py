import numpy as np 

#[Strassen's algorithm]
'''
A=[[A11,A12],[A21,A22]], B=[[B11,B12],[B21,B22]], C=[[C11,C12],[C21,C22]]

M1 = (A11 + A22)*(B11+B22), M2 = (A21 + A22)*B11
M3 = A11*(B12 - B22), M4 = A22*(B21 - B11)
M5 = (A11 + A12)*B22, M6 = (A21 - A11)*(B11 + B12)
M7 = (A12 - A22)*(B21 + B22)

C11 = M1 + M4 - M5 + M7
C12 = M3 + M5
C21 = M2 + M4
C22 = M1 - M2 + M3 + M6

'''

def Scalar_multi(A,B):

    C = A*B
    
    return C

def Divide(A,B,n):

    #Divide
    A11 = A[0:n//2,0:n//2]; A12 = A[0:n//2,n//2:n]
    A21 = A[n//2:n,0:n//2]; A22 = A[n//2:n,n//2:n]
    
    B11 = B[0:n//2,0:n//2]; B12 = B[0:n//2,n//2:n]
    B21 = B[n//2:n,0:n//2]; B22 = B[n//2:n,n//2:n]
    
    return A11, A12, A21, A22, B11, B12, B21, B22



def get_sol(M1, M2, M3, M4, M5, M6, M7):

    M1 = np.array(M1); M2 = np.array(M2); M3 = np.array(M3); M4 = np.array(M4)
    M5 = np.array(M5); M6 = np.array(M6); M7 = np.array(M7)
    
    C11 = M1 + M4 - M5 + M7
    C12 = M3 + M5
    C21 = M2 + M4
    C22 = M1 - M2 + M3 + M6     
    
    C_r1 = np.concatenate((C11, C12), axis=1)
    C_r2 = np.concatenate((C21, C22), axis=1)
    C_full = np. concatenate((C_r1, C_r2), axis = 0)
    
    return C_full
    

def Matmul_strassen(A,B,n):
    
    #7개 곱에 대한 해를 다 구해야 하니까 모두 이렇게 구해야한다.
    if n <= 1:
        return Scalar_multi(A,B)  

    #Divide
    A11, A12, A21, A22, B11, B12, B21, B22 = Divide(A,B,n)
    
    #step1: 4,4,4 (1개) -> 2,2,4
    #step2: 2,2,2 (4개) -> 1,1,2
    #step3: 1,1 -(16개) -> 2,2
    #step4: 2,2 -> 4,4    
    #get solution 
    #내부적으로 어떻게 돌아가는지 다 생각하면 머리 아프다. 
    #의미만으로 Recursive algorithm을 짜보자 
    
    #Divide한 이후에 각 Matrix에 대한 해를 또 구해야하고 Strassen이 Matix해를 구하는 거니까
    #Divided matrix에 대한 것도 M1~M7구해서 Solution을 구해야함
    M1 = Matmul_strassen(A11 + A22 , B11+B22, n//2)    
    M2 = Matmul_strassen((A21 + A22) , B11, n//2)  
    M3 = Matmul_strassen(A11 , (B12 - B22), n//2)
    M4 = Matmul_strassen(A22 , (B21 - B11), n//2)          
    M5 = Matmul_strassen((A11 + A12) , B22, n//2)        
    M6 = Matmul_strassen((A21 - A11), (B11 + B12), n//2)    
    M7 = Matmul_strassen((A12 - A22) ,(B21 + B22), n//2)    
    
    C = get_sol(M1, M2, M3, M4, M5, M6, M7)
    
    return C
    


A = np.zeros([4,4], dtype = np.float32)
B = np.zeros([4,4], dtype = np.float32)

A[:] = np.array([[1,0,2,1],[4,1,1,0],[0,1,3,0], [5,0,2,1]])
B[:] = np.array([[0,1,0,1],[2,1,1,4],[2,0,1,1], [1,3,5,0]])

print(A); print(B)

D = np.matmul(A,B) #Solution

n=4

C = Matmul_strassen(A,B,n)
print(D) #Exact solution
print(C) #Strassen's algorithm solution
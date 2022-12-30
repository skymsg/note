import numpy as np
m = np.random.randint(0,500,size = (200,201))
n = np.random.randint(0,500,size = (201,20000))
z = np.matmul(m,n)
print(z)


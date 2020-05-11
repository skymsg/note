import time
import random
count=0
for i in range(10000):
    time.sleep(random.random())
    count+=1
print(count)

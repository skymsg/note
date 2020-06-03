from concurrent.futures import ProcessPoolExecutor , wait
import pandas as pd
from pymongo import InsertOne
import time

executor = ProcessPoolExecutor(max_workers=100)

def task(msg,df):
    print(f"{msg} start!")
    print("df",df)
    time.sleep(5)
    print(f"{msg} end!")
    return f"{msg} done!"

def print_result(future):
    result = future.result()
    print(result)

def main():
    df = pd.DataFrame(data=[[1,1,1],[1,2,2],[2,1,1],[2,2,2],[3,1,1]])
    df.columns=['user_id','item_id','rank']
    op_list = list(map(lambda g:  InsertOne({'user_id':int(g[0]), 'item_ids' : g[1].item_id.values.tolist()}),df.groupby(['user_id'])))
    for i in range(100):
        executor.submit(task,f"task_{i}",op_list).add_done_callback(print_result)
    executor.shutdown()

if __name__ == "__main__":
    start = time.time()
    main()
    end =  time.time()
    print(f"total delay:{end-start}")

from concurrent.futures import ProcessPoolExecutor , wait
import time

executor = ProcessPoolExecutor(max_workers=100)

def task(msg):
    print(f"{msg} start!")
    time.sleep(1)
    print(f"{msg} end!")
    return f"{msg} done!"

def print_result(future):
    result = future.result()
    print(result)

def main():
    for i  in range(100):
        executor.submit(task,"task"+str(i)).add_done_callback(print_result)
    executor.shutdown()

if __name__ == "__main__":
    start = time.time()
    main()
    end =  time.time()
    print(f"total delay:{end-start}")

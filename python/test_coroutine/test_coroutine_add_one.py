import asyncio
import random

count = 0

async def add_one():
    global count
    await asyncio.sleep(random.random())
    count += 1

async def main():
    await asyncio.gather( *[ add_one() for i in range(10000) ]  )
    print(count)

asyncio.run(main())

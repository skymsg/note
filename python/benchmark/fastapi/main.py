from fastapi import FastAPI
from redisUtil import RedisUtil
app = FastAPI()




@app.get("/hello")
def hello(name:str):
    return {"msg":f"hello {name}"}


@app.get("/redis")
async def getRedis(key:str):
    res = await RedisUtil.instance().get(key)
    return {"msg":f"{res}"}
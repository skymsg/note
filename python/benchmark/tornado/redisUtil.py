from  aredis import StrictRedis

class RedisUtil(object):

    _instance = None

    @classmethod
    def instance(cls):
        if not cls._instance:
            cls._instance = cls()
        return cls._instance


    def __init__(self):
        print("创建RedisUtil实例")
        self._redis_conn = StrictRedis(host='localhost',port=6379,decode_responses=True)
    
    async def get(self,key):
        res = await self._redis_conn.get(key)
        return res
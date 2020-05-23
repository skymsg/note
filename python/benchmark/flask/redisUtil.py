from redis import StrictRedis

class RedisUtil(object):

    _instance = None
    @classmethod
    def instance(cls):
        if cls._instance is None:
            cls._instance = cls()
        return cls._instance
    
    def __init__(self):
        self._conn = StrictRedis(host="localhost",port=6379,decode_responses=True)

    def get(self,key):
        return self._conn.get(key)
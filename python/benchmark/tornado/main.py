import os
import sys
import tornado
from tornado.web import RequestHandler,Application
from tornado.httpserver import HTTPServer
from tornado.ioloop import IOLoop
import json
from tornado.platform.asyncio import AsyncIOMainLoop
import asyncio
import uvloop
from redisUtil import RedisUtil


class HelloHandler(RequestHandler):
    def get(self):
        name = self.get_argument("name")
        res = {"msg":f"hello {name}"}
        self.set_header("Content-Type","application/json")
        self.write(json.dumps(res,ensure_ascii=False))
    

class RedisHandler(RequestHandler):
    async def get(self):
        key = self.get_argument("key")
        res = await RedisUtil.instance().get(key)
        self.set_header("Content-Type","application/json")
        self.write(json.dumps({"msg":f"{res}"},ensure_ascii=False))

if __name__ == "__main__":
    num_processes = os.cpu_count()
    if len(sys.argv) > 1:
        num_processes = int(sys.argv[1])
    app = Application([
        ('/hello',HelloHandler),
        ('/redis',RedisHandler)
    ])
    server = HTTPServer(app)
    server.bind(port=8000)
    server.start(num_processes=num_processes)
    print("tornado started")
    IOLoop.current().start()


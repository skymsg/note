# tornado keep-alive
## tornado官网对keep-alive的说明
```
HTTPServer supports keep-alive connections by default (automatically for HTTP/1.1, or for HTTP/1.0 when the client requests Connection: keep-alive).
```
tornado的http server 默认支持长连接 (HTTP/1.1 协议自动为长连接   HTTP/1.0协议 需要在请求头中配置 Connection: keep-alive)
## 使用curl 构造请求验证

### 打印tornado收到的请求
首先将tornado收到的请求的http协议版本 server连接实例  请求头  打印出来
```python3 
  async def get(self):
        print(self.request)
        print("sever_connection:\n%s" % self.request.server_connection)
        print("request_time:\n%s" % self.request.request_time())
        print("header:\n%s" % self.request.headers)
        self.out(1)
```
### curl 使用 HTTP1.1协议 发送请求
curl 默认情况下使用HTTP1.1 协议 因此不指定使用HTTP1.0 的情况下 , 使用curl 请求 tornado 应该复用连接
使用curl 同时发出多个请求 查看是否复用了连接
```
curl -v 'http://127.0.0.1:1000/_health' 'http://127.0.0.1:1000/_health'
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.1
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 12:55:07 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
<
* Connection #0 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1}* Found bundle for host 127.0.0.1: 0x7fe2f42183d0 [can pipeline]
* Could pipeline, but not asked to!
* Re-using existing connection! (#0) with host 127.0.0.1
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.1
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 12:55:07 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
<
* Connection #0 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1}* Closing connection 0
```
可以看到第二次的请求出现了 Re-using existing connection! 说明第一次的连接没有关闭

查看 tornado 打印的日志 
```
HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.1', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119a539e8>
request_time:
0.0025680065155029297
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*
HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.1', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119a539e8>
request_time:
0.003062725067138672
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*
```
可以看到 收到的协议版本是HTTP/1.1  因此第一次请求完成后连接没有关闭,两次使用的连接实例都是0x119a539e8   

### curl 使用HTTP/1.0 协议发送请求
curl 发送请求时加上 --http1.0 强制使用HTTP/1.0协议 
```
curl --http1.0 -v 'http://127.0.0.1:1000/_health' 'http://127.0.0.1:1000/_health'

*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.0
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 13:13:13 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
<
* Connection #0 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1} * Found bundle for host 127.0.0.1: 0x7fdd1cd06180 [can pipeline]
* Re-using existing connection! (#0) with host 127.0.0.1
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.0
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
>
* Recv failure: Connection reset by peer
* Connection died, retrying a fresh connect
* Closing connection 0
* Issue another request to this URL: 'http://127.0.0.1:1000/_health'
* Hostname 127.0.0.1 was found in DNS cache
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#1)
> GET /_health HTTP/1.0
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 13:13:13 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
<
* Connection #1 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1}* Closing connection 1
```
可以看到第二次请求的日志 中 curl尝试复用第一次创建的0号连接 但是 tornado 已经把连接关闭了(Connection reset by peer ) 
因此又建立了一个新的1号连接
```
 Recv failure: Connection reset by peer
* Connection died, retrying a fresh connect
* Closing connection 0
* Issue another request to this URL: 'http://127.0.0.1:1000/_health'
* Hostname 127.0.0.1 was found in DNS cache
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#1)
```

tornado 打印的信息中发现 协议版本已经变成了 HTTP/1.0 
两次的连接实例0x119a53b70 0x119ab2860   已经是不同的了
说明第一次请求完成后tornado 就关闭了 和curl的连接
```
HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.0', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119a53b70>
request_time:
0.0024340152740478516
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*

HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.0', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119ab2860>
request_time:
0.002335071563720703
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*
```

### curl 使用HTTP/1.0 协议 请求头加上 Connection: keep-alive

curl 发送请求时加上 --http1.0 强制使用HTTP/1.0协议 
使用 -H 设置请求头

```
curl --http1.0  -H "Connection: keep-alive" -v 'http://127.0.0.1:1000/_health' 'http://127.0.0.1:1000/_health'
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.0
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
> Connection: keep-alive
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 14:24:56 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
< Connection: Keep-Alive
<
* Connection #0 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1}* Found bundle for host 127.0.0.1: 0x7fdf96f15a50 [can pipeline]
* Re-using existing connection! (#0) with host 127.0.0.1
* Connected to 127.0.0.1 (127.0.0.1) port 1000 (#0)
> GET /_health HTTP/1.0
> Host: 127.0.0.1:1000
> User-Agent: curl/7.64.1
> Accept: */*
> Connection: keep-alive
>
< HTTP/1.1 200 OK
< Server: TornadoServer/6.0.3
< Content-Type: application/json
< Date: Sat, 25 Apr 2020 14:24:56 GMT
< Etag: "2fc9adf058c7b65ebbd482e3f5445a3e18045baf"
< Content-Length: 40
< Connection: Keep-Alive
<
* Connection #0 to host 127.0.0.1 left intact
{"code": 0, "msg": "SUCCESS", "data": 1}* Closing connection 0
```
可以看到 HTTP1.0 加了 Connection: keep-alive 请求头之后 
第二次请求复用了第一次的连接

tornado 的日志 中也能看到 请求头中添加了Connection: keep-alive
而且第二次复用了第一次的连接实例
```
HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.0', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119b69f28>
request_time:
0.003483295440673828
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*
Connection: keep-alive
HTTPServerRequest(protocol='http', host='127.0.0.1:1000', method='GET', uri='/_health', version='HTTP/1.0', remote_ip='127.0.0.1')
sever_connection:
<tornado.http1connection.HTTP1ServerConnection object at 0x119b69f28>
request_time:
0.001657247543334961
header:
Host: 127.0.0.1:1000
User-Agent: curl/7.64.1
Accept: */*
Connection: keep-alive
```

## 结论
使用HTTP/1.1时, tornado 使用的是长连接
使用HTTP/1.0时, 加了Connection: keep-alive 使用长连接
使用HTTP/1.0时, 不加Connection: keep-alive 使用短连接
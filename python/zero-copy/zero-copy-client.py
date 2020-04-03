import socket
import os
import time

sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
server_address = ('127.0.0.1',10000)
sock.connect(server_address)
start = time.time()
try:
    with open("file_for_test","rb") as f:
        ret = 0
        offset = 0
        while True:
            ret = os.sendfile(sock.fileno(),f.fileno(),offset,65536)
            offset +=ret
            if ret ==0:
                break

finally:
    sock.close()
end = time.time()
print('total time',end-start)

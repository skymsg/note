import socket
import time

sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
server_address = ('127.0.0.1',10000)
sock.connect(server_address)
start = time.time()
try:
    with open("file_for_test","rb") as input_f:
        data  = input_f.read(1024*1000)
        while data:
            sock.sendall(data)
            data  = input_f.read(1024*1000)
finally:
    sock.close()
end = time.time()
print('total time',end-start)
